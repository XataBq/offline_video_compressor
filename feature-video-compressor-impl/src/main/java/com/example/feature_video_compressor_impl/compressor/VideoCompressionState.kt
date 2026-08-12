package com.example.feature_video_compressor_impl.compressor


internal sealed interface VideoCompressionState {

    data object Preparing : VideoCompressionState

    data class Compressing(
        val progress: Int,
    ) : VideoCompressionState

    data class Completed(
        val originalSizeBytes: Long?,
        val compressedSizeBytes: Long,
    ) : VideoCompressionState

    data class Failed(
        val throwable: Throwable,
    ) : VideoCompressionState

    data object Cancelled : VideoCompressionState
}