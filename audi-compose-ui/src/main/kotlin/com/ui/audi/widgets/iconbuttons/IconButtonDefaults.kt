package com.ui.audi.widgets.iconbuttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.iconbuttons.IconButtonBranchColors
import com.ui.core.widgets.iconbuttons.IconButtonLabelColors
import com.ui.core.widgets.iconbuttons.IconButtonStateColors
import com.ui.core.widgets.iconbuttons.IconButtonStyle
import com.ui.core.widgets.iconbuttons.IconButtonTypeColors

/**
 * Audi default [IconButtonStyle] — resolves every value from the token engine.
 *
 * Per-tone container colours (surfaceFill, border, stateLayer) are sourced from
 * the regular Button tokens (`Cmp.Color.Action.Button.{Tone}.*`) because
 * dedicated IconButton fill/border tokens are not yet defined.
 * Content/icon colours use the IconButton-specific tokens.
 */
internal object IconButtonDefaults {
    @Composable
    fun style(): IconButtonStyle =
        IconButtonStyle(
            cornerRadius =
                Cmp.BorderRadius.Action.IconButton.ButtonShape.Surface.Default
                    .dimension()
                    .pxToDp(),
            touchTarget =
                Cmp.Size.Action.IconButton.MD.ButtonShape.TouchTarget
                    .dimension()
                    .pxToDp(),
            stateLayerHeight =
                Cmp.Size.Action.IconButton.MD.ButtonShape.StateLayer.Height
                    .dimension()
                    .pxToDp(),
            stateLayerWidth =
                Cmp.Size.Action.IconButton.MD.ButtonShape.StateLayer.Width
                    .dimension()
                    .pxToDp(),
            gap =
                Cmp.Space.Action.IconButton.MD.Container.Gap
                    .dimension()
                    .pxToDp(),
            unselectedLabelStyle =
                Cmp.Typography.Action.IconButton.MD.Unselected.Label
                    .typography(),
            selectedLabelStyle =
                Cmp.Typography.Action.IconButton.MD.Selected.Label
                    .typography(),
            disabledOpacity = Sem.Opacity.Disabled.opacity(),
            prominent = prominentColors(),
            primary = primaryColors(),
            secondary = secondaryColors(),
            tertiary = tertiaryColors(),
            destructive = destructiveColors(),
            unselectedLabelColors = unselectedLabelColors(),
            selectedLabelColors = selectedLabelColors(),
        )

    @Composable
    private fun unselectedLabelColors(): IconButtonLabelColors =
        IconButtonLabelColors(
            idle =
                Cmp.Color.Action.IconButton.Unselected.Content.Label.Idle
                    .color(),
            pressed =
                Cmp.Color.Action.IconButton.Unselected.Content.Label.Pressed
                    .color(),
            loading =
                Cmp.Color.Action.IconButton.Unselected.Content.Label.Loading
                    .color(),
            disabled =
                Cmp.Color.Action.IconButton.Unselected.Content.Label.Disabled
                    .color(),
        )

    @Composable
    private fun selectedLabelColors(): IconButtonLabelColors =
        IconButtonLabelColors(
            idle =
                Cmp.Color.Action.IconButton.Selected.Content.Label.Idle
                    .color(),
            pressed =
                Cmp.Color.Action.IconButton.Selected.Content.Label.Pressed
                    .color(),
            loading =
                Cmp.Color.Action.IconButton.Selected.Content.Label.Loading
                    .color(),
            disabled =
                Cmp.Color.Action.IconButton.Selected.Content.Label.Disabled
                    .color(),
        )

    // ── Prominent ──────────────────────────────────────────────────────

