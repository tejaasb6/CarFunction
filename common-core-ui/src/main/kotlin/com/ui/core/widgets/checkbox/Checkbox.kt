package com.ui.core.widgets.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Brand-agnostic checkbox — the **single public API** for all checkbox layouts.
 *
 * ## Content ([CheckboxContent])
 * The checkbox supports optional text fields around the control (all single-line with ellipsis).
 * Text is specified via [TextResource] (`.TR`), rendered internally by the brand implementation:
 * - [CheckboxContent.label] — primary label next to the control (truncated if overlapping appendix)
 * - [CheckboxContent.hint] — supplementary hint text below the label
 * - [CheckboxContent.appendix] — additional text at the trailing end of the label row
 * - [CheckboxContent.error] — error message shown when [CheckboxState.isError] is `true`
 * - [CheckboxContent.errorIcon] — optional composable slot for an icon before the error text
 *
 * ## State ([CheckboxState])
 * - [CheckboxState.enabled] — when `false` the checkbox is non-interactive and dimmed
 * - [CheckboxState.isError] — when `true` error styling is applied
 *
 * ## Rotary / D-pad focus
 * ```kotlin
 * val focusRequester = remember { FocusRequester() }
 * LaunchedEffect(Unit) { focusRequester.requestFocus() }
 *
 * Checkbox(
 *     content = CheckboxContent(label = "Enable feature".TR),
 *     state = CheckboxState(enabled = true, isError = false),
 *     interactionConfig = CheckboxInteractionConfig(
 *         selected = isChecked,
 *         onSelectedChange = { isChecked = it },
 *         focusRequester = focusRequester,
 *     ),
 * )
 * ```
 *
 * @param modifier           Applied to the outermost layout node.
 * @param content            Text content (label, hint, appendix, error) as [TextResource].
 * @param state              Runtime state flags (enabled, isError).
 * @param interactionConfig  Selection, focus, and distraction optimization config.
 */
@Composable
fun Checkbox(
    modifier: Modifier = Modifier,
    content: CheckboxContent = CheckboxContent(),
    state: CheckboxState = CheckboxState(),
    interactionConfig: CheckboxInteractionConfig = CheckboxInteractionConfig(),
) {
    LocalWidgets.Checkbox.current(
        modifier,
        content,
        state,
        interactionConfig,
    )
}
