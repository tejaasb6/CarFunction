package com.ui.core.widgets.toggleswitch

import androidx.compose.runtime.Immutable

/**
 * Bundles the visual-state flags consumed by [ToggleSwitch].
 *
 * Grouping these booleans into a single immutable data class keeps the
 * [ToggleSwitch] composable signature compact and stays under the
 * Compose-compiler `$$changed`-bit arity threshold.
 *
 * @property enabled         When `false` the toggle is non-interactive and dimmed.
 * @property isLoading       When `true` a spinner replaces the control; tap is suppressed.
 * @property controlLeading  When `true` (default) the switch is before the label;
 *                           when `false` the label comes first.
 */
@Immutable
data class ToggleSwitchState(
    val enabled: Boolean = true,
    val isLoading: Boolean = false,
    val controlLeading: Boolean = true,
)
