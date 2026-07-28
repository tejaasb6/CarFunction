package com.ui.core.widgets.buttons

import androidx.compose.runtime.Immutable

/**
 * Bundles the preview-state flags consumed by [Button].
 * Kept under the Compose-compiler $$changed-bit arity threshold.
 */
@Immutable
data class ButtonState(
    val enabled: Boolean = true,
    val isLoading: Boolean = false,
    val isSelected: Boolean = false,
    val isFocused: Boolean = false,
)
