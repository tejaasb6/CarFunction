package com.ui.core.widgets.progresstrackers

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Full visual specification for [ProgressTracker].
 *
 * ```kotlin
 * CompositionLocalProvider(LocalProgressTrackerStyle provides customStyle) {
 *     ProgressTracker(...)
 * }
 * ```
 *
 * @param indicatorCornerRadius  Corner radius for indicator circles (fully rounded).
 * @param indicatorSize          Width and height of the indicator circle.
 *  Same token (`Cmp.Size.Feedback.ProgressTracker.Bar.Indicator.Surface.All`)
 *  is used for every step variant.
 * @param markerCornerRadius     Corner radius for the inner marker content shape
 *  (`Cmp.BorderRadius.Feedback.ProgressTracker.Bar.Indicator.Content.Marker`).
 * @param markerSize             Width and height of the inner filled-dot used by the
 *  InProgress indicator. Figma frame = 16 px (`Sem.Size.Fixed.400`); no dedicated
 *  Cmp-level token exists yet.
 * @param iconContentHeight      Height of icon content inside Finished, Skipped, and
 *  NotPossible indicators (`Cmp.Size.DataDisplay.Icon.SM.Height`).
 * @param iconContentMinWidth    Minimum width of icon content inside Finished, Skipped,
 *  and NotPossible indicators (`Cmp.Size.DataDisplay.Icon.SM.MinWidth`).
 * @param connectorHeight        Height (thickness) of the connecting line.
 * @param gap                    Horizontal gap between the tracker and outer edges.
 * @param barGap                 Horizontal gap between indicators and connectors.
 * @param indicatorContentGap    Gap between indicator and its step label.
 * @param titleTextStyle         Typography for the optional title text.
 * @param stepLabelTextStyle     Typography for step labels (default state).
 * @param stepLabelInProgressTextStyle Typography for the InProgress step label (bold).
 * @param defaultOpacity         Default opacity applied to indicators.
 * @param notPossibleOpacity     Reduced opacity for NotPossible step indicators only.
 * @param colors                 Full per-state colour specification.
 */
@Immutable
data class ProgressTrackerStyle(
    val indicatorCornerRadius: Dp,
    val indicatorSize: Dp,
    val markerCornerRadius: Dp,
    // TODO - markersize value should be replaced with border radius token. It will be replaced once customer clarify and provide the token
    val markerSize: Dp,
    val iconContentHeight: Dp,
    val iconContentMinWidth: Dp,
    val connectorHeight: Dp,
    val gap: Dp,
    val barGap: Dp,
    val indicatorContentGap: Dp,
    val titleTextStyle: TextStyle,
    val stepLabelTextStyle: TextStyle,
    val stepLabelInProgressTextStyle: TextStyle,
    val defaultOpacity: Float,
    val notPossibleOpacity: Float,
    val colors: ProgressTrackerTypeColors,
)

/**
 * Composition local for [ProgressTrackerStyle].
 *
 * Provided by brand themes (e.g. AudiTheme, LamborghiniTheme).
 * Throws if accessed outside a theme scope.
 */
val LocalProgressTrackerStyle =
    compositionLocalOf<ProgressTrackerStyle> {
        error("No ProgressTrackerStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
