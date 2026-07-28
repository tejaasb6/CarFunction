package com.ui.audi.widgets.progresstrackers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.progresstrackers.LocalProgressTrackerStyle
import com.ui.core.widgets.progresstrackers.ProgressTrackerContent
import com.ui.core.widgets.progresstrackers.ProgressTrackerIndicatorColors
import com.ui.core.widgets.progresstrackers.ProgressTrackerStep
import com.ui.core.widgets.progresstrackers.ProgressTrackerStyle
import com.ui.core.widgets.progresstrackers.StepState
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.text.TextState
import java.text.NumberFormat
import java.util.Locale
import com.ui.core.widgets.icons.Icon as DesignIcon
import com.ui.core.widgets.text.Text as DesignText

/**
 * Audi brand implementation of the ProgressTracker widget.
 *
 * Non-interactive — users cannot tap on steps. Navigation is handled
 * externally by the wizard host.
 *
 * **RTL:** Compose [Row] auto-mirrors child order, so steps begin
 * from the right edge automatically.
 *
 * **RHD (Right-Hand Drive):** No adaptation.
 */
@Suppress("CyclomaticComplexMethod", "LongMethod")
@Composable
internal fun ProgressTracker(
    modifier: Modifier = Modifier,
    content: ProgressTrackerContent = ProgressTrackerContent(),
) {
    val style = LocalProgressTrackerStyle.current

    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        // ── Optional title ─────────────────────────────────────────────────
        if (content.title != EmptyTextResource) {
            DesignText(
                state = TextState(text = content.title, maxLines = 1),
                style = style.titleTextStyle.copy(color = style.colors.titleColor),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = style.gap),
            )
        }

        // ── Tracker bar ────────────────────────────────────────────────────
        if (content.steps.isNotEmpty()) {
            TrackerBar(
                steps = content.steps,
                showLabels = content.showStepLabels,
                style = style,
            )
        }
    }
}

/**
 * Lays out step indicators, connectors, and labels in a horizontal track.
 *
 * Each step is a fixed-width column (= indicatorSize) with the label
 * centered under the indicator. The label is allowed to overflow the
 * column width via [wrapContentWidth] with `unbounded = true`, matching
 * the Figma "Indicator + Label" frame (FIXED 36px, cross=CENTER).
 */
@Composable
private fun TrackerBar(
    steps: List<ProgressTrackerStep>,
    showLabels: Boolean,
    style: ProgressTrackerStyle,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        steps.forEachIndexed { index, step ->
            // Fixed-width column matching Figma "Indicator + Label" frame
            Column(
                modifier = Modifier.width(style.indicatorSize),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                StepIndicator(
                    step = step,
                    stepIndex = index,
                    style = style,
                )

                if (showLabels && step.label != EmptyTextResource) {
                    Spacer(Modifier.height(style.indicatorContentGap))
                    DesignText(
                        state = TextState(text = step.label, maxLines = 1),
                        style =
                            if (step.state == StepState.InProgress) {
                                style.stepLabelInProgressTextStyle.copy(color = style.colors.stepLabelColor)
                            } else {
                                style.stepLabelTextStyle.copy(color = style.colors.stepLabelColor)
                            },
                        overflow = TextOverflow.Ellipsis,
                        modifier =
                            Modifier
                                .wrapContentWidth(unbounded = true)
                                .then(
                                    if (step.state == StepState.NotPossible) {
                                        Modifier.alpha(style.notPossibleOpacity)
                                    } else {
                                        Modifier
                                    },
                                ),
                    )
                }
            }

            if (index < steps.size - 1) {
                ConnectorLine(
                    color = style.colors.connectorFill,
                    lineHeight = style.connectorHeight,
                    indicatorSize = style.indicatorSize,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = style.barGap),
                )
            }
        }
    }
}

/**
 * Renders the circular indicator for a single step.
 *
 * All variants use the same [ProgressTrackerStyle.indicatorSize] from the
 * single token `Cmp.Size.Feedback.ProgressTracker.Bar.Indicator.Surface.All`.
 *
 * The InProgress inner dot shape is driven by [ProgressTrackerStyle.markerCornerRadius]
 * (`Cmp.BorderRadius.Feedback.ProgressTracker.Bar.Indicator.Content.Marker`).
 *
 * Icon content in Finished and Skipped states is sized using
 * [ProgressTrackerStyle.iconContentHeight] / [ProgressTrackerStyle.iconContentMinWidth]
 * (`Cmp.Size.DataDisplay.Icon.SM.Height` / `Cmp.Size.DataDisplay.Icon.SM.MinWidth`).
 */
