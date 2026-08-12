package com.example.feature_video_compressor_api.model

sealed interface CompressionState {

    data object Idle : CompressionState

    data object Preparing : CompressionState

    data class Compressing(
        val progress: Int,
        val remainingTimeMs: Long?,
    ) : CompressionState

    data class Completed(
        val outputLocation: VideoLocation,
        val originalSizeBytes: Long?,
        val compressedSizeBytes: Long,
    ) : CompressionState

    data class Failed(
        val throwable: Throwable,
    ) : CompressionState

    data object Cancelled : CompressionState
}

@JvmInline
value class VideoLocation(
    val value: String,
)