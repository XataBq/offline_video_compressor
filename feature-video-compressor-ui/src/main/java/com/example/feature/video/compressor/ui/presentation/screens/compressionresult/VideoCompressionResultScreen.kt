package com.example.feature.video.compressor.ui.presentation.screens.compressionresult

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AspectRatio
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.VideoFile
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.Background
import com.example.core_design_system.theme.BorderLight
import com.example.core_design_system.theme.IconPrimary
import com.example.core_design_system.theme.OfflineVideoCompressorTheme
import com.example.core_design_system.theme.Primary
import com.example.core_design_system.theme.ShadowPurple
import com.example.core_design_system.theme.Surface
import com.example.core_design_system.theme.SurfaceLightPurple
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.TextTertiary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import com.example.feature.video.compressor.ui.presentation.components.CompressVideoButton
import com.example.feature.video.compressor.ui.presentation.state.VideoCompressorUiState
import com.example.feature_video_compressor_api.model.CompressionPreset
import com.example.feature_video_compressor_api.model.VideoLocation
import com.example.feature_video_compressor_api.model.VideoMetadata
import kotlin.math.roundToInt

@Composable
internal fun VideoCompressionResultScreen(
    state: VideoCompressorUiState,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onEditClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        val isCompact = maxWidth < 600.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState(),
                )
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Top,
                    ),
                )
                .padding(
                    horizontal = if (isCompact) 16.dp else 24.dp,
                    vertical = 16.dp,
                ),
        ) {
            ResultHeader(
                isCompact = isCompact,
                onBackClick = onBackClick,
                onHomeClick = onHomeClick,
            )

            Spacer(
                modifier = Modifier.height(
                    if (isCompact) 16.dp else 22.dp,
                ),
            )

            CompressionSummaryCard(
                state = state,
                isCompact = isCompact,
            )

            Spacer(
                modifier = Modifier.height(16.dp),
            )

            CompressedVideoCard(
                state = state,
                isCompact = isCompact,
                onEditClick = onEditClick,
            )

            Spacer(
                modifier = Modifier.height(22.dp),
            )

            Text(
                text = stringResource(R.string.compression_result_next,),
                style = VideoCompressorTypography.titleMedium,
                color = TextPrimary,
            )

            Spacer(
                modifier = Modifier.height(10.dp),
            )

            ShareCard(
                onClick = onShareClick,
            )

            Spacer(
                modifier = Modifier.height(24.dp),
            )

            CompressVideoButton(
                enabled = true,
                onClick = onHomeClick,
                text = stringResource(
                    R.string.compression_result_home,
                ),
                icon = Icons.Outlined.Home,
            )

            Spacer(
                modifier = Modifier.height(16.dp),
            )
        }
    }
}

@Composable
private fun ResultHeader(
    isCompact: Boolean,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ResultHeaderButton(
            onClick = onBackClick,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(
                R.string.compression_result_cd_back,
            ),
        )

        Spacer(
            modifier = Modifier.width(12.dp),
        )

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = stringResource(
                    R.string.compression_result_title,
                ),
                style = if (isCompact) {
                    VideoCompressorTypography.titleLarge
                } else {
                    VideoCompressorTypography.headlineMedium
                },
                color = TextPrimary,
                maxLines = 2,
            )

            Spacer(
                modifier = Modifier.height(2.dp),
            )

            Text(
                text = stringResource(
                    R.string.compression_result_subtitle,
                ),
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
                maxLines = if (isCompact) 2 else 1,
            )
        }

        Spacer(
            modifier = Modifier.width(10.dp),
        )

        if (isCompact) {
            ResultHeaderButton(
                onClick = onHomeClick,
                icon = Icons.Outlined.Home,
                contentDescription = stringResource(
                    R.string.compression_result_cd_home,
                ),
            )
        } else {
            Surface(
                onClick = onHomeClick,
                shape = RoundedCornerShape(18.dp),
                color = Surface,
                shadowElevation = 3.dp,
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 12.dp,
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(21.dp),
                    )

                    Text(
                        text = stringResource(
                            R.string.compression_result_home,
                        ),
                        style = VideoCompressorTypography.bodyMedium,
                        color = TextPrimary,
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultHeaderButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(46.dp),
        shape = RoundedCornerShape(16.dp),
        color = Surface,
        shadowElevation = 3.dp,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = TextPrimary,
                modifier = Modifier.size(23.dp),
            )
        }
    }
}

