package com.example.feature_video_compressor_impl.metadata

interface VideoMetadataReader {

    fun read(uri: android.net.Uri): VideoMetadata
}