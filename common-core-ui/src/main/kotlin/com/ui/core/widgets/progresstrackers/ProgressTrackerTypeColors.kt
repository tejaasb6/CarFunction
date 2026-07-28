package com.ui.core.widgets.progresstrackers

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Per-step-state colour set for an indicator circle.
 *
 * Each step state has its own [ProgressTrackerIndicatorColors] instance
 * bundled inside [ProgressTrackerTypeColors].
 *
 * ## Example
 * ```kotlin
 * val finishedColors = ProgressTrackerIndicatorColors(
 *     surfaceFill = Color(0xFF4CAF50),
 *     strokeColor = Color(0xFF388E3C),
 *     markerColor = Color.White,
 * )
 * ```
 *
 * @param surfaceFill  Background fill of the indicator circle.
 * @param strokeColor  Border/stroke colour.
 * @param markerColor  Tint applied to the inner content (icon, number, or dot).
 */
@Immutable
data class ProgressTrackerIndicatorColors(
    val surfaceFill: Color,
    val strokeColor: Color,
    val markerColor: Color,
)

/**
 * Full colour specification for [ProgressTracker].
 *
 * Constructed by the brand defaults (e.g. `ProgressTrackerDefaults.style()`)
 * using Cmp design tokens, then stored in [ProgressTrackerStyle.colors].
 *
 * ## Example
 * ```kotlin
 * val colors = ProgressTrackerTypeColors(
 *     connectorFill = Color.Gray,
 *     titleColor = Color.Black,
 *     stepLabelColor = Color.DarkGray,
 *     indicatorStrokeWidth = 1.dp,
 *     unfinished = ProgressTrackerIndicatorColors(...),
 *     unfinishedNumeric = ProgressTrackerIndicatorColors(...),
 *     inProgress = ProgressTrackerIndicatorColors(...),
 *     finished = ProgressTrackerIndicatorColors(...),
 *     skipped = ProgressTrackerIndicatorColors(...),
 *     notPossible = ProgressTrackerIndicatorColors(...),
 * )
 * ```
 *
 * @param connectorFill     Colour of the line connecting step indicators.
 * @param titleColor        Colour of the optional title text.
 * @param stepLabelColor    Default colour for step labels.
 * @param indicatorStrokeWidth  Border width for indicator circles.
 * @param unfinished        Colours for [StepState.Unfinished].
 * @param unfinishedNumeric Colours for [StepState.UnfinishedNumeric].
 * @param inProgress        Colours for [StepState.InProgress].
 * @param finished          Colours for [StepState.Finished].
 * @param skipped           Colours for [StepState.Skipped].
 * @param notPossible       Colours for [StepState.NotPossible] (disabled).
 */
@Immutable
data class ProgressTrackerTypeColors(
    val connectorFill: Color,
    val titleColor: Color,
    val stepLabelColor: Color,
    val indicatorStrokeWidth: Dp,
    val unfinished: ProgressTrackerIndicatorColors,
    val unfinishedNumeric: ProgressTrackerIndicatorColors,
    val inProgress: ProgressTrackerIndicatorColors,
    val finished: ProgressTrackerIndicatorColors,
    val skipped: ProgressTrackerIndicatorColors,
    val notPossible: ProgressTrackerIndicatorColors,
)
