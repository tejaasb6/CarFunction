package com.ui.core.widgets.checkbox

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Per-state colour set for one checkbox variant (default or error) × selection state.
 *
 * Each combination of (selected/unselected) × (default/error) × (idle/pressed/disabled)
 * is fully specified so the brand impl never needs conditional logic beyond a simple lookup.
 *
 * ```kotlin
 * val colors = resolveColors(style, checked, isError, enabled, isPressed)
 * ```
 */
@Immutable
data class CheckboxStateColors(
    // ── Control box ─────────────────────────────────────────────────────────
    val controlFill: Color,
    val controlStroke: Color,
    val controlStrokeWidth: Dp,
    val iconTint: Color,
    val stateLayerColor: Color,
    // ── Text slots ──────────────────────────────────────────────────────────
    val labelColor: Color,
    val hintColor: Color,
    val appendixColor: Color,
    val errorColor: Color,
)

/**
 * Full visual specification for [Checkbox].
 *
 * Holds dimensions, typography, and per-variant colour sets.
 * Override at any level of the composition tree via [LocalCheckboxStyle].
 *
 * ```kotlin
 * CompositionLocalProvider(LocalCheckboxStyle provides customStyle) {
 *     Checkbox(...)
 * }
 * ```
 *
 * @param controlSize               Width/height of the checkbox control box.
 * @param controlCornerRadius       Corner radius of the control box.
 * @param touchTargetSize           Minimum touch target height (meets automotive HMI guidelines).
 * @param touchTargetWidth          Minimum touch target width (`Cmp.Size.Forms.FormControls.TouchTarget.Width`).
 * @param surfaceGap                Item spacing inside the surface (`Cmp.Space.Forms.FormControls.Surface.Gap`).
 * @param controlLabelSpacing       Horizontal spacing between control box and text content.
 * @param labelTopPadding           Top padding for the checkbox+label row (`Cmp.Space.Forms.FormControls.Content.Label.T-Padding`).
 * @param labelLeftPadding          Left padding for the label+appendix row (`Cmp.Space.Forms.FormControls.Content.Label.L-Padding`).
 * @param labelAppendixGap          Horizontal gap between label text and appendix text.
 * @param hintSpacing               Vertical spacing between the label row and the hint text.
 * @param hintLeftPadding           Left padding for the hint wrapper (`Cmp.Space.Forms.FormControls.Content.Hint.L-Padding`).
 * @param errorSpacing              Vertical spacing between the hint text and the error row.
 * @param errorLeftPadding          Left padding for the error wrapper (`Cmp.Space.Forms.FormControls.Content.CaptionError.L-Padding`).
 * @param errorIconGap              Horizontal gap between the error icon and error text.
 * @param errorIconWidth            Width of the error icon.
 * @param errorIconHeight           Height of the error icon.
 * @param badgeGap                  Item spacing inside the error badge surface (`Cmp.Space.Feedback.Badge.Gap`).
 * @param semanticShapeDimension    Container dimension for the semantic shape (triangle) (`Cmp.Size.Feedback.SemanticShape.Container.Dimension`).
 * @param semanticShapeFill         Fill colour for the critical semantic shape (`Cmp.Color.Feedback.SemanticShape.Critical.Surface.Fill`).
 * @param semanticShapeStroke       Border colour for the critical semantic shape (`Cmp.Color.Feedback.SemanticShape.Critical.Surface.Stroke`).
 * @param semanticShapeBorderWidth  Border width for the semantic shape (`Cmp.BorderWidth.Feedback.SemanticShape.Default`).
 * @param contentPadding            Inner padding around the entire checkbox row.
 * @param focusRingColor            Colour of the outer focus ring (`Sem.Color.Stroke.Signal.Focus`).
 * @param focusRingWidth            Width of the outer focus ring (`Sem.BorderWidth.FocusRing`).
 * @param focusInnerBorderColor     Colour of the inner grey border shown in focused state (`Sem.Color.Stroke.Subtle`).
 * @param focusInnerBorderWidth     Width of the inner grey border shown in focused state (`Sem.BorderWidth.SM`).
 * @param focusRingGap              Gap between the outer focus ring and the inner content border (`Sem.Space.Fixed._50`).
 * @param labelTextStyle            Typography for the label text.
 * @param hintTextStyle             Typography for the hint text.
 * @param appendixTextStyle         Typography for the appendix text.
 * @param errorTextStyle            Typography for the error text.
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
data class CheckboxStyle(
    // ── Dimensions ──────────────────────────────────────────────────────────
    val controlSize: Dp,
    val controlCornerRadius: Dp,
    val touchTargetSize: Dp,
    val touchTargetWidth: Dp,
    val surfaceGap: Dp,
    val controlLabelSpacing: Dp,
    val labelTopPadding: Dp,
    val labelLeftPadding: Dp,
    val labelAppendixGap: Dp,
    val hintSpacing: Dp,
    val hintLeftPadding: Dp,
    val errorSpacing: Dp,
    val errorLeftPadding: Dp,
    val errorIconGap: Dp,
    val errorIconWidth: Dp,
    val errorIconHeight: Dp,
    val badgeGap: Dp,
    val semanticShapeDimension: Dp,
    val semanticShapeFill: Color,
    val semanticShapeStroke: Color,
    val semanticShapeBorderWidth: Dp,
    val contentPadding: Dp,
    // ── Focus ring ──────────────────────────────────────────────────────────
    val focusRingColor: Color,
    val focusRingWidth: Dp,
    val focusInnerBorderColor: Color,
    val focusInnerBorderWidth: Dp,
    val focusRingGap: Dp,
    // ── Typography ──────────────────────────────────────────────────────────
    val labelTextStyle: TextStyle,
    val hintTextStyle: TextStyle,
    val appendixTextStyle: TextStyle,
    val errorTextStyle: TextStyle,
    // ── Per-variant × selection colour sets ──────────────────────────────────
    val unselectedDefault: CheckboxStateColors,
    val unselectedDefaultPressed: CheckboxStateColors,
    val unselectedDefaultDisabled: CheckboxStateColors,
    val unselectedError: CheckboxStateColors,
    val unselectedErrorPressed: CheckboxStateColors,
    val selectedDefault: CheckboxStateColors,
    val selectedDefaultPressed: CheckboxStateColors,
    val selectedDefaultDisabled: CheckboxStateColors,
    val selectedError: CheckboxStateColors,
    val selectedErrorPressed: CheckboxStateColors,
)

/**
 * Composition local for [CheckboxStyle].
 *
 * Provided by brand themes (e.g. [com.ui.audi.AudiTheme]).
 * Throws if accessed outside a theme scope.
 */
val LocalCheckboxStyle =
    compositionLocalOf<CheckboxStyle> {
        error("No CheckboxStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
