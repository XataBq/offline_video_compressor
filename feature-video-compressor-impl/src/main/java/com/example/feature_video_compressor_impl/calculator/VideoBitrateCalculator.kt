package com.example.feature_video_compressor_impl.calculator

import com.example.feature_video_compressor_api.model.CompressionQuality
import com.example.feature_video_compressor_api.model.VideoCodec
import com.example.feature_video_compressor_api.model.VideoMetadata
import javax.inject.Inject
import kotlin.math.roundToInt

class VideoBitrateCalculator @Inject constructor() {

    fun calculate(
        metadata: VideoMetadata,
        targetSize: VideoSize,
        targetFrameRate: Int?,
        quality: CompressionQuality,
        codec: VideoCodec,
    ): Int {
        val calculationFrameRate =
            targetFrameRate
                ?: metadata.frameRate
                ?: DEFAULT_ESTIMATION_FRAME_RATE

        val pixelsPerSecond =
            targetSize.width.toLong() *
                targetSize.height.toLong() *
                calculationFrameRate.toLong()

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
        const val DEFAULT_ESTIMATION_FRAME_RATE = 30
        const val MIN_VIDEO_BITRATE = 300_000
        const val MAX_VIDEO_BITRATE = 20_000_000
    }
}