package com.ui.core.widgets.sliders

import androidx.compose.runtime.Immutable

/**
 * Descriptor for [Slider]: the visual alignment, mode, and boolean type axes
 * that select the colour branch.
 *
 * Example:
 * ```kotlin
 * Slider(
 *     value = 0.5f,
 *     onValueChange = { /* … */ },
 *     config = SliderConfig(
 *         alignment = SliderConfig.Alignment.Horizontal,
 *         mode = SliderConfig.Mode.Single,
 *         isError = true,
 *     ),
 * )
 * ```
 *
 * @property alignment the orientation of the slider — [Alignment.Horizontal] or
 *  [Alignment.Vertical].
 * @property mode the behavioural mode of the slider — see [Mode].
 * @property isError when `true`, the widget renders the Error colour branch (red
 *  handle and progress fill).
 * @property steps when non-null and > 0, the slider snaps (magnetic latching)
 *  to evenly-spaced discrete positions. For example, `steps = 4` creates 5
 *  positions: 0%, 25%, 50%, 75%, 100%. `null` means fully continuous.
 */
@Immutable
data class SliderConfig(
    val alignment: Alignment = Alignment.Horizontal,
    val mode: Mode = Mode.Single,
    val isError: Boolean = false,
    val steps: Int? = null,
) {
    /**
     * Orientation of the slider.
     *
     * Example:
     * ```kotlin
     * SliderConfig(alignment = SliderConfig.Alignment.Vertical)
     * ```
     */
    enum class Alignment {
        /** Standard left-to-right slider. */
        Horizontal,

        /** Top-to-bottom slider for special use cases. */
        Vertical,
    }

    /**
     * Behavioural mode of the slider.
     *
     * Example:
     * ```kotlin
     * SliderConfig(mode = SliderConfig.Mode.Temperature)
     * ```
     */
    enum class Mode {
        /** Standard single-handle continuous slider. */
        Single,

        /**
         * Split slider — represents positive and negative values with a visual
         * gap at the midpoint (zero).
         */
        Split,

        /**
         * Multi / dual-handle slider — two handles define a value range. Caller
         * provides `value` for the lower bound and `valueEnd` for the upper bound.
         */
        Multi,

        /** Temperature slider — progress track uses a temperature-specific fill colour. */
        Temperature,

        /** Charging slider — progress track uses a charging-specific fill colour. */
        Charging,
    }
}
