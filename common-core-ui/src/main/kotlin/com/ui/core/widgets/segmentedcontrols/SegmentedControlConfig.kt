package com.ui.core.widgets.segmentedcontrols

import androidx.compose.runtime.Immutable

/**
 * Configuration for a [SegmentedControl].
 *
 * Defines the content variant (label-only, icon-only, or combined) and the
 * layout orientation (horizontal or vertical).
 *
 * ```kotlin
 * SegmentedControl(
 *     config = SegmentedControlConfig(
 *         title = "View mode",
 *         variant = SegmentedControlConfig.Variant.Label,
 *         orientation = SegmentedControlConfig.Orientation.Horizontal,
 *     ),
 *     segments = segmentsOf(
 *         SegmentedControlSegment(label = "Tab 1"),
 *         SegmentedControlSegment(label = "Tab 2"),
 *     ),
 *     interactionConfig = SegmentedControlInteractionConfig(
 *         selectedIndex = 0,
 *         onSelectedIndexChange = { index -> /* handle */ },
 *     ),
 * )
 * ```
 */
@Immutable
data class SegmentedControlConfig(
    /** Optional title label displayed above the segmented control. */
    val title: String? = null,
    val variant: Variant = Variant.Label,
    val orientation: Orientation = Orientation.Horizontal,
) {
    /**
     * Content variant for every segment in this control.
     *
     * All segments within a single [SegmentedControl] must share the same variant.
     */
    enum class Variant {
        /** Text label only. */
        Label,

        /** Icon only. */
        Icon,

        /** Icon positioned before (leading) the label. */
        LeadingIcon,

        /** Icon positioned after (trailing) the label. */
        TrailingIcon,

        /** Icons on both sides of the label (leading + trailing). */
        BothIcons,
    }

    /**
     * Layout orientation of the segment row/column.
     */
    enum class Orientation {
        /** Segments are arranged side-by-side (default). */
        Horizontal,

        /** Segments are stacked vertically (special cases only). */
        Vertical,
    }
}
