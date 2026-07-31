package com.example.feature_video_compressor_impl.compressor

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.CompressionState
import java.io.File
import kotlinx.coroutines.flow.Flow

internal interface VideoCompressor {

    fun compress(
        inputUri: Uri,
        outputFile: File,
        settings: CompressionSettings,
    ): Flow<CompressionState>

    fun cancel()
}