@Composable
private fun CompressionSummaryCard(
    state: VideoCompressorUiState,
    isCompact: Boolean,
) {
    val source = state.videoMetadata
    val result = state.compressedVideoMetadata

    ResultCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.size(
                    if (isCompact) 46.dp else 58.dp,
                ),
                shape = CircleShape,
                color = Color(0xFFE6F8EC),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF20B95B),
                        modifier = Modifier.size(
                            if (isCompact) 27.dp else 36.dp,
                        ),
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(12.dp),
            )

            Column {
                Text(
                    text = stringResource(
                        R.string.compression_result_success_title,
                    ),
                    style = VideoCompressorTypography.titleMedium,
                    color = Primary,
                )

                Text(
                    text = stringResource(
                        R.string.compression_result_success_description,
                    ),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp),
        )

        ComparisonHeader()

        HorizontalDivider(
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 6.dp,
            ),
            color = BorderLight,
        )

        ComparisonRow(
            icon = Icons.Outlined.VideoFile,
            title = stringResource(
                R.string.compression_result_size,
            ),
            before = formatSize(
                state.originalSizeMb,
            ),
            after = formatSize(
                state.compressedSizeMb,
            ),
            badge = state.actualSavingsPercent
                ?.let {
                    stringResource(
                        R.string.compression_result_savings_format,
                        it,
                    )
                },
        )

        SummaryDivider()

        ComparisonRow(
            icon = Icons.Outlined.AspectRatio,
            title = stringResource(
                R.string.compression_result_resolution,
            ),
            before = source?.let {
                stringResource(
                    R.string.compression_result_resolution_format,
                    it.width,
                    it.height,
                )
            } ?: "--",
            after = result?.let {
                stringResource(
                    R.string.compression_result_resolution_format,
                    it.width,
                    it.height,
                )
            } ?: "--",
        )

        SummaryDivider()

        ComparisonRow(
            icon = Icons.Outlined.Speed,
            title = stringResource(
                R.string.compression_result_fps,
            ),
            before = source?.frameRate
                ?.let {
                    stringResource(
                        R.string.compression_result_fps_format,
                        it,
                    )
                }
                ?: "--",
            after = result?.frameRate
                ?.let {
                    stringResource(
                        R.string.compression_result_fps_format,
                        it,
                    )
                }
                ?: "--",
        )

        SummaryDivider()

        ComparisonRow(
            icon = Icons.Outlined.Schedule,
            title = stringResource(
                R.string.compression_result_duration,
            ),
            before = source
                ?.durationMs
                ?.formatDuration()
                ?: "--",
            after = result
                ?.durationMs
                ?.formatDuration()
                ?: source
                    ?.durationMs
                    ?.formatDuration()
                ?: "--",
        )
    }
}

