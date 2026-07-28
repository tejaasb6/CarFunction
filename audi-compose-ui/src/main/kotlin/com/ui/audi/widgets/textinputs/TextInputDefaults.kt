package com.ui.audi.widgets.textinputs

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.textinputs.TextInputBranchColors
import com.ui.core.widgets.textinputs.TextInputStateColors
import com.ui.core.widgets.textinputs.TextInputStyle
import com.ui.core.widgets.textinputs.TextInputTypeColors

@Suppress("ForbiddenComment")
internal object TextInputDefaults {
    @Composable
    fun style(): TextInputStyle =
        TextInputStyle(
            cornerRadius =
                Cmp.BorderRadius.Forms.FormFields.Default
                    .dimension()
                    .pxToDp(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Idle
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.Forms.FormFields.Field.VisualHeight
                    .dimension()
                    .pxToDp(),
            touchTargetHeight =
                Cmp.Size.Forms.FormFields.Field.TouchTarget
                    .dimension()
                    .pxToDp(),
            paddingHorizontal =
                Cmp.Space.Forms.FormFields.Field.H_Padding
                    .dimension()
                    .pxToDp(),
            iconSpacing =
                Cmp.Space.Forms.FormFields.Field.Gap
                    .dimension()
                    .pxToDp(),
            labelGap =
                Cmp.Space.Forms.FormFields.LabelGroup.B_Padding
                    .dimension()
                    .pxToDp(),
            labelItemSpacing =
                Cmp.Space.Forms.FormFields.LabelGroup.Gap
                    .dimension()
                    .pxToDp(),
            captionGap =
                Cmp.Space.Forms.FormFields.CaptionGroup.T_Padding
                    .dimension()
                    .pxToDp(),
            trailingExtensionRPadding =
                Cmp.Space.Forms.FormFields.Field.TrailingExtension.R_Padding
                    .dimension()
                    .pxToDp(),
            labelTextStyle =
                Cmp.Typography.Forms.FormFields.Label
                    .typography(),
            appendixTextStyle =
                Cmp.Typography.Forms.FormFields.Caption
                    .typography(),
            valueTextStyle =
                Cmp.Typography.Forms.FormFields.UserInput
                    .typography(),
            placeholderTextStyle =
                Cmp.Typography.Forms.FormFields.Placeholder
                    .typography(),
            unitTextStyle =
                Cmp.Typography.Forms.FormFields.Unit
                    .typography(),
            captionTextStyle =
                Cmp.Typography.Forms.FormFields.Caption
                    .typography(),
            captionErrorTextStyle =
                Cmp.Typography.Forms.FormControls.Content.CaptionError
                    .typography(),
            strengthTextStyle =
                Cmp.Typography.Forms.FormFields.Caption
                    .typography(),
            cursorHeight =
                Cmp.Size.Forms.FormFields.Field.Cursor.Height
                    .dimension()
                    .pxToDp(),
            cursorWidth =
                Cmp.Size.Forms.FormFields.Field.Cursor.Width
                    .dimension()
                    .pxToDp(),
            cursorColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Active
                    .color(),
            spinnerTrackColor =
                Cmp.Color.Feedback.ProgressIndicator.NonInverted.TrackLine.Fill
                    .color(),
            spinnerTrainColor =
                Cmp.Color.Feedback.ProgressIndicator.NonInverted.TrainLine.Fill
                    .color(),
            spinnerStrokeWidth =
                Cmp.BorderWidth.Feedback.ProgressIndicator.Spinner.MD
                    .dimension()
                    .pxToDp(),
            spinnerSize =
                Cmp.Size.Feedback.ProgressIndicator.Spinner.MD.All
                    .dimension()
                    .pxToDp(),
            colors = typeColors(),
        )

    @Composable
    private fun typeColors(): TextInputTypeColors {
        val defaultBranch = defaultBranch()
        return TextInputTypeColors(
            default = defaultBranch,
            error = errorBranch(defaultBranch),
        )
    }

    @Composable
    private fun defaultBranch(): TextInputBranchColors {
        val surfaceFill =
            Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                .color()
        val appendixDefault =
            Cmp.Color.Forms.FormFields.Apendix.Default
                .color()
        val captionDefault =
            Cmp.Color.Forms.FormFields.Caption.Default
                .color()
        val appendixColor =
            Cmp.Color.Forms.FormFields.Apendix.Default
                .color()
        return TextInputBranchColors(
            stateLayerPressed =
                Cmp.Color.Forms.FormFields.Field.Default.StateLayer.Pressed
                    .color(),
            idle =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Idle
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Idle
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Idle
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Unit.Idle
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Idle
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionDefault,
                    errorColor = appendixDefault,
                ),
            pressed =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Pressed
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Pressed
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Pressed
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Unit.Pressed
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Pressed
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionDefault,
                    errorColor = appendixDefault,
                ),
            active =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Active
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Active
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Active
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Active
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Unit.Active
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Active
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionDefault,
                    errorColor = appendixDefault,
                ),
            focused =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Active
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Active
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Active
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Active
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Unit.Active
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Active
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionDefault,
                    errorColor = appendixDefault,
                ),
            loading =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Loading
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Loading
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Loading
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Unit.Loading
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Loading
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionDefault,
                    errorColor = appendixDefault,
                ),
            disabled =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Disabled
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Disabled
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Disabled
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Unit.Disabled
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Default.Disabled
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionDefault,
                    errorColor = appendixDefault,
                ),
            readOnly =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.ReadOnly
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.ReadOnly
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.ReadOnly
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Default.Content.Unit.Idle
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Default.ReadOnly
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionDefault,
                    errorColor = appendixDefault,
                ),
        )
    }

    @Composable
    private fun errorBranch(fallback: TextInputBranchColors): TextInputBranchColors {
        val surfaceFill =
            Cmp.Color.Forms.FormFields.Field.Error.Surface.Fill
                .color()
        val captionError =
            Cmp.Color.Forms.FormFields.Caption.Error
                .color()
        val appendixColor =
            Cmp.Color.Forms.FormFields.Apendix.Error
                .color()
        return TextInputBranchColors(
            stateLayerPressed =
                Cmp.Color.Forms.FormFields.Field.Error.StateLayer.Pressed
                    .color(),
            idle =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Error.Surface.Stroke.Idle
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.UserInput.Idle
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Placeholder.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Icon.Idle
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Unit.Idle
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Error.Idle
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionError,
                    errorColor = captionError,
                ),
            pressed =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Error.Surface.Stroke.Pressed
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.UserInput.Pressed
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Placeholder.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Icon.Pressed
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Unit.Pressed
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Error.Pressed
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionError,
                    errorColor = captionError,
                ),
            active =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Error.Surface.Stroke.Active
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.UserInput.Active
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Placeholder.Active
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Icon.Active
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Unit.Active
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Error.Active
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionError,
                    errorColor = captionError,
                ),
            focused =
                TextInputStateColors(
                    surfaceFill = surfaceFill,
                    border =
                        Cmp.Color.Forms.FormFields.Field.Error.Surface.Stroke.Active
                            .color(),
                    valueColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.UserInput.Active
                            .color(),
                    placeholderColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Placeholder.Active
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Icon.Active
                            .color(),
                    unitColor =
                        Cmp.Color.Forms.FormFields.Field.Error.Content.Unit.Active
                            .color(),
                    labelColor =
                        Cmp.Color.Forms.FormFields.Label.Error.Active
                            .color(),
                    appendixColor = appendixColor,
                    hintColor = captionError,
                    errorColor = captionError,
                ),
            loading =
                fallback.loading.copy(
                    appendixColor = appendixColor,
                    hintColor = captionError,
                    errorColor = captionError,
                ),
            disabled =
                fallback.disabled.copy(
                    appendixColor = appendixColor,
                    hintColor = captionError,
                    errorColor = captionError,
                ),
            readOnly =
                fallback.readOnly.copy(
                    appendixColor = appendixColor,
                    hintColor = captionError,
                    errorColor = captionError,
                ),
        )
    }
}
