package com.ui.core.widgets.radiobuttons

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for [RadioButton].
 *
 * State priority (highest → lowest):
 * `disabled > error > pressed > focused > selected/idle`
 *
 * ```kotlin
 * RadioButton(
 *     state = RadioButtonState(enabled = true, isError = false),
 *     content = RadioButtonContent(label = "Option A".TR),
 *     interactionConfig = RadioButtonInteractionConfig(
 *         selected = isSelected,
 *         onSelectedChange = { isSelected = it },
 *     ),
 * )
 * ```
 *
 * @property enabled when `false` the radio button is non-interactive and rendered at reduced opacity.
 * @property isError when `true` error styling is applied and error text is shown.
 */
@Immutable
data class RadioButtonState(
    val enabled: Boolean = true,
    val isError: Boolean = false,
)
