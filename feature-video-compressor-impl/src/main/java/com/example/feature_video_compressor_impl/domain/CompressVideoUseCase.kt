package com.example.feature_video_compressor_impl.domain

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.CompressionState
import com.example.feature_video_compressor_api.usecase.CompressVideoUseCase
import com.example.feature_video_compressor_impl.compressor.VideoCompressor
import com.example.feature_video_compressor_impl.preset.CompressionPresetFactory
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class DefaultCompressVideoUseCase @Inject constructor(
    private val videoCompressor: VideoCompressor,
    private val presetFactory: CompressionPresetFactory,
) : CompressVideoUseCase {

    override fun withPreset(
        inputUri: Uri,
        outputFile: File,
        preset: CompressionPreset,
    ): Flow<CompressionState> {
        val settings = presetFactory.create(preset)

        return videoCompressor.compress(
            inputUri = inputUri,
            outputFile = outputFile,
            settings = settings,
        )
    }

    override fun withCustomSettings(
        inputUri: Uri,
        outputFile: File,
        settings: CompressionSettings,
    ): Flow<CompressionState> {
        return videoCompressor.compress(
            inputUri = inputUri,
            outputFile = outputFile,
            settings = settings,
        )
    }

    override fun cancel() {
        videoCompressor.cancel()
    }
}