package com.ui.core.widgets.segmentedcontrols

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.SegmentSelectionConfig

/**
 * Interaction configuration for [SegmentedControl].
 *
 * Carries segment selection state via [SegmentSelectionConfig] along with
 * distraction-optimisation and focus control.
 *
 * ```kotlin
 * var selected by remember { mutableIntStateOf(0) }
 *
 * SegmentedControl(
 *     config = SegmentedControlConfig(),
 *     segments = segments,
 *     interactionConfig = SegmentedControlInteractionConfig(
 *         selectedIndex = selected,
 *         onSelectedIndexChange = { selected = it },
 *     ),
 * )
 * ```
 */
data class SegmentedControlInteractionConfig(
    override val selectedIndex: Int = 0,
    override val onSelectedIndexChange: (Int) -> Unit = {},
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : SegmentSelectionConfig,
    DistractionOptimizationConfig,
    FocusConfig
