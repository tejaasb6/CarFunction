package com.ui.audi.widgets.progressindicators

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.ui.core.widgets.progressindicators.LocalProgressIndicatorStyle
import com.ui.core.widgets.progressindicators.ProgressIndicatorConfig
import com.ui.core.widgets.progressindicators.ProgressIndicatorContent
import com.ui.core.widgets.progressindicators.ProgressIndicatorStyle
import com.ui.core.widgets.progressindicators.ProgressIndicatorThemeColors
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.Text as DesignText

/** Audi brand implementation of the ProgressIndicator widget. */
@Suppress("LongMethod")
@Composable
internal fun ProgressIndicator(
    config: ProgressIndicatorConfig,
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    content: ProgressIndicatorContent = ProgressIndicatorContent(),
) {
    val style = LocalProgressIndicatorStyle.current
    val isDark = isSystemInDarkTheme()

    // StaticDark means "always rendered on a dark surface". In light mode
    // the inverted colour set provides light-on-dark colours. In dark mode
    // the nonInverted set already provides light-on-dark colours, so using
    // the inverted set would produce dark-on-dark — invisible. Swap
    // accordingly so the indicator is always visible against its dark
    // background regardless of the system theme.
    val themeColors =
        when (config.theme) {
            ProgressIndicatorConfig.Theme.Dynamic -> style.colors.nonInverted
            ProgressIndicatorConfig.Theme.StaticDark ->
                if (isDark) style.colors.nonInverted else style.colors.inverted
        }

    when (config.variant) {
        ProgressIndicatorConfig.Variant.Spinner ->
            DeterminateSpinner(config, style, themeColors, modifier, progress)

        ProgressIndicatorConfig.Variant.SpinnerInfinite ->
            IndeterminateSpinner(config, style, themeColors, modifier)

        ProgressIndicatorConfig.Variant.Bar ->
            DeterminateBar(style, themeColors, modifier, progress, content)

        ProgressIndicatorConfig.Variant.BarInfinite ->
            IndeterminateBar(style, themeColors, modifier, content)
    }
}

// ── Determinate Spinner ────────────────────────────────────────────────────────

@Composable
private fun DeterminateSpinner(
    config: ProgressIndicatorConfig,
    style: ProgressIndicatorStyle,
    colors: ProgressIndicatorThemeColors,
    modifier: Modifier,
    progress: Float,
) {
    val diameter =
        when (config.size) {
            ProgressIndicatorConfig.Size.MD -> style.spinnerSizeMD
            ProgressIndicatorConfig.Size.SM -> style.spinnerSizeSM
        }
    val strokeWidth =
        when (config.size) {
            ProgressIndicatorConfig.Size.MD -> style.spinnerStrokeWidthMD
            ProgressIndicatorConfig.Size.SM -> style.spinnerStrokeWidthSM
        }
    val clampedProgress = progress.coerceIn(0f, 1f)
    // Minimum sweep so a small train dot is visible even at 0 %
    val minSweep = 1f
    val sweepAngle = if (clampedProgress == 0f) minSweep else clampedProgress * 360f
    val trackColor = colors.trackFill
    val trainColor = colors.trainFill

    Canvas(modifier = modifier.size(diameter)) {
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        val arcSize = Size(size.width - stroke.width, size.height - stroke.width)
        val topLeft = Offset(stroke.width / 2f, stroke.width / 2f)

        // Track (full circle)
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke,
        )
        // Train arc — always drawn; minimum sweep ensures a dot at 0 %
        drawArc(
            color = trainColor,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke,
        )
    }
}

// ── Indeterminate Spinner ──────────────────────────────────────────────────────

