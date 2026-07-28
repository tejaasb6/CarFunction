package com.ui.core.widgets.toggleswitch

import com.ui.core.widgets.text.TextResource

/**
 * Text content slots for [ToggleSwitch].
 *
 * All fields are optional — a toggle switch with no content shows only the control.
 * Text is supplied as [TextResource] so that both plain strings and Android string
 * resources are supported via the `.TR` extension.
 *
 * ```kotlin
 * ToggleSwitch(
 *     content = ToggleSwitchContent(
 *         label = "Wi-Fi".TR,
 *         hint = "Connect to nearby networks".TR,
 *     ),
 *     interactionConfig = ToggleSwitchInteractionConfig(
 *         selected = isEnabled,
 *         onSelectedChange = { isEnabled = it },
 *     ),
 * )
 * ```
 *
 * @param label Primary label displayed next to the toggle control (single line, ellipsis).
 * @param hint  Supplementary hint text displayed below the label (single line).
 */
data class ToggleSwitchContent(
    val label: TextResource? = null,
    val hint: TextResource? = null,
)
