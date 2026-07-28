package com.ui.audi.widgets.sliders

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.sliders.SliderBranchColors
import com.ui.core.widgets.sliders.SliderStateColors
import com.ui.core.widgets.sliders.SliderStyle
import com.ui.core.widgets.sliders.SliderTypeColors

/** Audi default [SliderStyle]. */
internal object SliderDefaults {
    @Suppress("ForbiddenComment")
    @Composable
    fun style(): SliderStyle =
        SliderStyle(
            trackCornerRadius =
                Cmp.BorderRadius.Forms.Slider.Track.Default
                    .dimension()
                    .pxToDp(),
            trackHeight =
                Cmp.Size.Forms.Slider.Track.Height
                    .dimension()
                    .pxToDp(),
            progressCornerRadius =
                Cmp.BorderRadius.Forms.Slider.Train.Default
                    .dimension()
                    .pxToDp(),
            progressHeight =
                Cmp.Size.Forms.Slider.Progress.Height
                    .dimension()
                    .pxToDp(),
            handleCornerRadius =
                Cmp.BorderRadius.Forms.Slider.Handle.Default
                    .dimension()
                    .pxToDp(),
            handleWidth =
                Cmp.Size.Forms.Slider.Handle.Width
                    .dimension()
                    .pxToDp(),
            handleHeight =
                Cmp.Size.Forms.Slider.Handle.Height
                    .dimension()
                    .pxToDp(),
            handleBorderWidth =
                Cmp.BorderWidth.Forms.Slider.Handle.Idle
                    .dimension()
                    .pxToDp(),
            valueCornerRadius =
                Cmp.BorderRadius.Forms.Slider.Value.Default
                    .dimension()
                    .pxToDp(),
            valueBorderWidthIdle =
                Cmp.BorderWidth.Forms.Slider.Value.Idle
                    .dimension()
                    .pxToDp(),
            valueBorderWidthPressed =
                Cmp.BorderWidth.Forms.Slider.Value.Pressed
                    .dimension()
                    .pxToDp(),
            labelGroupBottomPadding =
                Cmp.Space.Forms.FormFields.LabelGroup.B_Padding
                    .dimension()
                    .pxToDp(),
            labelGroupGap =
                Cmp.Space.Forms.FormFields.LabelGroup.Gap
                    .dimension()
                    .pxToDp(),
            verticalLabelGroupBottomPadding =
                Cmp.Space.Forms.Slider.Vertical.LabelGroup.B_Padding
                    .dimension()
                    .pxToDp(),
            verticalLabelGroupGap =
                Cmp.Space.Forms.Slider.Vertical.LabelGroup.Gap
                    .dimension()
                    .pxToDp(),
            iconButtonGap =
                Cmp.Space.Action.IconButton.MD.Container.Gap
                    .dimension()
                    .pxToDp(),
            iconMdHeight =
                Cmp.Size.DataDisplay.Icon.MD.Height
                    .dimension()
                    .pxToDp(),
            iconMdMinWidth =
                Cmp.Size.DataDisplay.Icon.MD.MinWidth
                    .dimension()
                    .pxToDp(),
            splitTouchTargetHeight =
                Cmp.Size.Forms.FormControls.TouchTarget.Height
                    .dimension()
                    .pxToDp(),
            splitTrackGap =
                Cmp.Space.Forms.Slider.Horizontal.Track.Gap
                    .dimension()
                    .pxToDp(),
            trackGap =
                Cmp.Space.Forms.Slider.Horizontal.Track.Gap
                    .dimension()
                    .pxToDp(),
            rangeGap =
                Cmp.Space.Forms.Slider.Horizontal.Range.Gap
                    .dimension()
                    .pxToDp(),
            captionGroupGap =
                Cmp.Space.Forms.Slider.Vertical.CaptionGroup.Gap
                    .dimension()
                    .pxToDp(),
            captionGroupTopPadding =
                Cmp.Space.Forms.Slider.Vertical.CaptionGroup.T_Padding
                    .dimension()
                    .pxToDp(),
            hCaptionGroupGap =
                Cmp.Space.Forms.FormFields.CaptionGroup.Gap
                    .dimension()
                    .pxToDp(),
            hCaptionGroupTopPadding =
                Cmp.Space.Forms.FormFields.CaptionGroup.T_Padding
                    .dimension()
                    .pxToDp(),
            valuePaddingH =
                Cmp.Space.Forms.Slider.Horizontal.Value.Pressed.H_Padding
                    .dimension()
                    .pxToDp(),
            valuePaddingV =
                Cmp.Space.Forms.Slider.Horizontal.Value.Pressed.V_Padding
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.Forms.FormControls.TouchTarget.Height
                    .dimension()
                    .pxToDp(),
            triangleHeight =
                Cmp.Size.Feedback.SemanticShape.Triangle.Height
                    .dimension()
                    .pxToDp(),
            triangleWidth =
                Cmp.Size.Feedback.SemanticShape.Triangle.Width
                    .dimension()
                    .pxToDp(),
            triangleFill =
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Fill
                    .color(),
            triangleStroke =
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Stroke
                    .color(),
            triangleBorderWidth =
                Cmp.BorderWidth.Feedback.SemanticShape.Default
                    .dimension()
                    .pxToDp(),
            appendixColor =
                Cmp.Color.Forms.FormFields.Apendix.Default
                    .color(),
            appendixTextStyle =
                Cmp.Typography.Forms.FormFields.Caption
                    .typography(),
            titleTextStyle =
                Cmp.Typography.Forms.FormFields.Label
                    .typography(),
            valueTextStyleIdle =
                Cmp.Typography.Forms.Slider.UserInput.Idle
                    .typography(),
            valueTextStylePressed =
                Cmp.Typography.Forms.Slider.UserInput.Pressed
                    .typography(),
            rangeTextStyle =
                Cmp.Typography.Forms.Slider.Range
                    .typography(),
            hintTextStyle =
                Cmp.Typography.Forms.FormFields.Caption
                    .typography(),
            errorCaptionTextStyle =
                Cmp.Typography.Forms.FormFields.Caption
                    .typography(),
            errorCaptionColor =
                Cmp.Color.Forms.FormFields.Caption.Error
                    .color(),
            colors = sliderColors(),
            // TODO: No tokens available in Figma design.
            //  Using hardcoded values. Replace with proper Cmp tokens once they are
            //  defined in the design system.
            handleValueIdleGap = 24.dp,
            handleValuePressedGap = 40.dp,
            valuePopupShadow =
                Cmp.Shadow.Forms.Slider.Value.Default
                    .boxShadow(),
            popupAutoHideDelayMs = 1000L,
            hIdleValueGap = 8.dp,
            chargingGlowBlurRadius = 4.dp,
            chargingGlowAlpha = 0.2f,
            verticalRangeLabelGap = 43.dp,
            verticalMaxLabelOffsetY = (-5).dp,
            verticalMinLabelOffsetY = 7.dp,
        )

