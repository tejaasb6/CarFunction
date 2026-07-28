package com.ui.core.widgets.textinputs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

/**
 * Composable slots for TextInput widget matching Figma optional components.
 *
 * Optional components from Figma:
 * - leadingIcon: Icon before input field
 * - trailingIcon: Custom icon/component after input field
 * - clearButton: Custom clear (X) button — default provided if null
 * - infoButton: Custom info (i) button — default provided if null
 * - microphoneButton: Custom microphone button — default provided if null
 * - passwordVisibilityButton: Custom eye toggle button — default provided if null
 * - passwordStrengthIndicator: Custom password strength UI (Password variant only)
 * - infoPopoverContent: **DEPRECATED** - Use `TextInputContent.infoPopoverContent` instead for standard text content
 *
 * When a slot is null and the corresponding config flag is true,
 * the brand implementation renders a default built-in composable.
 *
 * **Note on Info Popover:**
 * For standard password policy or help text, use `TextInputContent.infoPopoverContent` with TR format.
 * The `infoPopoverContent` slot is only needed for highly custom popover UI beyond simple text.
 *
 * Example - Custom leading icon:
 * ```kotlin
 * TextInput(
 *     value = text,
 *     onValueChange = { text = it },
 *     config = TextInputConfig(showLeadingIcon = true),
 *     slots = TextInputSlots(
 *         leadingIcon = {
 *             Icon(
 *                 source = IconSource.Vector(Icons.Filled.Search),
 *                 config = IconConfig(size = IconConfig.Size.SM),
 *             )
 *         }
 *     )
 * )
 * ```
 *
 * Example - Standard info popover (preferred):
 * ```kotlin
 * TextInput(
 *     config = TextInputConfig(showInfoButton = true),
 *     content = TextInputContent(
 *         infoPopoverContent = "Password Policy\n• At least 8 characters".TR,
 *     )
 * )
 * ```
 */
@Immutable
data class TextInputSlots(
    /** Custom leading icon composable. */
    val leadingIcon: (@Composable () -> Unit)? = null,
    /** Custom trailing icon composable (for custom use). */
    val trailingIcon: (@Composable () -> Unit)? = null,
    /** Custom clear button composable — uses default if null. */
    val clearButton: (@Composable () -> Unit)? = null,
    /** Custom info button composable — uses default if null. */
    val infoButton: (@Composable () -> Unit)? = null,
    /** Custom microphone button composable — uses default if null. */
    val microphoneButton: (@Composable () -> Unit)? = null,
    /** Custom password visibility toggle composable — uses default if null. */
    val passwordVisibilityButton: (@Composable () -> Unit)? = null,
    /** Custom password strength indicator composable (Password variant only). */
    val passwordStrengthIndicator: (@Composable () -> Unit)? = null,
    /**
     * Custom info popover content composable (Password variant only) — provides onDismiss callback.
     * @deprecated Use `TextInputContent.infoPopoverContent` with TR format for standard text content.
     * Only use this slot for highly custom popover UI that requires composable rendering.
     */
    val infoPopoverContent: (@Composable (onDismiss: () -> Unit) -> Unit)? = null,
)
