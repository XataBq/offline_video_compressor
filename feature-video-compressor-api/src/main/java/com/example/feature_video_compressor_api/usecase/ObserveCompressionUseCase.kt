package com.example.feature_video_compressor_api.usecase

import com.example.feature_video_compressor_api.model.CompressionState
import kotlinx.coroutines.flow.Flow

interface ObserveCompressionUseCase {
    operator fun invoke(): Flow<CompressionState>
}