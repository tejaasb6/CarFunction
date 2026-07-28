package com.ui.core.widgets.iconbuttons

import androidx.compose.runtime.Immutable

/**
 * Configuration for [IconButton].
 *
 * ## Tones
 * | Tone | Purpose |
 * |------|---------|
 * | [Tone.Prominent] | Solid brand-colour fill — strongest CTA. |
 * | [Tone.Primary] | Main action the user should take. |
 * | [Tone.Secondary] | Outlined/bordered — equal-priority alternatives. |
 * | [Tone.Tertiary] | Ghost — low-emphasis, no visible container. |
 * | [Tone.Destructive] | Terminating / dangerous actions. |
 *
 * @property tone the visual colour/fill variant.
 * @property showLabel when `true`, a label is displayed below the icon.
 */
@Immutable
data class IconButtonConfig(
    val tone: Tone = Tone.Primary,
    val showLabel: Boolean = false,
) {
    /**
     * Visual colour/fill variant of an icon button.
     */
    enum class Tone {
        Prominent,
        Primary,
        Secondary,
        Tertiary,
        Destructive,
    }
}
