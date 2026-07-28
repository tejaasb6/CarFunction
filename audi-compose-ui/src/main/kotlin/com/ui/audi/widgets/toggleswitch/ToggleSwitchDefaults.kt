package com.ui.audi.widgets.toggleswitch

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.toggleswitch.ToggleSwitchStateColors
import com.ui.core.widgets.toggleswitch.ToggleSwitchStyle

/**
 * Provides the Audi-brand [ToggleSwitchStyle] derived from the current design-token
 * composition.
 *
 * This object is consumed inside `AudiTheme` so that every [ToggleSwitchStyle]
 * property reacts to token changes (e.g. theme switching at runtime).
 *
 * **Token mapping overview:**
 * | Style field            | Token path                                             |
 * |------------------------|--------------------------------------------------------|
 * | `controlLabelSpacing`  | `Cmp.Space.Forms.FormControls.Content.Gap`             |
 * | `captionEndPadding`    | `Cmp.Space.Forms.ToggleSwitch.Caption.R_Padding`       |
 * | `hintSpacing`          | `Cmp.Space.Forms.ToggleSwitch.Caption.T_Padding`       |
 */
internal object ToggleSwitchDefaults {
    /**
     * Builds the default [ToggleSwitchStyle] from the current composition tokens.
     *
     * Track dimensions are derived at the usage site from handle size and
     * padding — no dedicated track-dimension tokens are needed.
     */
    @Composable
    fun style(): ToggleSwitchStyle =
        ToggleSwitchStyle(
            trackCornerRadius =
                Cmp.BorderRadius.Forms.ToggleSwitch.Default
                    .dimension()
                    .pxToDp(),
            handleWidth =
                Cmp.Size.Forms.ToggleSwitch.Handle.Width
                    .dimension()
                    .pxToDp(),
            handleHeight =
                Cmp.Size.Forms.ToggleSwitch.Handle.Height
                    .dimension()
                    .pxToDp(),
            verticalPadding =
                Cmp.Space.Forms.ToggleSwitch.Control.Padding
                    .dimension()
                    .pxToDp(),
            horizontalPadding =
                Cmp.Space.Forms.ToggleSwitch.Control.Padding
                    .dimension()
                    .pxToDp(),
            touchTargetSize =
                Cmp.Size.Forms.ToggleSwitch.TouchTargetSize
                    .dimension()
                    .pxToDp(),
            controlLabelSpacing =
                Cmp.Space.Forms.FormControls.Content.Gap
                    .dimension()
                    .pxToDp(),
            captionEndPadding =
                Cmp.Space.Forms.ToggleSwitch.Caption.R_Padding
                    .dimension()
                    .pxToDp(),
            hintSpacing =
                Cmp.Space.Forms.ToggleSwitch.Caption.T_Padding
                    .dimension()
                    .pxToDp(),
            paddingTop =
                Sem.Space.Fixed._400
                    .dimension()
                    .pxToDp(),
            minHeight =
                Sem.Size.Fixed._1200
                    .dimension()
                    .pxToDp(),
            labelTextStyle =
                Cmp.Typography.Forms.ToggleSwitch.Label
                    .typography(),
            hintTextStyle =
                Cmp.Typography.Forms.ToggleSwitch.Caption
                    .typography(),
            disabledOpacity = Sem.Opacity.Disabled.opacity(),
            spinnerSize =
                Cmp.Size.Feedback.ProgressIndicator.Spinner.MD.All
                    .dimension()
                    .pxToDp(),
            spinnerStrokeWidth =
                Cmp.BorderWidth.Feedback.ProgressIndicator.Spinner.MD
                    .dimension()
                    .pxToDp(),
            spinnerTrackColor =
                Cmp.Color.Feedback.ProgressIndicator.NonInverted.TrackLine.Fill
                    .color(),
            unselectedIdle = unselectedIdle(),
            unselectedPressed = unselectedPressed(),
            unselectedDisabled = unselectedDisabled(),
            selectedIdle = selectedIdle(),
            selectedPressed = selectedPressed(),
            selectedDisabled = selectedDisabled(),
        )

    // ── Unselected ──────────────────────────────────────────────────────────────

    /** Colours for the **unselected + idle** state. */
    @Composable
    private fun unselectedIdle() =
        ToggleSwitchStateColors(
            trackFill =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Surface.Fill
                    .color(),
            trackStroke =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Surface.Stroke.Idle
                    .color(),
            trackStrokeWidth =
                Cmp.BorderWidth.Forms.ToggleSwitch.Default.Idle
                    .dimension()
                    .pxToDp(),
            handleFill =
                Cmp.Color.Forms.ToggleSwitch.Handle.Unselected.Content.Fill.Idle
                    .color(),
            iconTint =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Content.Icon.Idle
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.ToggleSwitch.Label.Idle
                    .color(),
            hintColor =
                Cmp.Color.Forms.ToggleSwitch.Caption.Idle
                    .color(),
        )

