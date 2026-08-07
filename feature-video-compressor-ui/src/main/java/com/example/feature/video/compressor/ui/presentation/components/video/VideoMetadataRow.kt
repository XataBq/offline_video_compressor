package com.example.feature.video.compressor.ui.presentation.components.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.SmartDisplay
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R


@Composable
internal fun VideoMetadataRow(
    resolution: String?,
    frameRate: Int?,
    sizeMb: Double?,
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
            text = frameRate?.let {
                stringResource(
                    R.string.video_frame_rate_format,
                    frameRate,
                )
            } ?: "---",
        )

        MetadataDivider()

        MetadataItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                )
            },
            text = sizeMb?.let {
                stringResource(
                    R.string.video_file_size_format,
                    it,
                )
            } ?: "---",
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