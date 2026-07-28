package com.ui.core.widgets.selects

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for the [Select] widget.
 *
 * ```kotlin
 * SelectState(
 *     enabled = true,
 *     readOnly = false,
 *     error = false,
 *     expanded = false
 * )
 * ```
 *
 * @param enabled whether the select field is interactive; when `false` the field
 *  displays in disabled visual style and ignores user input.
 * @param readOnly when `true`, the select shows its current value but does not open
 *  the dropdown menu on interaction.
 * @param error when `true`, the select displays error visual treatment and uses error
 *  colour tokens.
 * @param expanded controls the visibility of the dropdown menu; when `true` the menu
 *  is shown, when `false` it is hidden.
 */
@Immutable
data class SelectState(
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val error: Boolean = false,
    val expanded: Boolean = false,
)
