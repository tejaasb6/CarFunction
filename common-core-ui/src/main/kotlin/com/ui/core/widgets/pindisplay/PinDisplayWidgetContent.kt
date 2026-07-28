package com.ui.core.widgets.pindisplay

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.text.TextResource

/**
 * Composable function type for a PIN display widget.
 *
 * Brand implementations render a row of indicator dots based on [pinLength],
 * filling dots left-to-right according to the resolved text length of [value].
 * When [PinDisplayState.isError] is `true`, all dots render in the error colour set.
 */
typealias PinDisplayWidgetContent = @Composable (
    value: TextResource,
    pinLength: PinLength,
    modifier: Modifier,
    state: PinDisplayState,
) -> Unit
