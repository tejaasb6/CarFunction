package com.ui.audi.widgets.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.checkbox.CheckboxStateColors
import com.ui.core.widgets.checkbox.CheckboxStyle

/**
 * Provides the Audi brand [CheckboxStyle] derived from the current composition tokens.
 * Called inside [com.ui.audi.AudiTheme] so the style reacts to token changes.
 */
@Suppress("TooManyFunctions")
internal object AudiCheckboxDefaults {
    @Composable
    fun style(): CheckboxStyle =
        CheckboxStyle(
            // ── Dimensions ──────────────────────────────────────────────────────
            controlSize =
                Cmp.Size.Forms.FormControls.Control.Surface.All.Checkbox
                    .dimension()
                    .pxToDp(),
            controlCornerRadius =
                Cmp.BorderRadius.Forms.FormControls.Control.Surface.Checkbox
                    .dimension()
                    .pxToDp(),
            touchTargetSize =
                Cmp.Size.Forms.FormControls.TouchTarget.Height
                    .dimension()
                    .pxToDp(),
            touchTargetWidth =
                Cmp.Size.Forms.FormControls.TouchTarget.Width
                    .dimension()
                    .pxToDp(),
            surfaceGap =
                Cmp.Space.Forms.FormControls.Surface.Gap
                    .dimension()
                    .pxToDp(),
            controlLabelSpacing =
                Cmp.Space.Forms.FormControls.Content.Gap
                    .dimension()
                    .pxToDp(),
            labelTopPadding =
                Cmp.Space.Forms.FormControls.Content.Label.T_Padding
                    .dimension()
                    .pxToDp(),
            labelLeftPadding =
                Cmp.Space.Forms.FormControls.Content.Label.L_Padding
                    .dimension()
                    .pxToDp(),
            labelAppendixGap =
                Cmp.Space.Forms.FormControls.Content.Label.Gap
                    .dimension()
                    .pxToDp(),
            hintSpacing =
                Cmp.Space.Forms.FormControls.Content.Gap
                    .dimension()
                    .pxToDp(),
            hintLeftPadding =
                Cmp.Space.Forms.FormControls.Content.Hint.L_Padding
                    .dimension()
                    .pxToDp(),
            errorSpacing =
                Cmp.Space.Forms.FormControls.Content.Gap
                    .dimension()
                    .pxToDp(),
            errorLeftPadding =
                Cmp.Space.Forms.FormControls.Content.CaptionError.L_Padding
                    .dimension()
                    .pxToDp(),
            errorIconGap =
                Cmp.Space.Forms.FormControls.Content.CaptionError.Gap
                    .dimension()
                    .pxToDp(),
            errorIconWidth =
                Cmp.Size.Feedback.SemanticShape.Triangle.Width
                    .dimension()
                    .pxToDp(),
            errorIconHeight =
                Cmp.Size.Feedback.SemanticShape.Triangle.Height
                    .dimension()
                    .pxToDp(),
            badgeGap =
                Cmp.Space.Feedback.Badge.Gap
                    .dimension()
                    .pxToDp(),
            semanticShapeDimension =
                Cmp.Size.Feedback.SemanticShape.Container.Dimension
                    .dimension()
                    .pxToDp(),
            semanticShapeFill =
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Fill
                    .color(),
            semanticShapeStroke =
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Stroke
                    .color(),
            semanticShapeBorderWidth =
                Cmp.BorderWidth.Feedback.SemanticShape.Default
                    .dimension()
                    .pxToDp(),
            contentPadding =
                Cmp.Space.Forms.FormControls.Surface.L_Padding
                    .dimension()
                    .pxToDp(),
            // ── Focus ring ──────────────────────────────────────────────────────
            focusRingColor =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusRingWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            focusInnerBorderColor =
                Sem.Color.Stroke.Subtle
                    .color(),
            focusInnerBorderWidth =
                Sem.BorderWidth.SM
                    .dimension()
                    .pxToDp(),
            focusRingGap =
                Sem.Space.Fixed._50
                    .dimension()
                    .pxToDp(),
            // ── Typography ──────────────────────────────────────────────────────
            labelTextStyle =
                Cmp.Typography.Forms.FormControls.Content.Label
                    .typography(),
            hintTextStyle =
                Cmp.Typography.Forms.FormControls.Content.Hint
                    .typography(),
            appendixTextStyle =
                Cmp.Typography.Forms.FormControls.Content.Appendix
                    .typography(),
            errorTextStyle =
                Cmp.Typography.Forms.FormControls.Content.CaptionError
                    .typography(),
            // ── Unselected × Default ────────────────────────────────────────────
            unselectedDefault = unselectedDefaultIdle(),
            unselectedDefaultPressed = unselectedDefaultPressed(),
            unselectedDefaultDisabled = unselectedDefaultDisabled(),
            // ── Unselected × Error ──────────────────────────────────────────────
            unselectedError = unselectedErrorIdle(),
            unselectedErrorPressed = unselectedErrorPressed(),
            // ── Selected × Default ──────────────────────────────────────────────
            selectedDefault = selectedDefaultIdle(),
            selectedDefaultPressed = selectedDefaultPressed(),
            selectedDefaultDisabled = selectedDefaultDisabled(),
            // ── Selected × Error ────────────────────────────────────────────────
            selectedError = selectedErrorIdle(),
            selectedErrorPressed = selectedErrorPressed(),
        )

