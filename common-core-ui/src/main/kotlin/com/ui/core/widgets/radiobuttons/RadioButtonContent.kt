package com.ui.core.widgets.radiobuttons

import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextResource

/**
 * Text content slots for [RadioButton].
 *
 * All text fields use [TextResource] for localisation support.
 * Default values are [EmptyTextResource] — the slot is hidden when empty.
 *
 * ```kotlin
 * RadioButton(
 *     content = RadioButtonContent(
 *         label = "Premium Package".TR,
 *         hint = "Includes all features".TR,
 *         appendix = "Recommended".TR,
 *     ),
 *     state = RadioButtonState(),
 *     interactionConfig = RadioButtonInteractionConfig(
 *         selected = isSelected,
 *         onSelectedChange = { isSelected = it },
 *     ),
 * )
 * ```
 *
 * @param label     Primary label displayed next to the radio button control (single line, ellipsis).
 * @param hint      Supplementary hint text displayed below the label (single line).
 * @param appendix  Additional text displayed at the trailing end of the label row (single line).
 * @param error     Error message displayed when the radio button is in error state (single line).
 */
@Immutable
data class RadioButtonContent(
    val label: TextResource = EmptyTextResource,
    val hint: TextResource = EmptyTextResource,
    val appendix: TextResource = EmptyTextResource,
    val error: TextResource = EmptyTextResource,
)
