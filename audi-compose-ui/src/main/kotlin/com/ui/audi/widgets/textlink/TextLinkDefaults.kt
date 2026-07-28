package com.ui.audi.widgets.textlink

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.textlink.TextLinkStateColors
import com.ui.core.widgets.textlink.TextLinkStyleSpec
import com.ui.core.widgets.textlink.TextLinkVariantColors

/** Audi default [TextLinkStyleSpec] — resolves every value from the token engine. */
internal object TextLinkDefaults {
    /** Builds the Audi [TextLinkStyleSpec] from the active theme tokens. */
    @Composable
    fun style(): TextLinkStyleSpec =
        TextLinkStyleSpec(
            standalone =
                TextLinkVariantColors(
                    label =
                        TextLinkStateColors(
                            idle =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Label.Idle
                                    .color(),
                            pressed =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Label.Pressed
                                    .color(),
                            disabled =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Label.Disabled
                                    .color(),
                        ),
                    icon =
                        TextLinkStateColors(
                            idle =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Icon.Idle
                                    .color(),
                            pressed =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Icon.Pressed
                                    .color(),
                            disabled =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Icon.Disabled
                                    .color(),
                        ),
                    underline =
                        TextLinkStateColors(
                            idle =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Underline.Idle
                                    .color(),
                            pressed =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Underline.Pressed
                                    .color(),
                            disabled =
                                Cmp.Color.Navigation.TextLink.Standalone.Content.Underline.Disabled
                                    .color(),
                        ),
                ),
            inline =
                TextLinkVariantColors(
                    label =
                        TextLinkStateColors(
                            idle =
                                Cmp.Color.Navigation.TextLink.Inline.Content.Label.Idle
                                    .color(),
                            pressed =
                                Cmp.Color.Navigation.TextLink.Inline.Content.Label.Pressed
                                    .color(),
                            disabled =
                                Cmp.Color.Navigation.TextLink.Inline.Content.Label.Disabled
                                    .color(),
                        ),
                    icon =
                        TextLinkStateColors(
                            idle =
                                Cmp.Color.Navigation.TextLink.Inline.Content.Icon.Idle
                                    .color(),
                            pressed =
                                Cmp.Color.Navigation.TextLink.Inline.Content.Icon.Pressed
                                    .color(),
                            disabled =
                                Cmp.Color.Navigation.TextLink.Inline.Content.Icon.Disabled
                                    .color(),
                        ),
                    underline =
                        TextLinkStateColors(
                            // Inline shares underline colours with the base (generic) tokens
                            idle =
                                Cmp.Color.Navigation.TextLink.Content.Underline.Idle
                                    .color(),
                            pressed =
                                Cmp.Color.Navigation.TextLink.Content.Underline.Pressed
                                    .color(),
                            disabled =
                                Cmp.Color.Navigation.TextLink.Content.Underline.Disabled
                                    .color(),
                        ),
                ),
            idleTextStyle =
                Cmp.Typography.Navigation.TextLink.MD.Content.Label.Idle
                    .typography(),
            pressedTextStyle =
                Cmp.Typography.Navigation.TextLink.MD.Content.Label.Pressed
                    .typography(),
            disabledTextStyle =
                Cmp.Typography.Navigation.TextLink.MD.Content.Label.Disabled
                    .typography(),
            height =
                Cmp.Size.Navigation.TextLink.MD.Height
                    .dimension()
                    .pxToDp(),
            gap =
                Cmp.Space.Navigation.TextLink.MD.Gap
                    .dimension()
                    .pxToDp(),
            underlineOffset =
                Cmp.Space.Navigation.TextLink.MD.Underline.Offset
                    .dimension()
                    .pxToDp(),
            underlineThicknessIdle =
                Cmp.Size.Navigation.TextLink.MD.Underline.Thickness.Idle
                    .dimension()
                    .pxToDp(),
            underlineThicknessPressed =
                Cmp.Size.Navigation.TextLink.MD.Underline.Thickness.Pressed
                    .dimension()
                    .pxToDp(),
            underlineThicknessDisabled =
                Cmp.Size.Navigation.TextLink.MD.Underline.Thickness.Disabled
                    .dimension()
                    .pxToDp(),
            focusRingColor =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusRingWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            disabledOpacity =
                Cmp.Opacity.Navigation.Disabled
                    .opacity(),
        )
}
