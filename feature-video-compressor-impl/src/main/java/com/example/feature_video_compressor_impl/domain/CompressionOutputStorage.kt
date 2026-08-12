package com.example.feature_video_compressor_impl.domain

import android.net.Uri
import java.io.File

internal interface CompressionOutputStorage {

    fun newTempFile(): File

    suspend fun publish(
        tempFile: File,
    ): Uri
}