    @Composable
    private fun sliderColors(): SliderTypeColors =
        SliderTypeColors(
            default = defaultBranch(),
            error = errorBranch(),
            temperatureColdColor =
                Sem.Color.Graph.Temperature.Cold
                    .color(),
            temperatureHotColor =
                Sem.Color.Graph.Temperature.Hot
                    .color(),
            chargingProgressFill =
                Cmp.Color.Forms.Slider.Progress.Charging.Surface.Fill
                    .color(),
            valueSurfaceFill =
                Cmp.Color.Forms.Slider.Value.Surface.Fill.Pressed
                    .color(),
            valueSurfaceStroke =
                Cmp.Color.Forms.Slider.Value.Surface.Stroke.Pressed
                    .color(),
            progressStateLayerPressed =
                Cmp.Color.Forms.Slider.Progress.Default.StateLayer.Pressed
                    .color(),
        )

    @Composable
    private fun defaultBranch(): SliderBranchColors =
        SliderBranchColors(
            stateLayerPressed =
                Cmp.Color.Forms.Slider.Handle.Default.Surface.StateLayer.Pressed
                    .color(),
            idle =
                SliderStateColors(
                    handleFill =
                        Cmp.Color.Forms.Slider.Handle.Default.Surface.Fill
                            .color(),
                    handleStroke =
                        Cmp.Color.Forms.Slider.Handle.Default.Surface.Stroke.Idle
                            .color(),
                    progressFill =
                        Cmp.Color.Forms.Slider.Progress.Default.Surface.Fill
                            .color(),
                    trackFill =
                        Cmp.Color.Forms.Slider.Track.Surface.Fill
                            .color(),
                    titleColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Idle
                            .color(),
                    hintColor =
                        Cmp.Color.Forms.FormFields.Caption.Default
                            .color(),
                    rangeColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Text.Default
                            .color(),
                    rangeIconColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Icon.Default
                            .color(),
                    valueTextColor =
                        Cmp.Color.Forms.Slider.Value.Content.Text.Idle
                            .color(),
                ),
            pressed =
                SliderStateColors(
                    handleFill =
                        Cmp.Color.Forms.Slider.Handle.Default.Surface.Fill
                            .color(),
                    handleStroke =
                        Cmp.Color.Forms.Slider.Handle.Default.Surface.Stroke.Pressed
                            .color(),
                    progressFill =
                        Cmp.Color.Forms.Slider.Progress.Default.Surface.Fill
                            .color(),
                    trackFill =
                        Cmp.Color.Forms.Slider.Track.Surface.Fill
                            .color(),
                    titleColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Idle
                            .color(),
                    hintColor =
                        Cmp.Color.Forms.FormFields.Caption.Default
                            .color(),
                    rangeColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Text.Default
                            .color(),
                    rangeIconColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Icon.Default
                            .color(),
                    valueTextColor =
                        Cmp.Color.Forms.Slider.Value.Content.Text.Pressed
                            .color(),
                ),
            disabled =
                SliderStateColors(
                    handleFill =
                        Cmp.Color.Forms.Slider.Handle.Default.Surface.Fill
                            .color(),
                    handleStroke =
                        Cmp.Color.Forms.Slider.Handle.Default.Surface.Stroke.Idle
                            .color(),
                    progressFill =
                        Cmp.Color.Forms.Slider.Progress.Default.Surface.Fill
                            .color(),
                    trackFill =
                        Cmp.Color.Forms.Slider.Track.Surface.Fill
                            .color(),
                    titleColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Idle
                            .color(),
                    hintColor =
                        Cmp.Color.Forms.FormFields.Caption.Default
                            .color(),
                    rangeColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Text.Default
                            .color(),
                    rangeIconColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Icon.Default
                            .color(),
                    valueTextColor =
                        Cmp.Color.Forms.Slider.Value.Content.Text.Idle
                            .color(),
                ),
        )

