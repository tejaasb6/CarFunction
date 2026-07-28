package com.ui.core.widgets.dividers

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Describes the orientation and padding of a [Divider].
 *
 * ## Basic usage
 * ```kotlin
 * Divider(config = DividerConfig(orientation = DividerConfig.Orientation.Horizontal))
 * ```
 *
 * ## With padding
 * [padding] is applied **along the length** of the divider line:
 *
 * - **Horizontal** divider → padding is added to the **start** and **end** edges.
 * - **Vertical** divider → padding is added to the **top** and **bottom** edges.
 *
 * ```kotlin
 * // Horizontal divider inset 16 dp from start & end
 * Divider(
 *     config = DividerConfig(
 *         orientation = DividerConfig.Orientation.Horizontal,
 *         padding = 16.dp,
 *     ),
 * )
 *
 * // Vertical divider inset 8 dp from top & bottom
 * Divider(
 *     config = DividerConfig(
 *         orientation = DividerConfig.Orientation.Vertical,
 *         padding = 8.dp,
 *     ),
 * )
 * ```
 *
 * @property orientation the direction in which the divider line is drawn.
 * @property padding padding applied along the divider's length axis. Defaults to `0.dp`.
 */
@Immutable
data class DividerConfig(
    val orientation: Orientation = Orientation.Horizontal,
    val padding: Dp = 0.dp,
) {
    /**
     * Direction in which the divider line is drawn.
     *
     * | Value | Behaviour |
     * |-------|-----------|
     * | [Horizontal] | Thin line spanning the full available width. |
     * | [Vertical] | Thin line spanning the full available height. |
     */
    enum class Orientation {
        Horizontal,
        Vertical,
    }
}
