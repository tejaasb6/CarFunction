package com.ui.core.widgets.multitogglebuttons

import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig

/**
 * Interaction configuration for [MultiToggleButton].
 *
 * Carries the current toggle state index and a callback that fires the
 * **next** state index when the user taps.
 *
 * ```kotlin
 * var stateIdx by remember { mutableIntStateOf(0) }
 *
 * MultiToggleButton(
 *     config = MultiToggleButtonConfig(),
 *     interactionConfig = MultiToggleButtonInteractionConfig(
 *         currentStateIndex = stateIdx,
 *         statesCount = 3,
 *         onStateChange = { stateIdx = it },
 *     ),
 *     label = { Text("Mode") },
 * )
 * ```
 *
 * @param currentStateIndex Zero-based index of the currently active state.
 *                          Index 0 is always "unselected"; indices 1..statesCount-1
 *                          are indicator states.
 * @param statesCount       Total number of toggle states (3 or 4).
 *                          - 3 → unselected + 2 indicators
 *                          - 4 → unselected + 3 indicators
 * @param onStateChange     Callback with the next state index.
 */
data class MultiToggleButtonInteractionConfig(
    val currentStateIndex: Int = 0,
    val statesCount: Int = 3,
    val onStateChange: (Int) -> Unit = {},
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : DistractionOptimizationConfig,
    FocusConfig {
    init {
        require(statesCount in 3..4) { "statesCount must be 3 or 4, was $statesCount" }
        require(currentStateIndex in 0 until statesCount) {
            "currentStateIndex must be in 0..${statesCount - 1}, was $currentStateIndex"
        }
    }
}
