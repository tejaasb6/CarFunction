package com.ui.core.widgets.chips

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.ui.core.engine.api.BoxShadowData

// ToDo - TrailingButton will get replaced by audi componentButton once the implementation for component button is done.

/**
 * Full visual specification for the Chip widget.
 *
 * Dimensions, typography, and padding are fully token-driven.
 * Chip is always hug-content (no Fill mode).
 *
 * @property cornerRadius       Border radius — `Cmp.BorderRadius.Action.Chip.MD.Default`.
 * @property borderWidth        Stroke width — `Cmp.BorderWidth.Action.Chip.MD.Default`.
 * @property minHeight          Chip height — `Cmp.Size.Action.Chip.MD.Height`.
 * @property paddingHorizontal  H-padding for Filter/Suggestion/Assist — `Cmp.Space.Action.Chip.MD.H-Padding`.
 * @property paddingVertical    V-padding for all variants — `Cmp.Space.Action.Chip.MD.V-Padding`.
 * @property inputPaddingLeft   Left padding for Input variant — `Cmp.Space.Action.Chip.MD.InputChip.L-Padding`.
 * @property inputPaddingRight  Right padding for Input variant — `Cmp.Space.Action.Chip.MD.InputChip.R-Padding`.
 * @property gap                Spacing between slots — `Cmp.Space.Action.Chip.MD.Gap`.
 * @property iconSize           Icon dimension — `Cmp.Size.DataDisplay.Icon.SM.Height`.
 * @property trailingButtonComponentHeight      Dismiss button wrapper height — `Cmp.Size.Action.Chip.MD.Height`.
 * @property trailingButtonTouchTargetHeight    Dismiss button outer touch target height — `Cmp.Size.Action.ComponentButton.SM.TouchTarget.Height`.
 * @property trailingButtonTouchTargetMinWidth  Dismiss button outer touch target min width — `Cmp.Size.Action.ComponentButton.SM.TouchTarget.MinWidth`.
 * @property trailingButtonStateLayerHeight     Dismiss button inner stateLayer height — `Cmp.Size.Action.ComponentButton.SM.StateLayer.Height`.
 * @property trailingButtonStateLayerMinWidth   Dismiss button inner stateLayer min width — `Cmp.Size.Action.ComponentButton.SM.StateLayer.MinWidth`.
 * @property trailingButtonCornerRadius     Dismiss button border radius — `Cmp.BorderRadius.Action.ComponentButton.Default`.
 * @property trailingButtonBorderWidth      Dismiss button border width — `Cmp.BorderWidth.Action.ComponentButton.Idle`.
 * @property trailingButtonSurfaceFill      Dismiss button fill — `Cmp.Color.Action.ComponentButton.Unselected.Default.Surface.Fill`.
 * @property trailingButtonBorderColor      Dismiss button border — `Cmp.Color.Action.ComponentButton.Unselected.Default.Surface.Stroke.Idle`.
 * @property draggedShadow       Box shadow for dragged state — `Cmp.Shadow.Action.Chip.MD.Dragged`.
 * @property dragSurfaceHeight   Inner surface height in drag — `Cmp.Size.Action.Chip.MD.Surface.Height`.
 * @property dragSurfaceMinWidth Inner surface min-width in drag — `Cmp.Size.Action.Chip.MD.Surface.MinWidth`.
 * @property dragOpacityLayerFill Opacity-layer fill behind dragged surface — `Cmp.Color.Action.Chip.MD.Unselected.OpacityLayer.Default`.
 * @property selectedTypography  Label TextStyle for selected mode.
 * @property unselectedTypography Label TextStyle for unselected mode.
 * @property selected            Colour set for the selected branch.
 * @property unselected          Colour set for the unselected branch.
 */
@Immutable
data class ChipStyle(
    val cornerRadius: Dp,
    val borderWidth: Dp,
    val minHeight: Dp,
    val paddingHorizontal: Dp,
    val paddingVertical: Dp,
    val inputPaddingLeft: Dp,
    val inputPaddingRight: Dp,
    val gap: Dp,
    val iconSize: Dp,
    val trailingButtonComponentHeight: Dp,
    val trailingButtonTouchTargetHeight: Dp,
    val trailingButtonTouchTargetMinWidth: Dp,
    val trailingButtonStateLayerHeight: Dp,
    val trailingButtonStateLayerMinWidth: Dp,
    val trailingButtonCornerRadius: Dp,
    val trailingButtonBorderWidth: Dp,
    val trailingButtonSurfaceFill: Color,
    val trailingButtonBorderColor: Color,
    val draggedShadow: BoxShadowData,
    val dragSurfaceHeight: Dp,
    val dragSurfaceMinWidth: Dp,
    val dragOpacityLayerFill: Color,
    val selectedTypography: TextStyle,
    val unselectedTypography: TextStyle,
    val selected: ChipTypeColors,
    val unselected: ChipTypeColors,
)

/** Returns the [ChipTypeColors] for the given selection state. */
fun ChipStyle.colorsForSelection(isSelected: Boolean): ChipTypeColors = if (isSelected) selected else unselected

/** Returns the [TextStyle] for the given selection state. */
fun ChipStyle.typographyForSelection(isSelected: Boolean): TextStyle = if (isSelected) selectedTypography else unselectedTypography

/** Composition local for [ChipStyle]. */
val LocalChipStyle =
    compositionLocalOf<ChipStyle> {
        error("No ChipStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
