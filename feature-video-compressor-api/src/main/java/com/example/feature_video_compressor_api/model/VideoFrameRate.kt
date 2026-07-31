package com.example.feature_video_compressor_api.model

sealed interface VideoFrameRate {

    data object Original : VideoFrameRate

    data class Limited(
        val fps: Int,
    ) : VideoFrameRate
}