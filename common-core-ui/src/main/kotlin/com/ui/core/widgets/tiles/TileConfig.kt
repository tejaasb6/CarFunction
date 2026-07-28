package com.ui.core.widgets.tiles

import androidx.compose.runtime.Immutable

/**
 * Configuration for a [Tile] widget.
 *
 * Tiles are single-action interactive containers — similar to buttons but
 * designed for richer, non-interactive content. Unlike buttons they have no
 * tone axis; all tiles share one visual style.
 *
 * ```kotlin
 * Tile(
 *     config = TileConfig(mode = TileConfig.Mode.Hug),
 *     interactionConfig = TileInteractionConfig(onClick = { /* … */ }),
 * ) {
 *     Text(state = TextState(text = "Climate 22 °C".TR))
 * }
 * ```
 *
 * @property mode Sizing behaviour: [Mode.Hug] wraps content, [Mode.Fill] stretches to
 *   the available width.
 */
@Immutable
data class TileConfig(
    val mode: Mode = Mode.Hug,
) {
    /** Sizing behaviour for the tile container. */
    enum class Mode {
        /** Size to content (default). */
        Hug,

        /** Stretch to the full available width. */
        Fill,
    }
}
