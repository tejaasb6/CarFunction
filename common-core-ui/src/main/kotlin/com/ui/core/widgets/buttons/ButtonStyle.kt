package com.ui.core.widgets.buttons

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
data class ButtonStyle(
    val cornerRadius: Dp,
    val borderWidth: Dp,
    val minWidth: Dp,
    val minHeight: Dp,
    val paddingHorizontal: Dp,
    val paddingVertical: Dp,
    val iconSpacing: Dp,
    val textStyle: TextStyle,
    val destructive: ButtonTypeColors,
    val encourage: ButtonTypeColors,
    val primary: ButtonTypeColors,
    val prominent: ButtonTypeColors,
    val secondary: ButtonTypeColors,
    val tertiary: ButtonTypeColors,
)

fun ButtonStyle.colorsForTone(tone: ButtonConfig.Tone): ButtonTypeColors =
    when (tone) {
        ButtonConfig.Tone.Destructive -> destructive
        ButtonConfig.Tone.Encourage -> encourage
        ButtonConfig.Tone.Primary -> primary
        ButtonConfig.Tone.Prominent -> prominent
        ButtonConfig.Tone.Secondary -> secondary
        ButtonConfig.Tone.Tertiary -> tertiary
    }

val LocalButtonStyle =
    compositionLocalOf<ButtonStyle> {
        error("No ButtonStyle — wrap content in a brand theme")
    }
