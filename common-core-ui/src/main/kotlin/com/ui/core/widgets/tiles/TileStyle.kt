package com.ui.core.widgets.tiles

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

/**
 * Brand-resolved style for [Tile].
 *
 * Provided via [LocalTileStyle] by the brand theme. All dimensions come from
 * `Cmp.*` / `Sem.*` tokens resolved at composition time.
 *
 * ```kotlin
 * val style = LocalTileStyle.current
 * println(style.cornerRadius)
 * ```
 *
 * @property cornerRadius Corner radius of the tile container.
 * @property minWidth     Minimum width (touch-target aware).
 * @property minHeight    Minimum height (touch-target aware).
 * @property padding      Inner padding applied uniformly to all edges.
 * @property colors       Per-state colour set (selected / unselected branches).
 */
@Immutable
data class TileStyle(
    val cornerRadius: Dp,
    val minWidth: Dp,
    val minHeight: Dp,
    val padding: Dp,
    val colors: TileTypeColors,
)

/**
 * Composition local that provides the current [TileStyle].
 *
 * ```kotlin
 * val style = LocalTileStyle.current
 * ```
 */
val LocalTileStyle =
    compositionLocalOf<TileStyle> {
        error("No TileStyle — wrap content in a brand theme")
    }
