package com.example.feature_video_compressor_api.model

import java.io.File

sealed interface CompressionState {

    data object Idle : CompressionState

    data class Preparing(
        val inputUri: android.net.Uri,
    ) : CompressionState

    data class Compressing(
        val progress: Int,
    ) : CompressionState

    data class Completed(
        val outputFile: java.io.File,
        val originalSizeBytes: Long?,
        val compressedSizeBytes: Long,
    ) : CompressionState

    data class Failed(
        val throwable: Throwable,
    ) : CompressionState

    data object Cancelled : CompressionState
}