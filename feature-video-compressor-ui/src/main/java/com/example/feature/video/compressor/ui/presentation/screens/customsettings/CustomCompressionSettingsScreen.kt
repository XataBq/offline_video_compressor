package com.example.feature.video.compressor.ui.presentation.screens.customsettings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.AutofpsSelect
import androidx.compose.material.icons.outlined.HighQuality
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.VideoFile
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.BorderLight
import com.example.core_design_system.theme.IconBackground
import com.example.core_design_system.theme.IconPrimary
import com.example.core_design_system.theme.Primary
import com.example.core_design_system.theme.PrimaryLight
import com.example.core_design_system.theme.ShadowPurple
import com.example.core_design_system.theme.Surface
import com.example.core_design_system.theme.SurfaceLightPurple
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import com.example.feature_video_compressor_api.model.CompressionQuality
import com.example.feature_video_compressor_api.model.CompressionSettings
import com.example.feature_video_compressor_api.model.VideoCodec
import com.example.feature_video_compressor_api.model.VideoFrameRate
import com.example.feature_video_compressor_api.model.VideoMetadata
import com.example.feature_video_compressor_api.model.VideoResolution
import kotlin.math.roundToInt

@Composable
internal fun CustomCompressionSettingsScreen(
    metadata: VideoMetadata?,
    settings: CompressionSettings,
    onResetClick: () -> Unit,
    onResolutionSelected: (VideoResolution) -> Unit,
    onFrameRateSelected: (VideoFrameRate) -> Unit,
    onQualitySelected: (CompressionQuality) -> Unit,
    onCodecSelected: (VideoCodec) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(
            start = 15.dp,
            end = 15.dp,
            bottom = 24.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            CustomSettingsHeader(
                onResetClick = onResetClick,
            )
        }

        item {
            ResolutionSettingsCard(
                metadata = metadata,
                selectedResolution = settings.resolution,
                onResolutionSelected = onResolutionSelected,
            )
        }

        item {
            FrameRateSettingsCard(
                metadata = metadata,
                selectedFrameRate = settings.frameRate,
                onFrameRateSelected = onFrameRateSelected,
            )
        }

        item {
            QualitySettingsCard(
                selectedQuality = settings.quality,
                onQualitySelected = onQualitySelected,
            )
        }

        item {
            CodecSettingsCard(
                selectedCodec = settings.codec,
                onCodecSelected = onCodecSelected,
            )
        }

        item {
            CompressionSettingsHint()
        }

        item {
            Spacer(
                modifier = Modifier.navigationBarsPadding(),
            )
        }
    }
}

@Composable
private fun CustomSettingsHeader(
    onResetClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = stringResource(R.string.custom_screen_title),
                style = VideoCompressorTypography.headlineMedium.copy(
                    fontSize = VideoCompressorTypography.titleLarge.fontSize,
                    lineHeight = VideoCompressorTypography.titleLarge.lineHeight,
                ),
                color = TextPrimary,
            )

            Text(
                text = stringResource(R.string.custom_screen_subtitle),
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
                maxLines = 2,
            )
        }

        Surface(
            onClick = onResetClick,
            modifier = Modifier.height(46.dp),
            shape = RoundedCornerShape(15.dp),
            color = Surface,
            shadowElevation = 7.dp,
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.RestartAlt,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(23.dp),
                )

                Text(
                    text = stringResource(R.string.custom_reset),
                    style = VideoCompressorTypography.bodySmall,
                    color = Primary,
                )
            }
        }
    }
}

