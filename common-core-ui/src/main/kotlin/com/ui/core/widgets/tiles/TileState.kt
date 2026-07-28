package com.ui.core.widgets.tiles

import androidx.compose.runtime.Immutable

/**
 * Bundles the preview-state flags consumed by [Tile].
 *
 * Kept under the Compose-compiler `$$changed`-bit arity threshold.
 *
 * ```kotlin
 * Tile(
 *     config = TileConfig(),
 *     state = TileState(enabled = true, isSelected = false, isFocused = false),
 *     interactionConfig = TileInteractionConfig(onClick = {}),
 * ) {
 *     Text(state = TextState(text = "Dashboard".TR))
 * }
 * ```
 *
 * @property enabled When `false` the tile renders at reduced opacity and ignores taps.
 * @property isSelected Drives the selected/unselected colour branch.
 * @property isFocused When `true` a forced focus ring is drawn around the tile.
 */
@Immutable
data class TileState(
    val enabled: Boolean = true,
    val isSelected: Boolean = false,
    val isFocused: Boolean = false,
)
