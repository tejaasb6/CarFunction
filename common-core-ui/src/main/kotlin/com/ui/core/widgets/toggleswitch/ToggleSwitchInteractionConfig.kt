package com.ui.core.widgets.toggleswitch

import androidx.compose.runtime.Immutable
import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.SelectionConfig

/**
 * Interaction configuration for [ToggleSwitch].
 *
 * Extends [SelectionConfig], [FocusConfig], and [DistractionOptimizationConfig].
 *
 * ```kotlin
 * ToggleSwitch(
 *     interactionConfig = ToggleSwitchInteractionConfig(
 *         selected = isOn,
 *         onSelectedChange = { isOn = it },
 *         focusRequester = focusRequester,
 *         isDistractionOptimized = false,
 *     ),
 * )
 * ```
 *
 * @param selected               Current on/off state.
 * @param onSelectedChange       Callback when the user toggles the switch.
 * @param focusRequester         Optional [FocusRequester] for D-pad / Rotary focus.
 * @param isDistractionOptimized When `false`, auto-disable while driving.
 */
@Immutable
data class ToggleSwitchInteractionConfig(
    override val selected: Boolean = false,
    override val onSelectedChange: ((Boolean) -> Unit)? = null,
    override val focusRequester: FocusRequester? = null,
    override val isDistractionOptimized: Boolean = true,
) : SelectionConfig,
    FocusConfig,
    DistractionOptimizationConfig
