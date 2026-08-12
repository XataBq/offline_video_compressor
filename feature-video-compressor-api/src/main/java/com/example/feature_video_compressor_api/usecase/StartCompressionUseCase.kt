package com.example.feature_video_compressor_api.usecase

import android.net.Uri
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionState
import com.example.feature_video_compressor_api.model.VideoLocation
import java.io.File
import kotlinx.coroutines.flow.Flow

interface StartCompressionUseCase {

    operator fun invoke(
        inputLocation: VideoLocation,
        preset: CompressionPreset,
    )
}