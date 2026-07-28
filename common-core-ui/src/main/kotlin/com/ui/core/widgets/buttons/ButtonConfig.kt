package com.ui.core.widgets.buttons

import androidx.compose.runtime.Immutable

@Immutable
data class ButtonConfig(
    val tone: Tone = Tone.Destructive,
    val mode: Mode = Mode.Hug,
) {
    enum class Tone {
        Destructive,
        Encourage,
        Primary,
        Prominent,
        Secondary,
        Tertiary,
    }

    enum class Mode {
        Hug,
        Fill,
    }
}

fun ButtonConfig.Tone.hasDarkBackground(): Boolean =
    when (this) {
        ButtonConfig.Tone.Destructive,
        ButtonConfig.Tone.Encourage,
        ButtonConfig.Tone.Prominent,
        -> true

        ButtonConfig.Tone.Primary,
        ButtonConfig.Tone.Secondary,
        ButtonConfig.Tone.Tertiary,
        -> false
    }
