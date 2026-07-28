package com.ui.core.widgets.navigationbars

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for the [NavigationBar] widget.
 *
 * Controls global enable/disable and focus preview states for the entire bar.
 * Per-item enable/disable is handled via [NavigationBarItem.enabled].
 *
 * ```kotlin
 * NavigationBar(
 *     config = NavigationBarConfig(),
 *     items = items,
 *     state = NavigationBarState(enabled = true, isFocused = false),
 *     interactionConfig = NavigationBarInteractionConfig(
 *         selectedIndex = selected,
 *         onSelectedIndexChange = { selected = it },
 *     ),
 * )
 * ```
 *
 * @property enabled When `false` the entire bar and all items are non-interactive
 *   and rendered at reduced opacity, regardless of per-item
 *   [NavigationBarItem.enabled].
 * @property isFocused Preview flag — when `true` a forced focus ring is drawn
 *   around the first item regardless of actual focus state. Real D-pad / rotary
 *   focus is handled automatically via
 *   [NavigationBarInteractionConfig.focusRequester].
 */
@Immutable
data class NavigationBarState(
    val enabled: Boolean = true,
    val isFocused: Boolean = false,
)
