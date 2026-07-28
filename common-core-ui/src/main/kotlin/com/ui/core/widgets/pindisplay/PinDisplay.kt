package com.ui.core.widgets.pindisplay

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets
import com.ui.core.widgets.text.TextResource

/**
 * Brand-agnostic PIN display — a **display-only** widget that shows a row of
 * indicator dots representing a PIN code entry.
 *
 * The actual digit is never shown; instead each entered digit is represented by
 * a filled dot. Unfilled dots indicate remaining positions. Use together with a
 * Numeric Keypad for PIN entry.
 *
 * ## Basic usage (4-digit PIN)
 * ```kotlin
 * var pin by remember { mutableStateOf("") }
 *
 * PinDisplay(
 *     value = pin.TR,
 *     pinLength = PinLength.FOUR,
 * )
 * ```
 *
 * ## 6-digit PIN with error
 * ```kotlin
 * PinDisplay(
 *     value = pin.TR,
 *     pinLength = PinLength.SIX,
 *     state = PinDisplayState(isError = true),
 * )
 * ```
 *
 * @param value     Current PIN value as a [TextResource]. Each character fills
 *                  one dot left-to-right. Characters beyond [pinLength] are ignored.
 * @param pinLength Number of indicator dots, chosen from [PinLength].
 * @param modifier  Applied to the outermost layout node.
 * @param state     Runtime state bundle; see [PinDisplayState].
 */
@Composable
fun PinDisplay(
    value: TextResource,
    pinLength: PinLength,
    modifier: Modifier = Modifier,
    state: PinDisplayState = PinDisplayState(),
) {
    LocalWidgets.PinDisplay.current(
        value,
        pinLength,
        modifier,
        state,
    )
}
