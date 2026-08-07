package com.example.feature.video.compressor.ui.presentation.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.VideoFile
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.IconPrimary
import com.example.core_design_system.theme.ShadowPurple
import com.example.core_design_system.theme.Surface
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import com.example.feature.video.compressor.ui.presentation.components.video.VideoMetadataRow
import com.example.feature.video.compressor.ui.presentation.components.video.VideoPreviewPlayer
import com.example.feature.video.compressor.ui.presentation.state.VideoCompressorUiState
import com.example.feature.video.compressor.ui.utils.formatDuration

@Composable
internal fun SelectedVideoCard(
    state: VideoCompressorUiState,
) {
    state.uri ?: return

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
                uri = state.uri,
                videoPreview = state.videoPreview,
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
    uri: Uri,
    videoPreview: Bitmap?,
    duration: String,
    modifier: Modifier = Modifier,
) {
    var isPlaying by remember(uri) {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(18.dp)),
    ) {
        if (isPlaying) {
            VideoPreviewPlayer(
                uri = uri,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            if (videoPreview != null) {
                Image(
                    bitmap = videoPreview.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF7BA4D1),
                                    Color(0xFF355576),
                                    Color(0xFF173D41),
                                ),
                            ),
                        ),
                )
            }

            Surface(
                onClick = {
                    isPlaying = true
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(56.dp),
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.58f),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(
                            R.string.cd_play_video
                        ),
                        tint = Color.White,
                        modifier = Modifier.size(34.dp),
                    )
                }
            }

            Text(
                text = duration,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .background(
                        Color.Black.copy(alpha = 0.68f),
                        RoundedCornerShape(10.dp),
                    )
                    .padding(
                        horizontal = 10.dp,
                        vertical = 5.dp,
                    ),
            )
        }
    }
}
