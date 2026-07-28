package com.ui.core.widgets.tiles

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Per-state stroke colours for a single tile branch (selected **or** unselected).
 *
 * Tiles carry no content-level colour tokens (no label / icon); the visual
 * distinction across states is expressed through the surface stroke only.
 *
 * ```kotlin
 * val idle = TileStateColors(border = Color.Transparent)
 * ```
 */
@Immutable
data class TileStateColors(
    val border: Color,
)

/**
 * Full colour set for one selection branch of a tile.
 *
 * ```kotlin
 * val branch = TileBranchColors(
 *     surfaceFill = Color(0xFFF2F2F2),
 *     stateLayerPressed = Color(0x1A000000),
 *     idle = TileStateColors(border = Color.Transparent),
 *     pressed = TileStateColors(border = Color.Transparent),
 *     disabled = TileStateColors(border = Color.Transparent),
 * )
 * ```
 */
@Immutable
data class TileBranchColors(
    val surfaceFill: Color,
    val stateLayerPressed: Color,
    val idle: TileStateColors,
    val pressed: TileStateColors,
    val disabled: TileStateColors,
)

/**
 * Top-level tile colour set grouping the [unselected] and [selected] branches.
 *
 * ```kotlin
 * val colors = TileTypeColors(
 *     unselected = unselBranch,
 *     selected = selBranch,
 * )
 * ```
 */
@Immutable
data class TileTypeColors(
    val unselected: TileBranchColors,
    val selected: TileBranchColors,
)
