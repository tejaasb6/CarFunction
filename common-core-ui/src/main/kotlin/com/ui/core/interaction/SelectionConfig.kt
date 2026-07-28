package com.ui.core.interaction

/**
 * Selection configuration for toggle-based widgets (RadioButton, Checkbox, ToggleSwitch).
 *
 * Provides callbacks and semantics for selection state changes.
 * Implement this interface in widget-specific interaction configs.
 */
interface SelectionConfig {
    /** Whether the widget is currently in the selected/checked state. */
    val selected: Boolean

    /** Callback invoked when the user changes the selection state. */
    val onSelectedChange: ((Boolean) -> Unit)?
        get() = null
}
