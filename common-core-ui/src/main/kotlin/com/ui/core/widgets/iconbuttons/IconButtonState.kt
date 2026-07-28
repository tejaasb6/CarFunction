package com.ui.core.widgets.iconbuttons

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for [IconButton].
 *
 * @property enabled when `false` the icon button is dimmed and non-interactive.
 * @property isLoading when `true` content is replaced by a spinner.
 * @property isSelected when `true` the icon button renders in its selected colour set.
 * @property isFocused when `true` a forced focus ring is drawn.
 */
@Immutable
data class IconButtonState(
    val enabled: Boolean = true,
    val isLoading: Boolean = false,
    val isSelected: Boolean = false,
    val isFocused: Boolean = false,
)
