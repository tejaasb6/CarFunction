package com.ui.core.widgets.radiobuttons

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Per-state colour set for one radio button variant (default or error) × selection state.
 *
 * Each combination of (selected/unselected) × (default/error) × (idle/pressed/disabled)
 * is fully specified so the brand impl never needs conditional logic beyond a simple lookup.
 *
 * ```kotlin
 * val colors = resolveColors(style, selected, isError, enabled, isPressed)
 * ```
 */
@Immutable
data class RadioButtonStateColors(
    // ── Control circle ──────────────────────────────────────────────────────
    val controlFill: Color,
    val controlStroke: Color,
    val controlStrokeWidth: Dp,
    val indicatorTint: Color,
    val stateLayerColor: Color,
    // ── Text slots ──────────────────────────────────────────────────────────
    val labelColor: Color,
    val hintColor: Color,
    val appendixColor: Color,
    val errorColor: Color,
)

/**
 * Full visual specification for [RadioButton].
 *
 * Holds dimensions, typography, and per-variant colour sets.
 * Override at any level of the composition tree via [LocalRadioButtonStyle].
 *
 * ```kotlin
 * CompositionLocalProvider(LocalRadioButtonStyle provides customStyle) {
 *     RadioButton(...)
 * }
 * ```
 *
 * @param controlSize               Width/height of the radio button control circle.
 * @param controlCornerRadius       Corner radius of the control circle (typically full circle, but parametrized).
 * @param touchTargetSize           Minimum touch target height (`Cmp.Size.Forms.FormControls.TouchTarget.Height`).
 * @param touchTargetWidth          Minimum touch target width (`Cmp.Size.Forms.FormControls.TouchTarget.Width`).
 * @param controlLabelSpacing       Horizontal spacing between control circle and text content.
 * @param labelAppendixGap          Horizontal gap between label text and appendix text.
 * @param hintSpacing               Vertical spacing between the label row and the hint text.
 * @param errorSpacing              Vertical spacing between the hint text and the error row.
 * @param errorIconGap              Horizontal gap between the error icon and error text.
 * @param errorIconWidth            Width of the error triangle.
 * @param errorIconHeight           Height of the error triangle.
 * @param errorIconContainerSize    Container dimension for the semantic shape.
 * @param errorIconFill             Fill colour for the error triangle.
 * @param errorIconStroke           Border colour for the error triangle.
 * @param errorIconBorderWidth      Border width for the error triangle.
 * @param contentPadding            Inner padding around the entire radio button row.
 * @param surfaceLeftPadding        Left padding for the entire radio button surface (`Cmp.Space.Forms.FormControls.Surface.L-Padding`).
 * @param labelTopPadding           Top padding for the Control+Label row (`Cmp.Space.Forms.FormControls.Content.Label.T-Padding`).
 * @param labelLeftPadding          Left padding for the Label+Appendix container (`Cmp.Space.Forms.FormControls.Content.Label.L-Padding`).
 * @param hintLeftPadding           Left padding for the hint text wrapper (`Cmp.Space.Forms.FormControls.Content.Hint.L-Padding`).
 * @param errorLeftPadding          Left padding for the error text wrapper (`Cmp.Space.Forms.FormControls.Content.CaptionError.L-Padding`).
 * @param labelTextStyle            Typography for the label text.
 * @param hintTextStyle             Typography for the hint text.
 * @param appendixTextStyle         Typography for the appendix text.
 * @param errorTextStyle            Typography for the error text.
 * @param indicatorSize             Size of the selection indicator circle (when selected).
 * @param disabledOpacity           Opacity applied to the control when disabled (`Sem.Opacity.Disabled`).
 * @param focusRingColor            Colour of the D-pad / rotary focus ring (`Sem.Color.Stroke.Signal.Focus`).
 * @param focusRingWidth            Stroke width of the focus ring (`Sem.BorderWidth.FocusRing`).
 * @param focusRingGap              Gap between control border and focus ring (`Sem.Space.Fixed._50`).
 * @param unselectedDefault         Colours for unselected + default (no error) idle state.
 * @param unselectedDefaultPressed  Colours for unselected + default pressed state.
 * @param unselectedDefaultDisabled Colours for unselected + default disabled state.
 * @param unselectedError           Colours for unselected + error idle state.
 * @param unselectedErrorPressed    Colours for unselected + error pressed state.
 * @param selectedDefault           Colours for selected + default idle state.
 * @param selectedDefaultPressed    Colours for selected + default pressed state.
 * @param selectedDefaultDisabled   Colours for selected + default disabled state.
 * @param selectedError             Colours for selected + error idle state.
 * @param selectedErrorPressed      Colours for selected + error pressed state.
 */
@Immutable
data class RadioButtonStyle(
    // ── Dimensions ──────────────────────────────────────────────────────────
    val controlSize: Dp,
    val controlCornerRadius: Dp,
    val touchTargetSize: Dp,
    val touchTargetWidth: Dp,
    val controlLabelSpacing: Dp,
    val labelAppendixGap: Dp,
    val hintSpacing: Dp,
    val errorSpacing: Dp,
    val errorIconGap: Dp,
    val errorIconWidth: Dp,
    val errorIconHeight: Dp,
    val errorIconContainerSize: Dp,
    val errorIconFill: Color,
    val errorIconStroke: Color,
    val errorIconBorderWidth: Dp,
    val contentPadding: Dp,
    val surfaceLeftPadding: Dp,
    val labelTopPadding: Dp,
    val labelLeftPadding: Dp,
    val hintLeftPadding: Dp,
    val errorLeftPadding: Dp,
    val indicatorSize: Dp,
    // ── Disabled & Focus ring ───────────────────────────────────────────────
    val disabledOpacity: Float,
    val focusRingColor: Color,
    val focusRingWidth: Dp,
    val focusRingGap: Dp,
    // ── Typography ──────────────────────────────────────────────────────────
    val labelTextStyle: TextStyle,
    val hintTextStyle: TextStyle,
    val appendixTextStyle: TextStyle,
    val errorTextStyle: TextStyle,
    // ── Per-variant × selection colour sets ──────────────────────────────────
    val unselectedDefault: RadioButtonStateColors,
    val unselectedDefaultPressed: RadioButtonStateColors,
    val unselectedDefaultDisabled: RadioButtonStateColors,
    val unselectedError: RadioButtonStateColors,
    val unselectedErrorPressed: RadioButtonStateColors,
    val selectedDefault: RadioButtonStateColors,
    val selectedDefaultPressed: RadioButtonStateColors,
    val selectedDefaultDisabled: RadioButtonStateColors,
    val selectedError: RadioButtonStateColors,
    val selectedErrorPressed: RadioButtonStateColors,
)

/**
 * Composition local for [RadioButtonStyle].
 *
 * Provided by brand themes (e.g. [com.ui.audi.AudiTheme]).
 * Throws if accessed outside a theme scope.
 */
val LocalRadioButtonStyle =
    compositionLocalOf<RadioButtonStyle> {
        error("No RadioButtonStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
