package com.ui.audi.widgets.chips

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.chips.ChipStyle
import com.ui.core.widgets.chips.ChipTypeColors

/** Provides the Audi brand [ChipStyle] from current composition tokens. */
internal object AudiChipDefaults {
    @Composable
    fun style(): ChipStyle =
        ChipStyle(
            cornerRadius =
                Cmp.BorderRadius.Action.Chip.MD.Default
                    .dimension()
                    .pxToDp(),
            borderWidth =
                Cmp.BorderWidth.Action.Chip.MD.Default
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.Action.Chip.MD.Height
                    .dimension()
                    .pxToDp(),
            paddingHorizontal =
                Cmp.Space.Action.Chip.MD.H_Padding
                    .dimension()
                    .pxToDp(),
            paddingVertical =
                Cmp.Space.Action.Chip.MD.V_Padding
                    .dimension()
                    .pxToDp(),
            inputPaddingLeft =
                Cmp.Space.Action.Chip.MD.InputChip.L_Padding
                    .dimension()
                    .pxToDp(),
            inputPaddingRight =
                Cmp.Space.Action.Chip.MD.InputChip.R_Padding
                    .dimension()
                    .pxToDp(),
            gap =
                Cmp.Space.Action.Chip.MD.Gap
                    .dimension()
                    .pxToDp(),
            iconSize =
                Cmp.Size.DataDisplay.Icon.SM.Height
                    .dimension()
                    .pxToDp(),
            trailingButtonComponentHeight =
                Cmp.Size.Action.Chip.MD.Height
                    .dimension()
                    .pxToDp(),
            trailingButtonTouchTargetHeight =
                Cmp.Size.Action.ComponentButton.SM.TouchTarget.Height
                    .dimension()
                    .pxToDp(),
            trailingButtonTouchTargetMinWidth =
                Cmp.Size.Action.ComponentButton.SM.TouchTarget.MinWidth
                    .dimension()
                    .pxToDp(),
            trailingButtonStateLayerHeight =
                Cmp.Size.Action.ComponentButton.SM.StateLayer.Height
                    .dimension()
                    .pxToDp(),
            trailingButtonStateLayerMinWidth =
                Cmp.Size.Action.ComponentButton.SM.StateLayer.MinWidth
                    .dimension()
                    .pxToDp(),
            trailingButtonCornerRadius =
                Cmp.BorderRadius.Action.ComponentButton.Default
                    .dimension()
                    .pxToDp(),
            trailingButtonBorderWidth =
                Cmp.BorderWidth.Action.ComponentButton.Idle
                    .dimension()
                    .pxToDp(),
            trailingButtonSurfaceFill =
                Cmp.Color.Action.ComponentButton.Unselected.Default.Surface.Fill
                    .color(),
            trailingButtonBorderColor =
                Cmp.Color.Action.ComponentButton.Unselected.Default.Surface.Stroke.Idle
                    .color(),
            draggedShadow =
                Cmp.Shadow.Action.Chip.MD.Dragged
                    .boxShadow(),
            dragSurfaceHeight =
                Cmp.Size.Action.Chip.MD.Surface.Height
                    .dimension()
                    .pxToDp(),
            dragSurfaceMinWidth =
                Cmp.Size.Action.Chip.MD.Surface.MinWidth
                    .dimension()
                    .pxToDp(),
            dragOpacityLayerFill =
                Cmp.Color.Action.Chip.MD.Unselected.OpacityLayer.Default
                    .color(),
            selectedTypography =
                Cmp.Typography.Action.Chip.MD.Selected.Label
                    .typography(),
            unselectedTypography =
                Cmp.Typography.Action.Chip.MD.Unselected.Label
                    .typography(),
            selected = selectedColors(),
            unselected = unselectedColors(),
        )

    @Composable
    private fun selectedColors(): ChipTypeColors =
        ChipTypeColors(
            surfaceFill =
                Cmp.Color.Action.Chip.MD.Selected.Surface.Fill
                    .color(),
            strokeIdle =
                Cmp.Color.Action.Chip.MD.Selected.Surface.Stroke.Idle
                    .color(),
            strokePressed =
                Cmp.Color.Action.Chip.MD.Selected.Surface.Stroke.Pressed
                    .color(),
            strokeDisabled =
                Cmp.Color.Action.Chip.MD.Selected.Surface.Stroke.Disabled
                    .color(),
            strokeDragged =
                Cmp.Color.Action.Chip.MD.Selected.Surface.Stroke.Dragged
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Chip.MD.Selected.StateLayer.Pressed
                    .color(),
            stateLayerDragged =
                Cmp.Color.Action.Chip.MD.Selected.StateLayer.Dragged
                    .color(),
            labelIdle =
                Cmp.Color.Action.Chip.MD.Selected.Content.Label.Idle
                    .color(),
            labelPressed =
                Cmp.Color.Action.Chip.MD.Selected.Content.Label.Pressed
                    .color(),
            labelDisabled =
                Cmp.Color.Action.Chip.MD.Selected.Content.Label.Disabled
                    .color(),
            labelDragged =
                Cmp.Color.Action.Chip.MD.Selected.Content.Label.Dragged
                    .color(),
            iconIdle =
                Cmp.Color.Action.Chip.MD.Selected.Content.Icon.Idle
                    .color(),
            iconPressed =
                Cmp.Color.Action.Chip.MD.Selected.Content.Icon.Pressed
                    .color(),
            iconDisabled =
                Cmp.Color.Action.Chip.MD.Selected.Content.Icon.Disabled
                    .color(),
            iconDragged =
                Cmp.Color.Action.Chip.MD.Selected.Content.Icon.Dragged
                    .color(),
        )

    @Composable
    private fun unselectedColors(): ChipTypeColors =
        ChipTypeColors(
            surfaceFill =
                Cmp.Color.Action.Chip.MD.Unselected.Surface.Fill
                    .color(),
            strokeIdle =
                Cmp.Color.Action.Chip.MD.Unselected.Surface.Stroke.Idle
                    .color(),
            strokePressed =
                Cmp.Color.Action.Chip.MD.Unselected.Surface.Stroke.Pressed
                    .color(),
            strokeDisabled =
                Cmp.Color.Action.Chip.MD.Unselected.Surface.Stroke.Disabled
                    .color(),
            strokeDragged =
                Cmp.Color.Action.Chip.MD.Unselected.Surface.Stroke.Dragged
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Chip.MD.Unselected.StateLayer.Pressed
                    .color(),
            stateLayerDragged =
                Cmp.Color.Action.Chip.MD.Unselected.StateLayer.Dragged
                    .color(),
            labelIdle =
                Cmp.Color.Action.Chip.MD.Unselected.Content.Label.Idle
                    .color(),
            labelPressed =
                Cmp.Color.Action.Chip.MD.Unselected.Content.Label.Pressed
                    .color(),
            labelDisabled =
                Cmp.Color.Action.Chip.MD.Unselected.Content.Label.Disabled
                    .color(),
            labelDragged =
                Cmp.Color.Action.Chip.MD.Unselected.Content.Label.Dragged
                    .color(),
            iconIdle =
                Cmp.Color.Action.Chip.MD.Unselected.Content.Icon.Idle
                    .color(),
            iconPressed =
                Cmp.Color.Action.Chip.MD.Unselected.Content.Icon.Pressed
                    .color(),
            iconDisabled =
                Cmp.Color.Action.Chip.MD.Unselected.Content.Icon.Disabled
                    .color(),
            iconDragged =
                Cmp.Color.Action.Chip.MD.Unselected.Content.Icon.Dragged
                    .color(),
        )
}
