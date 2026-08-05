package com.example.feature_video_compressor_impl.domain

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionState
import com.example.feature_video_compressor_api.usecase.CompressVideoUseCase
import com.example.feature_video_compressor_impl.compressor.VideoCompressor
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

internal class DefaultCompressVideoUseCase @Inject constructor(
    private val videoCompressor: VideoCompressor,
) : CompressVideoUseCase {

    override operator fun invoke(
        inputUri: Uri,
        outputFile: File,
        preset: CompressionPreset,
    ): Flow<CompressionState> {
        return videoCompressor.compress(
            inputUri = inputUri,
            outputFile = outputFile,
            settings = preset.toSettings(),
        )
    }

    override fun cancel() {
        videoCompressor.cancel()
    }
}