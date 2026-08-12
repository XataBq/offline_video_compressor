package com.example.feature.video.compressor.ui.utils

import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoMetadata
import com.example.feature_video_compressor_api.model.VideoResolution
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.math.roundToLong

internal class CompressionEstimateCalculator @Inject constructor() {

    fun calculate(
        metadata: VideoMetadata?,
        settings: CompressionSettings,
    ): CompressionEstimate {
        metadata ?: return CompressionEstimate(
            estimatedSizeMb = null,
            savingsPercent = null,
        )

        val originalSizeBytes =
            metadata.sizeBytes
                ?.takeIf { it > 0L }
                ?: return CompressionEstimate(
                    estimatedSizeMb = null,
                    savingsPercent = null,
                )

        if (metadata.durationMs <= 0L) {
            return CompressionEstimate(
                estimatedSizeMb = null,
                savingsPercent = null,
            )
        }

        val targetSize =
            resolveTargetSize(
                sourceWidth = metadata.width,
                sourceHeight = metadata.height,
                resolution = settings.resolution,
            )

        val targetFrameRate =
            resolveTargetFrameRate(
                sourceFrameRate = metadata.frameRate,
                frameRate = settings.frameRate,
            )

        val calculatedVideoBitrate =
            (
                targetSize.width.toDouble() *
                    targetSize.height.toDouble() *
                    targetFrameRate *
                    settings.quality.bitsPerPixel
                ).toLong()

        val targetVideoBitrate =
            metadata.bitrate
                ?.takeIf { it > 0 }
                ?.let { sourceBitrate ->
                    minOf(
                        calculatedVideoBitrate,
                        sourceBitrate.toLong(),
                    )
                }
                ?: calculatedVideoBitrate

        val estimatedTotalBitrate =
            targetVideoBitrate +
                if (settings.keepAudio) {
                    ESTIMATED_AUDIO_BITRATE
                } else {
                    0L
                }

        val sourceEffectiveBitrate =
            originalSizeBytes *
                BITS_PER_BYTE *
                MILLIS_PER_SECOND /
                metadata.durationMs.toDouble()

        if (sourceEffectiveBitrate <= 0.0) {
            return CompressionEstimate(
                estimatedSizeMb = null,
                savingsPercent = null,
            )
        }

        val compressionRatio =
            (
                estimatedTotalBitrate /
                    sourceEffectiveBitrate
                )
                .coerceIn(
                    MIN_COMPRESSION_RATIO,
                    MAX_COMPRESSION_RATIO,
                )

        val estimatedSizeBytes =
            (
                originalSizeBytes *
                    compressionRatio
                ).toLong()

        val estimatedSizeMb =
            (
                estimatedSizeBytes /
                    BYTES_PER_MEGABYTE
                ).roundToLong()

        val savingsPercent =
            (
                (1.0 - compressionRatio) *
                    PERCENT
                )
                .roundToInt()
                .coerceIn(
                    0,
                    100,
                )

        return CompressionEstimate(
            estimatedSizeMb = estimatedSizeMb,
            savingsPercent = savingsPercent,
        )
    }

    private fun resolveTargetSize(
        sourceWidth: Int,
        sourceHeight: Int,
        resolution: VideoResolution,
    ): VideoSize {
        return when (resolution) {
            VideoResolution.Original -> {
                VideoSize(
                    width = sourceWidth,
                    height = sourceHeight,
                )
            }

            is VideoResolution.TargetShortSide -> {
                val sourceShortSide =
                    minOf(
                        sourceWidth,
                        sourceHeight,
                    )

                if (
                    resolution.pixels >=
                    sourceShortSide
                ) {
                    return VideoSize(
                        width = sourceWidth,
                        height = sourceHeight,
                    )
                }

                val scale =
                    resolution.pixels.toDouble() /
                        sourceShortSide

                VideoSize(
                    width = (
                        sourceWidth *
                            scale
                        )
                        .roundToInt()
                        .makeEven(),

                    height = (
                        sourceHeight *
                            scale
                        )
                        .roundToInt()
                        .makeEven(),
                )
            }
        }
    }

    private fun resolveTargetFrameRate(
        sourceFrameRate: Int?,
        frameRate: VideoFrameRate,
    ): Int {
        val source =
            sourceFrameRate
                ?.takeIf { it > 0 }

        return when (frameRate) {
            VideoFrameRate.Original -> {
                source
                    ?: DEFAULT_FRAME_RATE
            }

            is VideoFrameRate.Limited -> {
                source
                    ?.let {
                        minOf(
                            it,
                            frameRate.fps,
                        )
                    }
                    ?: minOf(
                        DEFAULT_FRAME_RATE,
                        frameRate.fps,
                    )
            }
        }
    }

    private fun Int.makeEven(): Int {
        return if (this % 2 == 0) {
            this
        } else {
            this - 1
        }
    }

    private data class VideoSize(
        val width: Int,
        val height: Int,
    )

    private companion object {
        const val DEFAULT_FRAME_RATE = 30

        const val ESTIMATED_AUDIO_BITRATE =
            128_000L

        const val BITS_PER_BYTE =
            8.0

        const val MILLIS_PER_SECOND =
            1_000.0

        const val BYTES_PER_MEGABYTE =
            1024.0 * 1024.0

        const val PERCENT =
            100.0

        const val MIN_COMPRESSION_RATIO =
            0.0

        const val MAX_COMPRESSION_RATIO =
            1.0
    }
}

internal data class CompressionEstimate(
    val estimatedSizeMb: Long?,
    val savingsPercent: Int?,
)