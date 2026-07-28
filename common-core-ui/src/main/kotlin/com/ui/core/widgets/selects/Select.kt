package com.ui.core.widgets.selects

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a brand-themed Select widget.
 *
 * Brand implementations must match this signature exactly. The public [Select]
 * composable delegates to the brand lambda registered in [LocalWidgets.Select].
 */
typealias SelectWidgetContent = @Composable (
    options: List<SelectOption>,
    selectedOption: SelectOption?,
    onOptionSelected: (SelectOption) -> Unit,
    config: SelectConfig,
    modifier: Modifier,
    state: SelectState,
    content: SelectContent,
    slots: SelectSlots,
    onExpandedChange: (Boolean) -> Unit,
) -> Unit

/**
 * Brand-agnostic Select dropdown widget — the **single public API** for rendering
 * a themed select field with a dropdown menu.
 *
 * Text content (label, placeholder, hint, appendix, errorCaption) is bundled in
 * [SelectContent], following the same pattern as [TextInputContent].
 *
 * Selection is fully slot-based — [selectedOption] is the currently selected
 * [SelectOption] whose [SelectOption.label] slot is rendered in the field, and
 * [onOptionSelected] delivers the full [SelectOption] object back so the caller
 * can store and display it.
 *
 * ```kotlin
 * val options = listOf(
 *     SelectOption(label = { Text(state = TextState(text = "Option 1".TR)) }),
 *     SelectOption(label = { Text(state = TextState(text = "Option 2".TR)) }),
 * )
 * var selectedOption by remember { mutableStateOf<SelectOption?>(null) }
 * var expanded by remember { mutableStateOf(false) }
 *
 * Select(
 *     options = options,
 *     selectedOption = selectedOption,
 *     onOptionSelected = { selectedOption = it },
 *     state = SelectState(expanded = expanded),
 *     content = SelectContent(
 *         label = "Choose an option".TR,
 *         placeholder = "Select...".TR,
 *     ),
 *     onExpandedChange = { expanded = it },
 * )
 * ```
 *
 * @param options the list of selectable options displayed in the dropdown menu.
 * @param selectedOption the currently selected [SelectOption]; `null` if no
 *  selection. Its [SelectOption.label] slot is rendered inside the field.
 * @param onOptionSelected callback invoked when the user selects an option;
 *  receives the full [SelectOption] so the caller can store and display it.
 * @param config behavioural variant configuration (option icon visibility, menu behaviour).
 * @param modifier applied to the outermost layout node.
 * @param state runtime state flags (enabled, readOnly, error, expanded).
 * @param content text content bundle (label, appendix, placeholder, hint, errorCaption).
 * @param slots composable slot bundle (leadingIcon, trailingIcon).
 * @param onExpandedChange callback invoked when the dropdown's expanded state should
 *  change; typically `{ newExpanded -> expanded = newExpanded }`.
 */
@Composable
fun Select(
    options: List<SelectOption>,
    selectedOption: SelectOption?,
    onOptionSelected: (SelectOption) -> Unit,
    config: SelectConfig = SelectConfig(),
    modifier: Modifier = Modifier,
    state: SelectState = SelectState(),
    content: SelectContent = SelectContent(),
    slots: SelectSlots = SelectSlots(),
    onExpandedChange: (Boolean) -> Unit = {},
) {
    LocalWidgets.Select.current(
        options,
        selectedOption,
        onOptionSelected,
        config,
        modifier,
        state,
        content,
        slots,
        onExpandedChange,
    )
}
