package com.example.feature.video.compressor.ui.presentation.screens.videocompressorstart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel
import com.example.feature.video.compressor.ui.presentation.screens.VideoCompressorNews

@Composable
internal fun VideoCompressorRoute(
    viewModel: VideoCompressorViewModel,
    navigateSettings: () -> Unit,
    navigateCompressionSettings: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(
            Lifecycle.State.STARTED
        ) {
            viewModel.news.collect { news ->
                when(news) {
                    VideoCompressorNews.NavigateSettings -> {
                        navigateSettings()
                    }

                    VideoCompressorNews.NavigateCompressionSettings -> {
                        navigateCompressionSettings()
                    }
                }
            }
        }

    }

    VideoCompressorScreen(
        onSettingsClick = viewModel::onSettingsClick,
        onChooseVideoButtonClick = viewModel::onChooseVideoClick
    )
}