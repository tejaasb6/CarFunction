package com.ui.core.widgets.scrollbar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Colour set for the [Scrollbar] widget.
 *
 * The scrollbar has no per-state colour variation — it is a passive,
 * non-interactive indicator. The two colours represent the **thumb**
 * (active position indicator) and the **track** (background rail).
 *
 * ```kotlin
 * val colors = ScrollbarColors(
 *     thumbColor = Color.Black,
 *     trackColor = Color.Black.copy(alpha = 0.2f),
 * )
 * ```
 *
 * @property thumbColor Fill colour of the thumb (position indicator).
 * @property trackColor Fill colour of the track (background rail).
 */
@Immutable
data class ScrollbarColors(
    val thumbColor: Color,
    val trackColor: Color,
)
