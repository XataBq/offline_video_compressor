package com.example.core_desing_system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextOnPrimary,

    primaryContainer = SurfacePurple,
    onPrimaryContainer = TextPrimary,

    secondary = PrimaryLight,
    onSecondary = TextOnPrimary,

    secondaryContainer = SurfaceLightPurple,
    onSecondaryContainer = TextPrimary,

    tertiary = PrimarySoft,
    onTertiary = TextOnPrimary,

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceLightPurple,
    onSurfaceVariant = TextSecondary,

    outline = BorderLight,
    outlineVariant = Divider
)

@Composable
fun OfflineVideoCompressorTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = VideoCompressorTypography,
        content = content
    )
}