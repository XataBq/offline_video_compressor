package com.example.feature_video_compressor_impl.compressor

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionSettings
import kotlinx.coroutines.flow.Flow
import java.io.File

internal interface VideoCompressor {

    fun compress(
        inputUri: Uri,
        outputFile: File,
        settings: CompressionSettings,
    ): Flow<VideoCompressionState>

    fun cancel()
}