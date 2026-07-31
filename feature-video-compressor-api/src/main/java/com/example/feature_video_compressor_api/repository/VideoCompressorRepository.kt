package com.example.feature_video_compressor_api.repository

import com.example.feature_video_compressor_api.domain.CompressionParams

interface VideoCompressorRepository {
    suspend fun compress(params: CompressionParams)
}