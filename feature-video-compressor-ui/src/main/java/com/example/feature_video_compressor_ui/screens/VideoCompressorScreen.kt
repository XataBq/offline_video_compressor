package com.example.feature_video_compressor_ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoCameraBack
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_desing_system.theme.IconPrimary
import com.example.core_desing_system.theme.IconSecondary
import com.example.core_desing_system.theme.OfflineVideoCompressorTheme
import com.example.core_desing_system.theme.PrimaryDark
import com.example.core_desing_system.theme.PrimaryLight
import com.example.core_desing_system.theme.ShadowPurple
import com.example.core_desing_system.theme.Surface
import com.example.core_desing_system.theme.SurfaceLightPurple
import com.example.core_desing_system.theme.SurfacePurple
import com.example.core_desing_system.theme.TextOnPrimary
import com.example.core_desing_system.theme.TextPrimary
import com.example.core_desing_system.theme.TextSecondary
import com.example.core_desing_system.theme.VideoCompressorTypography
import com.example.feature_video_compressor_ui.R
import com.example.feature_video_compressor_ui.ui.components.BenefitRow

@Composable
internal fun VideoCompressorScreen(
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        label = "settings_button_scale",
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 10.dp,
        label = "settings_button_elevation",
    )

    val containerColor by animateColorAsState(
        targetValue = if (isPressed) {
            SurfacePurple
        } else {
            Surface
        },
        label = "settings_button_color",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .background(SurfaceLightPurple)
            .padding(horizontal = 15.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 17.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.home_title),
                style = VideoCompressorTypography.headlineLarge,
                color = TextPrimary,
            )

            Surface(
                onClick = onClick,
                interactionSource = interactionSource,
                modifier = Modifier
                    .size(46.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                shape = RoundedCornerShape(15.dp),
                color = containerColor,
                contentColor = IconSecondary,
                shadowElevation = elevation,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = IconSecondary,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
        Text(
            text = stringResource(R.string.home_subtitle),
            style = VideoCompressorTypography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier
                .fillMaxWidth(0.75F),
        )
        Card(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .dropShadow(
                    shape = RoundedCornerShape(25.dp),
                    shadow = Shadow(
                        radius = 16.dp,
                        spread = 0.dp,
                        color = ShadowPurple,
                        offset = DpOffset(
                            x = 0.dp,
                            y = 6.dp,
                        ),
                    ),
                ),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = SurfacePurple,
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Default.VideoCameraBack,
                    contentDescription = "video_camera_icon",
                    modifier = Modifier
                        .size(150.dp),
                    tint = IconPrimary
                )
                Text(
                    text = stringResource(R.string.select_video_title),
                    style = VideoCompressorTypography.headlineLarge,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
                Text(
                    text = stringResource(R.string.select_video_description),
                    textAlign = TextAlign.Center,
                    style = VideoCompressorTypography.bodyMedium,
                    color = TextSecondary
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = TextOnPrimary,
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.8F)
                        .padding(vertical = 20.dp)
                        .height(60.dp)
                        .dropShadow(
                            shape = RoundedCornerShape(20.dp),
                            shadow = Shadow(
                                radius = 4.dp,
                                spread = 0.dp,
                                color = PrimaryLight
                            )
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    PrimaryLight,
                                    PrimaryDark
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Videocam,
                            contentDescription = "video camera icon on button",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(30.dp),
                        )
                        Text(
                            text = stringResource(R.string.select_video_button),
                            style = VideoCompressorTypography.titleMedium,

                        )
                    }

                }
                Text(
                    text = stringResource(R.string.supported_formats),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
                Text(
                    text = stringResource(R.string.supported_formats_list),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
            }
        }
        Text(
            text = stringResource(R.string.benefits_title),
            style = VideoCompressorTypography.labelLarge,
            color = TextPrimary,
        )
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BenefitRow(
                titleResource = R.string.benefit_no_ads_title,
                subTitleResource = R.string.benefit_no_ads_description,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = PrimaryLight,
                )
            }
            BenefitRow(
                titleResource = R.string.benefit_no_watermark_title,
                subTitleResource = R.string.benefit_no_watermark_description,
            ) {
                Icon(
                    imageVector = Icons.Outlined.WaterDrop,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = PrimaryLight,
                )
            }
            BenefitRow(
                titleResource = R.string.benefit_privacy_title,
                subTitleResource = R.string.benefit_privacy_description,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = PrimaryLight,
                )
            }
            BenefitRow(
                titleResource = R.string.bottom_card_title,
                subTitleResource = R.string.bottom_card_description,
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = PrimaryLight,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, device = PIXEL_7)
private fun VideoCompressorScreenPreview() {
    OfflineVideoCompressorTheme {
        VideoCompressorScreen(
            onClick = {},
        )
    }
}