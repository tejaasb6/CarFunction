package com.ui.core.widgets.multitogglebuttons

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Colour set for the toggle indicator dots (one per state).
 *
 * The selected indicator uses [selectedFill]/[selectedStroke] at [selectedOpacity].
 * All other indicators use [unselectedFill]/[unselectedStroke] at [unselectedOpacity].
 *
 * Consumers can supply a custom instance via
 * [MultiToggleButton]'s `indicatorColorsOverride` parameter to use brand-specific
 * or use-case-specific colours (e.g. a custom tint beyond Default/Heating/Cooling).
 */
@Immutable
data class MultiToggleButtonIndicatorColors(
    val selectedFill: Color,
    val selectedStroke: Color,
    val unselectedFill: Color,
    val unselectedStroke: Color,
    val selectedOpacity: Float = 1f,
    val unselectedOpacity: Float = 1f,
)
