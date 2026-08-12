package com.example.feature.video.compressor.ui.presentation.screens.videocompressorsettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.feature.video.compressor.ui.presentation.VideoCompressorNews
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel

@Composable
internal fun VideoCompressorSettingsRoute(
    viewModel: VideoCompressorViewModel,
    navigateBack: () -> Unit,
    navigateHelp: () -> Unit,
    navigateCompressionProgress: () -> Unit,
) {
    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    var bottomSheetOpened by
    rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel) {
        viewModel.news.collect { news ->
            when (news) {
                VideoCompressorNews
                    .NavigateCompressionProgress -> {
                    navigateCompressionProgress()
                }

                else -> Unit
            }
        }
    }

    VideoCompressorSettingsScreen(
        state = uiState,
        bottomSheetOpened = bottomSheetOpened,
        openBottomSheet = {
            bottomSheetOpened = true
        },
        onDismissRequest = {
            bottomSheetOpened = false
        },
        onBackClick = navigateBack,
        onHelpClick = navigateHelp,
        onPresetSelected =
            viewModel::onPresetSelected,
        onCompressClick =
            viewModel::onCompressClick,
        onResetClick =
            viewModel::onCustomSettingsReset,
        onResolutionSelected =
            viewModel::onResolutionSelected,
        onFrameRateSelected =
            viewModel::onFrameRateSelected,
        onQualitySelected =
            viewModel::onQualitySelected,
        onCodecSelected =
            viewModel::onCodecSelected,
    )
}