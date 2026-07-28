package com.ui.core.widgets.pindisplay

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Per-state colour set for a single PIN indicator dot.
 *
 * @param fill       Background fill of the dot.
 * @param stroke     Stroke colour of the dot border.
 * @param strokeWidth Stroke width of the dot border.
 */
@Immutable
data class PinIndicatorColors(
    val fill: Color,
    val stroke: Color,
    val strokeWidth: Dp,
)

/**
 * Full visual specification for [PinDisplay].
 *
 * @param indicatorSize        Width/height of each indicator dot.
 * @param indicatorCornerRadius Corner radius of each dot (fully round = `indicatorSize / 2`).
 * @param indicatorSpacing     Horizontal gap between adjacent dots.
 * @param containerPadding     Horizontal padding around the dot row.
 * @param defaultColors        Colours for an empty (unfilled) dot.
 * @param filledColors         Colours for a filled dot.
 * @param errorColors          Colours for all dots when in error state.
 */
@Immutable
data class PinDisplayStyle(
    val indicatorSize: Dp,
    val indicatorCornerRadius: Dp,
    val indicatorSpacing: Dp,
    val containerPadding: Dp,
    val defaultColors: PinIndicatorColors,
    val filledColors: PinIndicatorColors,
    val errorColors: PinIndicatorColors,
)

/**
 * Composition local for [PinDisplayStyle].
 *
 * Provided by brand themes (e.g. [com.ui.audi.AudiTheme]).
 * Throws if accessed outside a theme scope.
 */
val LocalPinDisplayStyle =
    compositionLocalOf<PinDisplayStyle> {
        error("No PinDisplayStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
