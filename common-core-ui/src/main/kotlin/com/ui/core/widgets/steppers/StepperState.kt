package com.ui.core.widgets.steppers

import androidx.compose.runtime.Immutable

/**
 * Preview-state flags consumed by [Stepper].
 *
 * ```kotlin
 * StepperState(
 *     enabled = true,
 *     decrementEnabled = count > 0,
 *     incrementEnabled = count < 10,
 * )
 * ```
 *
 * @param enabled          When `false` the entire stepper is Disabled and dimmed.
 * @param decrementEnabled When `false` the minus button is greyed out (min reached).
 * @param incrementEnabled When `false` the plus button is greyed out (max reached).
 */
@Immutable
data class StepperState(
    val enabled: Boolean = true,
    val decrementEnabled: Boolean = true,
    val incrementEnabled: Boolean = true,
)
