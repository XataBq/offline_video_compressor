package com.example.feature.video.compressor.ui.presentation.state

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionSettings

data class VideoCompressorUiState(
    val uri: Uri? = null,

    val fileName: String = "Путешествие_2024.mp4",
    val resolution: String = "1920×1080",
    val frameRate: Int = 30,
    val originalSizeMb: Long = 128,
    val duration: String = "02:45",

    val compressionPreset: CompressionPreset = CompressionPreset.Balanced,

    val estimatedSizeMb: Long = 51,
    val savingsPercent: Int = 60,

    val isCompressButtonEnabled: Boolean = true,
) {
    val compressionSettings: CompressionSettings
        get() = compressionPreset.toSettings()
}