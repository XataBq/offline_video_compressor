package com.example.feature.video.compressor.ui.presentation.components.videoHeroIllustration

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.OfflineVideoCompressorTheme

@Composable
internal fun VideoHeroIllustration(
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "video_hero_animation",
    )

    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2600,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "video_icon_floating",
    )

    val glowScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3200,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "video_icon_glow",
    )

    val decorationRotation by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4200,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "video_decoration_rotation",
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        HeroBackgroundGlow(
            modifier = Modifier
                .size(195.dp)
                .graphicsLayer {
                    scaleX = glowScale
                    scaleY = glowScale
                }
        )

        FloatingDecorations(
            rotation = decorationRotation,
            modifier = Modifier.matchParentSize(),
        )

        VideoCameraIllustration(
            modifier = Modifier
                .size(
                    width = 145.dp,
                    height = 125.dp,
                )
                .graphicsLayer {
                    translationY = floatingOffset.dp.toPx()
                },
        )
    }
}

@Composable
@Preview(showSystemUi = true)
private fun VideoHeroIllustrationPreview() {
    OfflineVideoCompressorTheme {
        VideoHeroIllustration(
            modifier = Modifier.size(
                width = 260.dp,
                height = 180.dp,
                ),
            )
    }
}