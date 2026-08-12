package com.example.feature.video.compressor.ui.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.IconSecondary
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R

@Composable
internal fun VideoCompressorHeader(
    titleResId: Int,
    subtitleResId: Int,
    onBackClick: () -> Unit,
    onHelpClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        HeaderIconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = stringResource(R.string.cd_back),
                tint = IconSecondary,
                modifier = Modifier.size(26.dp),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = stringResource(titleResId),
                style = VideoCompressorTypography.headlineMedium,
                color = TextPrimary,
            )

            Text(
                text = stringResource(subtitleResId),
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
            )
        }

        HeaderIconButton(onHelpClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                contentDescription = stringResource(R.string.cd_help),
                tint = IconSecondary,
                modifier = Modifier.size(26.dp),
            )
        }
    }
}