package com.example.feature_video_compressor_impl.calculator

import com.example.feature_video_compressor_api.model.VideoResolution
import com.example.feature_video_compressor_impl.metadata.VideoMetadata
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

            is VideoResolution.TargetHeight -> {
                calculateForHeight(
                    originalWidth = metadata.width,
                    originalHeight = metadata.height,
                    targetHeight = resolution.height,
                )
            }
        }
    }

    private fun calculateForHeight(
        originalWidth: Int,
        originalHeight: Int,
        targetHeight: Int,
    ): VideoSize {
        if (originalWidth <= 0 || originalHeight <= 0) {
            throw IllegalArgumentException("Некорректный размер исходного видео")
        }

        /*
         * Не увеличиваем видео.
         * Если исходник 480p, выбор 1080p не должен растягивать его.
         */
        if (originalHeight <= targetHeight) {
            return VideoSize(
                width = makeEven(originalWidth),
                height = makeEven(originalHeight),
            )
        }

        val aspectRatio = originalWidth.toDouble() / originalHeight
        val calculatedWidth = (targetHeight * aspectRatio).roundToInt()

        return VideoSize(
            width = makeEven(calculatedWidth),
            height = makeEven(targetHeight),
        )
    }

    private fun makeEven(value: Int): Int {
        return if (value % 2 == 0) value else value - 1
    }
}