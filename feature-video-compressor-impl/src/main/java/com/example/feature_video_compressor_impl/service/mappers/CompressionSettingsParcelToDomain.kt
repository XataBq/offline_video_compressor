package com.example.feature_video_compressor_impl.service.mappers

import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoResolution
import com.example.feature_video_compressor_impl.service.dto.CompressionSettingsParcel
import com.example.feature_video_compressor_impl.service.dto.VideoFrameRateParcel
import com.example.feature_video_compressor_impl.service.dto.VideoResolutionParcel

internal fun CompressionSettingsParcel.toDomain(): CompressionSettings {
    return CompressionSettings(
        resolution = resolution.toDomain(),
        frameRate = frameRate.toDomain(),
        quality = quality,
        codec = codec,
        keepAudio = keepAudio,
    )
}

private fun VideoResolutionParcel.toDomain(): VideoResolution {
    return when (this) {
        VideoResolutionParcel.Original -> {
            VideoResolution.Original
        }

        is VideoResolutionParcel.TargetShortSide -> {
            VideoResolution.TargetShortSide(
                pixels = pixels,
            )
        }
    }
}

private fun VideoFrameRateParcel.toDomain(): VideoFrameRate {
    return when (this) {
        VideoFrameRateParcel.Original -> {
            VideoFrameRate.Original
        }

        is VideoFrameRateParcel.Limited -> {
            VideoFrameRate.Limited(
                fps = fps,
            )
        }
    }
}