    @Composable
    private fun errorBranch(): SliderBranchColors =
        SliderBranchColors(
            stateLayerPressed =
                Cmp.Color.Forms.Slider.Handle.Default.Surface.StateLayer.Pressed
                    .color(),
            idle =
                SliderStateColors(
                    handleFill =
                        Cmp.Color.Forms.Slider.Handle.Error.Surface.Fill
                            .color(),
                    handleStroke =
                        Cmp.Color.Forms.Slider.Handle.Error.Surface.Stroke
                            .color(),
                    progressFill =
                        Cmp.Color.Forms.Slider.Progress.Error.Surface.Fill
                            .color(),
                    trackFill =
                        Cmp.Color.Forms.Slider.Track.Surface.Fill
                            .color(),
                    titleColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Idle
                            .color(),
                    hintColor =
                        Cmp.Color.Forms.FormFields.Caption.Default
                            .color(),
                    rangeColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Text.Error
                            .color(),
                    rangeIconColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Icon.Error
                            .color(),
                    valueTextColor =
                        Cmp.Color.Forms.Slider.Value.Content.Text.Error
                            .color(),
                ),
            pressed =
                SliderStateColors(
                    handleFill =
                        Cmp.Color.Forms.Slider.Handle.Error.Surface.Fill
                            .color(),
                    handleStroke =
                        Cmp.Color.Forms.Slider.Handle.Error.Surface.Stroke
                            .color(),
                    progressFill =
                        Cmp.Color.Forms.Slider.Progress.Error.Surface.Fill
                            .color(),
                    trackFill =
                        Cmp.Color.Forms.Slider.Track.Surface.Fill
                            .color(),
                    titleColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Idle
                            .color(),
                    hintColor =
                        Cmp.Color.Forms.FormFields.Caption.Default
                            .color(),
                    rangeColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Text.Error
                            .color(),
                    rangeIconColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Icon.Error
                            .color(),
                    valueTextColor =
                        Cmp.Color.Forms.Slider.Value.Content.Text.Error
                            .color(),
                ),
            disabled =
                SliderStateColors(
                    handleFill =
                        Cmp.Color.Forms.Slider.Handle.Default.Surface.Fill
                            .color(),
                    handleStroke =
                        Cmp.Color.Forms.Slider.Handle.Default.Surface.Stroke.Idle
                            .color(),
                    progressFill =
                        Cmp.Color.Forms.Slider.Progress.Default.Surface.Fill
                            .color(),
                    trackFill =
                        Cmp.Color.Forms.Slider.Track.Surface.Fill
                            .color(),
                    titleColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Idle
                            .color(),
                    hintColor =
                        Cmp.Color.Forms.FormFields.Caption.Default
                            .color(),
                    rangeColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Text.Error
                            .color(),
                    rangeIconColor =
                        Cmp.Color.Forms.Slider.Range.Default.Content.Icon.Error
                            .color(),
                    valueTextColor =
                        Cmp.Color.Forms.Slider.Value.Content.Text.Error
                            .color(),
                ),
        )
}
