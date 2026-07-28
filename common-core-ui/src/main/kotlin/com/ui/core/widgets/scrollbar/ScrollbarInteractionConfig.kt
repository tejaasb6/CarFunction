package com.ui.core.widgets.scrollbar

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.InteractionConfig

/**
 * Interaction configuration for [Scrollbar].
 *
 * Implements [InteractionConfig] for consistency with other widgets.
 *
 * **Note**: The scrollbar is **non-interactive** (not touchable or draggable).
 * Scroll callbacks (`onScroll`, `onScrollStart`, `onScrollEnd`) are **not triggered**
 * since all scrolling is controlled through the list content itself.
 *
 * This configuration is maintained for:
 * - Consistency with the widget architecture
 * - Potential future extensibility
 * - Standard interaction fields from [InteractionConfig]
 *
 * All fields default to their "off" state:
 *
 * ```kotlin
 * // Standard usage — visual indicator only
 * Scrollbar(listState = listState)
 * ```
 *
 * @param isDistractionOptimized  Reserved for future use.
 * @param focusRequester       Reserved for future use.
 */
data class ScrollbarInteractionConfig(
    override val onClick: () -> Unit = {},
    override val onLongClick: (() -> Unit)? = null,
    override val onDoubleClick: (() -> Unit)? = null,
    override val clickDebounceMs: Long = 0L,
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : InteractionConfig,
    DistractionOptimizationConfig,
    FocusConfig
