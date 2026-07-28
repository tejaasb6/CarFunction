package com.ui.core.widgets.buttongroups

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.buttons.Button
import com.ui.core.widgets.buttons.ButtonConfig
import com.ui.core.widgets.buttons.ButtonInteractionConfig
import com.ui.core.widgets.buttons.ButtonState
import com.ui.core.widgets.iconbuttons.IconButton
import com.ui.core.widgets.iconbuttons.IconButtonConfig
import com.ui.core.widgets.iconbuttons.IconButtonInteractionConfig
import com.ui.core.widgets.iconbuttons.IconButtonState

/**
 * DSL scope for [ButtonGroup] — enforces that only the correct widget type
 * is added and respects the maximum item count.
 *
 * - When `iconOnly = false`: only [button] is available (max [ButtonGroupConfig.MAX_TEXT_BUTTONS])
 * - When `iconOnly = true`: only [iconButton] is available (max [ButtonGroupConfig.MAX_ICON_BUTTONS])
 *
 * Items beyond the max count are silently ignored.
 */
class ButtonGroupScope internal constructor(
    private val config: ButtonGroupConfig,
) {
    internal val items = mutableListOf<@Composable () -> Unit>()

    private val maxCount: Int
        get() =
            if (config.iconOnly) {
                ButtonGroupConfig.MAX_ICON_BUTTONS
            } else {
                ButtonGroupConfig.MAX_TEXT_BUTTONS
            }

    /**
     * Adds a text [Button] to the group.
     *
     * Only available when [ButtonGroupConfig.iconOnly] is `false`.
     * Silently ignored if max count ([ButtonGroupConfig.MAX_TEXT_BUTTONS]) is reached.
     *
     * Uses the design-system [Button] widget internally.
     */
    fun button(
        config: ButtonConfig = ButtonConfig(tone = ButtonConfig.Tone.Secondary),
        modifier: Modifier = Modifier,
        state: ButtonState = ButtonState(),
        interactionConfig: ButtonInteractionConfig = ButtonInteractionConfig(),
        leading: (@Composable () -> Unit)? = null,
        trailing: (@Composable () -> Unit)? = null,
        label: (@Composable () -> Unit)? = null,
    ) {
        if (this.config.iconOnly) return
        if (items.size >= maxCount) return
        // In horizontal groups, force Fill mode so each button stretches to
        // fill its equal-weight slot — prevents the last button from shrinking
        // when multiple buttons overflow the available width.
        val effectiveConfig =
            if (this.config.alignment == ButtonGroupConfig.Alignment.Horizontal) {
                config.copy(mode = ButtonConfig.Mode.Fill)
            } else {
                config
            }
        items.add {
            Button(
                config = effectiveConfig,
                modifier = modifier,
                state = state,
                interactionConfig = interactionConfig,
                leading = leading,
                trailing = trailing,
                label = label,
            )
        }
    }

    /**
     * Adds an [IconButton] to the group.
     *
     * Only available when [ButtonGroupConfig.iconOnly] is `true`.
     * Silently ignored if max count ([ButtonGroupConfig.MAX_ICON_BUTTONS]) is reached.
     *
     * Uses the design-system [IconButton] widget internally.
     */
    fun iconButton(
        config: IconButtonConfig = IconButtonConfig(tone = IconButtonConfig.Tone.Secondary),
        modifier: Modifier = Modifier,
        state: IconButtonState = IconButtonState(),
        interactionConfig: IconButtonInteractionConfig = IconButtonInteractionConfig(),
        icon: @Composable () -> Unit,
        label: (@Composable () -> Unit)? = null,
    ) {
        if (!this.config.iconOnly) return
        if (items.size >= maxCount) return
        items.add {
            IconButton(
                config = config,
                modifier = modifier,
                state = state,
                interactionConfig = interactionConfig,
                icon = icon,
                label = label,
            )
        }
    }
}
