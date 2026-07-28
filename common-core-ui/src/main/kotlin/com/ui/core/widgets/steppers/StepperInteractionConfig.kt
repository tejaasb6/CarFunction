package com.ui.core.widgets.steppers

import com.ui.core.interaction.DistractionOptimizationConfig

/**
 * Interaction configuration for [Stepper].
 *
 * ```kotlin
 * StepperInteractionConfig(
 *     onIncrement = { count++ },
 *     onDecrement = { count-- },
 * )
 * ```
 *
 * @param onIncrement            Called when the user taps the plus button.
 * @param onDecrement            Called when the user taps the minus button.
 * @param isDistractionOptimized When `false`, disabled while the vehicle is moving.
 */
data class StepperInteractionConfig(
    val onIncrement: () -> Unit = {},
    val onDecrement: () -> Unit = {},
    override val isDistractionOptimized: Boolean = true,
) : DistractionOptimizationConfig
