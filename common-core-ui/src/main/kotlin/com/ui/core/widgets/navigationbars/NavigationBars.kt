package com.ui.core.widgets.navigationbars

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Brand-agnostic composable function type for the NavigationBar widget.
 *
 * Brand themes provide an implementation via [LocalWidgets.NavigationBar].
 */
typealias NavigationBarWidgetContent = @Composable (
    config: NavigationBarConfig,
    modifier: Modifier,
    state: NavigationBarState,
    items: List<NavigationBarItem>,
    interactionConfig: NavigationBarInteractionConfig,
) -> Unit

/**
 * A horizontal navigation bar that displays a row of destination items.
 *
 * The active brand theme controls the rendering. App code calls this
 * brand-agnostic entry point exclusively.
 *
 * ```kotlin
 * var selected by remember { mutableIntStateOf(0) }
 *
 * NavigationBar(
 *     config = NavigationBarConfig(
 *         variant = NavigationBarConfig.Variant.LeadingIcon,
 *         mode = NavigationBarConfig.Mode.Fill,
 *     ),
 *     items = listOf(
 *         NavigationBarItem(
 *             label = "Home".TR,
 *             icon = IconSource.Resource(R.drawable.ic_home, "Home"),
 *         ),
 *         NavigationBarItem(
 *             label = "Search".TR,
 *             icon = IconSource.Resource(R.drawable.ic_search, "Search"),
 *         ),
 *     ),
 *     state = NavigationBarState(enabled = true),
 *     interactionConfig = NavigationBarInteractionConfig(
 *         selectedIndex = selected,
 *         onSelectedIndexChange = { selected = it },
 *     ),
 * )
 * ```
 *
 * @param config Visual configuration (variant, mode).
 * @param modifier Modifier applied to the bar container.
 * @param state Global enable/disable and focus preview flags.
 * @param items List of [NavigationBarItem] destinations.
 * @param interactionConfig Selection state, distraction optimisation, and focus control.
 */
@Composable
fun NavigationBar(
    config: NavigationBarConfig,
    modifier: Modifier = Modifier,
    state: NavigationBarState = NavigationBarState(),
    items: List<NavigationBarItem> = emptyList(),
    interactionConfig: NavigationBarInteractionConfig = NavigationBarInteractionConfig(),
) {
    LocalWidgets.NavigationBar.current(
        config,
        modifier,
        state,
        items,
        interactionConfig,
    )
}
