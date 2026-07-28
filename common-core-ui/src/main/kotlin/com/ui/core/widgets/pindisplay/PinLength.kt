package com.ui.core.widgets.pindisplay

/**
 * Supported PIN lengths for [PinDisplay].
 *
 * @param digits Number of indicator dots rendered.
 */
enum class PinLength(
    val digits: Int,
) {
    FOUR(4),
    SIX(6),
}
