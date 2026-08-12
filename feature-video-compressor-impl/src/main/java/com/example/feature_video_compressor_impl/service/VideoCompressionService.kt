package com.example.feature_video_compressor_impl.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import android.os.SystemClock
import androidx.core.app.ServiceCompat
import com.example.feature_video_compressor_api.model.CompressionState
import com.example.feature_video_compressor_api.model.VideoLocation
import com.example.feature_video_compressor_impl.compressor.CompressionStateStore
import com.example.feature_video_compressor_impl.compressor.VideoCompressionState
import com.example.feature_video_compressor_impl.compressor.VideoCompressor
import com.example.feature_video_compressor_impl.domain.CompressionOutputStorage
import com.example.feature_video_compressor_impl.service.dto.CompressionSettingsParcel
import com.example.feature_video_compressor_impl.service.mappers.toDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@AndroidEntryPoint
internal class VideoCompressionService : Service() {

    @Inject
    lateinit var videoCompressor: VideoCompressor

    @Inject
    lateinit var stateStore: CompressionStateStore

    @Inject
    lateinit var notificationFactory:
        CompressionNotificationFactory

    @Inject
    lateinit var outputStorage:
        CompressionOutputStorage

    private val serviceScope =
        CoroutineScope(
            SupervisorJob() +
                Dispatchers.Main.immediate,
        )

    private var compressionJob: Job? = null

    private var currentTempFile: File? = null

    override fun onCreate() {
        super.onCreate()

        notificationFactory.createChannel()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        when (intent?.action) {
            ACTION_START -> {
                startCompression(intent)
            }

            ACTION_CANCEL -> {
                cancelCompression()
            }
        }

        return START_NOT_STICKY
    }

    override fun onBind(
        intent: Intent?,
    ): IBinder? = null

    override fun onDestroy() {
        compressionJob?.cancel()
        compressionJob = null

        serviceScope.cancel()

        super.onDestroy()
    }

    override fun onTimeout(
        startId: Int,
        fgsType: Int,
    ) {
        videoCompressor.cancel()

        compressionJob?.cancel()
        compressionJob = null

        deleteTempFile()

        stateStore.update(
            CompressionState.Failed(
                throwable = IllegalStateException(
                    "Video compression foreground service timed out",
                ),
            ),
        )

        finishService()
    }

    private fun startCompression(
        intent: Intent,
    ) {
        if (compressionJob?.isActive == true) {
            return
        }

        val inputUri = intent
            .getStringExtra(
                EXTRA_INPUT_LOCATION,
            )
            ?.let(Uri::parse)
            ?: run {
                stopSelf()
                return
            }

        val settingsParcel =
            intent
                .getParcelableExtraCompat<
                    CompressionSettingsParcel
                    >(
                    EXTRA_COMPRESSION_SETTINGS,
                )
                ?: run {
                    stopSelf()
                    return
                }

        val tempFile = try {
            outputStorage.newTempFile()
        } catch (throwable: Throwable) {
            stateStore.update(
                CompressionState.Failed(
                    throwable = throwable,
                ),
            )

            stopSelf()

            return
        }

        currentTempFile = tempFile

        startAsForeground()

        val startedAtMs =
            SystemClock.elapsedRealtime()

        compressionJob = serviceScope.launch {
            try {
                videoCompressor.compress(
                    inputUri = inputUri,
                    outputFile = tempFile,
                    settings = settingsParcel.toDomain(),
                ).collect { state ->
                    handleCompressionState(
                        state = state,
                        tempFile = tempFile,
                        startedAtMs = startedAtMs,
                    )
                }
            } catch (
                cancellation: CancellationException,
            ) {
                throw cancellation
            } catch (
                throwable: Throwable,
            ) {
                deleteTempFile()

                stateStore.update(
                    CompressionState.Failed(
                        throwable = throwable,
                    ),
                )

                notificationFactory.showFailed()

                finishService()
            } finally {
                compressionJob = null
            }
        }
    }

