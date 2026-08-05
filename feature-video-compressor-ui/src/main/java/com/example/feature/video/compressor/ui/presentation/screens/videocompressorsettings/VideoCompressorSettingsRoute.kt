package com.example.feature.video.compressor.ui.presentation.screens.videocompressorsettings

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel

@Composable
internal fun VideoCompressorSettingsRoute(
    viewModel: VideoCompressorViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    VideoCompressorSettingsScreen()
}