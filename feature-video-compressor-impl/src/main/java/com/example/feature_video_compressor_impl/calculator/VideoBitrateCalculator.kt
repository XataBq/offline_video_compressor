package com.example.feature_video_compressor_impl.calculator

import com.example.feature_video_compressor_api.model.CompressionQuality
import com.example.feature_video_compressor_api.model.VideoCodec
import com.example.feature_video_compressor_impl.metadata.VideoMetadata
import kotlin.math.roundToInt

class VideoBitrateCalculator {

    fun calculate(
        metadata: VideoMetadata,
        targetSize: VideoSize,
        targetFrameRate: Int,
        quality: CompressionQuality,
        codec: VideoCodec,
    ): Int {
        val pixelsPerSecond =
            targetSize.width.toLong() *
                targetSize.height.toLong() *
                targetFrameRate.toLong()

        val codecMultiplier = when (codec) {
            VideoCodec.H264 -> 1.0
            VideoCodec.H265 -> 0.70
        }

        val calculatedBitrate = (
            pixelsPerSecond *
                quality.bitsPerPixel *
                codecMultiplier
            ).roundToInt()

        val limitedBitrate = calculatedBitrate.coerceIn(
            MIN_VIDEO_BITRATE,
            MAX_VIDEO_BITRATE,
        )

        return metadata.bitrate
            ?.takeIf { it > 0 }
            ?.let { sourceBitrate ->
                minOf(limitedBitrate, sourceBitrate)
            }
            ?: limitedBitrate
    }

    private companion object {
        const val MIN_VIDEO_BITRATE = 300_000
        const val MAX_VIDEO_BITRATE = 20_000_000
    }
}