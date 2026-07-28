package com.ui.core.widgets.steppers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for the brand-specific stepper widget.
 *
 * Value-content elements are grouped inside [StepperContent].
 */
typealias StepperWidgetContent = @Composable (
    modifier: Modifier,
    content: StepperContent,
    state: StepperState,
    interactionConfig: StepperInteractionConfig,
) -> Unit

/**
 * Brand-agnostic stepper — the **single public API**.
 *
 * A stepper lets the user adjust a value step-by-step via minus (−)
 * and plus (+) buttons. The current value is displayed between them.
 *
 * **Mandatory elements:** Container, Stepper Arrow Buttons, Value Content area.
 *
 * **Optional Value Content** (grouped in [StepperContent], independently optional):
 *  - **label**        — composable slot for the displayed value text.
 *  - **unit**         — composable slot for the unit string.
 *  - **leadingIcon**  — composable slot; mutually exclusive with trailingIcon.
 *  - **trailingIcon** — composable slot; mutually exclusive with leadingIcon.
 *
 * When the minimum or maximum value is reached, the respective button
 * becomes disabled and is greyed out.
 *
 * ## Layout adaptations
 * - **RTL:** Stepping direction and arrow buttons are mirrored.
 *   Label and optional icon are mirrored. Handled automatically by
 *   Compose [Row] layout reversal.
 * - **RHD (Right-Hand Drive):** No adaptation.
 *
 * ## Example
 * ```kotlin
 * var count by remember { mutableIntStateOf(5) }
 *
 * Stepper(
 *     content = StepperContent(
 *         label = { Text("$count") },
 *         unit = { Text("°C") },
 *     ),
 *     state = StepperState(
 *         decrementEnabled = count > 0,
 *         incrementEnabled = count < 10,
 *     ),
 *     interactionConfig = StepperInteractionConfig(
 *         onDecrement = { count-- },
 *         onIncrement = { count++ },
 *     ),
 * )
 * ```
 */
@Composable
fun Stepper(
    modifier: Modifier = Modifier,
    content: StepperContent = StepperContent(),
    state: StepperState = StepperState(),
    interactionConfig: StepperInteractionConfig = StepperInteractionConfig(),
) {
    LocalWidgets.Stepper.current(
        modifier,
        content,
        state,
        interactionConfig,
    )
}
