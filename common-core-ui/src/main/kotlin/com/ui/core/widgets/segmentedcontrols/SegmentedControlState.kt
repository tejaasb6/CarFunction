package com.ui.core.widgets.segmentedcontrols

import androidx.compose.runtime.Immutable

/**
 * Preview-state flags consumed by [SegmentedControl].
 *
 * These flags control global enable/disable and focus states for the entire
 * control. Per-segment enable/disable is handled via
 * [SegmentedControlSegment.enabled].
 *
 * ```kotlin
 * SegmentedControl(
 *     config = SegmentedControlConfig(),
 *     segments = segments,
 *     selectedIndex = selected,
 *     onSelectedIndexChange = { selected = it },
 *     state = SegmentedControlState(enabled = true, isFocused = false),
 * )
 * ```
 */
@Immutable
data class SegmentedControlState(
    /** `false` disables all segments regardless of per-segment [SegmentedControlSegment.enabled]. */
    val enabled: Boolean = true,
    /** When `true` the focus ring is drawn around the currently-focused segment. */
    val isFocused: Boolean = false,
)
