package com.example.feature_video_compressor_impl.calculator

import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoMetadata
import javax.inject.Inject

class CompressionSettingsResolver @Inject constructor(
    private val sizeCalculator: VideoSizeCalculator,
    private val bitrateCalculator: VideoBitrateCalculator,
) {

    fun resolve(
        metadata: VideoMetadata,
        settings: CompressionSettings,
    ): ResolvedCompressionSettings {
        require(metadata.width > 0 && metadata.height > 0) {
            "Unable to determine video resolution"
        }

        val targetSize = sizeCalculator.calculate(
            metadata = metadata,
            resolution = settings.resolution,
        )

        val targetFrameRate = resolveFrameRate(
            sourceFrameRate = metadata.frameRate,
            requestedFrameRate = settings.frameRate,
        )

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
            shouldResize =
                targetSize.width != metadata.width ||
                    targetSize.height != metadata.height,
            shouldLimitFrameRate =
                metadata.frameRate != null &&
                    targetFrameRate != null &&
                    targetFrameRate < metadata.frameRate!!,
        )
    }

    private fun resolveFrameRate(
        sourceFrameRate: Int?,
        requestedFrameRate: VideoFrameRate,
    ): Int? {
        return when (requestedFrameRate) {
            VideoFrameRate.Original -> {
                sourceFrameRate
            }

            is VideoFrameRate.Limited -> {
                sourceFrameRate?.let { source ->
                    minOf(
                        source,
                        requestedFrameRate.fps,
                    )
                }
            }
        }
    }
}