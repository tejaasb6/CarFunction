package com.ui.core.widgets.steppers

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Full visual specification for [Stepper].
 *
 * ```kotlin
 * CompositionLocalProvider(LocalStepperStyle provides customStyle) {
 *     Stepper(...)
 * }
 * ```
 *
 * @param cornerRadius          Corner radius of the stepper container.
 * @param minWidth              Minimum width of the overall stepper.
 * @param minHeight             Minimum height of the overall stepper.
 * @param stateLayerHeight      Height of the stepper state layer.
 * @param buttonTouchMinWidth   Minimum width of each sub-button touch target.
 * @param buttonTouchHeight     Height of each sub-button touch target.
 * @param buttonCornerRadius    Corner radius of each sub-button surface.
 * @param buttonStateLayerMinWidth  Minimum width of the button state layer.
 * @param buttonStateLayerHeight    Height of the button state layer.
 * @param iconWidth             Width of the icon inside a sub-button.
 * @param iconHeight            Height of the icon inside a sub-button.
 * @param gap                   Horizontal gap inside the value area.
 * @param labelTextStyle        Typography for the value label content.
 * @param colors                Full per-state colour specification.
 */
@Immutable
data class StepperStyle(
    val cornerRadius: Dp,
    val minWidth: Dp,
    val minHeight: Dp,
    val stateLayerHeight: Dp,
    val buttonTouchMinWidth: Dp,
    val buttonTouchHeight: Dp,
    val buttonCornerRadius: Dp,
    val buttonStateLayerMinWidth: Dp,
    val buttonStateLayerHeight: Dp,
    val iconWidth: Dp,
    val iconHeight: Dp,
    val gap: Dp,
    val labelTextStyle: TextStyle,
    val colors: StepperTypeColors,
)

/**
 * Composition local for [StepperStyle].
 *
 * Provided by brand themes (e.g. AudiTheme, LamborghiniTheme).
 * Throws if accessed outside a theme scope.
 */
val LocalStepperStyle =
    compositionLocalOf<StepperStyle> {
        error("No StepperStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