    // ── Unselected × Default ────────────────────────────────────────────────────

    @Composable
    private fun unselectedDefaultIdle() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Surface.Stroke.Idle
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Unselected.Surface.Idle
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Icon.Fill
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Label.Idle
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Hint.Idle
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Appendix.Idle
                    .color(),
            errorColor = Color.Transparent,
        )

    @Composable
    private fun unselectedDefaultPressed() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Surface.Stroke.Pressed
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Unselected.Surface.Pressed
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Icon.Fill
                    .color(),
            stateLayerColor =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.StateLayer.Pressed
                    .color(),
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Label.Pressed
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Hint.Pressed
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Appendix.Pressed
                    .color(),
            errorColor = Color.Transparent,
        )

    @Composable
    private fun unselectedDefaultDisabled() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Surface.Stroke.Disabled
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Unselected.Surface.Disabled
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Unselected.Default.Icon.Fill
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Label.Disabled
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Hint.Disabled
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Default.Appendix.Disabled
                    .color(),
            errorColor = Color.Transparent,
        )

    // ── Unselected × Error ──────────────────────────────────────────────────────

    @Composable
    private fun unselectedErrorIdle() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Unselected.Error.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Unselected.Error.Surface.Stroke.Idle
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Unselected.Surface.Idle
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Unselected.Error.Icon.Fill
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Error.Label.Idle
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Error.Hint.Idle
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Error.Appendix.Idle
                    .color(),
            errorColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Error.CaptionError.Idle
                    .color(),
        )

    @Composable
    private fun unselectedErrorPressed() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Unselected.Error.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Unselected.Error.Surface.Stroke.Pressed
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Unselected.Surface.Pressed
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Unselected.Error.Icon.Fill
                    .color(),
            stateLayerColor =
                Cmp.Color.Forms.FormControls.Control.Unselected.Error.StateLayer.Pressed
                    .color(),
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Error.Label.Pressed
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Error.Hint.Pressed
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Error.Appendix.Pressed
                    .color(),
            errorColor =
                Cmp.Color.Forms.FormControls.Content.Unselected.Error.CaptionError.Pressed
                    .color(),
        )

    // ── Selected × Default ──────────────────────────────────────────────────────

    @Composable
    private fun selectedDefaultIdle() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Surface.Stroke.Idle
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Selected.Surface.Idle
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Icon.Fill
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Label.Idle
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Hint.Idle
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Appendix.Idle
                    .color(),
            errorColor = Color.Transparent,
        )

    @Composable
    private fun selectedDefaultPressed() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Surface.Stroke.Pressed
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Selected.Surface.Pressed
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Icon.Fill
                    .color(),
            stateLayerColor =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.StateLayer.Pressed
                    .color(),
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Label.Pressed
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Hint.Pressed
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Appendix.Pressed
                    .color(),
            errorColor = Color.Transparent,
        )

    @Composable
    private fun selectedDefaultDisabled() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Surface.Stroke.Disabled
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Selected.Surface.Disabled
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Selected.Default.Icon.Fill
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Label.Disabled
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Hint.Disabled
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Default.Appendix.Disabled
                    .color(),
            errorColor = Color.Transparent,
        )

    // ── Selected × Error ────────────────────────────────────────────────────────

    @Composable
    private fun selectedErrorIdle() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Selected.Error.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Selected.Error.Surface.Stroke.Idle
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Selected.Surface.Idle
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Selected.Error.Icon.Fill
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Error.Label.Idle
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Error.Hint.Idle
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Error.Appendix.Idle
                    .color(),
            errorColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Error.CaptionError.Idle
                    .color(),
        )

    @Composable
    private fun selectedErrorPressed() =
        CheckboxStateColors(
            controlFill =
                Cmp.Color.Forms.FormControls.Control.Selected.Error.Surface.Fill
                    .color(),
            controlStroke =
                Cmp.Color.Forms.FormControls.Control.Selected.Error.Surface.Stroke.Pressed
                    .color(),
            controlStrokeWidth =
                Cmp.BorderWidth.Forms.FormControls.Control.Selected.Surface.Pressed
                    .dimension()
                    .pxToDp(),
            iconTint =
                Cmp.Color.Forms.FormControls.Control.Selected.Error.Icon.Fill
                    .color(),
            stateLayerColor =
                Cmp.Color.Forms.FormControls.Control.Selected.Error.StateLayer.Pressed
                    .color(),
            labelColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Error.Label.Pressed
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Error.Hint.Pressed
                    .color(),
            appendixColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Error.Appendix.Pressed
                    .color(),
            errorColor =
                Cmp.Color.Forms.FormControls.Content.Selected.Error.CaptionError.Pressed
                    .color(),
        )
}
