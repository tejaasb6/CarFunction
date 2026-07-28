package com.ui.core.widgets.text

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.InteractionConfig

/**
 * Interaction configuration for [Text].
 *
 * Used with [TextConfig.Type.Clickable] to handle tap events.
 *
 * ```kotlin
 * Text(
 *     state = TextState(text = "Tap me".TR),
 *     config = TextConfig(type = TextConfig.Type.Clickable),
 *     interactionConfig = TextInteractionConfig(onClick = { println("Tapped!") }),
 * )
 * ```
 *
 * @param onClick primary tap handler.
 * @param onLongClick optional long-press handler.
 * @param onDoubleClick optional double-tap handler.
 * @param clickDebounceMs minimum ms between accepted taps (0 = off).
 * @param isDistractionOptimized when `false`, auto-disables while driving.
 * @param focusRequester optional focus requester for programmatic focus.
 */
data class TextInteractionConfig(
    override val onClick: () -> Unit = {},
    override val onLongClick: (() -> Unit)? = null,
    override val onDoubleClick: (() -> Unit)? = null,
    override val clickDebounceMs: Long = 0L,
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : InteractionConfig,
    DistractionOptimizationConfig,
    FocusConfig
