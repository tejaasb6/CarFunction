package com.ui.core.widgets.sliders

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for the brand-provided Slider widget.
 *
 * Example:
 * ```kotlin
 * val widget: SliderWidgetContent = { value, onValueChange, config, modifier, state, content,
 *                                     valueEnd, onValueEndChange ->
 *     // brand implementation
 * }
 * ```
 */
typealias SliderWidgetContent = @Composable (
    value: Float,
    onValueChange: (Float) -> Unit,
    config: SliderConfig,
    modifier: Modifier,
    state: SliderState,
    content: SliderContent,
    valueEnd: Float,
    onValueEndChange: ((Float) -> Unit)?,
) -> Unit

/**
 * Brand-themed Slider.
 *
 * @param value current slider position in 0..1.
 * @param onValueChange invoked when the user drags the handle.
 * @param config alignment, mode, and error axis.
 * @param modifier caller-supplied modifier.
 * @param state runtime flags (enabled).
 * @param content strings and composable slots shown around the track.
 *  Use [SliderContent.minLabel] and [SliderContent.maxLabel] for range labels.
 * @param valueEnd upper bound for [SliderConfig.Mode.Multi]. Ignored for other
 *  modes. Must be in [0f, 1f] and ≥ `value`.
 * @param onValueEndChange callback for the upper handle in Multi mode.
 */
@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    config: SliderConfig = SliderConfig(),
    modifier: Modifier = Modifier,
    state: SliderState = SliderState(),
    content: SliderContent = SliderContent(),
    valueEnd: Float = 1f,
    onValueEndChange: ((Float) -> Unit)? = null,
) {
    LocalWidgets.Slider.current(
        value,
        onValueChange,
        config,
        modifier,
        state,
        content,
        valueEnd,
        onValueEndChange,
    )
}
