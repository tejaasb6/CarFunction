package com.ui.core.interaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Bundled click-interaction descriptor for any interactive widget.
 *
 * Brand widget implementations assemble a [ClickOptions] from the fields of
 * [com.ui.core.widgets.buttons.ButtonInteractionConfig] (which implements [InteractionConfig]) and pass it straight
 * to [Modifier.interactiveClickable].  Application code never instantiates this directly.
 *
 * @param onClick       Required primary tap handler.
 * @param onLongClick   Optional long-press handler (fires after ≥ 400 ms by default).
 * @param onDoubleClick Optional double-tap handler.
 * @param debounceMs    Minimum milliseconds between accepted clicks.  0 = disabled.
 *                      Useful in automotive where vibration or gloves can trigger
 *                      unintended multi-taps on the same element.
 */
data class ClickOptions(
    val onClick: () -> Unit,
    val onLongClick: (() -> Unit)? = null,
    val onDoubleClick: (() -> Unit)? = null,
    val debounceMs: Long = 0L,
)

/**
 * Attaches the full click-interaction stack to any composable:
 * primary tap · long press · double tap · leading-edge debounce · branded ripple.
 *
 * ## Behaviour
 * - Selects [combinedClickable] automatically when [ClickOptions.onLongClick] or
 *   [ClickOptions.onDoubleClick] is non-null; falls back to the lighter [clickable]
 *   otherwise.
 * - Debounce is per-instance (stored via [remember] inside [composed]).
 * - Indication (ripple) is supplied by the caller so brand widgets can pass their
 *   own [rememberBrandIndication] value without this modifier knowing about tokens.
 *
 * **Focusability is intentionally NOT added here.** Use [com.ui.core.focus.focusableWithRing]
 * alongside this modifier — the two are designed to be composed together, with
 * [focusableWithRing] preceding [interactiveClickable] in the chain.
 *
 * @param clickOptions      The full click descriptor.
 * @param interactionSource Shared source that drives both the ripple and the focus ring.
 * @param enabled           `false` suppresses all click events (ripple still shows when
 *                          `indication` is non-null and the element is touched).
 * @param indication        Ripple / indication to show on press.  Pass `null` to suppress.
 */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.interactiveClickable(
    clickOptions: ClickOptions,
    interactionSource: MutableInteractionSource,
    enabled: Boolean,
    indication: Indication?,
): Modifier =
    composed {
        // ── Debounce state ─────────────────────────────────────────────────────────
        val lastClickMs = remember { mutableStateOf(0L) }

        val effectiveOnClick: () -> Unit =
            if (clickOptions.debounceMs > 0L) {
                remember(clickOptions.onClick, clickOptions.debounceMs) {
                    {
                        val now = System.currentTimeMillis()
                        if (now - lastClickMs.value >= clickOptions.debounceMs) {
                            lastClickMs.value = now
                            clickOptions.onClick()
                        }
                    }
                }
            } else {
                clickOptions.onClick
            }

        // ── Select the appropriate clickable modifier ──────────────────────────────
        if (clickOptions.onLongClick != null || clickOptions.onDoubleClick != null) {
            Modifier.combinedClickable(
                interactionSource = interactionSource,
                indication = indication,
                enabled = enabled,
                onLongClick = clickOptions.onLongClick,
                onDoubleClick = clickOptions.onDoubleClick,
                onClick = effectiveOnClick,
            )
        } else {
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = indication,
                enabled = enabled,
                onClick = effectiveOnClick,
            )
        }
    }
