package com.ui.core.widgets.listitems

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Per-state colour set for a single list-item state
 * (e.g. Idle, Pressed, Disabled within the Selected or Unselected branch).
 *
 * Maps directly to the Cmp token structure:
 * `Cmp.Color.Forms.ListItem.{Selected|Unselected}.Content.{Icon|Label|Sublabel|SublabelIcon}.{State}`
 *
 * @param labelColor       Colour for the primary label text.
 * @param sublabelColor    Colour for the supporting / sublabel text.
 * @param iconColor        Colour for icons (leading, trailing).
 * @param sublabelIconColor Colour for the icon next to the sublabel.
 * @param surfaceFill      Background fill colour of the container.
 * @param stateLayerColor  Overlay colour for hover/pressed state layer.
 */
@Immutable
data class ListItemStateColors(
    val labelColor: Color,
    val sublabelColor: Color,
    val iconColor: Color,
    val sublabelIconColor: Color,
    val surfaceFill: Color,
    val stateLayerColor: Color,
)

/**
 * Colour branch for one selection state (Selected or Unselected).
 *
 * Each branch holds the per-interaction-state colours that the brand implementation
 * resolves based on press/hover/disabled status.
 *
 * Token mapping:
 * - `surfaceFill` ← `Cmp.Color.Forms.ListItem.{branch}.Surface.Fill.Default`
 * - `stateLayerHover` ← `Cmp.Color.Forms.ListItem.{branch}.Surface.StateLayer.Hover`
 * - `stateLayerPressed` ← `Cmp.Color.Forms.ListItem.{branch}.Surface.StateLayer.Pressed`
 * - content colours ← `Cmp.Color.Forms.ListItem.{branch}.Content.{slot}.{state}`
 *
 * @param idle     Colours for idle / default state.
 * @param pressed  Colours for pressed state.
 * @param hover    Colours for hover state.
 * @param disabled Colours for disabled state.
 */
@Immutable
data class ListItemBranchColors(
    val idle: ListItemStateColors,
    val pressed: ListItemStateColors,
    val hover: ListItemStateColors,
    val disabled: ListItemStateColors,
)

/**
 * Complete colour model for [ListItem].
 *
 * Mirrors the Cmp token hierarchy with two selection branches
 * (Selected / Unselected), each containing per-state colour sets.
 *
 * Additionally holds the opacity-layer colour for the unselected branch
 * and the drag shadow reference.
 *
 * @param selected       Colour branch used when `ListItemState.isSelected == true`.
 * @param unselected     Colour branch used when `ListItemState.isSelected == false`.
 * @param opacityLayer   Opacity overlay for the unselected disabled state
 *                       (`Cmp.Color.Forms.ListItem.Unselected.OpacityLayer`).
 * @param disabledContentOpacity Opacity applied to content when disabled
 *                       (`Cmp.Opacity.Forms.ListItem.Content.Disabled`).
 */
@Immutable
data class ListItemTypeColors(
    val selected: ListItemBranchColors,
    val unselected: ListItemBranchColors,
    val opacityLayer: Color,
    val disabledContentOpacity: Float,
)
