package com.example.feature.video.compressor.ui.presentation.screens.compressionprogress

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.video.compressor.ui.presentation.VideoCompressorNews
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel

@Composable
internal fun VideoCompressionProgressRoute(
    viewModel: VideoCompressorViewModel,
    navigateBack: () -> Unit,
    navigateHelp: () -> Unit,
    navigateCompressionResult: () -> Unit,
) {
    val state by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.news.collect { news ->
            when (news) {
                VideoCompressorNews
                    .NavigateCompressionResult -> {
                    navigateCompressionResult()
                }

                else -> Unit
            }
        }
    }

    VideoCompressionProgressScreen(
        state = state,
        onBackClick = navigateBack,
        onHelpClick = navigateHelp,
        onCancelClick = {
            viewModel.onCancelClick()
            navigateBack()
        },
    )
}