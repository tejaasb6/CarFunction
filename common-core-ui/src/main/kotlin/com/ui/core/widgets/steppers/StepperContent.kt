package com.ui.core.widgets.steppers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

/**
 * Value-content slot holder for [Stepper].
 *
 * All slots are composable lambdas — the brand Stepper implementation
 * wraps them with the correct typography and colour via
 * [CompositionLocalProvider] so the caller's content is rendered with
 * the proper design-system tokens (e.g. Audi Text widget, AudiFont).
 *
 * [leadingIcon] and [trailingIcon] are mutually exclusive — if both
 * are set, only [leadingIcon] is rendered.
 *
 * The [label] slot is a single composable that can contain any
 * combination of value text and unit text (e.g. `{ Row { Text("22"); Text("°C") } }`),
 * or an icon, depending on the use case.
 *
 * ## Example
 * ```kotlin
 * Stepper(
 *     content = StepperContent(
 *         label = { Text("22 °C") },
 *         leadingIcon = { Icon(...) },
 *     ),
 *     state = StepperState(
 *         decrementEnabled = count > 0,
 *         incrementEnabled = count < 30,
 *     ),
 *     interactionConfig = StepperInteractionConfig(
 *         onDecrement = { count-- },
 *         onIncrement = { count++ },
 *     ),
 * )
 * ```
 *
 * @param label        Optional composable slot for the displayed value content
 *                     (text, unit text, or any composable).
 * @param leadingIcon  Optional icon slot (mutually exclusive with [trailingIcon]).
 * @param trailingIcon Optional icon slot (mutually exclusive with [leadingIcon]).
 */
@Immutable
data class StepperContent(
    val label: (@Composable () -> Unit)? = null,
    val leadingIcon: (@Composable () -> Unit)? = null,
    val trailingIcon: (@Composable () -> Unit)? = null,
)