/**
 * Indeterminate spinner with 8-keyframe arc animation matching the Figma spec.
 *
 * The arc **grows** from 25% to 95% of the circle (KF1→KF5) while rotating,
 * then **shrinks** from 95% back to 2% (KF5→KF8), creating a breathing
 * expand/collapse effect. Figma keyframe data (degrees, Compose convention):
 *
 * | KF | Start° | Sweep° | % circle |
 * |----|--------|--------|----------|
 * | 1  | 270    | 90     | 25%      |
 * | 2  | 0      | 90     | 25%      |
 * | 3  | 30     | 216    | 60%      |
 * | 4  | 180    | 270    | 75%      |
 * | 5  | 270    | 342    | 95%      |
 * | 6  | 30     | 216    | 60%      |
 * | 7  | 90     | 180    | 50%      |
 * | 8  | 265    | 7      | 2%       |
 */
@Composable
private fun IndeterminateSpinner(
    config: ProgressIndicatorConfig,
    style: ProgressIndicatorStyle,
    colors: ProgressIndicatorThemeColors,
    modifier: Modifier,
) {
    val diameter =
        when (config.size) {
            ProgressIndicatorConfig.Size.MD -> style.spinnerSizeMD
            ProgressIndicatorConfig.Size.SM -> style.spinnerSizeSM
        }
    val strokeWidth =
        when (config.size) {
            ProgressIndicatorConfig.Size.MD -> style.spinnerStrokeWidthMD
            ProgressIndicatorConfig.Size.SM -> style.spinnerStrokeWidthSM
        }
    val trackColor = colors.trackFill
    val trainColor = colors.trainFill

    val totalDuration = style.spinnerRotationDurationMs
    val frameDuration = totalDuration / 8

    val infiniteTransition = rememberInfiniteTransition(label = "spinner_infinite")

    // Exact Figma arc values (from file.json arcData, MD Dynamic).
    // Compose drawArc: startAngle = tail position, sweepAngle = clockwise extent.
    //
    // |KF| Start(tail)° | Sweep° | Head°  | Description               |
    // |--|-------------|--------|--------|---------------------------|
    // | 1|   270       |   90   |    0   | tail top, head right      |
    // | 2|     0       |   90   |   90   | tail right, head bottom   |
    // | 3|    30       |  216   |  246   | arc growing               |
    // | 4|   180       |  270   |   90   | arc large, wraps around   |
    // | 5|   270       |  342   |  252   | arc near-full             |
    // | 6|    30       |  216   |  246   | arc shrinking             |
    // | 7|    90       |  180   |  270   | half circle               |
    // | 8|   265       |    7   |  272   | tiny sliver               |

    // Unwrap startAngle to be monotonically increasing for clockwise
    // interpolation: 270 → 360 → 390 → 540 → 630 → 750 → 810 → 985
    val startAngle by infiniteTransition.animateFloat(
        initialValue = 270f,
        targetValue = 985f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = totalDuration
                        270f at 0 using EaseInOut // KF1: 270°
                        360f at frameDuration using EaseInOut // KF2: 0° (+360)
                        390f at frameDuration * 2 using EaseInOut // KF3: 30° (+360)
                        540f at frameDuration * 3 using EaseInOut // KF4: 180° (+360)
                        630f at frameDuration * 4 using EaseInOut // KF5: 270° (+360)
                        750f at frameDuration * 5 using EaseInOut // KF6: 30° (+720)
                        810f at frameDuration * 6 using EaseInOut // KF7: 90° (+720)
                        985f at frameDuration * 7 using EaseInOut // KF8: 265° (+720)
                    },
                repeatMode = RepeatMode.Restart,
            ),
        label = "spinner_start",
    )

    // Sweep: arc grows (KF1→KF5) then shrinks (KF5→KF8)
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 90f,
        targetValue = 7f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = totalDuration
                        90f at 0 using EaseInOut // KF1: 25%
                        90f at frameDuration using EaseInOut // KF2: 25%
                        216f at frameDuration * 2 using EaseInOut // KF3: 60%
                        270f at frameDuration * 3 using EaseInOut // KF4: 75%
                        342f at frameDuration * 4 using EaseInOut // KF5: 95%
                        216f at frameDuration * 5 using EaseInOut // KF6: 60%
                        180f at frameDuration * 6 using EaseInOut // KF7: 50%
                        7f at frameDuration * 7 using EaseInOut // KF8:  2%
                    },
                repeatMode = RepeatMode.Restart,
            ),
        label = "spinner_sweep",
    )

    Canvas(modifier = modifier.size(diameter)) {
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        val arcSize = Size(size.width - stroke.width, size.height - stroke.width)
        val topLeft = Offset(stroke.width / 2f, stroke.width / 2f)

        // Track — full circle background
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke,
        )
        // Train — animated arc that grows and shrinks
        drawArc(
            color = trainColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke,
        )
    }
}

