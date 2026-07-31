package com.example.feature_video_compressor_api.usecase

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.CompressionState
import java.io.File
import kotlinx.coroutines.flow.Flow

interface CompressVideoUseCase {

    fun withPreset(
        inputUri: Uri,
        outputFile: File,
        preset: CompressionPreset,
    ): Flow<CompressionState>

    fun withCustomSettings(
        inputUri: Uri,
        outputFile: File,
        settings: CompressionSettings,
    ): Flow<CompressionState>

    fun cancel()
}