    @Composable
    private fun prominentColors(): IconButtonTypeColors {
        val unsel =
            toneUnselectedBranch(
                surfaceFill =
                    Cmp.Color.Action.Button.Prominent.Unselected.Surface.Fill
                        .color(),
                stateLayerPressed =
                    Cmp.Color.Action.Button.Prominent.Unselected.StateLayer.Pressed
                        .color(),
                contentIdle =
                    Cmp.Color.Action.Button.Prominent.Unselected.Content.Icon.Idle
                        .color(),
                contentPressed =
                    Cmp.Color.Action.Button.Prominent.Unselected.Content.Icon.Pressed
                        .color(),
                contentLoading =
                    Cmp.Color.Action.Button.Prominent.Unselected.Content.Icon.Loading
                        .color(),
                contentDisabled =
                    Cmp.Color.Action.Button.Prominent.Unselected.Content.Icon.Disabled
                        .color(),
                borderIdle = Color.Transparent,
                borderPressed = Color.Transparent,
                borderLoading = Color.Transparent,
                borderDisabled = Color.Transparent,
            )
        // Prominent has no Selected tokens — reuse Unselected for Selected
        return IconButtonTypeColors(
            unselected = unsel,
            selected = unsel,
            loadingIndicator = unsel.loading.contentColor,
            loadingTrackColour = unsel.loading.contentColor.copy(alpha = 0.2f),
        )
    }

    // ── Primary ────────────────────────────────────────────────────────

