package com.ui.audi.widgets.tiles

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.tiles.TileBranchColors
import com.ui.core.widgets.tiles.TileStateColors
import com.ui.core.widgets.tiles.TileStyle
import com.ui.core.widgets.tiles.TileTypeColors

/** Audi-brand default [TileStyle] factory. */
internal object TileDefaults {
    @Composable
    fun style(): TileStyle =
        TileStyle(
            cornerRadius =
                Cmp.BorderRadius.Action.Tile.Default
                    .dimension()
                    .pxToDp(),
            minWidth =
                Cmp.Size.Action.Tile.MinWidth
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.Action.Tile.MinHeight
                    .dimension()
                    .pxToDp(),
            padding =
                Cmp.Space.Action.Tile.Padding
                    .dimension()
                    .pxToDp(),
            colors = tileColors(),
        )

    @Composable
    private fun tileColors(): TileTypeColors =
        TileTypeColors(
            unselected = unselectedBranch(),
            selected = selectedBranch(),
        )

    @Composable
    private fun unselectedBranch(): TileBranchColors =
        TileBranchColors(
            surfaceFill =
                Cmp.Color.Action.Tile.Unselected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Tile.Unselected.StateLayer.Pressed
                    .color(),
            idle =
                TileStateColors(
                    border =
                        Cmp.Color.Action.Tile.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                TileStateColors(
                    border =
                        Cmp.Color.Action.Tile.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            disabled =
                TileStateColors(
                    border =
                        Cmp.Color.Action.Tile.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun selectedBranch(): TileBranchColors =
        TileBranchColors(
            surfaceFill =
                Cmp.Color.Action.Tile.Selected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Tile.Selected.StateLayer.Pressed
                    .color(),
            idle =
                TileStateColors(
                    border =
                        Cmp.Color.Action.Tile.Selected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                TileStateColors(
                    border =
                        Cmp.Color.Action.Tile.Selected.Surface.Stroke.Pressed
                            .color(),
                ),
            disabled =
                TileStateColors(
                    border =
                        Cmp.Color.Action.Tile.Selected.Surface.Stroke.Disabled
                            .color(),
                ),
        )
}