    /** Colours for the **unselected + pressed** state. */
    @Composable
    private fun unselectedPressed() =
        ToggleSwitchStateColors(
            trackFill =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Surface.Fill
                    .color(),
            trackStroke =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Surface.Stroke.Pressed
                    .color(),
            trackStrokeWidth =
                Cmp.BorderWidth.Forms.ToggleSwitch.Default.Pressed
                    .dimension()
                    .pxToDp(),
            handleFill =
                Cmp.Color.Forms.ToggleSwitch.Handle.Unselected.Content.Fill.Pressed
                    .color(),
            iconTint =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Content.Icon.Pressed
                    .color(),
            stateLayerColor =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.StateLayer.Pressed
                    .color(),
            labelColor =
                Cmp.Color.Forms.ToggleSwitch.Label.Pressed
                    .color(),
            hintColor =
                Cmp.Color.Forms.ToggleSwitch.Caption.Pressed
                    .color(),
        )

    /** Colours for the **unselected + disabled** state. */
    @Composable
    private fun unselectedDisabled() =
        ToggleSwitchStateColors(
            trackFill =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Surface.Fill
                    .color(),
            trackStroke =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Surface.Stroke.Disabled
                    .color(),
            trackStrokeWidth =
                Cmp.BorderWidth.Forms.ToggleSwitch.Default.Disabled
                    .dimension()
                    .pxToDp(),
            handleFill =
                Cmp.Color.Forms.ToggleSwitch.Handle.Unselected.Content.Fill.Disabled
                    .color(),
            iconTint =
                Cmp.Color.Forms.ToggleSwitch.Container.Unselected.Content.Icon.Disabled
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.ToggleSwitch.Label.Disabled
                    .color(),
            hintColor =
                Cmp.Color.Forms.ToggleSwitch.Caption.Disabled
                    .color(),
        )

    // ── Selected ────────────────────────────────────────────────────────────────

    /** Colours for the **selected + idle** state. */
    @Composable
    private fun selectedIdle() =
        ToggleSwitchStateColors(
            trackFill =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Surface.Fill
                    .color(),
            trackStroke =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Surface.Stroke.Idle
                    .color(),
            trackStrokeWidth =
                Cmp.BorderWidth.Forms.ToggleSwitch.Default.Idle
                    .dimension()
                    .pxToDp(),
            handleFill =
                Cmp.Color.Forms.ToggleSwitch.Handle.Selected.Content.Fill.Idle
                    .color(),
            iconTint =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Content.Icon.Idle
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.ToggleSwitch.Label.Idle
                    .color(),
            hintColor =
                Cmp.Color.Forms.ToggleSwitch.Caption.Idle
                    .color(),
        )

    /** Colours for the **selected + pressed** state. */
    @Composable
    private fun selectedPressed() =
        ToggleSwitchStateColors(
            trackFill =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Surface.Fill
                    .color(),
            trackStroke =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Surface.Stroke.Pressed
                    .color(),
            trackStrokeWidth =
                Cmp.BorderWidth.Forms.ToggleSwitch.Default.Pressed
                    .dimension()
                    .pxToDp(),
            handleFill =
                Cmp.Color.Forms.ToggleSwitch.Handle.Selected.Content.Fill.Pressed
                    .color(),
            iconTint =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Content.Icon.Pressed
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.ToggleSwitch.Label.Pressed
                    .color(),
            hintColor =
                Cmp.Color.Forms.ToggleSwitch.Caption.Pressed
                    .color(),
        )

    /** Colours for the **selected + disabled** state. */
    @Composable
    private fun selectedDisabled() =
        ToggleSwitchStateColors(
            trackFill =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Surface.Fill
                    .color(),
            trackStroke =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Surface.Stroke.Disabled
                    .color(),
            trackStrokeWidth =
                Cmp.BorderWidth.Forms.ToggleSwitch.Default.Disabled
                    .dimension()
                    .pxToDp(),
            handleFill =
                Cmp.Color.Forms.ToggleSwitch.Handle.Selected.Content.Fill.Disabled
                    .color(),
            iconTint =
                Cmp.Color.Forms.ToggleSwitch.Container.Selected.Content.Icon.Disabled
                    .color(),
            stateLayerColor = Color.Transparent,
            labelColor =
                Cmp.Color.Forms.ToggleSwitch.Label.Disabled
                    .color(),
            hintColor =
                Cmp.Color.Forms.ToggleSwitch.Caption.Disabled
                    .color(),
        )
}
