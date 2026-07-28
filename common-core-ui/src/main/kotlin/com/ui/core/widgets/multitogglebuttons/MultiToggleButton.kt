package com.ui.core.widgets.multitogglebuttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for the brand-provided MultiToggleButton implementation.
 */
typealias MultiToggleButtonWidgetContent = @Composable (
    config: MultiToggleButtonConfig,
    modifier: Modifier,
    state: MultiToggleButtonState,
    interactionConfig: MultiToggleButtonInteractionConfig,
    indicatorColorsOverride: MultiToggleButtonIndicatorColors?,
    icon: (@Composable () -> Unit)?,
    label: (@Composable () -> Unit)?,
) -> Unit

/**
 * A MultiToggleButton cycles through 3–4 predefined states on each tap.
 *
 * Toggle indicator bars at the bottom of the button surface show which
 * state is currently active. The button surface itself reuses the existing
 * [com.ui.core.widgets.buttons.Button] composable.
 *
 * ## Modes
 * - **Hug** — text label, auto-width
 * - **Fill** — text label, fills available width
 * - **Icon** — icon-only button (circular); optional label rendered below the shape
 *
 * ## Variants (indicator colour scheme)
 * - **Default** — neutral
 * - **Heating** — amber/orange accent
 * - **Cooling** — blue/teal accent
 *
 * Consumers can override indicator colours via [indicatorColorsOverride]
 * for custom use-cases beyond the three built-in variants.
 *
 * ## Usage
 * ```kotlin
 * var stateIdx by remember { mutableIntStateOf(0) }
 *
 * MultiToggleButton(
 *     config = MultiToggleButtonConfig(
 *         tone = MultiToggleButtonConfig.Tone.Secondary,
 *         mode = MultiToggleButtonConfig.Mode.Hug,
 *         variant = MultiToggleButtonConfig.Variant.Default,
 *     ),
 *     interactionConfig = MultiToggleButtonInteractionConfig(
 *         currentStateIndex = stateIdx,
 *         statesCount = 3,
 *         onStateChange = { stateIdx = it },
 *     ),
 *     label = { Text("Mode") },
 * )
 * ```
 *
 * @param config                 Tone, mode, and variant.
 * @param modifier               Standard Compose modifier.
 * @param state                  Enable/disable and focus flags.
 * @param interactionConfig      Current state index, states count, callback.
 * @param indicatorColorsOverride Custom indicator colours overriding the variant default.
 * @param icon                   Icon slot (Icon mode, or leading in Label modes).
 * @param label                  Label slot (Label modes, or optional text below icon shape).
 */
@Composable
fun MultiToggleButton(
    config: MultiToggleButtonConfig,
    modifier: Modifier = Modifier,
    state: MultiToggleButtonState = MultiToggleButtonState(),
    interactionConfig: MultiToggleButtonInteractionConfig = MultiToggleButtonInteractionConfig(),
    indicatorColorsOverride: MultiToggleButtonIndicatorColors? = null,
    icon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
) {
    LocalWidgets.MultiToggleButton.current(
        config,
        modifier,
        state,
        interactionConfig,
        indicatorColorsOverride,
        icon,
        label,
    )
}
