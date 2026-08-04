package com.example.core_desing_system.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.core_desing_system.R

val InterFontFamily = FontFamily(
    Font(
        resId = R.font.inter_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.inter_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.inter_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.inter_bold,
        weight = FontWeight.Bold
    )
)

val VideoCompressorTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = InterFontFamily,
        fontSize = 30.sp,
        lineHeight = 41.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = InterFontFamily,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = InterFontFamily,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = InterFontFamily,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = InterFontFamily,
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = InterFontFamily,
        fontSize = 16.sp,
        lineHeight = 23.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = InterFontFamily,
        fontSize = 18.sp,
        lineHeight = 22.sp
    )
)
