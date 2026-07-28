package com.ui.core.widgets.textlink

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.InteractionConfig

/**
 * Interaction configuration for [TextLink].
 *
 * Example:
 * ```kotlin
 * TextLink(
 *     interactionConfig = TextLinkInteractionConfig(
 *         onClick = { navController.navigate("details") },
 *     ),
 *     label = { Text(text = "View details") },
 * )
 * ```
 *
 * @property onClick primary tap handler — navigates to the link target.
 * @property onLongClick optional long-press handler.
 * @property onDoubleClick optional double-tap handler.
 * @property clickDebounceMs minimum ms between accepted clicks; 0 disables.
 * @property isDistractionOptimized when `false`, the link is disabled while driving.
 * @property focusRequester optional requester for programmatic focus control.
 */
data class TextLinkInteractionConfig(
    override val onClick: () -> Unit = {},
    override val onLongClick: (() -> Unit)? = null,
    override val onDoubleClick: (() -> Unit)? = null,
    override val clickDebounceMs: Long = 0L,
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : InteractionConfig,
    DistractionOptimizationConfig,
    FocusConfig
