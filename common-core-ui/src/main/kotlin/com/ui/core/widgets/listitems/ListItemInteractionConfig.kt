package com.ui.core.widgets.listitems

import androidx.compose.runtime.Immutable
import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig
import com.ui.core.interaction.InteractionConfig
import com.ui.core.interaction.SelectionConfig

/**
 * Interaction configuration for [ListItem].
 *
 * Combines [InteractionConfig] (click/tap on the primary interaction area),
 * [SelectionConfig] (checked / selected state), [FocusConfig] (D-pad / Rotary),
 * and [DistractionOptimizationConfig] (auto-disable while driving).
 *
 * ```kotlin
 * var isSelected by remember { mutableStateOf(false) }
 *
 * ListItem(
 *     content = ListItemContent(label = "Preferred network"),
 *     interactionConfig = ListItemInteractionConfig(
 *         onClick = { navigateToDetail() },
 *         selected = isSelected,
 *         onSelectedChange = { isSelected = it },
 *         isDistractionOptimized = true,
 *     ),
 * )
 * ```
 *
 * @param onClick                Primary tap handler on the main interaction area.
 * @param onLongClick            Optional long-press handler (≥ 400 ms).
 * @param onDoubleClick          Optional double-tap handler.
 * @param clickDebounceMs        Minimum ms between accepted taps (0 = disabled; recommended: 300 ms for automotive).
 * @param selected               Current selection/checked state.
 * @param onSelectedChange       Callback fired when selection state changes.
 * @param focusRequester         Optional [FocusRequester] for D-pad / Rotary navigation.
 * @param isDistractionOptimized When `false`, auto-disable while driving.
 * @param onDelete               Callback invoked when the delete button is tapped
 *                               (only applicable when [ListItemConfig.Mode.Delete] is active).
 *                               The delete button is rendered automatically by the brand
 *                               implementation; the consumer only provides the icon via
 *                               [ListItemConfig.deleteIcon] and this callback.
 */
@Immutable
data class ListItemInteractionConfig(
    override val onClick: () -> Unit = {},
    override val onLongClick: (() -> Unit)? = null,
    override val onDoubleClick: (() -> Unit)? = null,
    override val clickDebounceMs: Long = 0L,
    override val selected: Boolean = false,
    override val onSelectedChange: ((Boolean) -> Unit)? = null,
    override val focusRequester: FocusRequester? = null,
    override val isDistractionOptimized: Boolean = true,
    val onDelete: (() -> Unit)? = null,
) : InteractionConfig,
    SelectionConfig,
    FocusConfig,
    DistractionOptimizationConfig
