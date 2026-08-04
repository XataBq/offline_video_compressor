package com.example.feature.video.compressor.ui.presentation.components.videoHeroIllustration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun VideoCameraIllustration(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CameraTail(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 1.dp)
                .size(
                    width = 47.dp,
                    height = 76.dp,
                ),
        )

        CameraBody(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(
                    width = 112.dp,
                    height = 112.dp,
                ),
        )
    }
}