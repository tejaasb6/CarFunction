package com.ui.core.widgets.navigationbars

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.SegmentSelectionConfig

/**
 * Interaction configuration for [NavigationBar].
 *
 * Carries the selection state via [SegmentSelectionConfig] along with
 * distraction-optimisation and focus control — consistent with every other
 * interactive widget in the design system.
 *
 * ```kotlin
 * var selected by remember { mutableIntStateOf(0) }
 *
 * NavigationBar(
 *     config = NavigationBarConfig(
 *         variant = NavigationBarConfig.Variant.LeadingIcon,
 *         mode = NavigationBarConfig.Mode.Fill,
 *     ),
 *     items = items,
 *     interactionConfig = NavigationBarInteractionConfig(
 *         selectedIndex = selected,
 *         onSelectedIndexChange = { selected = it },
 *     ),
 * )
 * ```
 *
 * @property selectedIndex Zero-based index of the currently active item.
 * @property onSelectedIndexChange Callback invoked with the tapped item's index.
 * @property isDistractionOptimized When `false`, the widget is disabled while the
 *   vehicle is moving.
 * @property focusRequester Optional [FocusRequester] for programmatic D-pad / rotary
 *   focus control.
 */
data class NavigationBarInteractionConfig(
    override val selectedIndex: Int = 0,
    override val onSelectedIndexChange: (Int) -> Unit = {},
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : SegmentSelectionConfig,
    DistractionOptimizationConfig,
    FocusConfig
