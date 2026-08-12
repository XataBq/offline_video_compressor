package com.example.feature_video_compressor_impl.domain

import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.VideoLocation
import com.example.feature_video_compressor_api.usecase.StartCompressionUseCase
import com.example.feature_video_compressor_impl.service.CompressionServiceController
import javax.inject.Inject

internal class DefaultStartCompressionUseCase @Inject constructor(
    private val serviceController:
    CompressionServiceController,
) : StartCompressionUseCase {

    override fun invoke(
        inputLocation: VideoLocation,
        preset: CompressionPreset,
    ) {
        serviceController.start(
            inputLocation = inputLocation,
            settings = preset.toSettings(),
        )
    }
}