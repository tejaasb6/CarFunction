package com.ui.core.widgets.pindisplay

import androidx.compose.runtime.Immutable

/**
 * Runtime state bundle for [PinDisplay].
 *
 * Example:
 * ```kotlin
 * PinDisplay(
 *     value = pin.TR,
 *     pinLength = PinLength.FOUR,
 *     state = PinDisplayState(isError = pinFailed),
 * )
 * ```
 *
 * @property isError When `true`, all dots render in the error colour regardless
 *  of fill state.
 */
@Immutable
data class PinDisplayState(
    val isError: Boolean = false,
)