@Composable
private fun ComparisonHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier.width(42.dp),
        )

        Spacer(
            modifier = Modifier.width(10.dp),
        )

        Spacer(
            modifier = Modifier.weight(1f),
        )

        Text(
            text = stringResource(
                R.string.compression_result_before,
            ),
            style = VideoCompressorTypography.bodySmall,
            color = TextSecondary,
            modifier = Modifier.weight(0.85f),
            textAlign = TextAlign.Center,
        )

        Text(
            text = stringResource(
                R.string.compression_result_after,
            ),
            style = VideoCompressorTypography.bodySmall,
            color = Primary,
            modifier = Modifier.weight(0.85f),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ComparisonRow(
    icon: ImageVector,
    title: String,
    before: String,
    after: String,
    badge: String? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.size(38.dp),
            shape = RoundedCornerShape(11.dp),
            color = SurfaceLightPurple,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = IconPrimary,
                    modifier = Modifier.size(19.dp),
                )
            }
        }

        Spacer(
            modifier = Modifier.width(10.dp),
        )

        Text(
            text = title,
            style = VideoCompressorTypography.bodySmall,
            color = TextPrimary,
            modifier = Modifier.weight(1f),
            maxLines = 2,
        )

        Text(
            text = before,
            style = VideoCompressorTypography.bodySmall,
            color = TextPrimary,
            modifier = Modifier.weight(0.85f),
            textAlign = TextAlign.Center,
            maxLines = 1,
        )

        Column(
            modifier = Modifier.weight(0.85f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = after,
                style = VideoCompressorTypography.bodySmall,
                color = Primary,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )

            if (badge != null) {
                Spacer(
                    modifier = Modifier.height(3.dp),
                )

                Surface(
                    shape = RoundedCornerShape(7.dp),
                    color = Color(0xFFE5F8EA),
                ) {
                    Text(
                        text = badge,
                        color = Color(0xFF16A34A),
                        style = VideoCompressorTypography.labelMedium,
                        modifier = Modifier.padding(
                            horizontal = 5.dp,
                            vertical = 1.dp,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(
            vertical = 10.dp,
        ),
        color = BorderLight,
    )
}

@Composable
private fun CompressedVideoCard(
    state: VideoCompressorUiState,
    isCompact: Boolean,
    onEditClick: () -> Unit,
) {
    val metadata = state.compressedVideoMetadata

    val preview =
        state.compressedVideoPreview
            ?: state.videoPreview

    ResultCard {
        if (isCompact) {
            Column {
                ResultVideoPreview(
                    bitmap = preview,
                    duration = metadata
                        ?.durationMs
                        ?.formatDuration()
                        .orEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(
                    modifier = Modifier.height(12.dp),
                )

                CompressedVideoInfo(
                    state = state,
                    onEditClick = onEditClick,
                )
            }
        } else {
            Row(
                verticalAlignment = Alignment.Top,
            ) {
                ResultVideoPreview(
                    bitmap = preview,
                    duration = metadata
                        ?.durationMs
                        ?.formatDuration()
                        .orEmpty(),
                    modifier = Modifier.width(190.dp),
                )

                Spacer(
                    modifier = Modifier.width(16.dp),
                )

                CompressedVideoInfo(
                    state = state,
                    onEditClick = onEditClick,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun CompressedVideoInfo(
    state: VideoCompressorUiState,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val metadata =
        state.compressedVideoMetadata

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = metadata
                    ?.fileName
                    ?: stringResource(
                        R.string.compression_result_video_fallback_name,
                    ),
                style = VideoCompressorTypography.titleMedium,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )

            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(38.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(
                        R.string.compression_result_cd_edit,
                    ),
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp),
                )
            }
        }

        Spacer(
            modifier = Modifier.height(6.dp),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = metadata
                    ?.let {
                        stringResource(
                            R.string.compression_result_resolution_format,
                            it.width,
                            it.height,
                        )
                    }
                    ?: "--",
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
            )

            MetadataDot()

            Text(
                text = metadata
                    ?.frameRate
                    ?.let {
                        stringResource(
                            R.string.compression_result_fps_format,
                            it,
                        )
                    }
                    ?: "--",
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
            )

            MetadataDot()

            Text(
                text = formatSize(
                    state.compressedSizeMb,
                ),
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp),
        )

        Surface(
            shape = RoundedCornerShape(11.dp),
            color = Color(0xFFE7F8EC),
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 9.dp,
                    vertical = 6.dp,
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(17.dp),
                )

                Text(
                    text = stringResource(
                        R.string.compression_result_saved,
                    ),
                    style = VideoCompressorTypography.bodySmall,
                    color = Color(0xFF168A3F),
                )
            }
        }
    }
}

@Composable
private fun MetadataDot() {
    Text(
        text = "•",
        style = VideoCompressorTypography.bodySmall,
        color = TextTertiary,
        modifier = Modifier.padding(
            horizontal = 7.dp,
        ),
    )
}

@Composable
private fun ResultVideoPreview(
    bitmap: Bitmap?,
    duration: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(16f / 9f)
            .clip(
                RoundedCornerShape(16.dp),
            ),
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        SurfaceLightPurple,
                    ),
            )
        }

        if (duration.isNotBlank()) {
            Text(
                text = duration,
                color = Color.White,
                style = VideoCompressorTypography.bodySmall,
                modifier = Modifier
                    .align(
                        Alignment.BottomEnd,
                    )
                    .padding(8.dp)
                    .background(
                        color = Color.Black.copy(
                            alpha = 0.65f,
                        ),
                        shape = RoundedCornerShape(
                            7.dp,
                        ),
                    )
                    .padding(
                        horizontal = 6.dp,
                        vertical = 2.dp,
                    ),
            )
        }
    }
}

