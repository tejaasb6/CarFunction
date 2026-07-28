package com.ui.core.widgets.tiles

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.InteractionConfig

/**
 * Interaction configuration for [Tile].
 *
 * Implements [InteractionConfig] so shared utilities such as
 * [com.ui.core.interaction.interactiveClickable] work transparently.
 *
 * ```kotlin
 * Tile(
 *     config = TileConfig(),
 *     interactionConfig = TileInteractionConfig(
 *         onClick = { navigateToClimate() },
 *         clickDebounceMs = 300L,
 *     ),
 * ) {
 *     Text(state = TextState(text = "Climate".TR))
 * }
 * ```
 */
data class TileInteractionConfig(
    override val onClick: () -> Unit = {},
    override val onLongClick: (() -> Unit)? = null,
    override val onDoubleClick: (() -> Unit)? = null,
    override val clickDebounceMs: Long = 0L,
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : InteractionConfig,
    DistractionOptimizationConfig,
    FocusConfig
