package com.ui.audi.widgets.progressindicators

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.progressindicators.ProgressIndicatorStyle
import com.ui.core.widgets.progressindicators.ProgressIndicatorThemeColors
import com.ui.core.widgets.progressindicators.ProgressIndicatorTypeColors

/** Audi default [ProgressIndicatorStyle]. */
internal object ProgressIndicatorDefaults {
    @Composable
    fun style(): ProgressIndicatorStyle =
        ProgressIndicatorStyle(
            spinnerSizeMD =
                Cmp.Size.Feedback.ProgressIndicator.Spinner.MD.All
                    .dimension()
                    .pxToDp(),
            spinnerSizeSM =
                Cmp.Size.Feedback.ProgressIndicator.Spinner.SM.All
                    .dimension()
                    .pxToDp(),
            spinnerStrokeWidthMD =
                Cmp.BorderWidth.Feedback.ProgressIndicator.Spinner.MD
                    .dimension()
                    .pxToDp(),
            spinnerStrokeWidthSM =
                Cmp.BorderWidth.Feedback.ProgressIndicator.Spinner.SM
                    .dimension()
                    .pxToDp(),
            barLineHeight =
                Cmp.Size.Feedback.ProgressIndicator.ProgressBar.LineHeight
                    .dimension()
                    .pxToDp(),
            barGap =
                Cmp.Space.Feedback.ProgressIndicator.ProgressBar.Gap
                    .dimension()
                    .pxToDp(),
            labelTextStyle =
                Cmp.Typography.Feedback.ProgressIndicator.ProgressBar.Label
                    .typography(),
            valueTextStyle =
                Cmp.Typography.Feedback.ProgressIndicator.ProgressBar.Value
                    .typography(),
            rangeTextStyle =
                Cmp.Typography.Feedback.ProgressIndicator.ProgressBar.Range
                    .typography(),
            colors = typeColors(),
            spinnerRotationDurationMs = SPINNER_ROTATION_DURATION_MS,
            barIndeterminateDurationMs = BAR_INDETERMINATE_DURATION_MS,
            // StaticDark surface must always be dark. In light mode
            // Sem.Color.Fill.Primary resolves to Grey.15 (dark). In dark mode
            // Sem.Color.Fill.Canvas resolves to Grey.15 (dark). Swap so the
            // background stays dark regardless of the system theme.
            staticDarkSurfaceColor =
                if (isSystemInDarkTheme()) {
                    Sem.Color.Fill.Canvas
                        .color()
                } else {
                    Sem.Color.Fill.Primary
                        .color()
                },
        )

    private const val SPINNER_ROTATION_DURATION_MS = 1000
    private const val BAR_INDETERMINATE_DURATION_MS = 1500

    @Composable
    private fun typeColors(): ProgressIndicatorTypeColors =
        ProgressIndicatorTypeColors(
            nonInverted = nonInvertedColors(),
            inverted = invertedColors(),
        )

    @Composable
    private fun nonInvertedColors(): ProgressIndicatorThemeColors =
        ProgressIndicatorThemeColors(
            labelColor =
                Cmp.Color.Feedback.ProgressIndicator.NonInverted.Content.Label
                    .color(),
            valueColor =
                Cmp.Color.Feedback.ProgressIndicator.NonInverted.Content.Value
                    .color(),
            rangeColor =
                Cmp.Color.Feedback.ProgressIndicator.NonInverted.Content.Range
                    .color(),
            trackFill =
                Cmp.Color.Feedback.ProgressIndicator.NonInverted.TrackLine.Fill
                    .color(),
            trainFill =
                Cmp.Color.Feedback.ProgressIndicator.NonInverted.TrainLine.Fill
                    .color(),
        )

    @Composable
    private fun invertedColors(): ProgressIndicatorThemeColors =
        ProgressIndicatorThemeColors(
            labelColor =
                Cmp.Color.Feedback.ProgressIndicator.Inverted.Content.Label
                    .color(),
            valueColor =
                Cmp.Color.Feedback.ProgressIndicator.Inverted.Content.Value
                    .color(),
            rangeColor =
                Cmp.Color.Feedback.ProgressIndicator.Inverted.Content.Range
                    .color(),
            trackFill =
                Cmp.Color.Feedback.ProgressIndicator.Inverted.TrackLine.Fill
                    .color(),
            trainFill =
                Cmp.Color.Feedback.ProgressIndicator.Inverted.TrainLine.Fill
                    .color(),
        )
}
