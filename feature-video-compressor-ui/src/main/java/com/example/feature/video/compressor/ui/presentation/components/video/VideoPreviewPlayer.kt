package com.example.feature.video.compressor.ui.presentation.components.video

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.ContentFrame
import com.example.core_design_system.theme.VideoCompressorTypography
import kotlinx.coroutines.delay

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
internal fun VideoPreviewPlayer(
    uri: Uri,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val player = remember(uri) {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
                playWhenReady = true
            }
    }

    var controlsVisible by remember {
        mutableStateOf(true)
    }

    var isPlaying by remember {
        mutableStateOf(player.isPlaying)
    }

    var positionMs by remember {
        mutableLongStateOf(0L)
    }

    var durationMs by remember {
        mutableLongStateOf(0L)
    }

    DisposableEffect(player) {
        val listener = object : Player.Listener {

            override fun onIsPlayingChanged(value: Boolean) {
                isPlaying = value
            }

            override fun onPlaybackStateChanged(
                playbackState: Int,
            ) {
                durationMs = player.duration
                    .takeIf { it > 0 }
                    ?: 0L
            }
        }

        player.addListener(listener)

        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    LaunchedEffect(player) {
        while (true) {
            positionMs = player.currentPosition

            durationMs = player.duration
                .takeIf { it > 0 }
                ?: durationMs

            delay(250L)
        }
    }

    LaunchedEffect(
        controlsVisible,
        isPlaying,
    ) {
        if (
            controlsVisible &&
            isPlaying
        ) {
            delay(2_000L)
            controlsVisible = false
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
            ) {
                controlsVisible = !controlsVisible
            },
    ) {
        ContentFrame(
            player = player,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        AnimatedVisibility(
            visible = controlsVisible,
            modifier = Modifier.align(
                Alignment.Center,
            ),
        ) {
            Surface(
                onClick = {
                    if (player.isPlaying) {
                        player.pause()
                    } else {
                        player.play()
                    }

                    controlsVisible = true
                },
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = Color.Black.copy(
                    alpha = 0.58f,
                ),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (isPlaying) {
                            Icons.Default.Pause
                        } else {
                            Icons.Default.PlayArrow
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(34.dp),
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = controlsVisible,
            modifier = Modifier.align(
                Alignment.BottomCenter,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp,
                    ),
            ) {
                VideoProgressBar(
                    positionMs = positionMs,
                    durationMs = durationMs,
                    onSeek = { progress ->
                        if (durationMs > 0L) {
                            player.seekTo(
                                (durationMs * progress).toLong(),
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = "${positionMs.formatPlayerTime()} / ${durationMs.formatPlayerTime()}",
                    style = VideoCompressorTypography.bodySmall,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.End)
                        .background(
                            color = Color.Black.copy(
                                alpha = 0.55f,
                            ),
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 3.dp,
                        ),
                )
            }
        }
    }
}


private fun Long.formatPlayerTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return "%02d:%02d".format(
        minutes,
        seconds,
    )
}