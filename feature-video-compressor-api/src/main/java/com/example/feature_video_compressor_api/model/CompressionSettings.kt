package com.example.feature_video_compressor_api.model

data class CompressionSettings(
    val resolution: VideoResolution,
    val frameRate: VideoFrameRate,
    val quality: CompressionQuality,
    val codec: VideoCodec,
    val keepAudio: Boolean = true,
)