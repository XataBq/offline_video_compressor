package com.example.feature.video.compressor.ui.presentation.components.video

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.core_design_system.theme.PrimaryLight

@Composable
internal fun VideoProgressBar(
    positionMs: Long,
    durationMs: Long,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    val progress = if (durationMs > 0L) {
        positionMs.toFloat() /
            durationMs.toFloat()
    } else {
        0f
    }

    Slider(
        value = progress.coerceIn(
            0f,
            1f,
        ),
        onValueChange = onSeek,
        modifier = modifier,
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = PrimaryLight,
            inactiveTrackColor = Color.White.copy(
                alpha = 0.35f,
            ),
        ),
    )
}