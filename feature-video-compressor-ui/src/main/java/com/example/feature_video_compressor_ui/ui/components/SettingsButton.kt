package com.example.feature_video_compressor_ui.ui.components

import android.R.attr.onClick
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.core_desing_system.theme.IconSecondary
import com.example.core_desing_system.theme.Surface
import com.example.core_desing_system.theme.SurfacePurple
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun SettingsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()

    var clicked by remember { mutableStateOf(false) }

    val isPressed by interactionSource.collectIsPressedAsState()
    val pressedState = isPressed || clicked

    val scale by animateFloatAsState(
        targetValue = if (pressedState) 0.92f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "settings_button_scale",
    )

    val elevation by animateDpAsState(
        targetValue = if (pressedState) 2.dp else 10.dp,
        animationSpec = tween(durationMillis = 100),
        label = "settings_button_elevation",
    )

    val containerColor by animateColorAsState(
        targetValue = if (pressedState) {
            SurfacePurple
        } else {
            Surface
        },
        animationSpec = tween(durationMillis = 100),
        label = "settings_button_color",
    )
    Surface(
        onClick = {
            scope.launch {
                clicked = true
                delay(120)
                clicked = false
            }

            onClick()
        },
        interactionSource = interactionSource,
        modifier = modifier
            .size(46.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        shape = RoundedCornerShape(15.dp),
        color = containerColor,
        contentColor = IconSecondary,
        shadowElevation = elevation,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                modifier = Modifier.size(26.dp),
            )
        }
    }
}