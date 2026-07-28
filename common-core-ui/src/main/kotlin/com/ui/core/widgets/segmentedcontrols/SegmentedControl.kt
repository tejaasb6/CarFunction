package com.ui.core.widgets.segmentedcontrols

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for the brand-provided SegmentedControl implementation.
 *
 * Registered via [LocalWidgets.SegmentedControl] inside each brand theme.
 */
typealias SegmentedControlWidgetContent = @Composable (
    config: SegmentedControlConfig,
    modifier: Modifier,
    state: SegmentedControlState,
    segments: SegmentedControlSegments,
    interactionConfig: SegmentedControlInteractionConfig,
) -> Unit

/**
 * A SegmentedControl is a horizontal or vertical group of segments (buttons)
 * that allows the user to select **one** option from a set.
 *
 * It is commonly used to toggle between views, filter content, or switch modes
 * within the same context.
 *
 * ## Compile-time segment count safety
 * The segment count is enforced at compile time via [segmentsOf] factory
 * functions that accept exactly 2, 3, 4, 5, or 6 [SegmentedControlSegment]
 * arguments. There is **no way** to construct a [SegmentedControlSegments]
 * with fewer than 2 or more than 6 items.
 *
 * ## Key constraints
 * - Minimum 2 segments, maximum 6 segments (enforced by [segmentsOf]).
 * - All segments share the same [SegmentedControlConfig.Variant] (no mixed formats).
 * - Labels are single-line and will truncate with ellipsis if too long.
 * - In horizontal mode segments share width equally.
 *
 * ## Internal implementation
 * Each segment is rendered using the existing [com.ui.core.widgets.buttons.Button]
 * composable with a scoped style override derived from SegmentedControl design tokens.
 *
 * ## Usage example
 * ```kotlin
 * var selected by remember { mutableIntStateOf(0) }
 *
 * SegmentedControl(
 *     config = SegmentedControlConfig(
 *         variant = SegmentedControlConfig.Variant.Label,
 *         orientation = SegmentedControlConfig.Orientation.Horizontal,
 *     ),
 *     segments = segmentsOf(
 *         SegmentedControlSegment(label = "Day"),
 *         SegmentedControlSegment(label = "Week"),
 *         SegmentedControlSegment(label = "Month"),
 *     ),
 *     interactionConfig = SegmentedControlInteractionConfig(
 *         selectedIndex = selected,
 *         onSelectedIndexChange = { selected = it },
 *     ),
 * )
 * ```
 *
 * @param config            Variant and orientation configuration.
 * @param segments          The segments (2–6 items, created via [segmentsOf]).
 * @param modifier          Standard Compose modifier.
 * @param state             Global enable/disable and focus state flags.
 * @param interactionConfig Selection state, distraction optimisation, and focus requester.
 */
@Composable
fun SegmentedControl(
    config: SegmentedControlConfig,
    segments: SegmentedControlSegments,
    modifier: Modifier = Modifier,
    state: SegmentedControlState = SegmentedControlState(),
    interactionConfig: SegmentedControlInteractionConfig = SegmentedControlInteractionConfig(),
) {
    LocalWidgets.SegmentedControl.current(
        config,
        modifier,
        state,
        segments,
        interactionConfig,
    )
}
