package com.ui.audi.widgets.pindisplay

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.pindisplay.PinDisplayStyle
import com.ui.core.widgets.pindisplay.PinIndicatorColors

/**
 * Provides the Audi brand [PinDisplayStyle] derived from the current composition tokens.
 * Called inside [com.ui.audi.AudiTheme] so the style reacts to token changes.
 */
internal object PinDisplayDefaults {
    @Composable
    fun style(): PinDisplayStyle =
        PinDisplayStyle(
            indicatorSize =
                Cmp.Size.Forms.PinDisplay.Indicator.Surface.All
                    .dimension()
                    .pxToDp(),
            indicatorCornerRadius =
                Cmp.BorderRadius.Forms.PinDisplay.Indicator
                    .dimension()
                    .pxToDp(),
            indicatorSpacing =
                Cmp.Space.Forms.PinDisplay.H_Padding
                    .dimension()
                    .pxToDp(),
            containerPadding =
                Cmp.Space.Forms.PinDisplay.H_Padding
                    .dimension()
                    .pxToDp(),
            defaultColors = defaultColors(),
            filledColors = filledColors(),
            errorColors = errorColors(),
        )

    @Composable
    private fun defaultColors() =
        PinIndicatorColors(
            fill =
                Cmp.Color.Forms.PinDisplay.Indicator.Surface.Fill.Default
                    .color(),
            stroke =
                Cmp.Color.Forms.PinDisplay.Indicator.Surface.Stroke.Default
                    .color(),
            strokeWidth =
                Cmp.BorderWidth.Forms.PinDisplay.Indicator.Default
                    .dimension()
                    .pxToDp(),
        )

    @Composable
    private fun filledColors() =
        PinIndicatorColors(
            fill =
                Cmp.Color.Forms.PinDisplay.Indicator.Surface.Fill.Filled
                    .color(),
            stroke =
                Cmp.Color.Forms.PinDisplay.Indicator.Surface.Stroke.Filled
                    .color(),
            strokeWidth =
                Cmp.BorderWidth.Forms.PinDisplay.Indicator.Filled
                    .dimension()
                    .pxToDp(),
        )

    @Composable
    private fun errorColors() =
        PinIndicatorColors(
            fill =
                Cmp.Color.Forms.PinDisplay.Indicator.Surface.Fill.Error
                    .color(),
            stroke =
                Cmp.Color.Forms.PinDisplay.Indicator.Surface.Stroke.Error
                    .color(),
            strokeWidth =
                Cmp.BorderWidth.Forms.PinDisplay.Indicator.Error
                    .dimension()
                    .pxToDp(),
        )
}
