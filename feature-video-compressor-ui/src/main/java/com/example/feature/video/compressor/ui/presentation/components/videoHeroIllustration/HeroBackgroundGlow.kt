package com.example.feature.video.compressor.ui.presentation.components.videoHeroIllustration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun HeroBackgroundGlow(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .blur(
                radius = 18.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded,
            )
            .background(
                brush = Brush.radialGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF8E72FF).copy(alpha = 0.22f),
                        0.42f to Color(0xFFB8A6FF).copy(alpha = 0.12f),
                        0.76f to Color(0xFFE1D8FF).copy(alpha = 0.06f),
                        1f to Color.Transparent,
                    ),
                ),
                shape = CircleShape,
            ),
    )
}