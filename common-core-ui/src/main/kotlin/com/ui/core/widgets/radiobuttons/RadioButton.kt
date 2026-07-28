package com.ui.core.widgets.radiobuttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a radio button widget.
 *
 * Brand implementations must render the control (selection indicator) and text content
 * according to the visual style in [RadioButtonStyle].
 *
 * Selection state (`selected`) and its callback (`onSelectedChange`) are carried inside
 * [RadioButtonInteractionConfig] via [com.ui.core.interaction.SelectionConfig].
 * Focus and distraction optimization are also bundled in the same config.
 */
typealias RadioButtonWidgetContent = @Composable (
    modifier: Modifier,
    content: RadioButtonContent,
    state: RadioButtonState,
    interactionConfig: RadioButtonInteractionConfig,
) -> Unit

/**
 * Brand-agnostic radio button — the **single public API** for all radio button layouts.
 *
 * ## Content ([RadioButtonContent])
 * - [RadioButtonContent.label] — primary label next to the control
 * - [RadioButtonContent.hint] — supplementary hint text below the label
 * - [RadioButtonContent.appendix] — additional text at the trailing end of the label row
 * - [RadioButtonContent.error] — error message shown when [RadioButtonState.isError] is `true`
 *
 * ## States ([RadioButtonState])
 * - **Idle** — default resting state
 * - **Pressed** — while the user presses the control
 * - **Disabled** — non-interactive, dimmed via opacity (`state.enabled = false`)
 * - **Error** — error styling applied (`state.isError = true`)
 * - **Selected** — when the radio button is checked (`interactionConfig.selected = true`)
 *
 * ## Group behaviour
 * ```kotlin
 * var selectedOption by remember { mutableStateOf("optionA") }
 *
 * Column {
 *     RadioButton(
 *         content = RadioButtonContent(label = "Option A".TR),
 *         interactionConfig = RadioButtonInteractionConfig(
 *             selected = selectedOption == "optionA",
 *             onSelectedChange = { if (it) selectedOption = "optionA" },
 *         ),
 *     )
 * }
 * ```
 *
 * @param modifier           Applied to the outermost layout node.
 * @param content            Text content slots (label, hint, appendix, error).
 * @param state              Runtime state flags (enabled, isError).
 * @param interactionConfig  Selection, focus, and distraction optimization config.
 */
@Composable
fun RadioButton(
    modifier: Modifier = Modifier,
    content: RadioButtonContent = RadioButtonContent(),
    state: RadioButtonState = RadioButtonState(),
    interactionConfig: RadioButtonInteractionConfig = RadioButtonInteractionConfig(),
) {
    LocalWidgets.RadioButton.current(
        modifier,
        content,
        state,
        interactionConfig,
    )
}
