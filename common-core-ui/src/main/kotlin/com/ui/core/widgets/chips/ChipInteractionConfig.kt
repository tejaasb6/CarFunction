package com.ui.core.widgets.chips

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.InteractionConfig

/**
 * Interaction configuration for [Chip].
 *
 * Extends [InteractionConfig] with a chip-specific [onDismiss] callback for
 * Input chip's trailing dismiss button.
 *
 * Example:
 * ```kotlin
 * Chip(
 *     config = ChipConfig(variant = ChipConfig.Variant.Input),
 *     interactionConfig = ChipInteractionConfig(
 *         onClick   = { selectChip() },
 *         onDismiss = { removeChip() },
 *     ),
 *     label = { Text("tag") },
 * )
 * ```
 *
 * @param onClick                Primary tap handler on the chip body.
 * @param onDismiss              Fired when the Input chip dismiss button is tapped.
 * @param onLongClick            Fired after a sustained press.
 * @param onDoubleClick          Fired on a double-tap.
 * @param clickDebounceMs        Minimum ms between accepted clicks (0 = off).
 * @param isDistractionOptimized `true` → chip remains interactive while driving.
 * @param focusRequester         External [FocusRequester] for programmatic focus.
 */
data class ChipInteractionConfig(
    override val onClick: () -> Unit = {},
    val onDismiss: () -> Unit = {},
    override val onLongClick: (() -> Unit)? = null,
    override val onDoubleClick: (() -> Unit)? = null,
    override val clickDebounceMs: Long = 0L,
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : InteractionConfig,
    DistractionOptimizationConfig,
    FocusConfig
