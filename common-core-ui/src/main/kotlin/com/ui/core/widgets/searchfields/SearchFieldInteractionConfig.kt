package com.ui.core.widgets.searchfields

import androidx.compose.runtime.Immutable
import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig

/**
 * Interaction configuration for [SearchField].
 *
 * Contains callbacks and interaction options only — runtime state flags
 * (enabled, loading, focused, readOnly) belong in [SearchFieldState].
 *
 * Follows the same naming convention as
 * [com.ui.core.widgets.textinputs.TextInputInteractionConfig]:
 * - `onClearClick` — clear button tapped.
 * - `onMicrophoneClick` — microphone / voice button tapped.
 *
 * ```kotlin
 * SearchField(
 *     value = query.TR,
 *     onValueChange = { query = it },
 *     content = SearchFieldContent(placeholder = "Search".TR),
 *     interactionConfig = SearchFieldInteractionConfig(
 *         onClearClick = { query = "" },
 *         onMicrophoneClick = { startVoiceInput() },
 *         isDistractionOptimized = false,
 *     ),
 * )
 * ```
 *
 * @property onClearClick            Callback when the clear (X) button is tapped.
 * @property onMicrophoneClick       Callback when the microphone / voice button is tapped.
 *                                   Named `onMicrophoneClick` to match
 *                                   [TextInputInteractionConfig] convention.
 * @property isDistractionOptimized  `true` -> search field remains interactive while the car
 *                                   moves. `false` -> automatically disabled when moving.
 * @property focusRequester          Attach an external [FocusRequester] for programmatic
 *                                   focus control (D-pad / rotary).
 */
@Immutable
data class SearchFieldInteractionConfig(
    /** Callback when clear (X) button is clicked. */
    val onClearClick: (() -> Unit)? = null,
    /** Callback when microphone / voice button is clicked. */
    val onMicrophoneClick: (() -> Unit)? = null,
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : DistractionOptimizationConfig,
    FocusConfig
