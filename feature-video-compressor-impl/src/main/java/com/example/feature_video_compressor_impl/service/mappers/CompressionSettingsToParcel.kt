package com.example.feature_video_compressor_impl.service.mappers

import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoResolution
import com.example.feature_video_compressor_impl.service.dto.CompressionSettingsParcel
import com.example.feature_video_compressor_impl.service.dto.VideoFrameRateParcel
import com.example.feature_video_compressor_impl.service.dto.VideoResolutionParcel

internal fun CompressionSettings.toParcel(): CompressionSettingsParcel {
    return CompressionSettingsParcel(
        resolution = resolution.toParcel(),
        frameRate = frameRate.toParcel(),
        quality = quality,
        codec = codec,
        keepAudio = keepAudio,
    )
}

private fun VideoResolution.toParcel(): VideoResolutionParcel {
    return when (this) {
        VideoResolution.Original -> {
            VideoResolutionParcel.Original
        }

        is VideoResolution.TargetShortSide -> {
            VideoResolutionParcel.TargetShortSide(
                pixels = pixels,
            )
        }
    }
}

private fun VideoFrameRate.toParcel(): VideoFrameRateParcel {
    return when (this) {
        VideoFrameRate.Original -> {
            VideoFrameRateParcel.Original
        }

        is VideoFrameRate.Limited -> {
            VideoFrameRateParcel.Limited(
                fps = fps,
            )
        }
    }
}