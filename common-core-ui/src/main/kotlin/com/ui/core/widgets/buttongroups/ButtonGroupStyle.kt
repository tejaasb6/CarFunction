package com.ui.core.widgets.buttongroups

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

/** Visual specification for [ButtonGroup]. */
@Immutable
data class ButtonGroupStyle(
    val horizontalGap: Dp,
    val verticalGap: Dp,
)

/** Returns the gap for the given [alignment] direction. */
fun ButtonGroupStyle.gapForAlignment(alignment: ButtonGroupConfig.Alignment): Dp =
    when (alignment) {
        ButtonGroupConfig.Alignment.Horizontal -> horizontalGap
        ButtonGroupConfig.Alignment.Vertical -> verticalGap
    }

val LocalButtonGroupStyle =
    compositionLocalOf<ButtonGroupStyle> {
        error("No ButtonGroupStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
