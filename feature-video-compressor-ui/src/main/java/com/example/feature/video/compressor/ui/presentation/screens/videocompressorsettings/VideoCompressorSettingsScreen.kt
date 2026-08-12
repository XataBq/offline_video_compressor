package com.example.feature.video.compressor.ui.presentation.screens.videocompressorsettings

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.Background
import com.example.core_design_system.theme.Primary
import com.example.core_design_system.theme.PrimaryDark
import com.example.core_design_system.theme.PrimaryPale
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import com.example.feature.video.compressor.ui.presentation.components.CompressVideoButton
import com.example.feature.video.compressor.ui.presentation.components.CompressionPresetCard
import com.example.feature.video.compressor.ui.presentation.components.EstimatedSizeCard
import com.example.feature.video.compressor.ui.presentation.components.PrivacyNotice
import com.example.feature.video.compressor.ui.presentation.components.SelectedVideoCard
import com.example.feature.video.compressor.ui.presentation.components.VideoCompressorHeader
import com.example.feature.video.compressor.ui.presentation.screens.customsettings.CustomCompressionSettingsScreen
import com.example.feature.video.compressor.ui.presentation.state.VideoCompressorUiState
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.CompressionQuality
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoCodec
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoMetadata
import com.example.feature_video_compressor_api.model.VideoResolution

@Composable
internal fun VideoCompressorSettingsScreen(
    state: VideoCompressorUiState,
    bottomSheetOpened: Boolean,
    openBottomSheet: () -> Unit,
    onDismissRequest: () -> Unit,
    onBackClick: () -> Unit,
    onHelpClick: () -> Unit,
    onPresetSelected: (CompressionPreset) -> Unit,
    onCompressClick: () -> Unit,
    onResetClick: () -> Unit,
    onResolutionSelected: (VideoResolution) -> Unit,
    onFrameRateSelected: (VideoFrameRate) -> Unit,
    onQualitySelected: (CompressionQuality) -> Unit,
    onCodecSelected: (VideoCodec) -> Unit,
    modifier: Modifier = Modifier,
) {
    requireNotNull(state.uri) {
        "VideoCompressorSettingsScreen requires selected video Uri"
    }

    val topInset = WindowInsets.safeDrawing
        .only(WindowInsetsSides.Top)
        .asPaddingValues()
        .calculateTopPadding()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = topInset + 16.dp,
            end = 16.dp,
            bottom = 28.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        item {
            VideoCompressorHeader(
                titleResId = R.string.compression_settings_title,
                subtitleResId = R.string.compression_settings_subtitle,
                onBackClick = onBackClick,
                onHelpClick = onHelpClick,
            )
        }

        item {
            SelectedVideoCard(
                state = state,
            )
        }

        item {
            CompressionPresetsSection(
                selectedPreset = state.compressionPreset,
                onPresetSelected = onPresetSelected,
                onCustomSettingsClick = openBottomSheet,
            )
        }

        item {
            EstimatedSizeCard(
                estimatedSizeMb = state.estimatedSizeMb,
                originalSizeMb = state.originalSizeMb,
                savingsPercent = state.savingsPercent,
            )
        }

        item {
            CompressVideoButton(
                enabled = state.isCompressButtonEnabled,
                onClick = onCompressClick,
                textResId = R.string.compress_video_button
            )
        }

        item {
            PrivacyNotice()
        }
    }

    CustomSettingsBottomSheet(
        metadata = state.videoMetadata,
        settings = state.compressionSettings,
        openBottomSheet = bottomSheetOpened,
        onDismissRequest = onDismissRequest,
        onResetClick = onResetClick,
        onResolutionSelected = onResolutionSelected,
        onFrameRateSelected = onFrameRateSelected,
        onQualitySelected = onQualitySelected,
        onCodecSelected = onCodecSelected,
    )
}

