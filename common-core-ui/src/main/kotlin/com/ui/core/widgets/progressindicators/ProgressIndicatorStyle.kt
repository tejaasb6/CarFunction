package com.ui.core.widgets.progressindicators

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Holds all dimension, typography, and colour tokens consumed by the
 * [ProgressIndicator].
 *
 * Example:
 * ```kotlin
 * val style = ProgressIndicatorStyle(
 *     spinnerSizeMD = 48.dp,
 *     spinnerSizeSM = 32.dp,
 *     spinnerStrokeWidthMD = 4.dp,
 *     spinnerStrokeWidthSM = 3.dp,
 *     barLineHeight = 6.dp,
 *     barGap = 4.dp,
 *     labelTextStyle = TextStyle.Default,
 *     valueTextStyle = TextStyle.Default,
 *     rangeTextStyle = TextStyle.Default,
 *     colors = typeColors,
 *     spinnerRotationDurationMs = 1000,
 *     barIndeterminateDurationMs = 1500,
 *     staticDarkSurfaceColor = Color(0xFF1A1A1A),
 * )
 * ```
 *
 * @property spinnerSizeMD diameter of the MD spinner.
 * @property spinnerSizeSM diameter of the SM spinner.
 * @property spinnerStrokeWidthMD stroke width of the MD spinner arc.
 * @property spinnerStrokeWidthSM stroke width of the SM spinner arc.
 * @property barLineHeight height (thickness) of the linear progress bar track.
 * @property barGap vertical gap between label row and bar, and bar and range row.
 * @property labelTextStyle text style for the title/label.
 * @property valueTextStyle text style for the progress value.
 * @property rangeTextStyle text style for the range min/max labels.
 * @property colors per-theme colour sets.
 * @property spinnerRotationDurationMs milliseconds for one full rotation of the
 *  indeterminate spinner. Defaults to 1000 ms.
 * @property barIndeterminateDurationMs milliseconds for the indeterminate bar
 *  segment to travel the full track width. Defaults to 1500 ms.
 * @property staticDarkSurfaceColor background surface colour used when the
 *  [ProgressIndicatorConfig.Theme.StaticDark] theme is active. Sourced from
 *  `Sem.Color.Fill.Primary` by default.
 */
@Immutable
data class ProgressIndicatorStyle(
    val spinnerSizeMD: Dp,
    val spinnerSizeSM: Dp,
    val spinnerStrokeWidthMD: Dp,
    val spinnerStrokeWidthSM: Dp,
    val barLineHeight: Dp,
    val barGap: Dp,
    val labelTextStyle: TextStyle,
    val valueTextStyle: TextStyle,
    val rangeTextStyle: TextStyle,
    val colors: ProgressIndicatorTypeColors,
    val spinnerRotationDurationMs: Int,
    val barIndeterminateDurationMs: Int,
    val staticDarkSurfaceColor: Color,
)

/**
 * Resolves the [ProgressIndicatorThemeColors] for a given theme axis.
 *
 * Example:
 * ```kotlin
 * val resolved = style.colorsForTheme(ProgressIndicatorConfig.Theme.Dynamic)
 * ```
 */
fun ProgressIndicatorStyle.colorsForTheme(theme: ProgressIndicatorConfig.Theme): ProgressIndicatorThemeColors =
    when (theme) {
        ProgressIndicatorConfig.Theme.Dynamic -> colors.nonInverted
        ProgressIndicatorConfig.Theme.StaticDark -> colors.inverted
    }

/**
 * Composition local providing the current [ProgressIndicatorStyle].
 *
 * Example:
 * ```kotlin
 * val style = LocalProgressIndicatorStyle.current
 * ```
 */
val LocalProgressIndicatorStyle =
    compositionLocalOf<ProgressIndicatorStyle> {
        error("No ProgressIndicatorStyle — wrap content in a brand theme")
    }
