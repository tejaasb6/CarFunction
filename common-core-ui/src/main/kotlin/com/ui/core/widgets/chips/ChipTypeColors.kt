package com.ui.core.widgets.chips

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Per-state colour set for one selection branch (selected or unselected) of a chip.
 *
 * Separates icon/label content colours because the design system provides
 * distinct Cmp tokens for each.
 *
 * @property surfaceFill   Surface fill colour in idle state.
 * @property strokeIdle    Border colour — idle.
 * @property strokePressed Border colour — pressed.
 * @property strokeDisabled Border colour — disabled.
 * @property strokeDragged Border colour — dragged.
 * @property stateLayerPressed  State-layer overlay colour — pressed.
 * @property stateLayerDragged  State-layer overlay colour — dragged.
 * @property labelIdle     Label colour — idle.
 * @property labelPressed  Label colour — pressed.
 * @property labelDisabled Label colour — disabled.
 * @property labelDragged  Label colour — dragged.
 * @property iconIdle      Icon colour — idle.
 * @property iconPressed   Icon colour — pressed.
 * @property iconDisabled  Icon colour — disabled.
 * @property iconDragged   Icon colour — dragged.
 */
@Immutable
data class ChipTypeColors(
    val surfaceFill: Color,
    val strokeIdle: Color,
    val strokePressed: Color,
    val strokeDisabled: Color,
    val strokeDragged: Color,
    val stateLayerPressed: Color,
    val stateLayerDragged: Color,
    val labelIdle: Color,
    val labelPressed: Color,
    val labelDisabled: Color,
    val labelDragged: Color,
    val iconIdle: Color,
    val iconPressed: Color,
    val iconDisabled: Color,
    val iconDragged: Color,
)
