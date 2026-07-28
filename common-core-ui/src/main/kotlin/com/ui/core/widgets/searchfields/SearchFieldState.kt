package com.ui.core.widgets.searchfields

import androidx.compose.runtime.Immutable

/**
 * Runtime state for [SearchField] matching the Figma State axis.
 *
 * States from Figma:
 * - **Idle**: Default resting state.
 * - **Active**: Focused / being edited.
 * - **Pressed**: Touch / click pressed.
 * - **Disabled**: Cannot interact.
 * - **Loading**: Async search operation in progress.
 *
 * ```kotlin
 * SearchField(
 *     value = query.TR,
 *     onValueChange = { query = it },
 *     state = SearchFieldState(enabled = true, isLoading = false),
 *     content = SearchFieldContent(placeholder = "Search".TR),
 * )
 * ```
 *
 * @property enabled    Whether the field is enabled for interaction.
 * @property isLoading  Whether an async search operation is in progress (shows spinner).
 * @property isFocused  Whether the field is in focused / active state (shows focus ring).
 */
@Immutable
data class SearchFieldState(
    val enabled: Boolean = true,
    val isLoading: Boolean = false,
    val isFocused: Boolean = false,
)
