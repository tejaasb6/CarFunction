package com.ui.core.widgets.progressindicators

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Colour set for a single theme branch (Dynamic or Static Dark) of the
 * [ProgressIndicator].
 *
 * Example:
 * ```kotlin
 * val colors = ProgressIndicatorThemeColors(
 *     labelColor = Color.White,
 *     valueColor = Color.White,
 *     rangeColor = Color.Gray,
 *     trackFill = Color.DarkGray,
 *     trainFill = Color.Blue,
 * )
 * ```
 *
 * @property labelColor colour for the title/label text (bar variant).
 * @property valueColor colour for the progress-value text (bar variant).
 * @property rangeColor colour for the range labels (bar variant).
 * @property trackFill fill colour for the background track.
 * @property trainFill fill colour for the active/progress indicator train.
 */
@Immutable
data class ProgressIndicatorThemeColors(
    val labelColor: Color,
    val valueColor: Color,
    val rangeColor: Color,
    val trackFill: Color,
    val trainFill: Color,
)

/**
 * Top-level colour container bundling both theme branches.
 *
 * Example:
 * ```kotlin
 * val typeColors = ProgressIndicatorTypeColors(
 *     nonInverted = dynamicColors,
 *     inverted = staticDarkColors,
 * )
 * ```
 *
 * @property nonInverted colours used when [ProgressIndicatorConfig.Theme.Dynamic].
 * @property inverted colours used when [ProgressIndicatorConfig.Theme.StaticDark].
 */
@Immutable
data class ProgressIndicatorTypeColors(
    val nonInverted: ProgressIndicatorThemeColors,
    val inverted: ProgressIndicatorThemeColors,
)
