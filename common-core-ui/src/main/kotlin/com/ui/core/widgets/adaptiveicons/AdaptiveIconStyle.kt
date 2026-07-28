package com.ui.core.widgets.adaptiveicons

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/** Visual specification for [AdaptiveIcon]. */
@Immutable
data class AdaptiveIconStyle(
    val containerSize: Dp,
    val iconSize: Dp,
    val backgroundColor: Color,
    val cornerRadius: Dp,
    val borderColor: Color,
    val borderWidth: Dp,
)

val LocalAdaptiveIconStyle =
    compositionLocalOf<AdaptiveIconStyle> {
        error("No AdaptiveIconStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
