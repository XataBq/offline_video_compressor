package com.example.feature.video.compressor.ui.presentation.state

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoMetadata

data class VideoCompressorUiState(
    val uri: Uri? = null,

    val videoMetadata: VideoMetadata? = null,

    val compressionPreset: CompressionPreset = CompressionPreset.Balanced,

    val estimatedSizeMb: Long? = null,
    val savingsPercent: Int? = null,

    val isCompressButtonEnabled: Boolean = true,
) {
    val compressionSettings: CompressionSettings
        get() = compressionPreset.toSettings()

    val originalSizeMb: Int?
        get() = videoMetadata?.sizeBytes?.let { (it / 1024).toInt() }
}