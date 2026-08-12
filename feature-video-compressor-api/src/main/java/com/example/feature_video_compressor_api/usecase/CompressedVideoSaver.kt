package com.example.feature_video_compressor_api.usecase

import android.net.Uri
import java.io.File

interface CompressedVideoSaver {

    fun save(
        sourceFile: File,
        displayName: String,
    ): Uri
}