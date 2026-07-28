package com.ui.audi.widgets.searchfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.searchfields.SearchFieldStateColors
import com.ui.core.widgets.searchfields.SearchFieldStyle

/**
 * Provides the Audi brand [SearchFieldStyle] derived from the current composition tokens.
 *
 * Called inside `AudiTheme` so the style reacts to theme / token changes.
 *
 * ### Token mapping overview
 * | Dimension / colour             | Token path                                                |
 * |:-------------------------------|:----------------------------------------------------------|
 * | Field height                   | `Cmp.Size.Forms.FormFields.Field.VisualHeight`            |
 * | Corner radius                  | `Cmp.BorderRadius.Forms.SearchField.Default`              |
 * | Horizontal padding             | `Cmp.Space.Forms.FormFields.Field.H_Padding`              |
 * | Icon / trailing gap            | `Cmp.Space.Forms.FormFields.Field.Gap`                    |
 * | Hint top padding               | `Cmp.Space.Forms.FormFields.CaptionGroup.T_Padding`       |
 * | Hint start padding             | `Cmp.Space.Forms.SearchField.Caption.L_Padding`           |
 * | Leading icon width             | `Cmp.Size.DataDisplay.Icon.MD.MinWidth`                   |
 * | Leading icon height            | `Cmp.Size.DataDisplay.Icon.MD.Height`                     |
 * | Trailing button width          | `Cmp.Size.Action.ComponentButton.MD.StateLayer.MinWidth`  |
 * | Trailing button height         | `Cmp.Size.Action.ComponentButton.MD.StateLayer.Height`    |
 * | State-layer (pressed)          | `Sem.Color.StateLayer.Subtle.Pressed`                     |
 * | Surface fill                   | `Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill`   |
 * | Border width per state         | `Cmp.BorderWidth.Forms.FormFields.Default.*`              |
 */
internal object AudiSearchFieldDefaults {
    /**
     * Builds the complete [SearchFieldStyle] from Audi design-system tokens.
     *
     * @return A fully-resolved [SearchFieldStyle] for the current theme.
     */
    @Composable
    fun style(): SearchFieldStyle =
        SearchFieldStyle(
            // -- Dimensions --
            fieldHeight =
                Cmp.Size.Forms.FormFields.Field.VisualHeight
                    .dimension()
                    .pxToDp(),
            cornerRadius =
                Cmp.BorderRadius.Forms.SearchField.Default
                    .dimension()
                    .pxToDp(),
            horizontalPadding =
                Cmp.Space.Forms.FormFields.Field.H_Padding
                    .dimension()
                    .pxToDp(),
            iconGap =
                Cmp.Space.Forms.FormFields.Field.Gap
                    .dimension()
                    .pxToDp(),
            trailingGap =
                Cmp.Space.Forms.FormFields.Field.Gap
                    .dimension()
                    .pxToDp(),
            hintTopPadding =
                Cmp.Space.Forms.FormFields.CaptionGroup.T_Padding
                    .dimension()
                    .pxToDp(),
            hintStartPadding =
                Cmp.Space.Forms.SearchField.Caption.L_Padding
                    .dimension()
                    .pxToDp(),
            // -- Leading icon (DataDisplay.Icon.MD) --
            leadingIconWidth =
                Cmp.Size.DataDisplay.Icon.MD.MinWidth
                    .dimension()
                    .pxToDp(),
            leadingIconHeight =
                Cmp.Size.DataDisplay.Icon.MD.Height
                    .dimension()
                    .pxToDp(),
            // -- Trailing button (ComponentButton.MD.StateLayer) --
            trailingButtonWidth =
                Cmp.Size.Action.ComponentButton.MD.StateLayer.MinWidth
                    .dimension()
                    .pxToDp(),
            trailingButtonHeight =
                Cmp.Size.Action.ComponentButton.MD.StateLayer.Height
                    .dimension()
                    .pxToDp(),
            // -- Typography --
            inputTextStyle =
                Cmp.Typography.Forms.FormFields.UserInput
                    .typography(),
            placeholderTextStyle =
                Cmp.Typography.Forms.FormFields.Placeholder
                    .typography(),
            hintTextStyle =
                Cmp.Typography.Forms.FormFields.Caption
                    .typography(),
            // -- Default (unfilled) states --
            defaultIdle = defaultIdle(),
            defaultPressed = defaultPressed(),
            defaultActive = defaultActive(),
            defaultDisabled = defaultDisabled(),
            // -- Filled states --
            filledIdle = filledIdle(),
            filledPressed = filledPressed(),
            filledActive = filledActive(),
            filledDisabled = filledDisabled(),
            filledLoading = filledLoading(),
        )

    // ── Default (unfilled) states ───────────────────────────────────────────────

    /** Idle state colours when the field is empty. */
    @Composable
    private fun defaultIdle() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Idle
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Idle
                    .dimension()
                    .pxToDp(),
            stateLayerFill = Color.Transparent,
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Idle
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Idle
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Idle
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )

    /** Pressed state colours when the field is empty. */
    @Composable
    private fun defaultPressed() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Pressed
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Pressed
                    .dimension()
                    .pxToDp(),
            stateLayerFill =
                Sem.Color.StateLayer.Subtle.Pressed
                    .color(),
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Pressed
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Pressed
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Pressed
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )

    /** Active / editing state colours when the field is empty. */
    @Composable
    private fun defaultActive() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Active
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Active
                    .dimension()
                    .pxToDp(),
            stateLayerFill = Color.Transparent,
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Active
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Active
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Active
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )

    /** Disabled state colours when the field is empty. */
    @Composable
    private fun defaultDisabled() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Disabled
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Disabled
                    .dimension()
                    .pxToDp(),
            stateLayerFill = Color.Transparent,
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Disabled
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Disabled
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Disabled
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )

    // ── Filled states ───────────────────────────────────────────────────────────

    /** Idle state colours when the field contains user input. */
    @Composable
    private fun filledIdle() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Idle
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Idle
                    .dimension()
                    .pxToDp(),
            stateLayerFill = Color.Transparent,
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Idle
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Idle
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Idle
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )

    /** Pressed state colours when the field contains user input. */
    @Composable
    private fun filledPressed() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Pressed
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Pressed
                    .dimension()
                    .pxToDp(),
            stateLayerFill =
                Sem.Color.StateLayer.Subtle.Pressed
                    .color(),
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Pressed
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Pressed
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Pressed
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )

    /** Active / editing state colours when the field contains user input. */
    @Composable
    private fun filledActive() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Active
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Active
                    .dimension()
                    .pxToDp(),
            stateLayerFill = Color.Transparent,
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Active
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Active
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Active
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )

    /** Disabled state colours when the field contains user input. */
    @Composable
    private fun filledDisabled() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Disabled
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Disabled
                    .dimension()
                    .pxToDp(),
            stateLayerFill = Color.Transparent,
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Disabled
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Disabled
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Disabled
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )

    /** Loading state colours when the field contains user input. */
    @Composable
    private fun filledLoading() =
        SearchFieldStateColors(
            surfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            border =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Loading
                    .color(),
            borderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Loading
                    .dimension()
                    .pxToDp(),
            stateLayerFill = Color.Transparent,
            focusBorder =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusBorderWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            placeholderColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Idle
                    .color(),
            inputTextColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Loading
                    .color(),
            iconColor =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Loading
                    .color(),
            hintColor =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
        )
}
