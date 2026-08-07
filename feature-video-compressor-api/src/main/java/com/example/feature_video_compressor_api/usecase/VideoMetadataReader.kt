package com.example.feature_video_compressor_api.usecase

import android.net.Uri
import com.example.feature_video_compressor_api.model.VideoMetadata

interface VideoMetadataReader {

    fun read(uri: Uri): VideoMetadata
}