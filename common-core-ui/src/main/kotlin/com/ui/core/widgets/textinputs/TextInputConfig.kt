package com.ui.core.widgets.textinputs

import androidx.compose.runtime.Immutable

/**
 * Configuration for TextInput widget matching Figma component properties.
 *
 * Based on Figma variant axes:
 * - Variant: Default, Password
 * - State: Idle, Active, Pressed, Disabled, Loading, ReadOnly
 * - Error: True, False
 * - Filled: True, False
 *
 * Supports all optional elements from the Figma specification:
 * - Leading Icon / Leading Text (unit)
 * - Trailing Extension (trailing unit)
 * - Trailing Component Button (clear, info, microphone, password visibility)
 * - Label, Appendix, Hint
 *
 * Example:
 * ```kotlin
 * TextInput(
 *     value = text,
 *     onValueChange = { text = it },
 *     config = TextInputConfig(
 *         variant = TextInputConfig.Variant.Password,
 *         showClearButton = true,
 *         showInfoButton = true,
 *         showPasswordVisibilityButton = true,
 *     )
 * )
 * ```
 */
@Immutable
data class TextInputConfig(
    /** Input variant — Default or Password. */
    val variant: Variant = Variant.Default,
    /** Show a leading icon slot before the input. */
    val showLeadingIcon: Boolean = false,
    /** Show a leading unit text (e.g. "€") before the input. */
    val showLeadingUnit: Boolean = false,
    /** Show a trailing unit text (e.g. "km") after the input. */
    val showTrailingUnit: Boolean = false,
    /** Show a clear (X) action button — visible when input contains text. Enabled by default. */
    val showClearButton: Boolean = true,
    /** Show an info (i) action button — typically for password policy popover. */
    val showInfoButton: Boolean = false,
    /** Show a microphone action button for voice input. */
    val showMicrophoneButton: Boolean = false,
    /** Show a password visibility toggle button (eye icon) — Password variant. */
    val showPasswordVisibilityButton: Boolean = false,
    /** Show the label text above the input. */
    val showLabel: Boolean = true,
    /** Show the appendix text next to the label. */
    val showAppendix: Boolean = false,
    /** Show the hint/error text below the input. */
    val showHint: Boolean = true,
) {
    /**
     * Text Input variant.
     *
     * - Default: Standard text input with optional leading/trailing units and icons
     * - Password: Password input with masking, visibility toggle, and strength indicator
     */
    enum class Variant {
        Default,
        Password,
    }
}
