package com.example.feature.video.compressor.ui.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.PrimaryDark
import com.example.core_design_system.theme.PrimaryLight
import com.example.core_design_system.theme.PrimaryPale
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R

@Composable
internal fun SavingsIndicator(
    savingsPercent: Int?,
) {
    Box(
        modifier = Modifier.size(80.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            val strokeWidth = 8.dp.toPx()

            drawArc(
                color = PrimaryPale.copy(alpha = 0.45f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Butt,
                ),
            )

            drawArc(
                brush = Brush.sweepGradient(
                    listOf(
                        PrimaryLight,
                        PrimaryDark,
                    ),
                ),
                startAngle = -90f,
                sweepAngle = savingsPercent?.let { 360f * (it / 100f) } ?: 0f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Butt,
                ),
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = savingsPercent?.let {
                    stringResource(
                        R.string.savings_percent,
                        savingsPercent,
                    )
                } ?: "---",
                style = VideoCompressorTypography.titleMedium,
                color = TextSecondary,
            )

            Text(
                text = stringResource(R.string.savings_label),
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
            )
        }
    }
}