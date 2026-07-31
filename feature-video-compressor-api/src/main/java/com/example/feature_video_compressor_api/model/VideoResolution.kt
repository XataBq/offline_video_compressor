package com.example.feature_video_compressor_api.model

sealed interface VideoResolution {

    data object Original : VideoResolution

    data class TargetHeight(
        val height: Int,
    ) : VideoResolution
}