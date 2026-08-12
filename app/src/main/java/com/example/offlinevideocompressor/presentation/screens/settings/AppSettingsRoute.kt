package com.example.offlinevideocompressor.presentation.screens.settings

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
internal fun AppSettingsRoute(
    navigateBack: () -> Unit,
    viewModel: AppSettingsViewModel = hiltViewModel(),
) {
    AppSettingsScreen(
        selectedLanguage =
            viewModel.selectedLanguage,
        onBackClick =
            navigateBack,
        onLanguageSelected =
            viewModel::onLanguageSelected,
    )
}