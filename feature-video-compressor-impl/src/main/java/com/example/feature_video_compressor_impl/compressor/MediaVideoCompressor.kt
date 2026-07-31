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
import com.example.feature_video_compressor_impl.metadata.VideoMetadata
import com.example.feature_video_compressor_impl.metadata.VideoMetadataReader
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import android.media.MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR
import com.example.feature_video_compressor_impl.mappers.toMimeType

@UnstableApi
class Media3VideoCompressor(
    private val context: Context,
    private val metadataReader: VideoMetadataReader,
    private val settingsResolver: CompressionSettingsResolver,
) : VideoCompressor {

    private var currentTransformer: Transformer? = null

    override fun compress(
        inputUri: Uri,
        outputFile: File,
        settings: CompressionSettings,
    ): Flow<CompressionState> = callbackFlow {
        trySend(CompressionState.Preparing(inputUri))

        val metadata = try {
            metadataReader.read(inputUri)
        } catch (throwable: Throwable) {
            trySend(CompressionState.Failed(throwable))
            close()
            return@callbackFlow
        }

        val resolvedSettings = try {
            settingsResolver.resolve(
                metadata = metadata,
                settings = settings,
            )
        } catch (throwable: Throwable) {
            trySend(CompressionState.Failed(throwable))
            close()
            return@callbackFlow
        }

        outputFile.parentFile?.mkdirs()

        if (outputFile.exists()) {
            outputFile.delete()
        }

        val effects = createEffects(
            metadata = metadata,
            settings = resolvedSettings,
        )

        val editedMediaItem = EditedMediaItem.Builder(
            MediaItem.fromUri(inputUri),
        )
            .setRemoveAudio(!resolvedSettings.keepAudio)
            .setEffects(
                Effects(
                    emptyList(),
                    effects,
                ),
            )
            .build()

        val videoEncoderSettings = VideoEncoderSettings.Builder()
            .setBitrate(resolvedSettings.bitrate)
            .setBitrateMode(BITRATE_MODE_VBR)
            .build()

        val encoderFactory = DefaultEncoderFactory.Builder(context)
            .setRequestedVideoEncoderSettings(videoEncoderSettings)
            .build()

        val listener = object : Transformer.Listener {

            override fun onCompleted(
                composition: androidx.media3.transformer.Composition,
                exportResult: ExportResult,
            ) {
                trySend(
                    CompressionState.Completed(
                        outputFile = outputFile,
                        originalSizeBytes = getInputSize(inputUri),
                        compressedSizeBytes = outputFile.length(),
                    ),
                )

                close()
            }

            override fun onError(
                composition: androidx.media3.transformer.Composition,
                exportResult: ExportResult,
                exportException: ExportException,
            ) {
                trySend(
                    CompressionState.Failed(exportException),
                )

                close(exportException)
            }
        }

        val transformer = Transformer.Builder(context)
            .setVideoMimeType(resolvedSettings.codec.toMimeType())
            .setEncoderFactory(encoderFactory)
            .addListener(listener)
            .build()

        currentTransformer = transformer

        transformer.start(
            editedMediaItem,
            outputFile.absolutePath,
        )

        val progressJob = launch(Dispatchers.Default) {
            val progressHolder = ProgressHolder()

            while (isActive) {
                val progressState = transformer.getProgress(progressHolder)

                if (
                    progressState ==
                    Transformer.PROGRESS_STATE_AVAILABLE
                ) {
                    trySend(
                        CompressionState.Compressing(
                            progress = progressHolder.progress,
                        ),
                    )
                }

                delay(PROGRESS_UPDATE_INTERVAL_MS)
            }
        }

        awaitClose {
            progressJob.cancel()

            if (currentTransformer === transformer) {
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
                add(
                    Presentation.createForHeight(
                        settings.height,
                    ),
                )
            }

            if (settings.shouldLimitFrameRate) {
                add(
                    FrameDropEffect.createSimpleFrameDropEffect(
                        metadata.frameRate.toFloat(),
                        settings.frameRate.toFloat(),
                    ),
                )
            }
        }
    }

    private fun getInputSize(uri: Uri): Long? {
        return context.contentResolver
            .openAssetFileDescriptor(uri, "r")
            ?.use { descriptor ->
                descriptor.length.takeIf { it >= 0 }
            }
    }

    private companion object {
        const val PROGRESS_UPDATE_INTERVAL_MS = 250L
    }
}