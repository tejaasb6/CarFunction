package com.ui.core.widgets.checkbox

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for [Checkbox].
 *
 * ```kotlin
 * Checkbox(
 *     content = CheckboxContent(label = "Accept terms".TR),
 *     state = CheckboxState(enabled = true, isError = false),
 *     interactionConfig = CheckboxInteractionConfig(
 *         selected = isChecked,
 *         onSelectedChange = { isChecked = it },
 *     ),
 * )
 * ```
 *
 * @property enabled  When `false` the checkbox is non-interactive and dimmed.
 * @property isError  When `true` error styling is applied and [CheckboxContent.error] is shown.
 */
@Immutable
data class CheckboxState(
    val enabled: Boolean = true,
    val isError: Boolean = false,
)
