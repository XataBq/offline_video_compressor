package com.example.feature.video.compressor.ui.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.OfflineVideoCompressorTheme
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import com.example.feature.video.compressor.ui.presentation.brush.premiumPurpleButtonBackground

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun ChooseVideoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cornerRadius = 20.dp
    val buttonShape = RoundedCornerShape(cornerRadius)

    val scope = rememberCoroutineScope()

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by interactionSource.collectIsPressedAsState()

    var clicked by remember {
        mutableStateOf(false)
    }

    val visuallyPressed = isPressed || clicked

    val scale by animateFloatAsState(
        targetValue = if (visuallyPressed) {
            0.97f
        } else {
            1f
        },
        animationSpec = tween(
            durationMillis = 90,
        ),
        label = "choose_video_button_scale",
    )

    Button(
        onClick = {
            scope.launch {
                clicked = true
                delay(120)
                clicked = false
            }

            onClick()
        },
        shape = buttonShape,
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
        ),
        contentPadding = PaddingValues(
            horizontal = 22.dp,
            vertical = 0.dp,
        ),
        modifier = modifier
            .fillMaxWidth(0.82f)
            .height(58.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .dropShadow(
                shape = buttonShape,
                shadow = Shadow(
                    radius = if (visuallyPressed) {
                        8.dp
                    } else {
                        16.dp
                    },
                    spread = 0.dp,
                    offset = DpOffset(
                        x = 0.dp,
                        y = if (visuallyPressed) {
                            3.dp
                        } else {
                            7.dp
                        },
                    ),
                    color = Color(0xFF694DF0).copy(
                        alpha = if (visuallyPressed) {
                            0.20f
                        } else {
                            0.34f
                        },
                    ),
                ),
            )
            .premiumPurpleButtonBackground(
                cornerRadius = cornerRadius,
                pressed = visuallyPressed,
            ),
    ) {
        Icon(
            imageVector = Icons.Outlined.Videocam,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.96f),
            modifier = Modifier.size(20.dp),
        )

        Spacer(
            modifier = Modifier.width(10.dp),
        )

        Text(
            text = stringResource(
                R.string.select_video_button,
            ),
            style = VideoCompressorTypography.titleMedium,
            color = Color.White,
            maxLines = 1,
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun ChooseVideoButtonPreview() {
    OfflineVideoCompressorTheme {
        ChooseVideoButton({})
    }
}