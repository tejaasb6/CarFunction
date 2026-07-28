package com.ui.core.widgets.toggleswitch

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a toggle switch widget.
 *
 * Selection state (`selected`) and its callback (`onSelectedChange`) are carried inside
 * [ToggleSwitchInteractionConfig] via [com.ui.core.interaction.SelectionConfig].
 */
typealias ToggleSwitchWidgetContent = @Composable (
    modifier: Modifier,
    content: ToggleSwitchContent,
    state: ToggleSwitchState,
    interactionConfig: ToggleSwitchInteractionConfig,
) -> Unit

/**
 * Brand-agnostic toggle switch — a binary on/off control for immediate-effect settings.
 *
 * ## Basic usage
 * ```kotlin
 * ToggleSwitch(
 *     content = ToggleSwitchContent(label = "Wi-Fi".TR),
 *     interactionConfig = ToggleSwitchInteractionConfig(
 *         selected = isWifiOn,
 *         onSelectedChange = { isWifiOn = it },
 *     ),
 * )
 * ```
 *
 * ## With hint + trailing control
 * ```kotlin
 * ToggleSwitch(
 *     content = ToggleSwitchContent(
 *         label = "Bluetooth".TR,
 *         hint = "Discoverable to nearby devices".TR,
 *     ),
 *     state = ToggleSwitchState(controlLeading = false),
 *     interactionConfig = ToggleSwitchInteractionConfig(
 *         selected = isBtOn,
 *         onSelectedChange = { isBtOn = it },
 *     ),
 * )
 * ```
 *
 * ## Loading state
 * ```kotlin
 * ToggleSwitch(
 *     content = ToggleSwitchContent(label = "Connecting...".TR),
 *     state = ToggleSwitchState(isLoading = true),
 *     interactionConfig = ToggleSwitchInteractionConfig(selected = false),
 * )
 * ```
 *
 * @param modifier          Applied to the outermost layout node.
 * @param content           Text content slots (label, hint) as [TextResource].
 * @param state             Visual-state flags (enabled, isLoading, controlLeading).
 * @param interactionConfig Selection, focus, and distraction optimization config.
 */
@Composable
fun ToggleSwitch(
    modifier: Modifier = Modifier,
    content: ToggleSwitchContent = ToggleSwitchContent(),
    state: ToggleSwitchState = ToggleSwitchState(),
    interactionConfig: ToggleSwitchInteractionConfig = ToggleSwitchInteractionConfig(),
) {
    LocalWidgets.ToggleSwitch.current(
        modifier,
        content,
        state,
        interactionConfig,
    )
}
