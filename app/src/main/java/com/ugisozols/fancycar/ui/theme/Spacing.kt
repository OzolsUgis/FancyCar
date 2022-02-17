package com.ugisozols.fancycar.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class Spacing(
    val spacingSmall : Dp = 8.dp,
    val spacingMedium : Dp = 16.dp,
    val spacingLarge : Dp = 32.dp,
    val spacingExtraLarge : Dp = 63.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }