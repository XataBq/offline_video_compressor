package com.example.feature.video.compressor.ui.presentation.components.videoHeroIllustration

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
internal fun CameraTail(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(
                x = size.width,
                y = 0f,
            )

            lineTo(
                x = size.width,
                y = size.height,
            )

            lineTo(
                x = 0f,
                y = size.height * 0.68f,
            )

            lineTo(
                x = 0f,
                y = size.height * 0.32f,
            )

            close()
        }

        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                colorStops = arrayOf(
                    0f to Color(0xFF7653F4),
                    0.55f to Color(0xFF5E38DE),
                    1f to Color(0xFF4924C7),
                ),
            ),
        )

        drawPath(
            path = path,
            color = Color.White.copy(alpha = 0.22f),
            style = Stroke(
                width = 1.dp.toPx(),
            ),
        )
    }
}