    private suspend fun handleCompressionState(
        state: VideoCompressionState,
        tempFile: File,
        startedAtMs: Long,
    ) {
        when (state) {
            VideoCompressionState.Preparing -> {
                stateStore.update(
                    CompressionState.Preparing,
                )

                notificationFactory.updateProgress(
                    notificationId =
                        NOTIFICATION_ID,
                    progress = 0,
                    indeterminate = true,
                )
            }

            is VideoCompressionState.Compressing -> {
                val progress =
                    state.progress.coerceIn(
                        0,
                        100,
                    )

                val remainingTimeMs =
                    estimateRemainingTime(
                        startedAtMs =
                            startedAtMs,
                        progress = progress,
                    )

                stateStore.update(
                    CompressionState.Compressing(
                        progress = progress,
                        remainingTimeMs =
                            remainingTimeMs,
                    ),
                )

                notificationFactory.updateProgress(
                    notificationId =
                        NOTIFICATION_ID,
                    progress = progress,
                    indeterminate = false,
                )
            }

            is VideoCompressionState.Completed -> {
                val outputUri =
                    outputStorage.publish(
                        tempFile = tempFile,
                    )

                currentTempFile = null

                stateStore.update(
                    CompressionState.Completed(
                        outputLocation =
                            VideoLocation(
                                value =
                                    outputUri.toString(),
                            ),
                        originalSizeBytes =
                            state.originalSizeBytes,
                        compressedSizeBytes =
                            state.compressedSizeBytes,
                    ),
                )

                notificationFactory.showCompleted()

                finishService()
            }

            is VideoCompressionState.Failed -> {
                deleteTempFile()

                stateStore.update(
                    CompressionState.Failed(
                        throwable =
                            state.throwable,
                    ),
                )

                notificationFactory.showFailed()

                finishService()
            }

            VideoCompressionState.Cancelled -> {
                deleteTempFile()

                stateStore.update(
                    CompressionState.Cancelled,
                )

                finishService()
            }
        }
    }

    private fun startAsForeground() {
        val notification =
            notificationFactory.progress(
                progress = 0,
                indeterminate = true,
            )

        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            notification,
            resolveForegroundServiceType(),
        )
    }

    private fun cancelCompression() {
        videoCompressor.cancel()

        compressionJob?.cancel()
        compressionJob = null

        deleteTempFile()

        stateStore.update(
            CompressionState.Cancelled,
        )

        finishService()
    }

    private fun deleteTempFile() {
        currentTempFile
            ?.takeIf(File::exists)
            ?.delete()

        currentTempFile = null
    }

    private fun finishService() {
        stopForeground(
            STOP_FOREGROUND_REMOVE,
        )

        stopSelf()
    }

    private fun estimateRemainingTime(
        startedAtMs: Long,
        progress: Int,
    ): Long? {
        if (
            progress <
            MIN_PROGRESS_FOR_ETA
        ) {
            return null
        }

        if (progress >= 100) {
            return 0L
        }

        val elapsedTimeMs =
            SystemClock.elapsedRealtime() -
                startedAtMs

        return (
            elapsedTimeMs *
                (100.0 - progress) /
                progress
            ).toLong()
    }

    private fun resolveForegroundServiceType(): Int {
        return if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.VANILLA_ICE_CREAM
        ) {
            ServiceInfo
                .FOREGROUND_SERVICE_TYPE_MEDIA_PROCESSING
        } else {
            ServiceInfo
                .FOREGROUND_SERVICE_TYPE_DATA_SYNC
        }
    }

    private inline fun <
        reified T : Parcelable,
        > Intent.getParcelableExtraCompat(
        key: String,
    ): T? {
        return if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {
            getParcelableExtra(
                key,
                T::class.java,
            )
        } else {
            @Suppress("DEPRECATION")
            getParcelableExtra(key)
        }
    }

    companion object {

        const val ACTION_START =
            "video_compression.action.START"

        const val ACTION_CANCEL =
            "video_compression.action.CANCEL"

        const val EXTRA_INPUT_LOCATION =
            "video_compression.extra.INPUT_LOCATION"

        const val EXTRA_COMPRESSION_SETTINGS =
            "video_compression.extra.COMPRESSION_SETTINGS"

        const val NOTIFICATION_ID =
            1001

        private const val MIN_PROGRESS_FOR_ETA =
            5
    }
}