package com.example.feature.video.compressor.ui.presentation.state

import android.graphics.Bitmap
import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoLocation
import com.example.feature_video_compressor_api.model.VideoMetadata

internal data class VideoCompressorUiState(
    val uri: Uri? = null,

    val videoMetadata: VideoMetadata? = null,

    val compressionTimeLeftMs: Long? = null,
    val compressionProgress: Int? = null,

    val compressionPreset: CompressionPreset =
        CompressionPreset.Balanced,

    val estimatedSizeMb: Long? = null,
    val savingsPercent: Int? = null,

    val isCompressButtonEnabled: Boolean = true,
    val videoPreview: Bitmap? = null,
    val isCompressing: Boolean = false,

    val compressedVideoLocation: VideoLocation? = null,
) {

    val compressionSettings: CompressionSettings
        get() = compressionPreset.toSettings()

    val originalSizeMb: Double?
        get() = videoMetadata
            ?.sizeBytes
            ?.let { sizeBytes ->
                sizeBytes / (1024.0 * 1024.0)
            }
}