// ── Determinate Bar ────────────────────────────────────────────────────────────

@Composable
private fun DeterminateBar(
    style: ProgressIndicatorStyle,
    colors: ProgressIndicatorThemeColors,
    modifier: Modifier,
    progress: Float,
    content: ProgressIndicatorContent,
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val label = content.label
    val progressValue = content.progressValue
    val rangeMin = content.rangeMin
    val rangeMax = content.rangeMax
    val hasLabel = label != EmptyTextResource
    val hasProgressValue = progressValue != EmptyTextResource
    val hasRangeMin = rangeMin != EmptyTextResource
    val hasRangeMax = rangeMax != EmptyTextResource

    Column(modifier = modifier.fillMaxWidth()) {
        // Label — rendered with theme-resolved color/typography.
        if (hasLabel) {
            DesignText(
                state = TextState(text = label, maxLines = 1),
                style = style.labelTextStyle.copy(color = colors.labelColor),
            )
        }

        // Progress value below label, positioned at the train-line end.
        // Uses valueTextStyle + valueColor tokens.
        if (hasProgressValue) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.weight(clampedProgress.coerceAtLeast(0.001f)))
                DesignText(
                    state = TextState(text = progressValue, maxLines = 1),
                    style = style.valueTextStyle.copy(color = colors.valueColor),
                )
                Spacer(Modifier.weight((1f - clampedProgress).coerceAtLeast(0.001f)))
            }
        }

        if (hasLabel || hasProgressValue) {
            Spacer(Modifier.height(style.barGap))
        }

        // Bar track + train — rectangle ends, minimum train visible at 0 %
        BarTrack(
            style = style,
            trackColor = colors.trackFill,
            trainColor = colors.trainFill,
            progress = clampedProgress,
            isRtl = isRtl,
        )

        // Range row
        if (hasRangeMin || hasRangeMax) {
            Spacer(Modifier.height(style.barGap))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                DesignText(
                    state = TextState(text = rangeMin, maxLines = 1),
                    style = style.rangeTextStyle.copy(color = colors.rangeColor),
                )
                Spacer(Modifier.weight(1f))
                DesignText(
                    state = TextState(text = rangeMax, maxLines = 1),
                    style = style.rangeTextStyle.copy(color = colors.rangeColor),
                )
            }
        }
    }
}

// ── Indeterminate Bar ──────────────────────────────────────────────────────────

/**
 * Indeterminate bar with 4-keyframe animation from Figma.
 *
 * The train segment grows and moves right, then collapses to the right edge,
 * reappears as a dot at the left edge, and restarts.
 *
 * Figma keyframes (track width = 520px):
 * | KF | Offset% | Width% | Description                    |
 * |----|---------|--------|--------------------------------|
 * |  1 |    8%   |  48%   | segment in left-centre area    |
 * |  2 |   21%   |  79%   | grows, leading edge hits right |
 * |  3 |   99%   |   1%   | collapses to dot at right edge |
 * |  4 |    0%   |   1%   | dot at left edge → restart     |
 */
