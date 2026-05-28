package com.chobitech.lib.android

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily

private val defaultTg by lazy { Typography() }

fun createTypoGraphy(fontFamily: FontFamily): Typography =
    Typography(
        displayLarge = defaultTg.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = defaultTg.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = defaultTg.displaySmall.copy(fontFamily = fontFamily),

        headlineLarge = defaultTg.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = defaultTg.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = defaultTg.headlineSmall.copy(fontFamily = fontFamily),

        titleLarge = defaultTg.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = defaultTg.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = defaultTg.titleSmall.copy(fontFamily = fontFamily),

        bodyLarge = defaultTg.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = defaultTg.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = defaultTg.bodySmall.copy(fontFamily = fontFamily),

        labelLarge = defaultTg.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = defaultTg.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = defaultTg.labelSmall.copy(fontFamily = fontFamily)
    )
