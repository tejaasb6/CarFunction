package com.ui.core.widgets.navigationbars

import androidx.compose.runtime.Immutable

/**
 * Configuration for the [NavigationBar] widget.
 *
 * Specifies the visual variant (item content layout) and the width mode of
 * the entire bar.
 *
 * **Constraints (design spec):**
 * - The chosen [variant] must be consistent across all items in one bar —
 *   do not mix label-only, icon-only, and icon+label items.
 * - Only one item can be active (selected) at a time.
 * - The item list must contain a minimum of **2** and a maximum of **10** items.
 * - Each item has a dynamic width based on its label length, but token-driven
 *   min/max widths ensure usability and visual clarity.
 * - Labels will be truncated with ellipsis if they are too long.
 * - When items cannot fit the available width the bar automatically becomes
 *   horizontally scrollable so that users can reach off-screen items via a
 *   horizontal drag gesture. Scroll behaviour is determined internally by the
 *   brand implementation — callers do **not** need to opt in.
 * - RTL: item sequence is auto-mirrored by Compose; scroll gesture is mirrored;
 *   icon/label positions switch accordingly.
 *
 * ```kotlin
 * NavigationBar(
 *     config = NavigationBarConfig(
 *         variant = NavigationBarConfig.Variant.LeadingIcon,
 *         mode = NavigationBarConfig.Mode.Fill,
 *     ),
 *     items = listOf(
 *         NavigationBarItem(label = "Home", icon = { Icon(Icons.Filled.Home, null) }),
 *         NavigationBarItem(label = "Search", icon = { Icon(Icons.Filled.Search, null) }),
 *     ),
 *     interactionConfig = NavigationBarInteractionConfig(
 *         selectedIndex = 0,
 *         onSelectedIndexChange = { index -> },
 *     ),
 * )
 * ```
 *
 * @property variant Controls the content layout of each item.
 * @property mode Controls the horizontal sizing behaviour of the bar.
 * @property scrollIndicator When `true`, scroll arrow buttons and fading
 *   gradients are displayed at the edges of the bar when items overflow.
 *   The bar is always swipe-scrollable on overflow regardless of this flag;
 *   this only controls the **visual indicator** affordance. Defaults to `false`.
 */
@Immutable
data class NavigationBarConfig(
    val variant: Variant = Variant.Label,
    val mode: Mode = Mode.Fill,
    val scrollIndicator: Boolean = false,
) {
    /**
     * Content layout variant for individual navigation items.
     *
     * - [Label] — text label only.
     * - [Icon] — icon only.
     * - [LeadingIcon] — icon followed by a text label.
     */
    enum class Variant {
        Label,
        Icon,
        LeadingIcon,
    }

    /**
     * Width mode of the navigation bar container.
     *
     * - [Fill] — items expand to equally fill the available width.
     * - [Hug] — items take only as much width as their content requires.
     */
    enum class Mode {
        Fill,
        Hug,
    }
}
