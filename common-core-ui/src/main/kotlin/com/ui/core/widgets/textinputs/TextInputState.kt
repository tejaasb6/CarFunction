package com.ui.core.widgets.textinputs

import androidx.compose.runtime.Immutable

/**
 * Runtime state for TextInput widget matching Figma State axis.
 *
 * States from Figma:
 * - Idle: Default resting state
 * - Active: Field is being edited (real keyboard focus) — drives active border + state colors
 * - Focused: Focus ring visible (externally controlled) — drives focus ring only
 * - Pressed: Touch/click pressed
 * - Disabled: Cannot interact
 * - Loading: Async operation in progress
 * - ReadOnly: Can see but not edit
 *
 * Active vs Focused:
 * - isActive: Automatically driven by real keyboard focus when the user taps/types in the field.
 *   Controls active border tokens (Cmp.BorderWidth/Color.Forms.FormFields.Default.Active).
 * - isFocused: Externally set by the caller to show the focus ring overlay.
 *   Controls focus ring tokens (Sem.BorderWidth.FocusRing, Sem.Color.Stroke.Signal.Focus).
 *
 * Password variant additions:
 * - passwordVisible: Whether password text is revealed
 * - passwordStrength: Dynamic strength level (0.0 to 1.0) for real-time feedback
 *
 * Example:
 * ```kotlin
 * TextInput(
 *     value = text,
 *     onValueChange = { text = it },
 *     state = TextInputState(
 *         enabled = true,
 *         isLoading = false,
 *         isError = false,
 *         isFocused = true, // shows focus ring
 *     )
 * )
 * ```
 */
@Immutable
data class TextInputState(
    /** Whether the input is enabled for interaction. */
    val enabled: Boolean = true,
    /** Whether an async operation is in progress (shows spinner). */
    val isLoading: Boolean = false,
    /** Whether the input is read-only (visible but not editable). */
    val isReadOnly: Boolean = false,
    /** Whether the input is in active state (real keyboard focus, field being edited).
     *  Typically driven internally by the component from interactionSource — not set by callers. */
    val isActive: Boolean = false,
    /** Whether the focus ring is visible. */
    val isFocused: Boolean = false,
    /** Whether the input is in error state. */
    val isError: Boolean = false,
    /** Whether password text is currently visible (Password variant). */
    val passwordVisible: Boolean = false,
    /** Password strength level from 0.0 (weak) to 1.0 (strong) for dynamic indicator. */
    val passwordStrength: Float = 0f,
)
