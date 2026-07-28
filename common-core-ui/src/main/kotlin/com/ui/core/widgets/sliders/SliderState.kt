package com.ui.core.widgets.sliders

import androidx.compose.runtime.Immutable

/**
 * Runtime flag bundle for [Slider].
 *
 * Example:
 * ```kotlin
 * Slider(
 *     value = 0.3f,
 *     onValueChange = {},
 *     config = SliderConfig(mode = SliderConfig.Mode.Multi),
 *     state = SliderState(enabled = true),
 *     valueEnd = 0.7f,
 *     onValueEndChange = { /* … */ },
 * )
 * ```
 *
 * @property enabled when `false` the slider is non-interactive and renders with
 *  the disabled opacity overlay.
 */
@Immutable
data class SliderState(
    val enabled: Boolean = true,
)
