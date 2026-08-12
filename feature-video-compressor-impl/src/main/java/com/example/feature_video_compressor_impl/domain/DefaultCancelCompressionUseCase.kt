package com.example.feature_video_compressor_impl.domain

import com.example.feature_video_compressor_api.usecase.CancelCompressionUseCase
import com.example.feature_video_compressor_impl.service.CompressionServiceController
import javax.inject.Inject

internal class DefaultCancelCompressionUseCase @Inject constructor(
    private val serviceController:
    CompressionServiceController,
) : CancelCompressionUseCase {

    override fun invoke() {
        serviceController.cancel()
    }
}