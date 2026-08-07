package com.example.feature_video_compressor_api.model

data class VideoMetadata(
    val width: Int,
    val height: Int,
    val frameRate: Int?,
    val bitrate: Int?,
    val durationMs: Long,
    val sizeBytes: Long?,
    val fileName: String?,
)