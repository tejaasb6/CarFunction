package com.ui.core.widgets.checkbox

import androidx.compose.runtime.Immutable
import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.SelectionConfig

/**
 * Interaction configuration for [Checkbox].
 *
 * Extends [SelectionConfig], [FocusConfig], and [DistractionOptimizationConfig] —
 * no click/tap handling since checkboxes are selection-driven, not action-driven.
 *
 * ```kotlin
 * var isChecked by remember { mutableStateOf(false) }
 *
 * Checkbox(
 *     content = CheckboxContent(label = "Enable notifications".TR),
 *     interactionConfig = CheckboxInteractionConfig(
 *         selected = isChecked,
 *         onSelectedChange = { isChecked = it },
 *         isDistractionOptimized = false,
 *     ),
 * )
 * ```
 *
 * @param selected               Current checked state.
 * @param onSelectedChange       Callback when checked state changes.
 * @param focusRequester         Optional [FocusRequester] for D-pad / Rotary focus.
 * @param isFocused              When `true` a focus ring is drawn around the checkbox
 *                               control regardless of actual system focus. Useful for
 *                               previewing the focused visual state programmatically.
 * @param isDistractionOptimized When `false`, auto-disable while driving.
 */
@Immutable
data class CheckboxInteractionConfig(
    override val selected: Boolean = false,
    override val onSelectedChange: ((Boolean) -> Unit)? = null,
    override val focusRequester: FocusRequester? = null,
    val isFocused: Boolean = false,
    override val isDistractionOptimized: Boolean = true,
) : SelectionConfig,
    FocusConfig,
    DistractionOptimizationConfig
