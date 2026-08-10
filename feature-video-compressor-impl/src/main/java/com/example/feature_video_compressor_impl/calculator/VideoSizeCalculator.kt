package com.example.feature_video_compressor_impl.calculator

import com.example.feature_video_compressor_api.model.VideoResolution
import com.example.feature_video_compressor_api.model.VideoMetadata
import javax.inject.Inject
import kotlin.math.roundToInt

class VideoSizeCalculator @Inject constructor() {

    fun calculate(
        metadata: VideoMetadata,
        resolution: VideoResolution,
    ): VideoSize {
        return when (resolution) {
            VideoResolution.Original -> {
                VideoSize(
                    width = metadata.width,
                    height = metadata.height,
                )
            }

            is VideoResolution.TargetShortSide -> {
                calculateTargetSize(
                    sourceWidth = metadata.width,
                    sourceHeight = metadata.height,
                    targetShortSide = resolution.pixels,
                )
            }
        }
    }

    private fun calculateTargetSize(
        sourceWidth: Int,
        sourceHeight: Int,
        targetShortSide: Int,
    ): VideoSize {
        val sourceShortSide = minOf(
            sourceWidth,
            sourceHeight,
        )

        // Никогда не увеличиваем разрешение.
        if (targetShortSide >= sourceShortSide) {
            return VideoSize(
                width = sourceWidth,
                height = sourceHeight,
            )
        }

        val scale = targetShortSide.toDouble() /
            sourceShortSide.toDouble()

        val targetWidth = (sourceWidth * scale)
            .roundToInt()
            .makeEven()

        val targetHeight = (sourceHeight * scale)
            .roundToInt()
            .makeEven()

        return VideoSize(
            width = targetWidth,
            height = targetHeight,
        )
    }

    private fun Int.makeEven(): Int {
        return if (this % 2 == 0) {
            this
        } else {
            this - 1
        }
    }
}