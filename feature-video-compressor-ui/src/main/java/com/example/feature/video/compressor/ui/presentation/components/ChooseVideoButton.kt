package com.example.feature.video.compressor.ui.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_desing_system.theme.OfflineVideoCompressorTheme
import com.example.core_desing_system.theme.PrimaryDark
import com.example.core_desing_system.theme.PrimaryLight
import com.example.core_desing_system.theme.TextOnPrimary
import com.example.core_desing_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun ChooseVideoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonShape = RoundedCornerShape(20.dp)

    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var clicked by remember { mutableStateOf(false)}

    val visuallyPressed: Boolean = isPressed || clicked

    val scale by animateFloatAsState(
        targetValue = if(visuallyPressed) 0.95F else 1F,
        animationSpec = tween(80),
        label = "botton_scale"
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
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = TextOnPrimary,
        ),
        modifier = modifier
            .fillMaxWidth(0.8F)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .padding(vertical = 20.dp)
            .height(60.dp)
            .dropShadow(
                shape = buttonShape,
                shadow = Shadow(
                    radius = 10.dp,
                    spread = 0.dp,
                    color = PrimaryLight
                )
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PrimaryLight,
                        PrimaryDark
                    )
                ),
                shape = buttonShape
            ),
        interactionSource = interactionSource,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Outlined.Videocam,
                contentDescription = "video camera icon on button",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(30.dp),
            )
            Text(
                text = stringResource(R.string.select_video_button),
                style = VideoCompressorTypography.titleMedium,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun ChooseVideoButtonPreview() {
    OfflineVideoCompressorTheme {
        ChooseVideoButton({})
    }
}