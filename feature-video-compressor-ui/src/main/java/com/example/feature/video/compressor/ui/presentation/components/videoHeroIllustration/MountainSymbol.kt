package com.example.feature.video.compressor.ui.presentation.components.videoHeroIllustration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
internal fun MountainSymbol(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val mountainPath = Path().apply {
            moveTo(
                x = size.width * 0.05f,
                y = size.height * 0.86f,
            )

            lineTo(
                x = size.width * 0.34f,
                y = size.height * 0.35f,
            )

            lineTo(
                x = size.width * 0.49f,
                y = size.height * 0.58f,
            )

            lineTo(
                x = size.width * 0.70f,
                y = size.height * 0.17f,
            )

            lineTo(
                x = size.width * 0.96f,
                y = size.height * 0.86f,
            )

            close()
        }

        drawPath(
            path = mountainPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White,
                    Color(0xFFECE6FF),
                ),
            ),
        )

        drawPath(
            path = mountainPath,
            color = Color.White.copy(alpha = 0.75f),
            style = Stroke(
                width = 1.dp.toPx(),
            ),
        )
    }
}