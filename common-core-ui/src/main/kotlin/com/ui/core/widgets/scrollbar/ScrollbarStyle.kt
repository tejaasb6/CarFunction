package com.ui.core.widgets.scrollbar

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

/**
 * Brand-resolved style for [Scrollbar].
 *
 * Provided via [LocalScrollbarStyle] by the brand theme. All dimensions
 * come from design tokens resolved at composition time.
 *
 * ```kotlin
 * val style = LocalScrollbarStyle.current
 * println(style.scrollbarWidth)  // from "Cmp.Size.Global.Scrollbar.Width"
 * println(style.thumbWidth)      // from "Cmp.Size.Global.Scrollbar.Thumb.Width"
 * println(style.trackWidth)      // from "Cmp.Size.Global.Scrollbar.Track.Width"
 * ```
 *
 * @property scrollbarWidth  Total width of the scrollbar container (from "Cmp.Size.Global.Scrollbar.Width").
 * @property thumbWidth      Width of the thumb indicator (from "Cmp.Size.Global.Scrollbar.Thumb.Width").
 * @property trackWidth      Width of the track background (from "Cmp.Size.Global.Scrollbar.Track.Width").
 * @property colors          Thumb and track fill colours.
 */
@Immutable
data class ScrollbarStyle(
    val scrollbarWidth: Dp,
    val thumbWidth: Dp,
    val trackWidth: Dp,
    val colors: ScrollbarColors,
)

/**
 * Composition local that provides the current [ScrollbarStyle].
 *
 * ```kotlin
 * val style = LocalScrollbarStyle.current
 * ```
 */
val LocalScrollbarStyle =
    compositionLocalOf<ScrollbarStyle> {
        error("No ScrollbarStyle — wrap content in a brand theme")
    }
