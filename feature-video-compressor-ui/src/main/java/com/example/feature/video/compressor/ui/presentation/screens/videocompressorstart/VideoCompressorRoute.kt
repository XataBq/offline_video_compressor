package com.example.feature.video.compressor.ui.presentation.screens.videocompressorstart

import androidx.compose.runtime.Composable
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel

@Composable
internal fun VideoCompressorRoute(
    viewModel: VideoCompressorViewModel
) {
    VideoCompressorScreen(
        onSettingsClick = viewModel::onSettingsClick,
        onChooseVideoButtonClick = viewModel::onChooseVideoClick
    )
}