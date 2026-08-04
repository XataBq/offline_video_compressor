package com.example.feature.video.compressor.ui.presentation.brush

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal fun Modifier.premiumPurpleButtonBackground(
    cornerRadius: Dp,
    pressed: Boolean,
): Modifier = drawWithCache {
    val radiusPx = cornerRadius.toPx()
    val corners = CornerRadius(radiusPx, radiusPx)

    /*
     * Основной материал.
     *
     * Верх немного светлее, низ — глубже и насыщеннее.
     */
    val baseBrush = Brush.verticalGradient(
        colorStops = arrayOf(
            0.00f to Color(0xFF917BFF),
            0.20f to Color(0xFF8168FC),
            0.58f to Color(0xFF6B4EF1),
            1.00f to Color(0xFF5334D6),
        ),
    )

    /*
     * Неравномерный диагональный блик.
     *
     * Он ярче слева сверху и постепенно исчезает
     * ближе к правой части кнопки.
     */
    val diagonalHighlight = Brush.linearGradient(
        colorStops = arrayOf(
            0.00f to Color.White.copy(alpha = 0.32f),
            0.22f to Color.White.copy(alpha = 0.16f),
            0.48f to Color.White.copy(alpha = 0.04f),
            0.72f to Color.Transparent,
        ),
        start = Offset(
            x = 0f,
            y = 0f,
        ),
        end = Offset(
            x = size.width,
            y = size.height * 0.75f,
        ),
    )

    /*
     * Мягкое светлое пятно в верхней центральной части.
     * Оно создаёт ощущение объёмного материала.
     */
    val centerGlow = Brush.radialGradient(
        colorStops = arrayOf(
            0.00f to Color.White.copy(alpha = 0.13f),
            0.35f to Color.White.copy(alpha = 0.06f),
            1.00f to Color.Transparent,
        ),
        center = Offset(
            x = size.width * 0.48f,
            y = size.height * 0.05f,
        ),
        radius = size.width * 0.55f,
    )

    /*
     * Углубление цвета снизу.
     */
    val bottomShade = Brush.verticalGradient(
        colorStops = arrayOf(
            0.48f to Color.Transparent,
            0.76f to Color(0xFF4123BD).copy(alpha = 0.04f),
            1.00f to Color(0xFF2E168F).copy(alpha = 0.22f),
        ),
    )

    /*
     * Очень слабое затемнение справа.
     * Благодаря ему кнопка не выглядит плоской и симметричной.
     */
    val rightShade = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.55f to Color.Transparent,
            1.00f to Color(0xFF3514A4).copy(alpha = 0.10f),
        ),
    )

    onDrawBehind {
        val pressedAlpha = if (pressed) 0.90f else 1f

        // 1. Основной фиолетовый слой
        drawRoundRect(
            brush = baseBrush,
            cornerRadius = corners,
            alpha = pressedAlpha,
        )

        // 2. Диагональный верхний блик
        drawRoundRect(
            brush = diagonalHighlight,
            cornerRadius = corners,
            alpha = if (pressed) 0.52f else 1f,
        )

        // 3. Центральное мягкое свечение
        drawRoundRect(
            brush = centerGlow,
            cornerRadius = corners,
            alpha = if (pressed) 0.45f else 1f,
        )

        // 4. Затемнение справа
        drawRoundRect(
            brush = rightShade,
            cornerRadius = corners,
        )

        // 5. Затемнение снизу
        drawRoundRect(
            brush = bottomShade,
            cornerRadius = corners,
        )

        // 6. Внутренняя светлая рамка
        drawRoundRect(
            color = Color.White.copy(
                alpha = if (pressed) 0.14f else 0.30f,
            ),
            cornerRadius = corners,
            style = Stroke(
                width = 1.dp.toPx(),
            ),
        )
    }
}