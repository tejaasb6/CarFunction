package com.ui.core.widgets.iconbuttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/** Composable function type for an icon button widget. */
typealias IconButtonWidgetContent = @Composable (
    config: IconButtonConfig,
    modifier: Modifier,
    state: IconButtonState,
    interactionConfig: IconButtonInteractionConfig,
    icon: @Composable () -> Unit,
    label: (@Composable () -> Unit)?,
    toggle: (@Composable () -> Unit)?,
) -> Unit

/**
 * Brand-agnostic icon button — a circular/rounded interactive button displaying
 * an icon (mandatory) with an optional label below it.
 *
 * Reuses the existing [com.ui.core.widgets.icons.Icon] widget for the icon slot
 * and [com.ui.core.widgets.text.Text] for the optional label slot.
 *
 * ```kotlin
 * IconButton(
 *     config = IconButtonConfig(showLabel = true),
 *     interactionConfig = IconButtonInteractionConfig(onClick = { toggleFavorite() }),
 *     icon = {
 *         Icon(
 *             config = IconConfig(size = IconConfig.Size.MD),
 *             icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorite") },
 *         )
 *     },
 *     label = { Text(text = "Favorite") },
 * )
 * ```
 */
@Composable
fun IconButton(
    config: IconButtonConfig = IconButtonConfig(),
    modifier: Modifier = Modifier,
    state: IconButtonState = IconButtonState(),
    interactionConfig: IconButtonInteractionConfig = IconButtonInteractionConfig(),
    icon: @Composable () -> Unit,
    label: (@Composable () -> Unit)? = null,
    toggle: (@Composable () -> Unit)? = null,
) {
    LocalWidgets.IconButton.current(config, modifier, state, interactionConfig, icon, label, toggle)
}
