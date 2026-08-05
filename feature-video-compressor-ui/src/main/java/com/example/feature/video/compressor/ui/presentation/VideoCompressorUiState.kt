package com.example.feature.video.compressor.ui.presentation

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset

data class VideoCompressorUiState(
    val uri: Uri? = null,
    val compressionPreset: CompressionPreset = CompressionPreset.Balanced
)