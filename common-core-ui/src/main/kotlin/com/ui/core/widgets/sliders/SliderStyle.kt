package com.ui.core.widgets.sliders

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.ui.core.engine.api.BoxShadowData

/**
 * Holds all dimension, typography, and colour tokens consumed by the [Slider].
 *
 * @property popupAutoHideDelayMs milliseconds after drag ends before the value
 *  popup fades out. Defaults to 2000 ms (2 seconds).
 * @property handleValueIdleGap gap between the handle top and the idle value
 *  label (8 dp for horizontal, 24 dp for vertical).
 * @property handleValuePressedGap gap between the handle top and the pressed
 *  (expanded) value popup.
 * @property valuePopupShadow resolved box-shadow token for the value popup.
 */
@Immutable
data class SliderStyle(
    val trackCornerRadius: Dp,
    val trackHeight: Dp,
    val progressCornerRadius: Dp,
    val progressHeight: Dp,
    val handleCornerRadius: Dp,
    val handleWidth: Dp,
    val handleHeight: Dp,
    val handleBorderWidth: Dp,
    val valueCornerRadius: Dp,
    val valueBorderWidthIdle: Dp,
    val valueBorderWidthPressed: Dp,
    val labelGroupBottomPadding: Dp,
    val labelGroupGap: Dp,
    val verticalLabelGroupBottomPadding: Dp,
    val verticalLabelGroupGap: Dp,
    val iconButtonGap: Dp,
    val iconMdHeight: Dp,
    val iconMdMinWidth: Dp,
    val splitTouchTargetHeight: Dp,
    val splitTrackGap: Dp,
    val trackGap: Dp,
    val rangeGap: Dp,
    val captionGroupGap: Dp,
    val captionGroupTopPadding: Dp,
    val hCaptionGroupGap: Dp,
    val hCaptionGroupTopPadding: Dp,
    val valuePaddingH: Dp,
    val valuePaddingV: Dp,
    val minHeight: Dp,
    val triangleHeight: Dp,
    val triangleWidth: Dp,
    val triangleFill: Color,
    val triangleStroke: Color,
    val triangleBorderWidth: Dp,
    val appendixColor: Color,
    val appendixTextStyle: TextStyle,
    val titleTextStyle: TextStyle,
    val valueTextStyleIdle: TextStyle,
    val valueTextStylePressed: TextStyle,
    val rangeTextStyle: TextStyle,
    val hintTextStyle: TextStyle,
    val errorCaptionTextStyle: TextStyle,
    val errorCaptionColor: Color,
    val colors: SliderTypeColors,
    val handleValueIdleGap: Dp,
    val handleValuePressedGap: Dp,
    val valuePopupShadow: BoxShadowData,
    val popupAutoHideDelayMs: Long,
    val hIdleValueGap: Dp,
    val chargingGlowBlurRadius: Dp,
    val chargingGlowAlpha: Float,
    val verticalRangeLabelGap: Dp,
    val verticalMaxLabelOffsetY: Dp,
    val verticalMinLabelOffsetY: Dp,
)

val LocalSliderStyle =
    compositionLocalOf<SliderStyle> {
        error("No SliderStyle - wrap content in a brand theme")
    }
