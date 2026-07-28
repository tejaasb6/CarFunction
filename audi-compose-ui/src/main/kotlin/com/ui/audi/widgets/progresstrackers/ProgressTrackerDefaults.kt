package com.ui.audi.widgets.progresstrackers

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.progresstrackers.ProgressTrackerIndicatorColors
import com.ui.core.widgets.progresstrackers.ProgressTrackerStyle
import com.ui.core.widgets.progresstrackers.ProgressTrackerTypeColors

/** Audi brand token-driven defaults for ProgressTracker. */
internal object ProgressTrackerDefaults {
    @Composable
    fun style(): ProgressTrackerStyle =
        ProgressTrackerStyle(
            indicatorCornerRadius =
                Cmp.BorderRadius.Feedback.ProgressTracker.Bar.Indicator.Default
                    .dimension()
                    .pxToDp(),
            indicatorSize =
                Cmp.Size.Feedback.ProgressTracker.Bar.Indicator.Surface.All
                    .dimension()
                    .pxToDp(),
            markerCornerRadius =
                Cmp.BorderRadius.Feedback.ProgressTracker.Bar.Indicator.Content.Marker
                    .dimension()
                    .pxToDp(),
            // Figma Marker frame = 16 px; no Cmp-level token — using Sem.Size.Fixed.400
            // TODO - marker size token need to be replace with customer provided boarder width token for inprogress varient
            markerSize =
                Sem.Size.Fixed._400
                    .dimension()
                    .pxToDp(),
            iconContentHeight =
                Cmp.Size.DataDisplay.Icon.SM.Height
                    .dimension()
                    .pxToDp(),
            iconContentMinWidth =
                Cmp.Size.DataDisplay.Icon.SM.MinWidth
                    .dimension()
                    .pxToDp(),
            connectorHeight =
                Cmp.Size.Feedback.ProgressTracker.Bar.Connector.Height
                    .dimension()
                    .pxToDp(),
            gap =
                Cmp.Space.Feedback.ProgressTracker.Gap
                    .dimension()
                    .pxToDp(),
            barGap =
                Cmp.Space.Feedback.ProgressTracker.Bar.Gap
                    .dimension()
                    .pxToDp(),
            indicatorContentGap =
                Cmp.Space.Feedback.ProgressTracker.Bar.Indicator.Gap
                    .dimension()
                    .pxToDp(),
            titleTextStyle =
                Cmp.Typography.Feedback.ProgressTracker.Content.Label
                    .typography(),
            stepLabelTextStyle =
                Cmp.Typography.Feedback.ProgressTracker.Bar.Indicator.Content.Label.Default
                    .typography(),
            stepLabelInProgressTextStyle =
                Cmp.Typography.Feedback.ProgressTracker.Bar.Indicator.Content.Label.InProgress
                    .typography(),
            defaultOpacity =
                Cmp.Opacity.Feedback.ProgressTracker.Bar.Indicator.Default
                    .opacity(),
            notPossibleOpacity =
                Cmp.Opacity.Feedback.ProgressTracker.Bar.Indicator.NotPossible
                    .opacity(),
            colors = colors(),
        )

    @Composable
    private fun colors(): ProgressTrackerTypeColors =
        ProgressTrackerTypeColors(
            connectorFill =
                Cmp.Color.Feedback.ProgressTracker.Bar.Connector.Fill
                    .color(),
            titleColor =
                Cmp.Color.Feedback.ProgressTracker.Content.Label
                    .color(),
            stepLabelColor =
                Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Content.Label.Default
                    .color(),
            indicatorStrokeWidth =
                Cmp.BorderWidth.Feedback.ProgressTracker.Bar.Indicator
                    .dimension()
                    .pxToDp(),
            unfinished =
                ProgressTrackerIndicatorColors(
                    surfaceFill =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Fill.Unfinished
                            .color(),
                    strokeColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Stroke.Unfinished
                            .color(),
                    markerColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Content.Marker.Unfinished
                            .color(),
                ),
            unfinishedNumeric =
                ProgressTrackerIndicatorColors(
                    surfaceFill =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Fill.Numeric
                            .color(),
                    strokeColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Stroke.Numeric
                            .color(),
                    markerColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Content.Marker.Numeric
                            .color(),
                ),
            inProgress =
                ProgressTrackerIndicatorColors(
                    surfaceFill =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Fill.InProgress
                            .color(),
                    strokeColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Stroke.InProgress
                            .color(),
                    markerColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Content.Marker.InProgress
                            .color(),
                ),
            finished =
                ProgressTrackerIndicatorColors(
                    surfaceFill =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Fill.Finished
                            .color(),
                    strokeColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Stroke.Finished
                            .color(),
                    markerColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Content.Marker.Finished
                            .color(),
                ),
            skipped =
                ProgressTrackerIndicatorColors(
                    surfaceFill =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Fill.Skipped
                            .color(),
                    strokeColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Stroke.Skipped
                            .color(),
                    markerColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Content.Marker.Skipped
                            .color(),
                ),
            notPossible =
                ProgressTrackerIndicatorColors(
                    surfaceFill =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Fill.Disabled
                            .color(),
                    strokeColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Surface.Stroke.Disabled
                            .color(),
                    markerColor =
                        Cmp.Color.Feedback.ProgressTracker.Bar.Indicator.Content.Marker.Disabled
                            .color(),
                ),
        )
}
