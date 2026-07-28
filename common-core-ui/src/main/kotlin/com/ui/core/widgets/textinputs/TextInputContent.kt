package com.ui.core.widgets.textinputs

import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextResource

/**
 * Text content for TextInput widget matching Figma text properties.
 *
 * All text fields from Figma:
 * - Label: Top label text
 * - Appendix: Top-right optional text
 * - Placeholder: Placeholder when empty
 * - Input Value: The actual input text (passed as `value` parameter)
 * - Leading Unit: Left unit text (e.g., "€")
 * - Trailing Unit: Right unit text (e.g., "km")
 * - Hint: Helper text below input
 * - Error: Error message text (shown when isError=true)
 * - Password Hint: Strength feedback text for password variant
 * - Info Popover Content: Content displayed in info button popover (e.g., password policy)
 *
 * Example:
 * ```kotlin
 * TextInput(
 *     value = text,
 *     onValueChange = { text = it },
 *     content = TextInputContent(
 *         label = "email_label".TR,
 *         placeholder = "email_placeholder".TR,
 *         hint = "email_hint".TR,
 *     )
 * )
 * ```
 *
 * Example with info popover:
 * ```kotlin
 * TextInput(
 *     value = password,
 *     onValueChange = { password = it },
 *     config = TextInputConfig(showInfoButton = true),
 *     content = TextInputContent(
 *         label = "Password".TR,
 *         infoPopoverContent = "Password Policy\n• At least 8 characters\n• Upper & lowercase".TR,
 *     )
 * )
 * ```
 */
@Immutable
data class TextInputContent(
    /** Top label text above the input field. */
    val label: TextResource = EmptyTextResource,
    /** Optional appendix text next to the label (e.g. "optional".TR, "required".TR). */
    val appendix: TextResource = EmptyTextResource,
    /** Placeholder text shown when input is empty. */
    val placeholder: TextResource = EmptyTextResource,
    /** Leading unit text before input (e.g. "euro_symbol".TR). */
    val leadingUnit: TextResource = EmptyTextResource,
    /** Trailing unit text after input (e.g. "km_unit".TR). */
    val trailingUnit: TextResource = EmptyTextResource,
    /** Helper hint text below the input field. */
    val hint: TextResource = EmptyTextResource,
    /** Error message text shown when state.isError is true. */
    val error: TextResource = EmptyTextResource,
    /** Password strength hint text (Password variant only). */
    val passwordHint: TextResource = EmptyTextResource,
    /** Content displayed in info button popover (e.g., password policy with title and requirements). */
    val infoPopoverContent: TextResource = EmptyTextResource,
)
