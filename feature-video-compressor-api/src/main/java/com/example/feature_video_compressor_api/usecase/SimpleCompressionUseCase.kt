package com.example.feature_video_compressor_api.usecase

import com.example.feature_video_compressor_api.domain.CompressionParams

interface SimpleCompressionUseCase {
    suspend operator fun invoke(params: CompressionParams)
}