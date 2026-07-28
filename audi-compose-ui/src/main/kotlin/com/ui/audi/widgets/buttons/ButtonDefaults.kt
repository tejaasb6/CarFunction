package com.ui.audi.widgets.buttons

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.buttons.ButtonBranchColors
import com.ui.core.widgets.buttons.ButtonStateColors
import com.ui.core.widgets.buttons.ButtonStyle
import com.ui.core.widgets.buttons.ButtonTypeColors

@Suppress("TooManyFunctions", "LargeClass")
internal object ButtonDefaults {
    @Composable
    fun style(): ButtonStyle =
        ButtonStyle(
            cornerRadius =
                Cmp.BorderRadius.Action.Button.Default
                    .dimension()
                    .pxToDp(),
            borderWidth =
                Cmp.BorderWidth.Action.Button.Unselected.Surface.Idle
                    .dimension()
                    .pxToDp(),
            minWidth =
                Cmp.Size.Action.Button.MD.StateLayer.MinWidth
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.Action.Button.MD.StateLayer.MinHeight
                    .dimension()
                    .pxToDp(),
            paddingHorizontal =
                Cmp.Space.Action.Button.MD.StateLayer.H_Padding
                    .dimension()
                    .pxToDp(),
            paddingVertical =
                Sem.Space.Fixed._200
                    .dimension()
                    .pxToDp(),
            iconSpacing =
                Cmp.Space.Action.Button.MD.StateLayer.Gap
                    .dimension()
                    .pxToDp(),
            textStyle =
                Cmp.Typography.Action.Button.MD.Unselected.Label
                    .typography(),
            destructive = destructiveColors(),
            encourage = encourageColors(),
            primary = primaryColors(),
            prominent = prominentColors(),
            secondary = secondaryColors(),
            tertiary = tertiaryColors(),
        )

    @Composable
    private fun destructiveColors(): ButtonTypeColors {
        val unsel = destructiveUnselectedBranch()
        val sel = destructiveUnselectedBranch()
        return ButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.labelColor,
            loadingTrackColour = unsel.loading.labelColor.copy(alpha = 0.2f),
        )
    }

    @Composable
    private fun encourageColors(): ButtonTypeColors {
        val unsel = encourageUnselectedBranch()
        val sel = encourageUnselectedBranch()
        return ButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.labelColor,
            loadingTrackColour = unsel.loading.labelColor.copy(alpha = 0.2f),
        )
    }

    @Composable
    private fun primaryColors(): ButtonTypeColors {
        val unsel = primaryUnselectedBranch()
        val sel = primarySelectedBranch()
        return ButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.labelColor,
            loadingTrackColour = unsel.loading.labelColor.copy(alpha = 0.2f),
        )
    }

    @Composable
    private fun prominentColors(): ButtonTypeColors {
        val unsel = prominentUnselectedBranch()
        val sel = prominentUnselectedBranch()
        return ButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.labelColor,
            loadingTrackColour = unsel.loading.labelColor.copy(alpha = 0.2f),
        )
    }

    @Composable
    private fun secondaryColors(): ButtonTypeColors {
        val unsel = secondaryUnselectedBranch()
        val sel = secondarySelectedBranch()
        return ButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.labelColor,
            loadingTrackColour = unsel.loading.labelColor.copy(alpha = 0.2f),
        )
    }

    @Composable
    private fun tertiaryColors(): ButtonTypeColors {
        val unsel = tertiaryUnselectedBranch()
        val sel = tertiarySelectedBranch()
        return ButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.labelColor,
            loadingTrackColour = unsel.loading.labelColor.copy(alpha = 0.2f),
        )
    }

    @Composable
    private fun destructiveUnselectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Destructive.Unselected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Destructive.Unselected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Destructive.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Destructive.Unselected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Destructive.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Destructive.Unselected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Destructive.Unselected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Destructive.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Destructive.Unselected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Destructive.Unselected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Destructive.Unselected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Destructive.Unselected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Destructive.Unselected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Destructive.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun encourageUnselectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Encourage.Unselected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Encourage.Unselected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Encourage.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Encourage.Unselected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Encourage.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Encourage.Unselected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Encourage.Unselected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Encourage.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Encourage.Unselected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Encourage.Unselected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Encourage.Unselected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Encourage.Unselected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Encourage.Unselected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Encourage.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun primaryUnselectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Primary.Unselected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Primary.Unselected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Primary.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Primary.Unselected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Primary.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Primary.Unselected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Primary.Unselected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Primary.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Primary.Unselected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Primary.Unselected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Primary.Unselected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Primary.Unselected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Primary.Unselected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Primary.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun primarySelectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Primary.Selected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Primary.Selected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Primary.Selected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Primary.Selected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Primary.Selected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Primary.Selected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Primary.Selected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Primary.Selected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Primary.Selected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Primary.Selected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Primary.Selected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Primary.Selected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Primary.Selected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Primary.Selected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun prominentUnselectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Prominent.Unselected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Prominent.Unselected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Prominent.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Prominent.Unselected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Prominent.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Prominent.Unselected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Prominent.Unselected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Prominent.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Prominent.Unselected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Prominent.Unselected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Prominent.Unselected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Prominent.Unselected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Prominent.Unselected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Prominent.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun secondaryUnselectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Secondary.Unselected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Secondary.Unselected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Secondary.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Secondary.Unselected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Secondary.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Secondary.Unselected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Secondary.Unselected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Secondary.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Secondary.Unselected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Secondary.Unselected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Secondary.Unselected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Secondary.Unselected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Secondary.Unselected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Secondary.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun secondarySelectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Secondary.Selected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Secondary.Selected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Secondary.Selected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Secondary.Selected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Secondary.Selected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Secondary.Selected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Secondary.Selected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Secondary.Selected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Secondary.Selected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Secondary.Selected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Secondary.Selected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Secondary.Selected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Secondary.Selected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Secondary.Selected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun tertiaryUnselectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Tertiary.Unselected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Tertiary.Unselected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Tertiary.Unselected.Surface.Stroke.Disabled
                            .color(),
                ),
        )

    @Composable
    private fun tertiarySelectedBranch(): ButtonBranchColors =
        ButtonBranchColors(
            surfaceFill =
                Cmp.Color.Action.Button.Tertiary.Selected.Surface.Fill
                    .color(),
            stateLayerPressed =
                Cmp.Color.Action.Button.Tertiary.Selected.StateLayer.Pressed
                    .color(),
            idle =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Tertiary.Selected.Content.Label.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Tertiary.Selected.Content.Icon.Idle
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Tertiary.Selected.Surface.Stroke.Idle
                            .color(),
                ),
            pressed =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Tertiary.Selected.Content.Label.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Tertiary.Selected.Content.Icon.Pressed
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Tertiary.Selected.Surface.Stroke.Pressed
                            .color(),
                ),
            loading =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Tertiary.Selected.Content.Label.Loading
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Tertiary.Selected.Content.Icon.Loading
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Tertiary.Selected.Surface.Stroke.Loading
                            .color(),
                ),
            disabled =
                ButtonStateColors(
                    labelColor =
                        Cmp.Color.Action.Button.Tertiary.Selected.Content.Label.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Action.Button.Tertiary.Selected.Content.Icon.Disabled
                            .color(),
                    border =
                        Cmp.Color.Action.Button.Tertiary.Selected.Surface.Stroke.Disabled
                            .color(),
                ),
        )
}
