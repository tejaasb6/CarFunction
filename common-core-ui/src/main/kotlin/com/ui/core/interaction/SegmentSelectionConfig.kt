package com.ui.core.interaction

/**
 * Selection configuration for segment-based widgets (SegmentedControl).
 *
 * Provides the currently selected index and a callback for index changes.
 * Implement this interface in widget-specific interaction configs.
 */
interface SegmentSelectionConfig {
    /** Zero-based index of the currently selected segment. */
    val selectedIndex: Int

    /** Callback invoked when the user selects a different segment. */
    val onSelectedIndexChange: (Int) -> Unit
}
