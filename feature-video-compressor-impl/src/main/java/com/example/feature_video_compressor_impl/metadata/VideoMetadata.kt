package com.example.feature_video_compressor_impl.metadata

data class VideoMetadata(
    val width: Int,
    val height: Int,
    val frameRate: Int,
    val bitrate: Int?,
    val durationMs: Long,
)