@Composable
private fun CompressionPresetsSection(
    selectedPreset: CompressionPreset,
    onPresetSelected: (CompressionPreset) -> Unit,
    onCustomSettingsClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(R.string.compression_mode_title),
            style = VideoCompressorTypography.titleMedium,
            color = TextPrimary,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CompressionPresetCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.preset_balanced_title),
                description = stringResource(R.string.preset_balanced_description),
                badge = stringResource(R.string.preset_balanced_savings),
                icon = Icons.Filled.Bolt,
                iconColors = listOf(
                    PrimaryPale,
                    PrimaryDark,
                ),
                badgeColor = Primary.copy(alpha = 0.1f),
                badgeTextColor = Primary,
                selected = selectedPreset == CompressionPreset.Balanced,
                onClick = {
                    onPresetSelected(CompressionPreset.Balanced)
                },
            )

            CompressionPresetCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.preset_max_quality_title),
                description = stringResource(R.string.preset_max_quality_description),
                badge = stringResource(R.string.preset_max_quality_savings),
                icon = Icons.Filled.HighQuality,
                iconColors = listOf(
                    Color(0xFF8FD0FF),
                    Color(0xFF2685F3),
                ),
                badgeColor = Color(0xFFE8F3FF),
                badgeTextColor = Color(0xFF227CE5),
                selected = selectedPreset == CompressionPreset.MaxQuality,
                onClick = {
                    onPresetSelected(CompressionPreset.MaxQuality)
                },
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CompressionPresetCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.preset_strong_title),
                description = stringResource(R.string.preset_strong_description),
                badge = stringResource(R.string.preset_strong_savings),
                icon = Icons.Filled.Compress,
                iconColors = listOf(
                    Color(0xFF87E4AD),
                    Color(0xFF1CA95A),
                ),
                badgeColor = Color(0xFFE7F8ED),
                badgeTextColor = Color(0xFF159B4B),
                selected = selectedPreset == CompressionPreset.StrongCompression,
                onClick = {
                    onPresetSelected(CompressionPreset.StrongCompression)
                },
            )

            val customPreset = selectedPreset as? CompressionPreset.Custom

            CompressionPresetCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.preset_custom_title),
                description = stringResource(R.string.preset_custom_description),
                badge = stringResource(
                    if (customPreset != null) {
                        R.string.preset_custom_selected_label
                    } else {
                        R.string.preset_custom_label
                    },
                ),
                icon = Icons.Outlined.Tune,
                iconColors = listOf(
                    Color(0xFFB9A7FF),
                    PrimaryDark,
                ),
                badgeColor = Primary.copy(alpha = 0.1f),
                badgeTextColor = Primary,
                selected = customPreset != null,
                onClick = onCustomSettingsClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomSettingsBottomSheet(
    metadata: VideoMetadata?,
    settings: CompressionSettings,
    openBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    onResetClick: () -> Unit,
    onResolutionSelected: (VideoResolution) -> Unit,
    onFrameRateSelected: (VideoFrameRate) -> Unit,
    onQualitySelected: (CompressionQuality) -> Unit,
    onCodecSelected: (VideoCodec) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!openBottomSheet) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Background,
        dragHandle = null,
        modifier = modifier.fillMaxSize(),
    ) {
        CustomCompressionSettingsScreen(
            metadata = metadata,
            settings = settings,
            onResetClick = onResetClick,
            onResolutionSelected = onResolutionSelected,
            onFrameRateSelected = onFrameRateSelected,
            onQualitySelected = onQualitySelected,
            onCodecSelected = onCodecSelected,
            modifier = Modifier,
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7,
)
@Composable
private fun VideoCompressionSettingsScreenPreview() {
    VideoCompressorSettingsScreen(
        state = VideoCompressorUiState(uri = Uri.parse("content://preview/video.mp4"),),
        openBottomSheet = {},
        onBackClick = {},
        onHelpClick = {},
        onPresetSelected = {},
        onCompressClick = {},
        bottomSheetOpened = false,
        onDismissRequest = {},
        modifier = Modifier,
        onResetClick = {},
        onResolutionSelected = {},
        onFrameRateSelected = {},
        onQualitySelected = {},
        onCodecSelected = {},
    )
}
