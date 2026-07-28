package com.ui.core.widgets.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Composable function type for a checkbox widget.
 *
 * Brand implementations must handle all states (idle, pressed, disabled) and
 * both selection states (checked/unchecked) with error variant support.
 *
 * Selection state (`selected`) and its callback (`onSelectedChange`) are carried inside
 * [CheckboxInteractionConfig] via [com.ui.core.interaction.SelectionConfig].
 * Focus and distraction optimization are also bundled in the same config.
 */
typealias CheckboxWidgetContent = @Composable (
    modifier: Modifier,
    content: CheckboxContent,
    state: CheckboxState,
    interactionConfig: CheckboxInteractionConfig,
) -> Unit
