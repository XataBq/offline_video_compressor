package com.example.feature.video.compressor.ui.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.BorderLight
import com.example.core_design_system.theme.Primary
import com.example.core_design_system.theme.PrimaryLight
import com.example.core_design_system.theme.Surface
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R


@Composable
internal fun CompressionPresetCard(
    title: String,
    description: String,
    badge: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColors: List<Color>,
    badgeColor: Color,
    badgeTextColor: Color,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor by animateColorAsState(
        targetValue = if (selected) {
            PrimaryLight
        } else {
            BorderLight.copy(alpha = 0.55f)
        },
        label = "preset_border_color",
    )

    val borderWidth by animateDpAsState(
        targetValue = if (selected) 1.5.dp else 0.5.dp,
        label = "preset_border_width",
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .height(172.dp)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(22.dp),
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 3.dp else 1.dp,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                PresetGradientIcon(
                    icon = icon,
                    colors = iconColors,
                )

                Text(
                    text = title,
                    style = VideoCompressorTypography.titleMedium,
                    color = if (selected) Primary else TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = description,
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                    maxLines = 2,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = badge,
                    style = VideoCompressorTypography.labelMedium,
                    color = badgeTextColor,
                    maxLines = 1,
                    softWrap = false,
                    modifier = Modifier
                        .background(
                            color = badgeColor,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        ),
                )
            }

            if (selected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(28.dp)
                        .background(
                            color = Primary,
                            shape = CircleShape,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = stringResource(
                            R.string.cd_selected_preset,
                        ),
                        tint = Color.White,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}

@Composable
internal fun PresetGradientIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    colors: List<Color>,
) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .background(
                brush = Brush.linearGradient(colors),
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp),
        )
    }
}