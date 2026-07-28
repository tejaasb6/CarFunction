package com.ui.core.widgets.dividers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a divider widget.
 *
 * Brand implementations must match this signature exactly. The public [Divider]
 * composable delegates to the brand lambda registered in [LocalWidgets.Divider].
 *
 * Example:
 * ```kotlin
 * val myDivider: DividerWidgetContent = { config, modifier ->
 *     // brand-specific rendering
 * }
 * ```
 */
typealias DividerWidgetContent = @Composable (
    config: DividerConfig,
    modifier: Modifier,
) -> Unit

/**
 * Brand-agnostic divider — a horizontal or vertical line that separates content.
 *
 * The divider adapts to the active brand theme automatically. Its colour and
 * thickness are resolved from `Cmp.Color.Global.Divider.Surface.Fill` and
 * `Cmp.Size.Global.Divider.{Horizontal|Vertical}` tokens via [LocalDividerStyle].
 *
 * ## Horizontal divider (default)
 * ```kotlin
 * Divider()
 * ```
 *
 * ## Vertical divider
 * ```kotlin
 * Row(modifier = Modifier.height(48.dp)) {
 *     Text("Left")
 *     Divider(config = DividerConfig(orientation = DividerConfig.Orientation.Vertical))
 *     Text("Right")
 * }
 * ```
 *
 * ## Divider with config-based padding
 * Padding is applied along the divider's length axis — **start/end** for
 * horizontal, **top/bottom** for vertical.
 * ```kotlin
 * Divider(config = DividerConfig(padding = 16.dp))
 * ```
 *
 * ## Full-width with external modifier padding
 * ```kotlin
 * Divider(modifier = Modifier.padding(horizontal = 16.dp))
 * ```
 *
 * @param config orientation and padding configuration.
 * @param modifier applied to the outermost layout node.
 */
@Composable
fun Divider(
    config: DividerConfig = DividerConfig(),
    modifier: Modifier = Modifier,
) {
    LocalWidgets.Divider.current(
        config,
        modifier,
    )
}