    @Composable
    private fun primaryColors(): IconButtonTypeColors {
        val unsel =
            toneUnselectedBranch(
                surfaceFill =
                    Cmp.Color.Action.Button.Primary.Unselected.Surface.Fill
                        .color(),
                stateLayerPressed =
                    Cmp.Color.Action.Button.Primary.Unselected.StateLayer.Pressed
                        .color(),
                contentIdle =
                    Cmp.Color.Action.Button.Primary.Unselected.Content.Icon.Idle
                        .color(),
                contentPressed =
                    Cmp.Color.Action.Button.Primary.Unselected.Content.Icon.Pressed
                        .color(),
                contentLoading =
                    Cmp.Color.Action.Button.Primary.Unselected.Content.Icon.Loading
                        .color(),
                contentDisabled =
                    Cmp.Color.Action.Button.Primary.Unselected.Content.Icon.Disabled
                        .color(),
                borderIdle =
                    Cmp.Color.Action.Button.Primary.Unselected.Surface.Stroke.Idle
                        .color(),
                borderPressed =
                    Cmp.Color.Action.Button.Primary.Unselected.Surface.Stroke.Pressed
                        .color(),
                borderLoading =
                    Cmp.Color.Action.Button.Primary.Unselected.Surface.Stroke.Loading
                        .color(),
                borderDisabled =
                    Cmp.Color.Action.Button.Primary.Unselected.Surface.Stroke.Disabled
                        .color(),
            )
        val sel =
            toneSelectedBranch(
                surfaceFill =
                    Cmp.Color.Action.Button.Primary.Selected.Surface.Fill
                        .color(),
                stateLayerPressed =
                    Cmp.Color.Action.Button.Primary.Selected.StateLayer.Pressed
                        .color(),
                contentIdle =
                    Cmp.Color.Action.Button.Primary.Selected.Content.Icon.Idle
                        .color(),
                contentPressed =
                    Cmp.Color.Action.Button.Primary.Selected.Content.Icon.Pressed
                        .color(),
                contentLoading =
                    Cmp.Color.Action.Button.Primary.Selected.Content.Icon.Loading
                        .color(),
                contentDisabled =
                    Cmp.Color.Action.Button.Primary.Selected.Content.Icon.Disabled
                        .color(),
                borderIdle =
                    Cmp.Color.Action.Button.Primary.Selected.Surface.Stroke.Idle
                        .color(),
                borderPressed =
                    Cmp.Color.Action.Button.Primary.Selected.Surface.Stroke.Pressed
                        .color(),
                borderLoading =
                    Cmp.Color.Action.Button.Primary.Selected.Surface.Stroke.Loading
                        .color(),
                borderDisabled =
                    Cmp.Color.Action.Button.Primary.Selected.Surface.Stroke.Disabled
                        .color(),
            )
        return IconButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.contentColor,
            loadingTrackColour = unsel.loading.contentColor.copy(alpha = 0.2f),
        )
    }

    // ── Secondary ──────────────────────────────────────────────────────

    @Composable
    private fun secondaryColors(): IconButtonTypeColors {
        val unsel =
            toneUnselectedBranch(
                surfaceFill =
                    Cmp.Color.Action.Button.Secondary.Unselected.Surface.Fill
                        .color(),
                stateLayerPressed =
                    Cmp.Color.Action.Button.Secondary.Unselected.StateLayer.Pressed
                        .color(),
                contentIdle =
                    Cmp.Color.Action.Button.Secondary.Unselected.Content.Icon.Idle
                        .color(),
                contentPressed =
                    Cmp.Color.Action.Button.Secondary.Unselected.Content.Icon.Pressed
                        .color(),
                contentLoading =
                    Cmp.Color.Action.Button.Secondary.Unselected.Content.Icon.Loading
                        .color(),
                contentDisabled =
                    Cmp.Color.Action.Button.Secondary.Unselected.Content.Icon.Disabled
                        .color(),
                borderIdle =
                    Cmp.Color.Action.Button.Secondary.Unselected.Surface.Stroke.Idle
                        .color(),
                borderPressed =
                    Cmp.Color.Action.Button.Secondary.Unselected.Surface.Stroke.Pressed
                        .color(),
                borderLoading =
                    Cmp.Color.Action.Button.Secondary.Unselected.Surface.Stroke.Loading
                        .color(),
                borderDisabled =
                    Cmp.Color.Action.Button.Secondary.Unselected.Surface.Stroke.Disabled
                        .color(),
            )
        val sel =
            toneSelectedBranch(
                surfaceFill =
                    Cmp.Color.Action.Button.Secondary.Selected.Surface.Fill
                        .color(),
                stateLayerPressed =
                    Cmp.Color.Action.Button.Secondary.Selected.StateLayer.Pressed
                        .color(),
                contentIdle =
                    Cmp.Color.Action.Button.Secondary.Selected.Content.Icon.Idle
                        .color(),
                contentPressed =
                    Cmp.Color.Action.Button.Secondary.Selected.Content.Icon.Pressed
                        .color(),
                contentLoading =
                    Cmp.Color.Action.Button.Secondary.Selected.Content.Icon.Loading
                        .color(),
                contentDisabled =
                    Cmp.Color.Action.Button.Secondary.Selected.Content.Icon.Disabled
                        .color(),
                borderIdle =
                    Cmp.Color.Action.Button.Secondary.Selected.Surface.Stroke.Idle
                        .color(),
                borderPressed =
                    Cmp.Color.Action.Button.Secondary.Selected.Surface.Stroke.Pressed
                        .color(),
                borderLoading =
                    Cmp.Color.Action.Button.Secondary.Selected.Surface.Stroke.Loading
                        .color(),
                borderDisabled =
                    Cmp.Color.Action.Button.Secondary.Selected.Surface.Stroke.Disabled
                        .color(),
            )
        return IconButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.contentColor,
            loadingTrackColour = unsel.loading.contentColor.copy(alpha = 0.2f),
        )
    }

    // ── Tertiary ───────────────────────────────────────────────────────

    @Composable
    private fun tertiaryColors(): IconButtonTypeColors {
        val unsel =
            toneUnselectedBranch(
                surfaceFill =
                    Cmp.Color.Action.Button.Tertiary.Unselected.Surface.Fill
                        .color(),
                stateLayerPressed =
                    Cmp.Color.Action.Button.Tertiary.Unselected.StateLayer.Pressed
                        .color(),
                contentIdle =
                    Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Idle
                        .color(),
                contentPressed =
                    Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Pressed
                        .color(),
                contentLoading =
                    Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Loading
                        .color(),
                contentDisabled =
                    Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Disabled
                        .color(),
                borderIdle = Color.Transparent,
                borderPressed = Color.Transparent,
                borderLoading = Color.Transparent,
                borderDisabled = Color.Transparent,
            )
        val sel =
            toneSelectedBranch(
                surfaceFill =
                    Cmp.Color.Action.Button.Tertiary.Selected.Surface.Fill
                        .color(),
                stateLayerPressed =
                    Cmp.Color.Action.Button.Tertiary.Selected.StateLayer.Pressed
                        .color(),
                contentIdle =
                    Cmp.Color.Action.Button.Tertiary.Selected.Content.Icon.Idle
                        .color(),
                contentPressed =
                    Cmp.Color.Action.Button.Tertiary.Selected.Content.Icon.Pressed
                        .color(),
                contentLoading =
                    Cmp.Color.Action.Button.Tertiary.Selected.Content.Icon.Loading
                        .color(),
                contentDisabled =
                    Cmp.Color.Action.Button.Tertiary.Selected.Content.Icon.Disabled
                        .color(),
                borderIdle = Color.Transparent,
                borderPressed = Color.Transparent,
                borderLoading = Color.Transparent,
                borderDisabled = Color.Transparent,
            )
        return IconButtonTypeColors(
            unselected = unsel,
            selected = sel,
            loadingIndicator = unsel.loading.contentColor,
            loadingTrackColour = unsel.loading.contentColor.copy(alpha = 0.2f),
        )
    }

    // ── Destructive ────────────────────────────────────────────────────

    @Composable
    private fun destructiveColors(): IconButtonTypeColors {
        val unsel =
            toneUnselectedBranch(
                surfaceFill =
                    Cmp.Color.Action.Button.Destructive.Unselected.Surface.Fill
                        .color(),
                stateLayerPressed =
                    Cmp.Color.Action.Button.Destructive.Unselected.StateLayer.Pressed
                        .color(),
                contentIdle =
                    Cmp.Color.Action.Button.Destructive.Unselected.Content.Icon.Idle
                        .color(),
                contentPressed =
                    Cmp.Color.Action.Button.Destructive.Unselected.Content.Icon.Pressed
                        .color(),
                contentLoading =
                    Cmp.Color.Action.Button.Destructive.Unselected.Content.Icon.Loading
                        .color(),
                contentDisabled =
                    Cmp.Color.Action.Button.Destructive.Unselected.Content.Icon.Disabled
                        .color(),
                borderIdle = Color.Transparent,
                borderPressed = Color.Transparent,
                borderLoading = Color.Transparent,
                borderDisabled = Color.Transparent,
            )
        // Destructive has no Selected tokens — reuse Unselected for Selected
        return IconButtonTypeColors(
            unselected = unsel,
            selected = unsel,
            loadingIndicator = unsel.loading.contentColor,
            loadingTrackColour = unsel.loading.contentColor.copy(alpha = 0.2f),
        )
    }

    // ── Branch builders ────────────────────────────────────────────────

    private fun toneUnselectedBranch(
        surfaceFill: Color,
        stateLayerPressed: Color,
        contentIdle: Color,
        contentPressed: Color,
        contentLoading: Color,
        contentDisabled: Color,
        borderIdle: Color,
        borderPressed: Color,
        borderLoading: Color,
        borderDisabled: Color,
    ): IconButtonBranchColors =
        IconButtonBranchColors(
            surfaceFill = surfaceFill,
            stateLayerPressed = stateLayerPressed,
            idle = IconButtonStateColors(contentColor = contentIdle, border = borderIdle),
            pressed = IconButtonStateColors(contentColor = contentPressed, border = borderPressed),
            loading = IconButtonStateColors(contentColor = contentLoading, border = borderLoading),
            disabled = IconButtonStateColors(contentColor = contentDisabled, border = borderDisabled),
        )

    private fun toneSelectedBranch(
        surfaceFill: Color,
        stateLayerPressed: Color,
        contentIdle: Color,
        contentPressed: Color,
        contentLoading: Color,
        contentDisabled: Color,
        borderIdle: Color,
        borderPressed: Color,
        borderLoading: Color,
        borderDisabled: Color,
    ): IconButtonBranchColors =
        IconButtonBranchColors(
            surfaceFill = surfaceFill,
            stateLayerPressed = stateLayerPressed,
            idle = IconButtonStateColors(contentColor = contentIdle, border = borderIdle),
            pressed = IconButtonStateColors(contentColor = contentPressed, border = borderPressed),
            loading = IconButtonStateColors(contentColor = contentLoading, border = borderLoading),
            disabled = IconButtonStateColors(contentColor = contentDisabled, border = borderDisabled),
        )
}
