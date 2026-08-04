package com.example.feature.video.compressor.ui.presentation.components.videoHeroIllustration

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
internal fun CameraBody(
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(25.dp)

    Box(
        modifier = modifier
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 15.dp,
                    spread = 1.dp,
                    offset = DpOffset(
                        x = 0.dp,
                        y = 9.dp,
                    ),
                    color = Color(0xFF5230D4).copy(alpha = 0.35f),
                ),
            )
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFFB49FFF),
                        0.18f to Color(0xFF9479FF),
                        0.58f to Color(0xFF7150F2),
                        1f to Color(0xFF4E2CCD),
                    ),
                ),
                shape = shape,
            )
            .innerShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 6.dp,
                    spread = 0.dp,
                    offset = DpOffset(
                        x = 0.dp,
                        y = 2.dp,
                    ),
                    color = Color.White.copy(alpha = 0.35f),
                ),
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.30f),
                shape = shape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        MountainSymbol(
            modifier = Modifier.size(
                width = 67.dp,
                height = 45.dp,
            ),
        )
    }
}