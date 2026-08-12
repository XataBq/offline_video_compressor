package com.example.feature.video.compressor.ui.presentation.screens.compressionprogress

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.Background
import com.example.core_design_system.theme.Primary
import com.example.core_design_system.theme.PrimaryDark
import com.example.core_design_system.theme.PrimaryLight
import com.example.core_design_system.theme.PrimaryPale
import com.example.core_design_system.theme.ShadowPurple
import com.example.core_design_system.theme.SurfaceLightPurple
import com.example.core_design_system.theme.SurfacePurple
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import com.example.feature.video.compressor.ui.presentation.components.PresetGradientIcon
import com.example.feature.video.compressor.ui.presentation.components.SelectedVideoCard
import com.example.feature.video.compressor.ui.presentation.components.VideoCompressorHeader
import com.example.feature.video.compressor.ui.presentation.state.VideoCompressorUiState
import com.example.feature.video.compressor.ui.utils.formatDuration
import com.example.feature_video_compressor_api.model.CompressionPreset

@Composable
internal fun VideoCompressionProgressScreen(
    state: VideoCompressorUiState,
    onBackClick: () -> Unit,
    onHelpClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val timeLeft = state
        .compressionTimeLeftMs
        ?.formatDuration()
        .orEmpty()

    val progress =
        ((state.compressionProgress ?: 0) / 100f)
            .coerceIn(0f, 1f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Background),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = 28.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        item {
            VideoCompressorHeader(
                titleResId = R.string.compression_progress_title,
                subtitleResId = R.string.compression_progress_subtitle,
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
            CompressionProgressCard(
                title = stringResource(R.string.compression_in_progress),
                progress = progress,
                timeLeft = timeLeft,
            )
        }
        item {
            PresetInfo(
                preset = state.compressionPreset,
                savingsPercent = state.savingsPercent,
            )
        }
        item {
            CancelButton(
                onClick = onCancelClick
            )
        }
    }
}

@Composable
private fun CompressionProgressCard(
    title: String,
    progress: Float,
    timeLeft: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(24.dp),
                shadow = Shadow(
                    radius = 14.dp,
                    color = ShadowPurple,
                    offset = DpOffset(0.dp, 4.dp),
                )
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = VideoCompressorTypography.titleMedium,
                color = TextPrimary,
            )
            CompressionProgressBarData(
                progress = (progress * 100).toInt(),
                timeLeft = timeLeft,
            )
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth(),
                color = Primary,
                trackColor = SurfaceLightPurple,
            )
            AdviceBlock(
                icon = Icons.Outlined.Lightbulb,
                tipDescription = stringResource(R.string.compression_tip_description)
            )
        }
    }
}

@Composable
private fun CompressionProgressBarData(
    progress: Int,
    timeLeft: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(
                id = R.string.compression_progress_format,
                progress,
            ),
            style = VideoCompressorTypography.headlineMedium,
            color = Primary,
        )
        Text(
            text = stringResource(
                id = R.string.compression_remaining_time,
                timeLeft
            ),
            style = VideoCompressorTypography.bodySmall,
            color = TextSecondary,
        )
    }
}

@Composable
private fun AdviceBlock(
    icon: ImageVector,
    tipDescription: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceLightPurple
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = SurfacePurple,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(25.dp),
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.compression_tip_title),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextPrimary,
                )
                Text(
                    text = tipDescription,
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
            }
        }
    }
}

@Composable
private fun PresetInfo(
    preset: CompressionPreset,
    savingsPercent: Int?,
    modifier: Modifier = Modifier,
) {
    var title: String
    var iconDescription: String
    var icon: ImageVector
    var iconColors: List<Color>

    when (preset) {
        CompressionPreset.Balanced -> {
            title = stringResource(R.string.preset_balanced_title)
            icon = Icons.Filled.Bolt
            iconColors = listOf(
                PrimaryPale,
                PrimaryDark,
            )
            iconDescription = stringResource(R.string.preset_balanced_description)
        }
        is CompressionPreset.Custom -> {
            title = stringResource(R.string.preset_custom_title)
            icon = Icons.Outlined.Tune
            iconColors = listOf(
                Color(0xFFB9A7FF),
                PrimaryDark,
            )
            iconDescription = stringResource(R.string.preset_custom_description)
        }
        CompressionPreset.MaxQuality -> {
            title = stringResource(R.string.preset_max_quality_title)
            icon = Icons.Filled.HighQuality
            iconColors = listOf(
                Color(0xFF8FD0FF),
                Color(0xFF2685F3),
            )
            iconDescription = stringResource(R.string.preset_max_quality_description)
        }
        CompressionPreset.StrongCompression -> {
            title = stringResource(R.string.preset_strong_title)
            icon = Icons.Filled.Compress
            iconColors = listOf(
                Color(0xFF87E4AD),
                Color(0xFF1CA95A),
            )
            iconDescription = stringResource(R.string.preset_strong_description)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(24.dp),
                shadow = Shadow(
                    radius = 14.dp,
                    color = ShadowPurple,
                    offset = DpOffset(x = 0.dp, y = 4.dp)
                )
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PresetGradientIcon(
                icon = icon,
                colors = iconColors,
                contentDescription = iconDescription
            )
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.compression_selected_mode),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
                Text(
                    text = title,
                    style = VideoCompressorTypography.bodyMedium,
                    color = Primary,
                )
                Text(
                    text = stringResource(
                            id = R.string.compression_size_reduction_format,
                            savingsPercent ?: 0
                    ),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                    )
            }
        }
    }
}

@Composable
private fun CancelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(20.dp)

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 10.dp,
                    color = ShadowPurple,
                    offset = DpOffset(
                        x = 0.dp,
                        y = 5.dp,
                    ),
                ),
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        PrimaryLight,
                        PrimaryDark,
                    ),
                ),
                shape = shape,
            ),
        border = BorderStroke(1.dp, color = Primary),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = SurfaceLightPurple,
            contentColor = Primary,
        ),
    ) {
        Icon(
            imageVector = Icons.Outlined.Cancel,
            contentDescription = null,
            modifier = Modifier.size(29.dp),
        )

        Spacer(modifier = Modifier.size(12.dp))

        Text(
            text = stringResource(R.string.compression_cancel),
            style = VideoCompressorTypography.titleMedium,
        )
    }
}


@Composable
@Preview(showSystemUi = true)
private fun VideoCompressionProgressScreenPreview() {
    VideoCompressionProgressScreen(
        state = VideoCompressorUiState(uri = Uri.parse(""),),
        onBackClick = {},
        onHelpClick = {},
        onCancelClick = {},
    )
}