package com.ui.core.widgets.navigationbars

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Per-state colour set for a single navigation-bar item in one state
 * (idle, pressed, or disabled).
 *
 * @property labelColor Foreground colour of the text label.
 * @property iconColor Foreground colour of the icon.
 * @property underlineColor Colour of the active-indicator underline.
 * @property surfaceFill Background fill colour of the item surface.
 * @property surfaceStroke Border/stroke colour of the item surface.
 */
@Immutable
data class NavigationBarStateColors(
    val labelColor: Color,
    val iconColor: Color,
    val underlineColor: Color,
    val surfaceFill: Color,
    val surfaceStroke: Color,
)

/**
 * Colour bundle for one branch (selected or unselected) of a navigation-bar item,
 * covering all visual states.
 *
 * @property stateLayerPressed Overlay colour applied on press.
 * @property idle Colours for the resting state.
 * @property pressed Colours for the touch-down state.
 * @property disabled Colours for the non-interactive state.
 */
@Immutable
data class NavigationBarBranchColors(
    val stateLayerPressed: Color,
    val idle: NavigationBarStateColors,
    val pressed: NavigationBarStateColors,
    val disabled: NavigationBarStateColors,
)

/**
 * Full colour definition for a navigation-bar item, combining both branches.
 *
 * @property selected Branch colours when the item is the active/chosen destination.
 * @property unselected Branch colours when the item is inactive.
 */
@Immutable
data class NavigationBarTypeColors(
    val selected: NavigationBarBranchColors,
    val unselected: NavigationBarBranchColors,
)