@Composable
private fun ShareCard(
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Surface,
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.padding(
                16.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.size(46.dp),
                shape = CircleShape,
                color = SurfaceLightPurple,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = stringResource(
                            R.string.compression_result_cd_share,
                        ),
                        tint = Primary,
                        modifier = Modifier.size(23.dp),
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(12.dp),
            )

            Column {
                Text(
                    text = stringResource(
                        R.string.compression_result_share,
                    ),
                    style = VideoCompressorTypography.titleMedium,
                    color = TextPrimary,
                )

                Text(
                    text = stringResource(
                        R.string.compression_result_share_description,
                    ),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
            }
        }
    }
}

@Composable
private fun ResultCard(
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
            modifier = Modifier.padding(16.dp),
            content = content,
        )
    }
}

@Composable
private fun formatSize(
    sizeMb: Double?,
): String {
    sizeMb ?: return "--"

    return if (sizeMb >= 10.0) {
        stringResource(
            R.string.compression_result_size_mb_integer,
            sizeMb.roundToInt(),
        )
    } else {
        stringResource(
            R.string.compression_result_size_mb,
            sizeMb,
        )
    }
}

private fun Long.formatDuration(): String {
    val totalSeconds = this / 1000L

    val hours = totalSeconds / 3600L

    val minutes = (totalSeconds % 3600L) / 60L

    val seconds = totalSeconds % 60L

    return if (hours > 0) {
        "%d:%02d:%02d".format(hours, minutes, seconds,)
    } else {
        "%02d:%02d".format(minutes, seconds,)
    }
}


@Preview(
    name = "Compression Result",
    showBackground = true,
    widthDp = 393,
    heightDp = 852,
)
@Composable
private fun VideoCompressionResultScreenPreview() {
    OfflineVideoCompressorTheme {
        VideoCompressionResultScreen(
            state = VideoCompressorUiState(
                uri = Uri.parse(
                    "content://preview/original_video.mp4",
                ),
                videoMetadata = VideoMetadata(
                    width = 1920,
                    height = 1080,
                    frameRate = 30,
                    bitrate = 6_000_000,
                    durationMs = 165_000L,
                    sizeBytes = 128L * 1024L * 1024L,
                    fileName = "Путешествие_2024.mp4",
                ),
                compressionPreset =
                    CompressionPreset.Balanced,
                compressedVideoLocation =
                    VideoLocation(
                        value =
                            "content://preview/compressed_video.mp4",
                    ),
                compressedVideoMetadata =
                    VideoMetadata(
                        width = 1280,
                        height = 720,
                        frameRate = 30,
                        bitrate = 2_000_000,
                        durationMs = 165_000L,
                        sizeBytes = 51L * 1024L * 1024L,
                        fileName =
                            "Путешествие_2024_сжато.mp4",
                    ),
                completedOriginalSizeBytes =
                    128L * 1024L * 1024L,
                completedCompressedSizeBytes =
                    51L * 1024L * 1024L,
                isCompressing = false,
                compressionProgress = 100,
                compressionTimeLeftMs = 0L,
            ),
            onBackClick = {},
            onHomeClick = {},
            onEditClick = {},
            onShareClick = {},
        )
    }
}