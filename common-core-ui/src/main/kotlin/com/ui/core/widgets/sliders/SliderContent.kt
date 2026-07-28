package com.ui.core.widgets.sliders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.TextResource

/**
 * Composable and string slots shown around the slider surface — label, appendix,
 * hint, error text, range labels, value display, and optional icon button.
 * Defaults to an empty bundle (all `null`).
 *
 * Example:
 * ```kotlin
 * Slider(
 *     value = 0.5f,
 *     onValueChange = {},
 *     content = SliderContent(
 *         label = "Volume".TR,
 *         appendix = "50%".TR,
 *         hint = "Drag to adjust".TR,
 *         errorText = "Error info".TR,
 *         minLabel = "Min.",
 *         maxLabel = "Max.",
 *     ),
 * )
 * ```
 *
 * @property label optional [TextResource] for the label above the slider track.
 *  Rendered internally with the correct title text style and colour from the
 *  design tokens.
 * @property appendix optional [TextResource] next to the label (e.g. current
 *  value display). Rendered internally with the correct appendix text style.
 *  Truncated with ellipsis when the text exceeds the available slider width.
 * @property hint optional [TextResource] hint below the slider track. Rendered
 *  internally with the correct hint text style and colour from the design tokens.
 *  Wraps to a maximum of 2 lines when the text exceeds the available width.
 * @property errorText optional [TextResource] error caption below the slider
 *  track. Rendered alongside an error icon when the slider is in error state.
 * @property showIcons when `true` (default), range labels are rendered
 *  with a leading icon. Set to `false` for text-only range labels.
 * @property showLabels when `true` (default), range label text is rendered.
 *  Set to `false` to show only the range icons without text.
 * @property iconButton optional composable slot for icon buttons at the ends
 *  of the track. When provided, the slider renders the icon at both the start
 *  and end of the track, automatically mirroring the end icon (rotated 180°).
 * @property minLabel optional text for the minimum range label at the track start.
 *  Rendered internally with the correct range text style and colour from the
 *  design tokens.
 * @property maxLabel optional text for the maximum range label at the track end.
 *  Rendered internally with the correct range text style and colour from the
 *  design tokens.
 * @property minIcon optional composable icon slot for the minimum range label.
 *  When provided, this icon is used instead of the default placeholder.
 *  When `null` and [showIcons] is `true`, a default placeholder icon is shown.
 * @property maxIcon optional composable icon slot for the maximum range label.
 *  When provided, this icon is used instead of the default placeholder.
 *  When `null` and [showIcons] is `true`, a default placeholder icon is shown.
 * @property valueDisplay optional composable slot to replace the built-in value popup.
 *  When provided, the slider renders this instead of the default percentage display.
 *  Receives the current value and whether the handle is pressed.
 */
@Immutable
data class SliderContent(
    val label: TextResource? = null,
    val appendix: TextResource? = null,
    val hint: TextResource? = null,
    val errorText: TextResource? = null,
    val showIcons: Boolean = true,
    val showLabels: Boolean = true,
    val iconButton: (@Composable () -> Unit)? = null,
    val minLabel: String? = null,
    val maxLabel: String? = null,
    val minIcon: (@Composable () -> Unit)? = null,
    val maxIcon: (@Composable () -> Unit)? = null,
    val valueDisplay: (@Composable (value: Float, pressed: Boolean) -> Unit)? = null,
)
