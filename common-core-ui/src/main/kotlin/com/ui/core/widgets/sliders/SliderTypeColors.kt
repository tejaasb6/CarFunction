package com.ui.core.widgets.sliders

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Per-state colour set for a single visual state of the [Slider].
 *
 * Example:
 * ```kotlin
 * val idle = SliderStateColors(
 *     handleFill = Color.Blue,
 *     handleStroke = Color.Transparent,
 *     progressFill = Color.DarkGray,
 *     trackFill = Color.LightGray,
 *     titleColor = Color.White,
 *     hintColor = Color.Gray,
 *     rangeColor = Color.Gray,
 *     rangeIconColor = Color.Gray,
 *     valueTextColor = Color.White,
 * )
 * ```
 *
 * @property handleFill surface fill of the draggable thumb.
 * @property handleStroke border colour of the thumb.
 * @property progressFill fill of the active/progress portion of the track.
 * @property trackFill fill of the inactive portion of the track.
 * @property titleColor colour for the title text above the slider.
 * @property hintColor colour for the hint text below the slider.
 * @property rangeColor colour for the range labels at track ends.
 * @property rangeIconColor colour for optional range icons.
 * @property valueTextColor colour for the value text shown on the handle overlay.
 */
@Immutable
data class SliderStateColors(
    val handleFill: Color,
    val handleStroke: Color,
    val progressFill: Color,
    val trackFill: Color,
    val titleColor: Color,
    val hintColor: Color,
    val rangeColor: Color,
    val rangeIconColor: Color,
    val valueTextColor: Color,
)

/**
 * Full per-state colour branch for one side of the Slider's boolean type axis
 * (Default vs Error).
 *
 * Example:
 * ```kotlin
 * val branch = SliderBranchColors(
 *     stateLayerPressed = Color.Black.copy(alpha = 0.15f),
 *     idle = idleColors,
 *     pressed = pressedColors,
 *     disabled = disabledColors,
 * )
 * ```
 *
 * @property stateLayerPressed overlay colour shown on the handle during press.
 * @property idle colours when the slider is idle.
 * @property pressed colours when the thumb is being dragged.
 * @property disabled colours when the slider is disabled.
 */
@Immutable
data class SliderBranchColors(
    val stateLayerPressed: Color,
    val idle: SliderStateColors,
    val pressed: SliderStateColors,
    val disabled: SliderStateColors,
)

/**
 * Top-level colour container bundling all branches.
 *
 * Example:
 * ```kotlin
 * val typeColors = SliderTypeColors(
 *     default = defaultBranch,
 *     error = errorBranch,
 *     temperatureColdColor = Color.Blue,
 *     temperatureHotColor = Color.Red,
 *     chargingProgressFill = Color(0xFF00CC00),
 *     valueSurfaceFill = Color.DarkGray,
 *     valueSurfaceStroke = Color.Gray,
 *     progressStateLayerPressed = Color.Black.copy(alpha = 0.1f),
 * )
 * ```
 *
 * @property default colours when `isError = false`.
 * @property error colours when `isError = true`.
 * @property temperatureColdColor cold end colour for the Temperature gradient.
 * @property temperatureHotColor hot end colour for the Temperature gradient.
 * @property chargingProgressFill fill colour for the progress track in Charging mode.
 * @property valueSurfaceFill fill of the value popup balloon shown on press.
 * @property valueSurfaceStroke stroke of the value popup balloon shown on press.
 * @property progressStateLayerPressed state-layer overlay on the progress track during press.
 */
@Immutable
data class SliderTypeColors(
    val default: SliderBranchColors,
    val error: SliderBranchColors,
    val temperatureColdColor: Color,
    val temperatureHotColor: Color,
    val chargingProgressFill: Color,
    val valueSurfaceFill: Color,
    val valueSurfaceStroke: Color,
    val progressStateLayerPressed: Color,
)
