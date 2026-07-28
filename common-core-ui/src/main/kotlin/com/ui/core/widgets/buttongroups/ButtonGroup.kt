package com.ui.core.widgets.buttongroups

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/** Composable function type for a button group widget. */
typealias ButtonGroupWidgetContent = @Composable (
    config: ButtonGroupConfig,
    modifier: Modifier,
    items: List<@Composable () -> Unit>,
) -> Unit

/**
 * Brand-agnostic button group — arranges multiple buttons horizontally or
 * vertically with token-driven spacing.
 *
 * Uses a **DSL scope** to enforce that only [com.ui.core.widgets.buttons.Button]
 * (when `iconOnly = false`) or [com.ui.core.widgets.iconbuttons.IconButton]
 * (when `iconOnly = true`) can be added. Items beyond the max count are
 * silently ignored.
 *
 * ## Text buttons (max 5)
 * ```kotlin
 * ButtonGroup(config = ButtonGroupConfig(iconOnly = false)) {
 *     button(
 *         config = ButtonConfig(tone = ButtonConfig.Tone.Secondary),
 *         interactionConfig = ButtonInteractionConfig(onClick = { }),
 *         label = { Text(text = "OK") },
 *     )
 *     button(
 *         config = ButtonConfig(tone = ButtonConfig.Tone.Secondary),
 *         interactionConfig = ButtonInteractionConfig(onClick = { }),
 *         label = { Text(text = "Cancel") },
 *     )
 * }
 * ```
 *
 * ## Icon buttons (max 7)
 * ```kotlin
 * ButtonGroup(config = ButtonGroupConfig(iconOnly = true)) {
 *     iconButton(
 *         interactionConfig = IconButtonInteractionConfig(onClick = { }),
 *         icon = { Icon(source = IconSource.Vector(Icons.Filled.Home)) },
 *     )
 * }
 * ```
 */
@Composable
fun ButtonGroup(
    config: ButtonGroupConfig = ButtonGroupConfig(),
    modifier: Modifier = Modifier,
    content: ButtonGroupScope.() -> Unit,
) {
    val scope = ButtonGroupScope(config)
    scope.content()
    LocalWidgets.ButtonGroup.current(config, modifier, scope.items)
}
