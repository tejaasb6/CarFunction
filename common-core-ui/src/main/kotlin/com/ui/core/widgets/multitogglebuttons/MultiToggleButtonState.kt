package com.ui.core.widgets.multitogglebuttons

import androidx.compose.runtime.Immutable

/**
 * Preview-state flags consumed by [MultiToggleButton].
 */
@Immutable
data class MultiToggleButtonState(
    val enabled: Boolean = true,
    val isFocused: Boolean = false,
)
