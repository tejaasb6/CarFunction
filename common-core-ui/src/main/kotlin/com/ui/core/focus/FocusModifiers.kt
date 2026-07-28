package com.ui.core.focus

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * Makes any composable reachable via D-pad, rotary encoder, or keyboard navigation,
 * and draws a visible focus ring around it when it holds focus.
 *
 * Designed for reuse across **all** interactive widgets — buttons, cards, list items,
 * chips, etc.  Brand widgets pass in their own shape and use the `borderFocus` /
 * `borderWidths.thick` tokens so the ring is always on-brand.
 *
 * ## Modifier chain placement
 * Apply **before** [com.ui.core.interaction.interactiveClickable] in the modifier chain.
 * [interactiveClickable] (via `clickable` / `combinedClickable`) also makes the element
 * focusable; having an explicit `focusable()` here as well follows the Material3 pattern
 * and ensures the element is reachable even before any click handler is wired up.
 *
 * ```kotlin
 * Box(
 *   modifier = Modifier
 *     .clip(shape)
 *     .background(...)
 *     .focusableWithRing(          // ← here: attaches focusRequester + draws ring
 *         interactionSource = interactionSource,
 *         shape             = shape,
 *         ringColor         = Sem.Color.Stroke.Signal.Focus.color(),
 *         ringWidth         = LocalBorderWidths.current.thick,
 *         focusRequester    = interactionConfig.focusRequester,
 *     )
 *     .interactiveClickable(...)   // ← here: click + ripple
 * )
 * ```
 *
 * ## Rotary / D-pad usage
 * Focus is moved by the system when the user turns the rotary knob or presses a
 * D-pad direction key.  To programmatically move focus (e.g. auto-focus the primary
 * CTA when a screen opens):
 * ```kotlin
 * val focusRequester = remember { FocusRequester() }
 * LaunchedEffect(Unit) { focusRequester.requestFocus() }
 *
 * Button(
 *     …,
 *     interactionConfig = ButtonInteractionConfig(focusRequester = focusRequester),
 * )
 * ```
 *
 * @param interactionSource Shared [MutableInteractionSource] — must be the same instance
 *                          passed to [com.ui.core.interaction.interactiveClickable] so
 *                          focus and press states are unified.
 * @param shape             Shape of the focus ring; should match the widget's visual shape.
 * @param ringColor         Ring stroke colour — use `Sem.Color.Stroke.Signal.Focus.color()`.
 * @param ringWidth         Ring stroke width — use [com.ui.core.styles.LocalBorderWidths.current]`.thick`.
 * @param focusRequester    Optional external requester for programmatic focus control.
 */
fun Modifier.focusableWithRing(
    interactionSource: MutableInteractionSource,
    shape: Shape,
    ringColor: Color,
    ringWidth: Dp,
    focusRequester: FocusRequester? = null,
): Modifier =
    composed {
        val isFocused by interactionSource.collectIsFocusedAsState()

        this
            .then(
                if (focusRequester != null) {
                    Modifier.focusRequester(focusRequester)
                } else {
                    Modifier
                },
            )
            // Explicit focusable() ensures the element is reachable via D-pad / rotary
            // even in scenarios where the click handler hasn't fired yet.
            .focusable(interactionSource = interactionSource)
            // Draw the ring only when focused — zero cost when idle.
            .then(
                if (isFocused) {
                    Modifier.border(ringWidth, ringColor, shape)
                } else {
                    Modifier
                },
            )
    }
