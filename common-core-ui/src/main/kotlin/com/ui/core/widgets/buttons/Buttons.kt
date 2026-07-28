package com.ui.core.widgets.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

typealias ButtonWidgetContent = @Composable (
    config: ButtonConfig,
    modifier: Modifier,
    state: ButtonState,
    interactionConfig: ButtonInteractionConfig,
    leading: (@Composable () -> Unit)?,
    label: (@Composable () -> Unit)?,
    trailing: (@Composable () -> Unit)?,
    toggle: (@Composable () -> Unit)?,
) -> Unit

@Composable
fun Button(
    config: ButtonConfig,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState(),
    interactionConfig: ButtonInteractionConfig = ButtonInteractionConfig(),
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    toggle: (@Composable () -> Unit)? = null,
) {
    LocalWidgets.Button.current(
        config,
        modifier,
        state,
        interactionConfig,
        leading,
        label,
        trailing,
        toggle,
    )
}
