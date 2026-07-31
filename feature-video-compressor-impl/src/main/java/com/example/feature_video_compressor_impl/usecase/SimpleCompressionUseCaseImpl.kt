package com.example.feature_video_compressor_impl.usecase

import com.example.feature_video_compressor_api.repository.VideoCompressorRepository
import com.example.feature_video_compressor_api.usecase.SimpleCompressionUseCase
import com.example.feature_video_compressor_api.domain.CompressionParams
import javax.inject.Inject

internal class SimpleCompressionUseCaseImpl @Inject constructor(
    val repository: VideoCompressorRepository
) : SimpleCompressionUseCase {
    override suspend fun invoke(params: CompressionParams) {

    }
}