@Composable
private fun ResolutionSettingsCard(
    metadata: VideoMetadata?,
    selectedResolution: VideoResolution,
    onResolutionSelected: (VideoResolution) -> Unit,
) {
    val options = availableResolutions(metadata)

    SettingsCard {
        SettingsSectionHeader(
            icon = Icons.Outlined.Videocam,
            title = stringResource(R.string.custom_resolution_title),
            description = stringResource(R.string.custom_resolution_description),
        )

        Spacer(modifier = Modifier.height(14.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            options.forEach { resolution ->
                SelectableOptionCard(
                    text = resolutionLabel(resolution),
                    selected = resolution == selectedResolution,
                    onClick = {
                        onResolutionSelected(resolution)
                    },
                    modifier = Modifier.weight(
                        weight = 1f,
                        fill = true,
                    ),
                )
            }
        }
    }
}

@Composable
private fun FrameRateSettingsCard(
    metadata: VideoMetadata?,
    selectedFrameRate: VideoFrameRate,
    onFrameRateSelected: (VideoFrameRate) -> Unit,
) {
    val options = availableFrameRates(metadata?.frameRate)

    SettingsCard {
        SettingsSectionHeader(
            icon = Icons.Outlined.AutofpsSelect,
            title = stringResource(R.string.custom_fps_title),
            description = stringResource(R.string.custom_fps_description),
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            options.forEach { frameRate ->
                SelectableOptionCard(
                    text = frameRateLabel(frameRate),
                    selected = frameRate == selectedFrameRate,
                    onClick = {
                        onFrameRateSelected(frameRate)
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun QualitySettingsCard(
    selectedQuality: CompressionQuality,
    onQualitySelected: (CompressionQuality) -> Unit,
) {
    val qualities = CompressionQuality.entries

    val selectedIndex = qualities.indexOf(selectedQuality)

    SettingsCard {
        SettingsSectionHeader(
            icon = Icons.Outlined.HighQuality,
            title = stringResource(R.string.custom_quality_title),
            description = stringResource(R.string.custom_quality_description),
        )

        Spacer(modifier = Modifier.height(14.dp))

        Slider(
            value = selectedIndex.toFloat(),
            onValueChange = { value ->
                val index = value
                    .roundToInt()
                    .coerceIn(qualities.indices)

                onQualitySelected(qualities[index])
            },
            valueRange = 0f..(qualities.lastIndex.toFloat()),
            steps = qualities.size - 2,
            colors = SliderDefaults.colors(
                thumbColor = Primary,
                activeTrackColor = Primary,
                inactiveTrackColor = PrimaryLight.copy(alpha = 0.22f),
                activeTickColor = Color.White,
                inactiveTickColor = PrimaryLight.copy(alpha = 0.5f),
            ),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.custom_quality_smaller_size),
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
            )

            Text(
                text = stringResource(R.string.custom_quality_better_quality),
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
            )
        }
    }
}

@Composable
private fun CodecSettingsCard(
    selectedCodec: VideoCodec,
    onCodecSelected: (VideoCodec) -> Unit,
) {
    SettingsCard {
        SettingsSectionHeader(
            icon = Icons.Outlined.VideoFile,
            title = stringResource(R.string.custom_codec_title),
            description = stringResource(R.string.custom_codec_description),
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CodecOptionCard(
                title = stringResource(R.string.custom_codec_h264),
                description = stringResource(
                    R.string.custom_codec_h264_description,
                ),
                selected = selectedCodec == VideoCodec.H264,
                onClick = {
                    onCodecSelected(VideoCodec.H264)
                },
                modifier = Modifier.weight(1f),
            )

            CodecOptionCard(
                title = stringResource(R.string.custom_codec_h265),
                description = stringResource(
                    R.string.custom_codec_h265_description,
                ),
                selected = selectedCodec == VideoCodec.H265,
                onClick = {
                    onCodecSelected(VideoCodec.H265)
                },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun SettingsCard(
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(22.dp),
                shadow = Shadow(
                    radius = 12.dp,
                    color = ShadowPurple,
                    offset = DpOffset(
                        x = 0.dp,
                        y = 4.dp,
                    ),
                ),
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = content,
        )
    }
}

@Composable
private fun SettingsSectionHeader(
    icon: ImageVector,
    title: String,
    description: String,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = IconBackground,
                    shape = RoundedCornerShape(13.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = IconPrimary,
                modifier = Modifier.size(25.dp),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = title,
                style = VideoCompressorTypography.titleMedium.copy(
                    fontSize = VideoCompressorTypography.bodyMedium.fontSize,
                ),
                color = TextPrimary,
            )

            Text(
                text = description,
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
                maxLines = 2,
            )
        }
    }
}

@Composable
private fun SelectableOptionCard(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(13.dp)

    Surface(
        onClick = onClick,
        modifier = modifier
            .height(58.dp)
            .then(
                if (selected) {
                    Modifier.border(
                        width = 1.5.dp,
                        color = PrimaryLight,
                        shape = shape,
                    )
                } else {
                    Modifier
                },
            ),
        shape = shape,
        color = if (selected) {
            PrimaryLight.copy(alpha = 0.07f)
        } else {
            SurfaceLightPurple.copy(alpha = 0.55f)
        },
        border = if (!selected) {
            BorderStroke(
                width = 1.dp,
                color = BorderLight,
            )
        } else {
            null
        },
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = VideoCompressorTypography.bodySmall.copy(
                    fontWeight = if (selected) {
                        FontWeight.SemiBold
                    } else {
                        FontWeight.Normal
                    },
                ),
                color = if (selected) Primary else TextPrimary,
                textAlign = TextAlign.Center,
                maxLines = 2,
            )
        }
    }
}

@Composable
private fun CodecOptionCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(14.dp)

    Surface(
        onClick = onClick,
        modifier = modifier
            .height(68.dp)
            .then(
                if (selected) {
                    Modifier.border(
                        width = 1.5.dp,
                        color = PrimaryLight,
                        shape = shape,
                    )
                } else {
                    Modifier
                },
            ),
        shape = shape,
        color = if (selected) {
            PrimaryLight.copy(alpha = 0.07f)
        } else {
            SurfaceLightPurple.copy(alpha = 0.55f)
        },
        border = if (!selected) {
            BorderStroke(
                width = 1.dp,
                color = BorderLight,
            )
        } else {
            null
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = VideoCompressorTypography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = if (selected) Primary else TextPrimary,
                textAlign = TextAlign.Center,
            )

            Text(
                text = description,
                style = VideoCompressorTypography.bodySmall,
                color = if (selected) Primary else TextSecondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun CompressionSettingsHint() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = SurfaceLightPurple,
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = IconBackground,
                        shape = RoundedCornerShape(12.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                    contentDescription = null,
                    tint = IconPrimary,
                    modifier = Modifier.size(24.dp),
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = stringResource(R.string.custom_hint_title),
                    style = VideoCompressorTypography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = TextPrimary,
                )

                Text(
                    text = stringResource(R.string.custom_hint_description),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
            }
        }
    }
}

@Composable
private fun resolutionLabel(
    resolution: VideoResolution,
): String {
    return when (resolution) {
        VideoResolution.Original -> {
            stringResource(R.string.value_original)
        }

        is VideoResolution.TargetShortSide -> {
            "${resolution.pixels}p"
        }
    }
}

@Composable
private fun frameRateLabel(
    frameRate: VideoFrameRate,
): String {
    return when (frameRate) {
        is VideoFrameRate.Limited -> {
            stringResource(
                R.string.value_fps,
                frameRate.fps,
            )
        }

        VideoFrameRate.Original -> {
            stringResource(R.string.value_original)
        }
    }
}

private fun availableResolutions(
    metadata: VideoMetadata?,
): List<VideoResolution> {

    metadata ?: return listOf(VideoResolution.Original)

    val sourceShortSide = minOf(
        metadata.width,
        metadata.height,
    )

    return buildList {
        if (sourceShortSide > 1080) {
            add(VideoResolution.TargetShortSide(1080))
        }

        if (sourceShortSide > 720) {
            add(VideoResolution.TargetShortSide(720))
        }

        if (sourceShortSide > 480) {
            add(VideoResolution.TargetShortSide(480))
        }

        if (sourceShortSide > 360) {
            add(VideoResolution.TargetShortSide(360))
        }

        add(VideoResolution.Original)
    }
}

private fun availableFrameRates(
    sourceFrameRate: Int?,
): List<VideoFrameRate> {
    return buildList {
        add(VideoFrameRate.Original)

        if (sourceFrameRate == null) {
            return@buildList
        }

        if (sourceFrameRate > 60) {
            add(VideoFrameRate.Limited(60))
        }

        if (sourceFrameRate > 30) {
            add(VideoFrameRate.Limited(30))
        }

        if (sourceFrameRate > 24) {
            add(VideoFrameRate.Limited(24))
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7,
)
@Composable
private fun CustomCompressionSettingsScreenPreview() {
    CustomCompressionSettingsScreen(
        metadata = VideoMetadata(
            width = 1080,
            height = 720,
            frameRate = 29,
            bitrate = 8000,
            durationMs = 500000,
            sizeBytes = 29000000,
            fileName = "Landscape",
        ),
        settings = CompressionSettings(
            resolution = VideoResolution.TargetShortSide(720),
            frameRate = VideoFrameRate.Limited(30),
            quality = CompressionQuality.MEDIUM,
            codec = VideoCodec.H264,
        ),
        onResetClick = {},
        onResolutionSelected = {},
        onFrameRateSelected = {},
        onQualitySelected = {},
        onCodecSelected = {},
    )
}