package com.example.feature.video.compressor.ui.presentation.screens.videocompressorsettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel

@Composable
internal fun VideoCompressorSettingsRoute(
    viewModel: VideoCompressorViewModel,
    navigateBack: () -> Unit,
    navigateHelp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var bottomSheetOpened by rememberSaveable { mutableStateOf(false) }

    fun openBottomSheet() {
        bottomSheetOpened = true
    }

    fun onDismissRequest() {
        bottomSheetOpened = false
    }

    VideoCompressorSettingsScreen(
        state = uiState,
        bottomSheetOpened = bottomSheetOpened,
        openBottomSheet = ::openBottomSheet,
        onDismissRequest = ::onDismissRequest,
        onBackClick = navigateBack,
        onHelpClick = navigateHelp,
        onPresetSelected = viewModel::onPresetSelected,
        onCompressClick = viewModel::onCompressClick,
        onResetClick = viewModel::onCustomSettingsReset,
        onResolutionSelected = viewModel::onResolutionSelected,
        onFrameRateSelected = viewModel::onFrameRateSelected,
        onQualitySelected = viewModel::onQualitySelected,
        onCodecSelected = viewModel::onCodecSelected,
    )
}