@Composable
private fun IndeterminateBar(
    style: ProgressIndicatorStyle,
    colors: ProgressIndicatorThemeColors,
    modifier: Modifier,
    content: ProgressIndicatorContent,
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val totalDuration = style.barIndeterminateDurationMs

    // 4 Figma keyframes with equal timing (totalDuration / 4 each).
    // Between KF3→KF4 the train is hidden (alpha=0) so it appears
    // to vanish at the right edge and reappear at the left edge
    // without any visible backward travel.
    val frameDuration = totalDuration / 4

    val infiniteTransition = rememberInfiniteTransition(label = "bar_infinite")

    // Train left-edge offset as fraction of track width (0..1).
    val trainOffset by infiniteTransition.animateFloat(
        initialValue = 0.08f,
        targetValue = 0.08f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = totalDuration
                        0.08f at 0 using EaseInOut // KF1:  8%
                        0.21f at frameDuration using EaseInOut // KF2: 21%
                        0.99f at frameDuration * 2 using EaseInOut // KF3: 99%
                        0.00f at frameDuration * 3 using EaseInOut // KF4:  0%
                    },
                repeatMode = RepeatMode.Restart,
            ),
        label = "bar_offset",
    )

    // Train width as fraction of track width (0..1).
    val trainWidth by infiniteTransition.animateFloat(
        initialValue = 0.48f,
        targetValue = 0.48f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = totalDuration
                        0.48f at 0 using EaseInOut // KF1: 48%
                        0.79f at frameDuration using EaseInOut // KF2: 79%
                        0.01f at frameDuration * 2 using EaseInOut // KF3:  1%
                        0.01f at frameDuration * 3 using EaseInOut // KF4:  1%
                    },
                repeatMode = RepeatMode.Restart,
            ),
        label = "bar_width",
    )

    // Train opacity: visible during KF1→KF2→KF3, hidden during
    // KF3→KF4 transition, visible again at KF4.
    val trainAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = totalDuration
                        1f at 0 using LinearEasing // KF1: visible
                        1f at frameDuration using LinearEasing // KF2: visible
                        1f at frameDuration * 2 using LinearEasing // KF3: visible
                        0f at frameDuration * 2 + 1 using LinearEasing // hide instantly after KF3
                        0f at frameDuration * 3 - 1 using LinearEasing // stay hidden
                        1f at frameDuration * 3 using LinearEasing // KF4: visible again
                    },
                repeatMode = RepeatMode.Restart,
            ),
        label = "bar_alpha",
    )

    val label = content.label
    val hasLabel = label != EmptyTextResource

    Column(modifier = modifier.fillMaxWidth()) {
        if (hasLabel) {
            DesignText(
                state = TextState(text = label, maxLines = 1),
                style = style.labelTextStyle.copy(color = colors.labelColor),
            )
            Spacer(Modifier.height(style.barGap))
        }

        Canvas(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(style.barLineHeight),
        ) {
            // Track background
            drawRect(color = colors.trackFill)

            // Train segment — alpha hides it during KF3→KF4 repositioning
            if (trainAlpha > 0f) {
                val segW = (size.width * trainWidth).coerceAtLeast(size.height)
                val rawLeft = size.width * trainOffset
                val left = if (isRtl) size.width - rawLeft - segW else rawLeft
                val clampedLeft = left.coerceIn(0f, size.width - segW.coerceAtMost(size.width))
                val clampedW = segW.coerceAtMost(size.width - clampedLeft)
                if (clampedW > 0f) {
                    drawRect(
                        color = colors.trainFill.copy(alpha = trainAlpha),
                        topLeft = Offset(clampedLeft, 0f),
                        size = Size(clampedW, size.height),
                    )
                }
            }
        }
    }
}

// ── Bar Track Canvas ───────────────────────────────────────────────────────────

/**
 * Determinate bar — rectangle ends, minimum train visible at 0 %.
 *
 * At 0 % a small train segment (equal to the bar height) is drawn so the
 * indicator start point is always visible.
 */
@Composable
private fun BarTrack(
    style: ProgressIndicatorStyle,
    trackColor: Color,
    trainColor: Color,
    progress: Float,
    isRtl: Boolean,
) {
    Canvas(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(style.barLineHeight),
    ) {
        drawRect(color = trackColor)

        val minTrainWidth = size.height
        val trainWidth = (size.width * progress).coerceAtLeast(minTrainWidth)
        val trainLeft = if (isRtl) size.width - trainWidth else 0f
        drawRect(
            color = trainColor,
            topLeft = Offset(trainLeft, 0f),
            size = Size(trainWidth, size.height),
        )
    }
}
