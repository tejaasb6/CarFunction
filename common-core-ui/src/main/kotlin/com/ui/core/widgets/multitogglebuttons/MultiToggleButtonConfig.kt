package com.ui.core.widgets.multitogglebuttons

import androidx.compose.runtime.Immutable

/**
 * Configuration for a [MultiToggleButton].
 *
 * @param tone    Button surface tone — Secondary or Tertiary.
 * @param mode    Content layout — Hug, Fill, Icon (with label), or IconOnly (no label).
 * @param variant Indicator colour scheme — Default, Heating (amber), or Cooling (blue).
 */
@Immutable
data class MultiToggleButtonConfig(
    val tone: Tone = Tone.Secondary,
    val mode: Mode = Mode.Hug,
    val variant: Variant = Variant.Default,
) {
    /** Button surface tone. */
    enum class Tone { Secondary, Tertiary }

    /** Content layout mode. */
    enum class Mode {
        /** Text label, auto-width. */
        Hug,

        /** Text label, fills available width. */
        Fill,

        /** Icon with an optional label rendered below the button shape. */
        Icon,

        /** Icon without a label — only the circular icon button is rendered. */
        IconOnly,
    }

    /** Indicator colour variant. */
    enum class Variant { Default, Heating, Cooling }
}
