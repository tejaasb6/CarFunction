package com.ui.audi.widgets.pindisplay

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ui.core.widgets.pindisplay.LocalPinDisplayStyle
import com.ui.core.widgets.pindisplay.PinDisplayState
import com.ui.core.widgets.pindisplay.PinIndicatorColors
import com.ui.core.widgets.pindisplay.PinLength
import com.ui.core.widgets.text.TextResource

/**
 * Audi brand implementation of [com.ui.core.widgets.pindisplay.PinDisplay].
 *
 * **Internal** — app code must not call this directly.
 * Use [com.ui.core.widgets.pindisplay.PinDisplay] instead.
 *
 * Renders a horizontal row of circular indicator dots. Each dot is either
 * empty (default), filled, or error-coloured based on the resolved [value]
 * length and [PinDisplayState.isError] flag. When [PinDisplayState.isError]
 * is `true`, all dots render with the error colour set regardless of fill state.
 */
@Composable
internal fun AudiPinDisplay(
    value: TextResource,
    pinLength: PinLength,
    modifier: Modifier = Modifier,
    state: PinDisplayState = PinDisplayState(),
) {
    val style = LocalPinDisplayStyle.current
    val resolvedValue = value.annotated.text
    val dotCount = pinLength.digits
    val filledCount = resolvedValue.length.coerceAtMost(dotCount)
    val dotShape = RoundedCornerShape(style.indicatorCornerRadius)

    Row(
        modifier = modifier.padding(horizontal = style.containerPadding),
        horizontalArrangement = Arrangement.spacedBy(style.indicatorSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(dotCount) { index ->
            val colors: PinIndicatorColors =
                when {
                    state.isError -> style.errorColors
                    index < filledCount -> style.filledColors
                    else -> style.defaultColors
                }

            Box(
                modifier =
                    Modifier
                        .size(style.indicatorSize)
                        .clip(dotShape)
                        .background(colors.fill, dotShape)
                        .then(
                            if (colors.strokeWidth > 0.dp) {
                                Modifier.border(colors.strokeWidth, colors.stroke, dotShape)
                            } else {
                                Modifier
                            },
                        ),
            )
        }
    }
}
