package com.example.feature_video_compressor_impl.compressor

import android.content.Context
import android.net.Uri
import androidx.media3.common.Effect
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.effect.FrameDropEffect
import androidx.media3.effect.Presentation
import androidx.media3.transformer.DefaultEncoderFactory
import androidx.media3.transformer.EditedMediaItem
import androidx.media3.transformer.Effects
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.ProgressHolder
import androidx.media3.transformer.Transformer
import androidx.media3.transformer.VideoEncoderSettings
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.CompressionState
import com.example.feature_video_compressor_impl.calculator.CompressionSettingsResolver
import com.example.feature_video_compressor_impl.calculator.ResolvedCompressionSettings
import com.example.feature_video_compressor_api.model.VideoMetadata
import com.example.feature_video_compressor_api.usecase.VideoMetadataReader
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import android.media.MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR
import androidx.media3.transformer.Composition
import com.example.feature_video_compressor_impl.mappers.toMimeType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@UnstableApi
internal class Media3VideoCompressor @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val metadataReader: VideoMetadataReader,
    private val settingsResolver: CompressionSettingsResolver,
) : VideoCompressor {

    private var currentTransformer: Transformer? = null

    override fun compress(
        inputUri: Uri,
        outputFile: File,
        settings: CompressionSettings,
    ): Flow<VideoCompressionState> = callbackFlow {

        trySend(
            VideoCompressionState.Preparing,
        )

        val metadata = try {
            withContext(Dispatchers.IO) {
                metadataReader.read(inputUri)
            }
        } catch (throwable: Throwable) {
            trySend(
                VideoCompressionState.Failed(
                    throwable = throwable,
                ),
            )

            close()
            return@callbackFlow
        }

        val resolvedSettings = try {
            settingsResolver.resolve(
                metadata = metadata,
                settings = settings,
            )
        } catch (throwable: Throwable) {
            trySend(
                VideoCompressionState.Failed(
                    throwable = throwable,
                ),
            )

            close()
            return@callbackFlow
        }

        try {
            prepareOutputFile(
                outputFile = outputFile,
            )
        } catch (throwable: Throwable) {
            trySend(
                VideoCompressionState.Failed(
                    throwable = throwable,
                ),
            )

            close()
            return@callbackFlow
        }

        val effects = createEffects(
            metadata = metadata,
            settings = resolvedSettings,
        )

        val editedMediaItem =
            EditedMediaItem.Builder(
                MediaItem.fromUri(inputUri),
            )
                .setRemoveAudio(
                    !resolvedSettings.keepAudio,
                )
                .setEffects(
                    Effects(
                        emptyList(),
                        effects,
                    ),
                )
                .build()

        val videoEncoderSettings =
            VideoEncoderSettings.Builder()
                .setBitrate(
                    resolvedSettings.bitrate,
                )
                .build()

        val encoderFactory =
            DefaultEncoderFactory.Builder(
                context,
            )
                .setRequestedVideoEncoderSettings(
                    videoEncoderSettings,
                )
                .build()

        val listener =
            object : Transformer.Listener {

                override fun onCompleted(
                    composition: Composition,
                    exportResult: ExportResult,
                ) {
                    trySend(
                        VideoCompressionState.Completed(
                            originalSizeBytes =
                                metadata.sizeBytes,
                            compressedSizeBytes =
                                outputFile.length(),
                        ),
                    )

                    close()
                }

                override fun onError(
                    composition: Composition,
                    exportResult: ExportResult,
                    exportException: ExportException,
                ) {
                    trySend(
                        VideoCompressionState.Failed(
                            throwable =
                                exportException,
                        ),
                    )

                    /*
                     * Здесь специально close(), а не
                     * close(exportException).
                     *
                     * Failed уже отправлен как state.
                     * Если закрыть Flow с exception,
                     * Service после Failed дополнительно
                     * попадёт в catch и обработает одну
                     * ошибку второй раз.
                     */
                    close()
                }
            }

        val transformer =
            Transformer.Builder(context)
                .setVideoMimeType(
                    resolvedSettings.codec.toMimeType(),
                )
                .setEncoderFactory(
                    encoderFactory,
                )
                .addListener(
                    listener,
                )
                .build()

        currentTransformer = transformer

        try {
            transformer.start(
                editedMediaItem,
                outputFile.absolutePath,
            )
        } catch (throwable: Throwable) {
            if (
                currentTransformer === transformer
            ) {
                currentTransformer = null
            }

            trySend(
                VideoCompressionState.Failed(
                    throwable = throwable,
                ),
            )

            close()
            return@callbackFlow
        }

        /*
         * Transformer должен использоваться с одного
         * application thread.
         *
         * Service собирает этот Flow на Main.immediate,
         * поэтому progress также запрашиваем на Main,
         * а не на Dispatchers.Default.
         */
        val progressJob =
            launch(Dispatchers.Main.immediate) {
                val progressHolder =
                    ProgressHolder()

                var lastProgress = -1

                while (isActive) {
                    val progressState =
                        transformer.getProgress(
                            progressHolder,
                        )

                    if (
                        progressState ==
                        Transformer.PROGRESS_STATE_AVAILABLE
                    ) {
                        val progress =
                            progressHolder.progress
                                .coerceIn(
                                    0,
                                    100,
                                )

                        /*
                         * Не отправляем один и тот же
                         * процент каждые 250 мс.
                         */
                        if (
                            progress != lastProgress
                        ) {
                            lastProgress = progress

                            trySend(
                                VideoCompressionState.Compressing(
                                    progress = progress,
                                ),
                            )
                        }
                    }

                    delay(
                        PROGRESS_UPDATE_INTERVAL_MS,
                    )
                }
            }

        awaitClose {
            progressJob.cancel()

            if (
                currentTransformer === transformer
            ) {
                currentTransformer = null
            }
        }
    }

    override fun cancel() {
        currentTransformer?.cancel()
        currentTransformer = null
    }

    private fun createEffects(
        metadata: VideoMetadata,
        settings: ResolvedCompressionSettings,
    ): List<Effect> {
        return buildList {

            if (settings.shouldResize) {
                val targetShortSide =
                    minOf(
                        settings.width,
                        settings.height,
                    )

                add(
                    Presentation
                        .createForShortSide(
                            targetShortSide,
                        )
                        .copyWithUnsetSideRoundedTo(
                            VIDEO_SIZE_DIVISOR,
                        ),
                )
            }

            if (
                settings.shouldLimitFrameRate
            ) {
                val sourceFrameRate =
                    metadata.frameRate

                val targetFrameRate =
                    settings.frameRate

                if (
                    sourceFrameRate != null &&
                    targetFrameRate != null
                ) {
                    add(
                        FrameDropEffect
                            .createSimpleFrameDropEffect(
                                sourceFrameRate.toFloat(),
                                targetFrameRate.toFloat(),
                            ),
                    )
                }
            }
        }
    }

    private suspend fun prepareOutputFile(
        outputFile: File,
    ) {
        withContext(Dispatchers.IO) {
            val parentDirectory =
                outputFile.parentFile

            if (
                parentDirectory != null &&
                !parentDirectory.exists() &&
                !parentDirectory.mkdirs()
            ) {
                throw IOException(
                    "Failed to create output directory: " +
                        parentDirectory.absolutePath,
                )
            }

            if (
                outputFile.exists() &&
                !outputFile.delete()
            ) {
                throw IOException(
                    "Failed to delete existing output file: " +
                        outputFile.absolutePath,
                )
            }
        }
    }

    private companion object {

        const val PROGRESS_UPDATE_INTERVAL_MS =
            250L

        const val VIDEO_SIZE_DIVISOR =
            2
    }
}