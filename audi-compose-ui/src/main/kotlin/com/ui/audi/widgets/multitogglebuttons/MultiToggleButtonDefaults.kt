package com.ui.audi.widgets.multitogglebuttons

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonIndicatorColors
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonStyle

/** Audi brand defaults for MultiToggleButton. */
internal object MultiToggleButtonDefaults {
    @Composable
    fun style(): MultiToggleButtonStyle =
        MultiToggleButtonStyle(
            indicatorWidth =
                Cmp.Size.Action.MultiToggleButton.IndicatorLinear.Surface.Width
                    .dimension()
                    .pxToDp(),
            indicatorHeight =
                Cmp.Size.Action.MultiToggleButton.IndicatorLinear.Surface.Height
                    .dimension()
                    .pxToDp(),
            indicatorCornerRadius =
                Cmp.BorderRadius.Action.MultiToggleButton.Indicator.Default
                    .dimension()
                    .pxToDp(),
            indicatorBorderWidth =
                Cmp.BorderWidth.Action.MultiToggleButton.Indicator.Default
                    .dimension()
                    .pxToDp(),
            indicatorGap =
                Cmp.Space.Action.MultiToggleButton.Indicator.Surface.Gap
                    .dimension()
                    .pxToDp(),
            contentToIndicatorSpacing =
                Cmp.Space.Action.MultiToggleButton.Indicator.Surface.Gap
                    .dimension()
                    .pxToDp(),
            labelModeMinWidth =
                Cmp.Size.Action.MultiToggleButton.LabelMode.StateLayer.MinWidth
                    .dimension()
                    .pxToDp(),
            labelModeMinHeight =
                Cmp.Size.Action.MultiToggleButton.LabelMode.StateLayer.MinHeight
                    .dimension()
                    .pxToDp(),
            labelModeHorizontalPadding =
                Cmp.Space.Action.MultiToggleButton.LabelMode.StateLayer.H_Padding
                    .dimension()
                    .pxToDp(),
            labelModeTypography =
                Cmp.Typography.Action.Button.MD.Selected.Label
                    .typography(),
            labelModeTypographyUnselected =
                Cmp.Typography.Action.Button.MD.Unselected.Label
                    .typography(),
            labelModeCornerRadius =
                Cmp.BorderRadius.Action.Button.Default
                    .dimension()
                    .pxToDp(),
            unselectedBorderWidthIdle =
                Cmp.BorderWidth.Action.Button.Unselected.Surface.Idle
                    .dimension()
                    .pxToDp(),
            unselectedBorderWidthPressed =
                Cmp.BorderWidth.Action.Button.Unselected.Surface.Pressed
                    .dimension()
                    .pxToDp(),
            unselectedBorderWidthDisabled =
                Cmp.BorderWidth.Action.Button.Unselected.Surface.Disabled
                    .dimension()
                    .pxToDp(),
            selectedBorderWidthIdle =
                Cmp.BorderWidth.Action.Button.Selected.Surface.Idle
                    .dimension()
                    .pxToDp(),
            selectedBorderWidthPressed =
                Cmp.BorderWidth.Action.Button.Selected.Surface.Pressed
                    .dimension()
                    .pxToDp(),
            selectedBorderWidthDisabled =
                Cmp.BorderWidth.Action.Button.Selected.Surface.Disabled
                    .dimension()
                    .pxToDp(),
            iconModeStateLayerSize =
                Cmp.Size.Action.MultiToggleButton.IconMode.StateLayer.MinWidth
                    .dimension()
                    .pxToDp(),
            iconModeTouchTarget =
                Cmp.Size.Action.MultiToggleButton.IconMode.TouchTarget.MinWidth
                    .dimension()
                    .pxToDp(),
            iconModeLabelTypography =
                Cmp.Typography.Action.IconButton.MD.Selected.Label
                    .typography(),
            iconModeLabelTypographyUnselected =
                Cmp.Typography.Action.IconButton.MD.Unselected.Label
                    .typography(),
            iconModeLabelColor =
                Cmp.Color.Action.IconButton.Selected.Content.Label.Pressed
                    .color(),
            iconModeLabelColorUnselected =
                Cmp.Color.Action.IconButton.Unselected.Content.Label.Pressed
                    .color(),
            disabledOpacity =
                Sem.Opacity.Disabled
                    .opacity(),
            focusRingWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            focusRingColor =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusRingGap =
                Sem.Space.Fixed._50
                    .dimension()
                    .pxToDp(),
            defaultColors = defaultIndicatorColors(),
            heatingColors = heatingIndicatorColors(),
            coolingColors = coolingIndicatorColors(),
        )

    @Composable
    private fun defaultIndicatorColors(): MultiToggleButtonIndicatorColors =
        MultiToggleButtonIndicatorColors(
            selectedFill =
                Cmp.Color.Action.MultiToggleButton.Indicator.Selected.Surface.Default.Fill
                    .color(),
            selectedStroke =
                Cmp.Color.Action.MultiToggleButton.Indicator.Selected.Surface.Default.Stroke
                    .color(),
            unselectedFill =
                Cmp.Color.Action.MultiToggleButton.Indicator.Unselected.Surface.Default.Fill
                    .color(),
            unselectedStroke =
                Cmp.Color.Action.MultiToggleButton.Indicator.Unselected.Surface.Default.Stroke
                    .color(),
            selectedOpacity =
                Cmp.Opacity.Action.MultiToggleButton.Indicator.Selected
                    .opacity(),
            unselectedOpacity =
                Cmp.Opacity.Action.MultiToggleButton.Indicator.Unselected
                    .opacity(),
        )

    @Composable
    private fun heatingIndicatorColors(): MultiToggleButtonIndicatorColors =
        MultiToggleButtonIndicatorColors(
            selectedFill =
                Cmp.Color.Action.MultiToggleButton.Indicator.Selected.Surface.Heating.Fill
                    .color(),
            selectedStroke =
                Cmp.Color.Action.MultiToggleButton.Indicator.Selected.Surface.Heating.Stroke
                    .color(),
            unselectedFill =
                Cmp.Color.Action.MultiToggleButton.Indicator.Unselected.Surface.Heating.Fill
                    .color(),
            unselectedStroke =
                Cmp.Color.Action.MultiToggleButton.Indicator.Unselected.Surface.Heating.Stroke
                    .color(),
            selectedOpacity =
                Cmp.Opacity.Action.MultiToggleButton.Indicator.Selected
                    .opacity(),
            unselectedOpacity =
                Cmp.Opacity.Action.MultiToggleButton.Indicator.Unselected
                    .opacity(),
        )

    @Composable
    private fun coolingIndicatorColors(): MultiToggleButtonIndicatorColors =
        MultiToggleButtonIndicatorColors(
            selectedFill =
                Cmp.Color.Action.MultiToggleButton.Indicator.Selected.Surface.Cooling.Fill
                    .color(),
            selectedStroke =
                Cmp.Color.Action.MultiToggleButton.Indicator.Selected.Surface.Cooling.Stroke
                    .color(),
            unselectedFill =
                Cmp.Color.Action.MultiToggleButton.Indicator.Unselected.Surface.Cooling.Fill
                    .color(),
            unselectedStroke =
                Cmp.Color.Action.MultiToggleButton.Indicator.Unselected.Surface.Cooling.Stroke
                    .color(),
            selectedOpacity =
                Cmp.Opacity.Action.MultiToggleButton.Indicator.Selected
                    .opacity(),
            unselectedOpacity =
                Cmp.Opacity.Action.MultiToggleButton.Indicator.Unselected
                    .opacity(),
        )
}