@Suppress("CyclomaticComplexMethod")
@Composable
private fun StepIndicator(
    step: ProgressTrackerStep,
    stepIndex: Int,
    style: ProgressTrackerStyle,
) {
    val colors = colorsForState(step.state, style)
    val size = style.indicatorSize
    // Outer shape: Cmp.BorderRadius.Feedback.ProgressTracker.Bar.Indicator.Default
    val shape = RoundedCornerShape(style.indicatorCornerRadius)

    Box(
        modifier =
            Modifier
                .size(size)
                .then(
                    if (step.state == StepState.NotPossible) {
                        Modifier.alpha(style.notPossibleOpacity)
                    } else {
                        Modifier
                    },
                ).clip(shape)
                .background(colors.surfaceFill)
                // Cmp.BorderWidth.Feedback.ProgressTracker.Bar.Indicator
                .border(
                    width = style.colors.indicatorStrokeWidth,
                    color = colors.strokeColor,
                    shape = shape,
                ),
        contentAlignment = Alignment.Center,
    ) {
        when (step.state) {
            StepState.Unfinished -> {
                // Hollow circle — no inner content
            }
            StepState.UnfinishedNumeric -> {
                // Format the step number using the current locale so that
                // digit shapes adapt to the active language (e.g. Arabic ١,
                // Devanagari १, Farsi ۱, etc.).
                val configuration = LocalConfiguration.current
                val localizedNumber =
                    remember(stepIndex, configuration) {
                        val locale = configuration.locales[0] ?: Locale.getDefault()
                        NumberFormat.getIntegerInstance(locale).format(stepIndex + 1)
                    }
                DesignText(
                    state = TextState(text = localizedNumber.TR),
                    style = style.stepLabelTextStyle.copy(color = colors.markerColor),
                )
            }
            StepState.InProgress -> {
                // Surface (parent Box) tokens:
                //   sizing:       Cmp.Size.Feedback.ProgressTracker.Bar.Indicator.Surface.All
                //   fill:         Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Fill.InProgress
                //   borderColor:  Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Stroke.InProgress
                //   borderRadius: Cmp.BorderRadius.Feedback.ProgressTracker.Bar.Indicator.Default
                //   borderWidth:  Cmp.BorderWidth.Feedback.ProgressTracker.Bar.Indicator
                //
                // Marker tokens:
                //   fill:         Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Content.Marker.InProgress
                //   borderRadius: Cmp.BorderRadius.Feedback.ProgressTracker.Bar.Indicator.Content.Marker
                //   size:         Figma frame 16 px (Sem.Size.Fixed.400) — style.markerSize

                // TODO - markersize value should be replaced with border radius token. It will be replaced once customer clarify and provide the token
                val markerShape = RoundedCornerShape(style.markerCornerRadius)
                Box(
                    modifier =
                        Modifier
                            .size(style.markerSize)
                            .clip(markerShape)
                            .background(colors.markerColor),
                )
            }
            StepState.Finished -> {
                // Size: Cmp.Size.DataDisplay.Icon.SM.Height / MinWidth
                DesignIcon(
                    source =
                        IconSource.Vector(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Finished",
                        ),
                    config = IconConfig(size = IconConfig.Size.SM, hasTintingColors = false),
                    modifier =
                        Modifier.sizeIn(
                            minWidth = style.iconContentMinWidth,
                            minHeight = style.iconContentHeight,
                        ),
                )
            }
            StepState.Skipped -> {
                // Size: Cmp.Size.DataDisplay.Icon.SM.Height / MinWidth
                DesignIcon(
                    source =
                        IconSource.Vector(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Skipped",
                        ),
                    config = IconConfig(size = IconConfig.Size.SM, hasTintingColors = false),
                    modifier =
                        Modifier.sizeIn(
                            minWidth = style.iconContentMinWidth,
                            minHeight = style.iconContentHeight,
                        ),
                )
            }
            StepState.NotPossible -> {
                // Dimmed empty circle — no inner content.
                // The NotPossible state is visually distinguished by the
                // reduced opacity applied on the outer indicator Box and
                // the dashed connector, so no icon is needed.
            }
        }
    }
}

/**
 * Draws a horizontal connector line between two step indicators.
 *
 * All connectors render as solid lines at full opacity. The [barGap] spacing
 * is applied via `padding(horizontal)` on the modifier by the caller.
 */
@Composable
private fun ConnectorLine(
    color: Color,
    lineHeight: Dp,
    indicatorSize: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier.height(indicatorSize),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(lineHeight),
        ) {
            val y = size.height / 2f
            drawLine(
                color = color,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = size.height,
            )
        }
    }
}

/** Maps a [StepState] to its corresponding per-state indicator colour set. */
private fun colorsForState(
    state: StepState,
    style: ProgressTrackerStyle,
): ProgressTrackerIndicatorColors =
    when (state) {
        StepState.Unfinished -> style.colors.unfinished
        StepState.UnfinishedNumeric -> style.colors.unfinishedNumeric
        StepState.InProgress -> style.colors.inProgress
        StepState.Finished -> style.colors.finished
        StepState.Skipped -> style.colors.skipped
        StepState.NotPossible -> style.colors.notPossible
    }
