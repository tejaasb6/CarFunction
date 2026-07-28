package com.ui.audi.widgets.segmentedcontrols

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.buttons.ButtonBranchColors
import com.ui.core.widgets.buttons.ButtonStateColors
import com.ui.core.widgets.buttons.ButtonStyle
import com.ui.core.widgets.buttons.ButtonTypeColors
import com.ui.core.widgets.segmentedcontrols.SegmentedControlContainerColors
import com.ui.core.widgets.segmentedcontrols.SegmentedControlStyle

/** Audi brand defaults for SegmentedControl. */
internal object SegmentedControlDefaults {
    @Composable
    fun style(): SegmentedControlStyle =
        SegmentedControlStyle(
            // ── Wrapper ────────────────────────────────────────────────────────────
            wrapperCornerRadius =
                Cmp.BorderRadius.Action.SegmentedControl.Wrapper
                    .dimension()
                    .pxToDp(),
            wrapperBorderWidth =
                Cmp.BorderWidth.Action.SegmentedControl.Wrapper
                    .dimension()
                    .pxToDp(),
            wrapperHeight =
                Cmp.Size.Action.SegmentedControl.Wrapper.Height
                    .dimension()
                    .pxToDp(),
            wrapperGap =
                Cmp.Space.Action.SegmentedControl.Wrapper.Gap
                    .dimension()
                    .pxToDp(),
            wrapperPadding =
                Cmp.Space.Action.SegmentedControl.Unit.TouchTarget.Padding
                    .dimension()
                    .pxToDp(),
            // ── Title ──────────────────────────────────────────────────────────────
            titleBottomPadding =
                Cmp.Space.Action.SegmentedControl.Label.B_Padding
                    .dimension()
                    .pxToDp(),
            titleTextStyle =
                Cmp.Typography.Action.SegmentedControl.Label
                    .typography(),
            // ── Container colours ──────────────────────────────────────────────────
            containerColors = containerColors(),
            // ── Segment ButtonStyle ────────────────────────────────────────────────
            buttonStyle = segmentButtonStyle(),
            // ── Selected typography ────────────────────────────────────────────────
            selectedTextStyle =
                Cmp.Typography.Action.SegmentedControl.Unit.Selected.Label
                    .typography(),
        )

    // ────────────────────────────────────────────────────────────────────────────
    // Container colours
    // ────────────────────────────────────────────────────────────────────────────

    @Composable
    private fun containerColors(): SegmentedControlContainerColors =
        SegmentedControlContainerColors(
            surfaceFill =
                Cmp.Color.Action.SegmentedControl.Container.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.Action.SegmentedControl.Container.Surface.Stroke
                    .color(),
            titleLabelColor =
                Cmp.Color.Action.SegmentedControl.Content.Label.Default
                    .color(),
        )

    // ────────────────────────────────────────────────────────────────────────────
    // ButtonStyle built from SegmentedControl Cmp tokens
    // ────────────────────────────────────────────────────────────────────────────

    @Composable
    private fun segmentButtonStyle(): ButtonStyle {
        val typeColors = segmentTypeColors()
        return ButtonStyle(
            cornerRadius =
                Cmp.BorderRadius.Action.SegmentedControl.Unit
                    .dimension()
                    .pxToDp(),
            borderWidth =
                Cmp.BorderWidth.Action.SegmentedControl.Wrapper
                    .dimension()
                    .pxToDp(),
            minWidth =
                Cmp.Size.Action.SegmentedControl.Unit.MinWidth
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.Action.SegmentedControl.Unit.Height
                    .dimension()
                    .pxToDp(),
            paddingHorizontal =
                Cmp.Space.Action.SegmentedControl.Unit.StateLayer.H_Padding
                    .dimension()
                    .pxToDp(),
            paddingVertical =
                Cmp.Space.Action.SegmentedControl.Unit.StateLayer.V_Padding
                    .dimension()
                    .pxToDp(),
            iconSpacing =
                Cmp.Space.Action.SegmentedControl.Unit.StateLayer.Gap
                    .dimension()
                    .pxToDp(),
            textStyle =
                Cmp.Typography.Action.SegmentedControl.Unit.Unselected.Label
                    .typography(),
            // All tones identical — SegmentedControl has no tone axis
            destructive = typeColors,
            encourage = typeColors,
            primary = typeColors,
            prominent = typeColors,
            secondary = typeColors,
            tertiary = typeColors,
        )
    }

    @Composable
    private fun segmentTypeColors(): ButtonTypeColors =
        ButtonTypeColors(
            unselected = unselectedBranch(),
            selected = selectedBranch(),
            loadingIndicator = Color.Transparent,
            loadingTrackColour = Color.Transparent,
        )

    @Composable
    private fun selectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.SegmentedControl.Unit.Selected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.SegmentedControl.Unit.Selected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Surface.Stroke.Pressed
                            .color(),
                ),
            // SegmentedControl has no loading state — reuse idle colours
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Surface.Stroke.Idle
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.SegmentedControl.Unit.Selected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun unselectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.SegmentedControl.Unit.Unselected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.SegmentedControl.Unit.Unselected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            // SegmentedControl has no loading state — reuse idle colours
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.SegmentedControl.Unit.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )
}
