package com.example.offlinevideocompressor.presentation.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_design_system.theme.Background
import com.example.core_design_system.theme.Primary
import com.example.core_design_system.theme.ShadowPurple
import com.example.core_design_system.theme.Surface
import com.example.core_design_system.theme.SurfaceLightPurple
import com.example.core_design_system.theme.TextPrimary
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.offlinevideocompressor.R

@Composable
internal fun AppSettingsScreen(
    selectedLanguage: AppLanguage,
    onBackClick: () -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
) {
    val topInset =
        WindowInsets.safeDrawing
            .only(
                WindowInsetsSides.Top,
            )
            .asPaddingValues()
            .calculateTopPadding()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = topInset + 16.dp,
                bottom = 24.dp,
            ),
    ) {
        SettingsHeader(
            onBackClick = onBackClick,
        )

        Spacer(
            modifier = Modifier.height(28.dp),
        )

        Text(
            text = stringResource(
                R.string.settings_language_title,
            ),
            style =
                VideoCompressorTypography.titleLarge,
            color = TextPrimary,
        )

        Spacer(
            modifier = Modifier.height(6.dp),
        )

        Text(
            text = stringResource(
                R.string.settings_language_description,
            ),
            style =
                VideoCompressorTypography.bodyMedium,
            color = TextSecondary,
        )

        Spacer(
            modifier = Modifier.height(18.dp),
        )

        LanguageCard(
            title = stringResource(
                R.string.settings_language_russian,
            ),
            subtitle = stringResource(
                R.string.settings_language_russian_native,
            ),
            flag = "🇷🇺",
            selected =
                selectedLanguage ==
                    AppLanguage.Russian,
            onClick = {
                onLanguageSelected(
                    AppLanguage.Russian,
                )
            },
        )

        Spacer(
            modifier = Modifier.height(12.dp),
        )

        LanguageCard(
            title = stringResource(
                R.string.settings_language_english,
            ),
            subtitle = stringResource(
                R.string.settings_language_english_native,
            ),
            flag = "🇬🇧",
            selected =
                selectedLanguage ==
                    AppLanguage.English,
            onClick = {
                onLanguageSelected(
                    AppLanguage.English,
                )
            },
        )
    }
}

@Composable
private fun SettingsHeader(
    onBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment =
            Alignment.CenterVertically,
    ) {
        Surface(
            onClick = onBackClick,
            modifier =
                Modifier.size(46.dp),
            shape =
                RoundedCornerShape(16.dp),
            color = Surface,
            shadowElevation = 3.dp,
        ) {
            Box(
                contentAlignment =
                    Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = TextPrimary,
                    modifier =
                        Modifier.size(23.dp),
                )
            }
        }

        Spacer(
            modifier = Modifier.width(14.dp),
        )

        Column {
            Text(
                text = stringResource(
                    R.string.settings_title,
                ),
                style =
                    VideoCompressorTypography.titleLarge,
                color = TextPrimary,
            )

            Text(
                text = stringResource(
                    R.string.settings_subtitle,
                ),
                style =
                    VideoCompressorTypography.bodySmall,
                color = TextSecondary,
            )
        }
    }
}

@Composable
private fun LanguageCard(
    title: String,
    subtitle: String,
    flag: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val shape =
        RoundedCornerShape(20.dp)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = if (selected) {
                        10.dp
                    } else {
                        5.dp
                    },
                    color = if (selected) {
                        ShadowPurple
                    } else {
                        Color.Black.copy(
                            alpha = 0.06f,
                        )
                    },
                    offset = DpOffset(
                        x = 0.dp,
                        y = 3.dp,
                    ),
                ),
            ),
        shape = shape,
        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (selected) {
                        SurfaceLightPurple
                    } else {
                        Surface
                    },
            ),
        border =
            if (selected) {
                BorderStroke(
                    width = 1.5.dp,
                    color = Primary,
                )
            } else {
                null
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 16.dp,
                ),
            verticalAlignment =
                Alignment.CenterVertically,
        ) {
            Text(
                text = flag,
                fontSize = 30.sp,
            )

            Spacer(
                modifier =
                    Modifier.width(16.dp),
            )

            Column(
                modifier =
                    Modifier.weight(1f),
            ) {
                Text(
                    text = title,
                    style =
                        VideoCompressorTypography.titleMedium,
                    color = TextPrimary,
                )

                Spacer(
                    modifier =
                        Modifier.height(2.dp),
                )

                Text(
                    text = subtitle,
                    style =
                        VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
            }

            RadioButton(
                selected = selected,
                onClick = null,
                colors =
                    RadioButtonDefaults.colors(
                        selectedColor = Primary,
                    ),
            )
        }
    }
}