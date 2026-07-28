package com.ui.audi.widgets.steppers

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.steppers.StepperButtonColors
import com.ui.core.widgets.steppers.StepperStateColors
import com.ui.core.widgets.steppers.StepperStyle
import com.ui.core.widgets.steppers.StepperTypeColors

/** Audi brand token-driven defaults for Stepper. */
internal object StepperDefaults {
    @Composable
    fun style(): StepperStyle =
        StepperStyle(
            cornerRadius =
                Cmp.BorderRadius.Forms.Stepper.Default
                    .dimension()
                    .pxToDp(),
            minWidth =
                Cmp.Size.Forms.Stepper.MinWidth
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.Forms.Stepper.MinHeight
                    .dimension()
                    .pxToDp(),
            stateLayerHeight =
                Cmp.Size.Forms.Stepper.StateLayer.Height
                    .dimension()
                    .pxToDp(),
            buttonTouchMinWidth =
                Cmp.Size.Action.ComponentButton.MD.TouchTarget.MinWidth
                    .dimension()
                    .pxToDp(),
            buttonTouchHeight =
                Cmp.Size.Action.ComponentButton.MD.TouchTarget.Height
                    .dimension()
                    .pxToDp(),
            buttonCornerRadius =
                Cmp.BorderRadius.Action.ComponentButton.Default
                    .dimension()
                    .pxToDp(),
            buttonStateLayerMinWidth =
                Cmp.Size.Action.ComponentButton.MD.StateLayer.MinWidth
                    .dimension()
                    .pxToDp(),
            buttonStateLayerHeight =
                Cmp.Size.Action.ComponentButton.MD.StateLayer.Height
                    .dimension()
                    .pxToDp(),
            iconWidth =
                Cmp.Size.DataDisplay.Icon.MD.MinWidth
                    .dimension()
                    .pxToDp(),
            iconHeight =
                Cmp.Size.DataDisplay.Icon.MD.Height
                    .dimension()
                    .pxToDp(),
            gap =
                Cmp.Space.Forms.Stepper.Gap
                    .dimension()
                    .pxToDp(),
            labelTextStyle =
                Cmp.Typography.Forms.FormFields.Label
                    .typography(),
            colors = colors(),
        )

    @Composable
    private fun colors(): StepperTypeColors =
        StepperTypeColors(
            idle =
                StepperStateColors(
                    surfaceFill =
                        Cmp.Color.Forms.Stepper.Surface.Fill
                            .color(),
                    border =
                        Cmp.Color.Forms.Stepper.Surface.Stroke.Idle
                            .color(),
                    borderWidth =
                        Cmp.BorderWidth.Forms.FormFields.Default.Idle
                            .dimension()
                            .pxToDp(),
                    labelColor =
                        Cmp.Color.Forms.FormControls.Content.Selected.Default.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.Stepper.Content.Icon.Idle
                            .color(),
                ),
            disabled =
                StepperStateColors(
                    surfaceFill =
                        Cmp.Color.Forms.Stepper.Surface.Fill
                            .color(),
                    border =
                        Cmp.Color.Forms.Stepper.Surface.Stroke.Disabled
                            .color(),
                    borderWidth =
                        Cmp.BorderWidth.Forms.FormFields.Default.Disabled
                            .dimension()
                            .pxToDp(),
                    labelColor =
                        Cmp.Color.Forms.FormControls.Content.Selected.Default.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.Stepper.Content.Icon.Disabled
                            .color(),
                ),
            button =
                StepperButtonColors(
                    surfaceFill =
                        Cmp.Color.Action.ComponentButton.Unselected.Default.Surface.Fill
                            .color(),
                    strokeColor =
                        Cmp.Color.Action.ComponentButton.Unselected.Default.Surface.Stroke.Idle
                            .color(),
                    strokeWidth =
                        Cmp.BorderWidth.Action.ComponentButton.Idle
                            .dimension()
                            .pxToDp(),
                    iconColor =
                        Cmp.Color.Action.ComponentButton.Unselected.Default.Content.Icon.Idle
                            .color(),
                    stateLayerPressed =
                        Cmp.Color.Action.ComponentButton.Unselected.Default.StateLayer.Pressed
                            .color(),
                    disabledStrokeColor =
                        Cmp.Color.Action.ComponentButton.Unselected.Default.Surface.Stroke.Disabled
                            .color(),
                    disabledStrokeWidth =
                        Cmp.BorderWidth.Action.ComponentButton.Disabled
                            .dimension()
                            .pxToDp(),
                    disabledIconColor =
                        Cmp.Color.Action.ComponentButton.Unselected.Default.Content.Icon.Disabled
                            .color(),
                ),
        )
}
