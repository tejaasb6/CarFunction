package com.ui.audi.widgets.navigationbars

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.navigationbars.NavigationBarBranchColors
import com.ui.core.widgets.navigationbars.NavigationBarStateColors
import com.ui.core.widgets.navigationbars.NavigationBarStyle
import com.ui.core.widgets.navigationbars.NavigationBarTypeColors

/** Audi brand defaults for the NavigationBar widget. */
@Suppress("TooManyFunctions")
internal object NavigationBarDefaults {
    @Composable
    fun style(): NavigationBarStyle =
        NavigationBarStyle(
            barHeight =
                Cmp.Size.Navigation.NavigationBar.Height
                    .dimension()
                    .pxToDp(),
            itemMinWidth =
                Cmp.Size.Navigation.NavigationBar.MD.Unit.Surface.MinWidth
                    .dimension()
                    .pxToDp(),
            itemSurfaceHeight =
                Cmp.Size.Navigation.NavigationBar.MD.Unit.Surface.Height
                    .dimension()
                    .pxToDp(),
            itemStateLayerHeight =
                Cmp.Size.Navigation.NavigationBar.MD.Unit.StateLayer.Height
                    .dimension()
                    .pxToDp(),
            underlineHeight =
                Cmp.Size.Navigation.NavigationBar.MD.Unit.Underline.Height
                    .dimension()
                    .pxToDp(),
            underlineWidth =
                Cmp.BorderWidth.Navigation.NavigationBar.Unit.Underline
                    .dimension()
                    .pxToDp(),
            itemCornerRadiusIdle =
                Cmp.BorderRadius.Navigation.NavigationBar.Unit.Idle
                    .dimension()
                    .pxToDp(),
            itemCornerRadiusPressed =
                Cmp.BorderRadius.Navigation.NavigationBar.Unit.Pressed
                    .dimension()
                    .pxToDp(),
            itemCornerRadiusDisabled =
                Cmp.BorderRadius.Navigation.NavigationBar.Unit.Disabled
                    .dimension()
                    .pxToDp(),
            itemBorderWidthIdle =
                Cmp.BorderWidth.Navigation.NavigationBar.Unit.Surface.Idle
                    .dimension()
                    .pxToDp(),
            itemBorderWidthPressed =
                Cmp.BorderWidth.Navigation.NavigationBar.Unit.Surface.Pressed
                    .dimension()
                    .pxToDp(),
            itemBorderWidthDisabled =
                Cmp.BorderWidth.Navigation.NavigationBar.Unit.Surface.Disabled
                    .dimension()
                    .pxToDp(),
            itemGap =
                Cmp.Space.Navigation.NavigationBar.MD.Unit.Gap
                    .dimension()
                    .pxToDp(),
            itemHPadding =
                Cmp.Space.Navigation.NavigationBar.MD.Unit.H_Padding
                    .dimension()
                    .pxToDp(),
            itemVPadding =
                Cmp.Space.Navigation.NavigationBar.MD.Unit.V_Padding
                    .dimension()
                    .pxToDp(),
            fadeOutWrapperHeight =
                Cmp.Size.Navigation.NavigationBar.MD.FadeOutWrapper.Height
                    .dimension()
                    .pxToDp(),
            iconSize = IconConfig.Size.MD,
            selectedTextStyle =
                Cmp.Typography.Navigation.NavigationBar.MD.Unit.Selected.Label
                    .typography(),
            unselectedTextStyle =
                Cmp.Typography.Navigation.NavigationBar.MD.Unit.Unselected.Label
                    .typography(),
            colors = colors(),
        )

    @Composable
    private fun colors(): NavigationBarTypeColors =
        NavigationBarTypeColors(
            selected = selectedBranch(),
            unselected = unselectedBranch(),
        )

    @Composable
    private fun selectedBranch(): NavigationBarBranchColors =
        NavigationBarBranchColors(
            stateLayerPressed =
                Cmp.Color.Navigation.NavigationBar.Unit.Selected.Surface.Fill.StateLayer.Pressed
                    .color(),
            idle =
                NavigationBarStateColors(
                    labelColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Icon.Idle
                            .color(),
                    underlineColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Underline.Idle
                            .color(),
                    surfaceFill =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Surface.Fill.Idle
                            .color(),
                    surfaceStroke =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                NavigationBarStateColors(
                    labelColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Icon.Pressed
                            .color(),
                    underlineColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Underline.Pressed
                            .color(),
                    surfaceFill =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Surface.Fill.Pressed
                            .color(),
                    surfaceStroke =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Surface.Stroke.Pressed
                            .color(),
                ),
            disabled =
                NavigationBarStateColors(
                    labelColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Icon.Idle
                            .color(),
                    underlineColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Content.Underline.Idle
                            .color(),
                    surfaceFill =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Surface.Fill.Idle
                            .color(),
                    surfaceStroke =
                        Cmp.Color.Navigation.NavigationBar.Unit.Selected.Surface.Stroke.Idle
                            .color(),
                ),
        )

    @Composable
    private fun unselectedBranch(): NavigationBarBranchColors =
        NavigationBarBranchColors(
            stateLayerPressed =
                Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Surface.Fill.StateLayer.Pressed
                    .color(),
            idle =
                NavigationBarStateColors(
                    labelColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Content.Icon.Idle
                            .color(),
                    underlineColor =
                        Sem.Color.Stroke.Transparent
                            .color(),
                    surfaceFill =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Surface.Fill.Idle
                            .color(),
                    surfaceStroke =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                NavigationBarStateColors(
                    labelColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Content.Icon.Pressed
                            .color(),
                    underlineColor =
                        Sem.Color.Stroke.Transparent
                            .color(),
                    surfaceFill =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Surface.Fill.Pressed
                            .color(),
                    surfaceStroke =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            disabled =
                NavigationBarStateColors(
                    labelColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Content.Icon.Disabled
                            .color(),
                    underlineColor =
                        Sem.Color.Stroke.Transparent
                            .color(),
                    surfaceFill =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Surface.Fill.Disabled
                            .color(),
                    surfaceStroke =
                        Cmp.Color.Navigation.NavigationBar.Unit.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )
}
