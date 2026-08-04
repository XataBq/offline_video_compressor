package com.example.feature.video.compressor.ui.presentation.components.videoHeroIllustration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
internal fun FloatingDecorations(
    rotation: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.graphicsLayer {
            rotationZ = rotation
        },
    ) {
        FloatingPlus(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    start = 34.dp,
                    top = 25.dp,
                )
                .size(17.dp),
        )

        FloatingCircle(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    end = 45.dp,
                    top = 19.dp,
                )
                .size(12.dp),
        )

        FloatingDiamond(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp)
                .size(11.dp),
        )

        FloatingSparkle(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
                .size(19.dp),
        )

        FloatingPlus(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 43.dp,
                    bottom = 18.dp,
                )
                .size(15.dp),
        )
    }
}

@Composable
private fun FloatingPlus(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.3.dp.toPx()
        val centerX = size.width / 2
        val centerY = size.height / 2

        drawLine(
            color = Color(0xFF8668FA).copy(alpha = 0.78f),
            start = Offset(centerX, 0f),
            end = Offset(centerX, size.height),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )

        drawLine(
            color = Color(0xFF8668FA).copy(alpha = 0.78f),
            start = Offset(0f, centerY),
            end = Offset(size.width, centerY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
private fun FloatingCircle(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = Color(0xFF8A6BFA).copy(alpha = 0.78f),
            style = Stroke(
                width = 2.dp.toPx(),
            ),
        )
    }
}

@Composable
private fun FloatingDiamond(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(
                x = size.width / 2,
                y = 0f,
            )
            lineTo(
                x = size.width,
                y = size.height / 2,
            )
            lineTo(
                x = size.width / 2,
                y = size.height,
            )
            lineTo(
                x = 0f,
                y = size.height / 2,
            )
            close()
        }

        drawPath(
            path = path,
            color = Color(0xFF9A80FF).copy(alpha = 0.75f),
            style = Stroke(
                width = 2.dp.toPx(),
                join = StrokeJoin.Round,
            ),
        )
    }
}
@Composable
private fun FloatingSparkle(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(size.width * 0.5f, 0f)

            cubicTo(
                size.width * 0.54f,
                size.height * 0.36f,
                size.width * 0.64f,
                size.height * 0.46f,
                size.width,
                size.height * 0.5f,
            )

            cubicTo(
                size.width * 0.64f,
                size.height * 0.54f,
                size.width * 0.54f,
                size.height * 0.64f,
                size.width * 0.5f,
                size.height,
            )

            cubicTo(
                size.width * 0.46f,
                size.height * 0.64f,
                size.width * 0.36f,
                size.height * 0.54f,
                0f,
                size.height * 0.5f,
            )

            cubicTo(
                size.width * 0.36f,
                size.height * 0.46f,
                size.width * 0.46f,
                size.height * 0.36f,
                size.width * 0.5f,
                0f,
            )

            close()
        }

        drawPath(
            path = path,
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFBCA9FF),
                    Color(0xFF7656EE),
                ),
            ),
        )
    }
}
