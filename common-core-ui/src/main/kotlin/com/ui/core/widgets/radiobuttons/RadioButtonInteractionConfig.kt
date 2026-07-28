package com.ui.core.widgets.radiobuttons

import androidx.compose.runtime.Immutable
import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.SelectionConfig

/**
 * Interaction configuration for [RadioButton].
 *
 * Extends [SelectionConfig], [FocusConfig], and [DistractionOptimizationConfig] —
 * no click/tap handling since radio buttons are selection-driven, not action-driven.
 *
 * ```kotlin
 * val focusRequester = remember { FocusRequester() }
 *
 * RadioButton(
 *     interactionConfig = RadioButtonInteractionConfig(
 *         selected = isSelected,
 *         onSelectedChange = { isSelected = it },
 *         focusRequester = focusRequester,
 *         isDistractionOptimized = false,
 *     ),
 * )
 * ```
 *
 * @param selected               Current selection state.
 * @param onSelectedChange       Callback when selection state changes.
 * @param focusRequester         Optional [FocusRequester] for D-pad / Rotary focus.
 * @param isDistractionOptimized When `false`, auto-disable while driving.
 */
@Immutable
data class RadioButtonInteractionConfig(
    override val selected: Boolean = false,
    override val onSelectedChange: ((Boolean) -> Unit)? = null,
    override val focusRequester: FocusRequester? = null,
    override val isDistractionOptimized: Boolean = true,
) : SelectionConfig,
    FocusConfig,
    DistractionOptimizationConfig
