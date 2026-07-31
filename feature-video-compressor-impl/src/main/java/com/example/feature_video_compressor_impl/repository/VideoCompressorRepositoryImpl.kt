package com.example.feature_video_compressor_impl.repository

import com.example.feature_video_compressor_api.repository.VideoCompressorRepository
import com.example.feature_video_compressor_api.domain.CompressionParams
import javax.inject.Inject

internal class VideoCompressorRepositoryImpl @Inject constructor() : VideoCompressorRepository {
    override suspend fun compress(params: CompressionParams) {
        TODO("Not yet implemented")
    }
}