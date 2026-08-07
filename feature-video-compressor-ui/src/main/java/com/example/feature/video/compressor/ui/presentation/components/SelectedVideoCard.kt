package com.example.feature.video.compressor.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.VideoFile
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.IconPrimary
import com.example.core_design_system.theme.ShadowPurple
import com.example.core_design_system.theme.Surface
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import com.example.feature.video.compressor.ui.presentation.state.VideoCompressorUiState
import com.example.feature.video.compressor.ui.utils.formatDuration

@Composable
internal fun SelectedVideoCard(
    state: VideoCompressorUiState,
) {
    val duration = state.videoMetadata
        ?.durationMs
        ?.formatDuration()
        .orEmpty()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(24.dp),
                shadow = Shadow(
                    radius = 14.dp,
                    color = ShadowPurple,
                    offset = DpOffset(0.dp, 4.dp),
                ),
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            VideoPreview(
                duration = duration,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.VideoFile,
                    contentDescription = null,
                    tint = IconPrimary,
                    modifier = Modifier.size(26.dp),
                )

                Text(
                    text = state.videoMetadata?.fileName ?: "",
                    style = VideoCompressorTypography.titleMedium,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            VideoMetadataRow(
                resolution = "${state.videoMetadata?.width}x${state.videoMetadata?.height}",
                frameRate = state.videoMetadata?.frameRate,
                sizeMb = state.originalSizeMb,
            )
        }
    }
}

@Composable
private fun VideoPreview(
    duration: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 7f)
            .clip(RoundedCornerShape(18.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF7BA4D1),
                        Color(0xFF355576),
                        Color(0xFF173D41),
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite,
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.58f),
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = stringResource(R.string.cd_play_video),
                    tint = Color.White,
                    modifier = Modifier.size(38.dp),
                )
            }
        }

        Text(
            text = duration,
            style = VideoCompressorTypography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.68f),
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 5.dp,
                ),
        )
    }
}

@Composable
private fun VideoMetadataRow(
    resolution: String?,
    frameRate: Int?,
    sizeMb: Int?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MetadataItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.SmartDisplay,
                    contentDescription = null,
                )
            },
            text = stringResource(
                R.string.video_resolution_format,
                resolution ?: "---",
            ),
        )

        MetadataDivider()

        MetadataItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Speed,
                    contentDescription = null,
                )
            },
            text = stringResource(
                R.string.video_frame_rate_format,
                frameRate ?: "---",
            ),
        )

        MetadataDivider()

        MetadataItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                )
            },
            text = stringResource(
                R.string.video_file_size_format,
                sizeMb ?: "---",
            ),
        )
    }
}

@Composable
private fun MetadataItem(
    icon: @Composable () -> Unit,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Box(
            modifier = Modifier.size(18.dp),
            contentAlignment = Alignment.Center,
        ) {
            icon()
        }

        Text(
            text = text,
            style = VideoCompressorTypography.bodySmall,
            color = TextSecondary,
        )
    }
}

@Composable
private fun MetadataDivider() {
    Box(
        modifier = Modifier
            .size(4.dp)
            .background(
                color = TextSecondary.copy(alpha = 0.65f),
                shape = CircleShape,
            ),
    )
}