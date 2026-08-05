package com.example.feature.video.compressor.ui.presentation.screens.videocompressorstart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_desing_system.theme.OfflineVideoCompressorTheme
import com.example.core_desing_system.theme.PrimaryLight
import com.example.core_desing_system.theme.ShadowPurple
import com.example.core_desing_system.theme.SurfaceLightPurple
import com.example.core_desing_system.theme.SurfacePurple
import com.example.core_desing_system.theme.TextPrimary
import com.example.core_desing_system.theme.TextSecondary
import com.example.core_desing_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import com.example.feature.video.compressor.ui.presentation.components.BenefitRow
import com.example.feature.video.compressor.ui.presentation.components.ChooseVideoButton
import com.example.feature.video.compressor.ui.presentation.components.SettingsButton
import com.example.feature.video.compressor.ui.presentation.components.videoHeroIllustration.VideoHeroIllustration

@Composable
internal fun VideoCompressorScreen(
    onSettingsClick: () -> Unit,
    onChooseVideoButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceLightPurple)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 17.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.home_title),
                style = VideoCompressorTypography.headlineLarge,
                color = TextPrimary,
            )
            SettingsButton(onSettingsClick)
        }
        Text(
            text = stringResource(R.string.home_subtitle),
            style = VideoCompressorTypography.bodySmall,
            color = TextSecondary,
            modifier = Modifier
                .fillMaxWidth(0.75F),
        )
        Card(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth()
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
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VideoHeroIllustration(
                    modifier = Modifier
                        .width(320.dp)
                        .height(145.dp),
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
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary
                )
                ChooseVideoButton(
                    onClick = onChooseVideoButtonClick,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
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
            ) { modifier ->
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    modifier = modifier,
                    tint = PrimaryLight,
                )
            }
            BenefitRow(
                titleResource = R.string.benefit_no_watermark_title,
                subTitleResource = R.string.benefit_no_watermark_description,
            ) { modifier ->
                Icon(
                    imageVector = Icons.Outlined.WaterDrop,
                    contentDescription = null,
                    modifier = modifier,
                    tint = PrimaryLight,
                )
            }
            BenefitRow(
                titleResource = R.string.benefit_privacy_title,
                subTitleResource = R.string.benefit_privacy_description,
            ) { modifier ->
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    modifier = modifier,
                    tint = PrimaryLight,
                )
            }
            BenefitRow(
                titleResource = R.string.bottom_card_title,
                subTitleResource = R.string.bottom_card_description,
            ) { modifier ->
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    modifier = modifier,
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
            onSettingsClick = {},
            onChooseVideoButtonClick = {}
        )
    }
}