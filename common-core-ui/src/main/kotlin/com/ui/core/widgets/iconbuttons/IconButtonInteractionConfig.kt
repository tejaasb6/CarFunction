package com.ui.core.widgets.iconbuttons

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.InteractionConfig

/** Interaction configuration for [IconButton]. */
data class IconButtonInteractionConfig(
    override val onClick: () -> Unit = {},
    override val onLongClick: (() -> Unit)? = null,
    override val onDoubleClick: (() -> Unit)? = null,
    override val clickDebounceMs: Long = 0L,
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : InteractionConfig,
    DistractionOptimizationConfig,
    FocusConfig
