package com.example.feature_video_compressor_impl.calculator

import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_impl.metadata.VideoMetadata

class CompressionSettingsResolver(
    private val sizeCalculator: VideoSizeCalculator,
    private val bitrateCalculator: VideoBitrateCalculator,
) {

    fun resolve(
        metadata: VideoMetadata,
        settings: CompressionSettings,
    ): ResolvedCompressionSettings {
        val targetSize = sizeCalculator.calculate(
            metadata = metadata,
            resolution = settings.resolution,
        )

        val requestedFrameRate = when (val frameRate = settings.frameRate) {
            VideoFrameRate.Original -> metadata.frameRate
            is VideoFrameRate.Limited -> frameRate.fps
        }

        val targetFrameRate = minOf(
            requestedFrameRate,
            metadata.frameRate,
        ).coerceAtLeast(1)

        val bitrate = bitrateCalculator.calculate(
            metadata = metadata,
            targetSize = targetSize,
            targetFrameRate = targetFrameRate,
            quality = settings.quality,
            codec = settings.codec,
        )

        return ResolvedCompressionSettings(
            width = targetSize.width,
            height = targetSize.height,
            frameRate = targetFrameRate,
            bitrate = bitrate,
            codec = settings.codec,
            keepAudio = settings.keepAudio,
            shouldResize = targetSize.width != metadata.width ||
                targetSize.height != metadata.height,
            shouldLimitFrameRate = targetFrameRate < metadata.frameRate,
        )
    }
}