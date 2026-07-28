package com.ui.core.widgets.steppers

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Per-state colour and border set for the stepper **container**.
 */
@Immutable
data class StepperStateColors(
    val surfaceFill: Color,
    val border: Color,
    val borderWidth: Dp,
    val labelColor: Color,
    val iconColor: Color,
)

/**
 * Colour set for a stepper **sub-button** (decrement / increment).
 *
 * @param surfaceFill        Background fill of the button surface.
 * @param strokeColor        Border colour (idle).
 * @param strokeWidth        Border width (idle).
 * @param iconColor          Icon tint (idle).
 * @param stateLayerPressed  Overlay colour when pressed.
 * @param disabledStrokeColor  Border colour when button is disabled.
 * @param disabledStrokeWidth  Border width when button is disabled.
 * @param disabledIconColor    Icon tint when button is disabled.
 */
@Immutable
data class StepperButtonColors(
    val surfaceFill: Color,
    val strokeColor: Color,
    val strokeWidth: Dp,
    val iconColor: Color,
    val stateLayerPressed: Color,
    val disabledStrokeColor: Color,
    val disabledStrokeWidth: Dp,
    val disabledIconColor: Color,
)

/**
 * Full colour specification for [Stepper].
 *
 * @param idle     Container colours when enabled.
 * @param disabled Container colours when disabled.
 * @param button   Sub-button colours (idle + disabled).
 */
@Immutable
data class StepperTypeColors(
    val idle: StepperStateColors,
    val disabled: StepperStateColors,
    val button: StepperButtonColors,
)
