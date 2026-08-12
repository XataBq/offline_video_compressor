package com.example.feature.video.compressor.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.PrimaryDark
import com.example.core_design_system.theme.PrimaryLight
import com.example.core_design_system.theme.ShadowPurple
import com.example.core_design_system.theme.TextOnPrimary
import com.example.core_design_system.theme.VideoCompressorTypography

@Composable
internal fun CompressVideoButton(
    enabled: Boolean,
    onClick: () -> Unit,
    text: String,
    icon: ImageVector = Icons.Filled.AutoAwesome,
) {
    val shape =
        RoundedCornerShape(
            20.dp,
        )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 10.dp,
                    color = ShadowPurple,
                    offset = DpOffset(
                        x = 0.dp,
                        y = 5.dp,
                    ),
                ),
            )
            .background(
                brush =
                    Brush.horizontalGradient(
                        colors = listOf(
                            PrimaryLight,
                            PrimaryDark,
                        ),
                    ),
                shape = shape,
            ),
        shape = shape,
        colors =
            ButtonDefaults.buttonColors(
                containerColor =
                    Color.Transparent,
                contentColor =
                    TextOnPrimary,
                disabledContainerColor =
                    Color.Transparent,
                disabledContentColor =
                    TextOnPrimary.copy(
                        alpha = 0.5f,
                    ),
            ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier =
                Modifier.size(
                    26.dp,
                ),
        )

        Spacer(
            modifier =
                Modifier.size(
                    12.dp,
                ),
        )

        Text(
            text = text,
            style =
                VideoCompressorTypography.titleLarge,
        )
    }
}

@Composable
internal fun CompressVideoButton(
    enabled: Boolean,
    onClick: () -> Unit,
    textResId: Int,
) {
    val shape = RoundedCornerShape(20.dp)

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 10.dp,
                    color = ShadowPurple,
                    offset = DpOffset(
                        x = 0.dp,
                        y = 5.dp,
                    ),
                ),
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        PrimaryLight,
                        PrimaryDark,
                    ),
                ),
                shape = shape,
            ),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = TextOnPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = TextOnPrimary.copy(alpha = 0.5f),
        ),
    ) {
        Icon(
            imageVector = Icons.Filled.AutoAwesome,
            contentDescription = null,
            modifier = Modifier.size(29.dp),
        )

        Spacer(modifier = Modifier.size(12.dp))

        Text(
            text = stringResource(textResId),
            style = VideoCompressorTypography.titleLarge,
        )
    }
}