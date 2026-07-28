package com.ui.core.widgets.textinputs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a Text Input widget.
 *
 * Brand implementations must match this signature exactly.
 */
typealias TextInputWidgetContent = @Composable (
    value: String,
    onValueChange: (String) -> Unit,
    config: TextInputConfig,
    modifier: Modifier,
    state: TextInputState,
    content: TextInputContent,
    slots: TextInputSlots,
    interactionConfig: TextInputInteractionConfig,
) -> Unit

/**
 * Brand-agnostic Text Input widget following Figma specifications.
 *
 * Anatomy (4 sections from Figma):
 * 1. Label + Appendix row (optional)
 * 2. Input field with optional icons/units/buttons
 * 3. Password Strength indicator (Password variant only)
 * 4. Error/Hint text
 *
 * Variants from Figma:
 * - Variant: Default, Password
 * - State: Idle, Active, Pressed, Disabled, Loading, ReadOnly
 * - Error: True, False
 * - Filled: True, False
 *
 * Example - Basic input:
 * ```kotlin
 * var text by remember { mutableStateOf("") }
 * TextInput(
 *     value = text,
 *     onValueChange = { text = it },
 *     content = TextInputContent(
 *         label = "Email".TR,
 *         placeholder = "you@example.com".TR,
 *     )
 * )
 * ```
 *
 * Example - Password with info button and clear button:
 * ```kotlin
 * var password by remember { mutableStateOf("") }
 * var passwordVisible by remember { mutableStateOf(false) }
 * TextInput(
 *     value = password,
 *     onValueChange = { password = it },
 *     config = TextInputConfig(
 *         variant = TextInputConfig.Variant.Password,
 *         showPasswordVisibilityButton = true,
 *         showClearButton = true,
 *         showInfoButton = true,
 *     ),
 *     state = TextInputState(
 *         passwordVisible = passwordVisible,
 *         passwordStrength = calculateStrength(password),
 *     ),
 *     interactionConfig = TextInputInteractionConfig(
 *         onClearClick = { password = "" },
 *         onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
 *     ),
 *     content = TextInputContent(
 *         label = "Password".TR,
 *         placeholder = "Enter password".TR,
 *         infoPopoverContent = "Password Policy\n• At least 8 characters\n• Upper & lowercase".TR,
 *     )
 * )
 * ```
 */
@Composable
fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    config: TextInputConfig = TextInputConfig(),
    modifier: Modifier = Modifier,
    state: TextInputState = TextInputState(),
    content: TextInputContent = TextInputContent(),
    slots: TextInputSlots = TextInputSlots(),
    interactionConfig: TextInputInteractionConfig = TextInputInteractionConfig(),
) {
    LocalWidgets.TextInput.current(
        value,
        onValueChange,
        config,
        modifier,
        state,
        content,
        slots,
        interactionConfig,
    )
}
