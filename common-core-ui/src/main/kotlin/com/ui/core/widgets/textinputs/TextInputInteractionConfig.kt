package com.ui.core.widgets.textinputs

import androidx.compose.runtime.Immutable
import androidx.compose.ui.focus.FocusRequester
import com.ui.core.interaction.DistractionOptimizationConfig
import com.ui.core.interaction.FocusConfig

/**
 * Interaction callbacks for TextInput widget.
 *
 * Handles all user interactions:
 * - onClearClick: When clear (X) button is clicked
 * - onInfoClick: When info (i) button is clicked (optional - popover shows automatically via infoPopoverContent)
 * - onMicrophoneClick: When microphone button is clicked (voice input)
 * - onPasswordVisibilityToggle: When password visibility eye icon is toggled
 *
 * Note: The info button popover content is now provided via `TextInputContent.infoPopoverContent`.
 * The `onInfoClick` callback is optional and only needed for additional custom logic beyond showing the popover.
 *
 * Example:
 * ```kotlin
 * var password by remember { mutableStateOf("") }
 * var passwordVisible by remember { mutableStateOf(false) }
 * TextInput(
 *     value = password,
 *     onValueChange = { password = it },
 *     config = TextInputConfig(
 *         variant = TextInputConfig.Variant.Password,
 *         showClearButton = true,
 *         showInfoButton = true,
 *         showPasswordVisibilityButton = true,
 *     ),
 *     interactionConfig = TextInputInteractionConfig(
 *         onClearClick = { password = "" },
 *         onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
 *         // onInfoClick is optional - popover shows automatically
 *     ),
 *     content = TextInputContent(
 *         label = "Password".TR,
 *         infoPopoverContent = "Password Policy\n• Requirements...".TR,
 *     )
 * )
 * ```
 */
@Immutable
data class TextInputInteractionConfig(
    /** Callback when clear (X) button is clicked. */
    val onClearClick: (() -> Unit)? = null,
    /** Callback when info (i) button is clicked. */
    val onInfoClick: (() -> Unit)? = null,
    /** Callback when microphone button is clicked. */
    val onMicrophoneClick: (() -> Unit)? = null,
    /** Callback when password visibility toggle (eye icon) is clicked. */
    val onPasswordVisibilityToggle: (() -> Unit)? = null,
    override val isDistractionOptimized: Boolean = true,
    override val focusRequester: FocusRequester? = null,
) : DistractionOptimizationConfig,
    FocusConfig
