package com.ui.audi.widgets.tags

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.tags.TagStyle
import com.ui.core.widgets.tags.TagToneColors

/** Audi default [TagStyle] — resolves every value from the token engine. */
internal object TagDefaults {
    @Composable
    fun style(): TagStyle =
        TagStyle(
            cornerRadius =
                Cmp.BorderRadius.DataDisplay.Tag.MD.Default
                    .dimension()
                    .pxToDp(),
            borderWidth =
                Cmp.BorderWidth.DataDisplay.Tag.Default
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.DataDisplay.Tag.MD.Height
                    .dimension()
                    .pxToDp(),
            paddingHorizontal =
                Cmp.Space.DataDisplay.Tag.MD.H_Padding
                    .dimension()
                    .pxToDp(),
            paddingVertical =
                Cmp.Space.DataDisplay.Tag.MD.V_Padding
                    .dimension()
                    .pxToDp(),
            gap =
                Cmp.Space.DataDisplay.Tag.MD.Gap
                    .dimension()
                    .pxToDp(),
            textStyle =
                Cmp.Typography.DataDisplay.Tag.MD.Label
                    .typography(),
            backgroundBlur =
                Cmp.Blur.DataDisplay.Tag.Default
                    .dimension()
                    .pxToDp(),
            disabledOpacity = Sem.Opacity.Disabled.opacity(),
            default = defaultToneColors(),
            onImage = onImageToneColors(),
            prominent = prominentToneColors(),
        )

    @Composable
    private fun defaultToneColors(): TagToneColors =
        TagToneColors(
            iconColor =
                Cmp.Color.DataDisplay.Tag.Default.Icon
                    .color(),
            textColor =
                Cmp.Color.DataDisplay.Tag.Default.Text
                    .color(),
            surfaceFill =
                Cmp.Color.DataDisplay.Tag.Default.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.DataDisplay.Tag.Default.Surface.Stroke
                    .color(),
        )

    @Composable
    private fun onImageToneColors(): TagToneColors =
        TagToneColors(
            iconColor =
                Cmp.Color.DataDisplay.Tag.OnImage.Icon
                    .color(),
            textColor =
                Cmp.Color.DataDisplay.Tag.OnImage.Text
                    .color(),
            surfaceFill =
                Cmp.Color.DataDisplay.Tag.OnImage.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.DataDisplay.Tag.OnImage.Surface.Stroke
                    .color(),
        )

    @Composable
    private fun prominentToneColors(): TagToneColors =
        TagToneColors(
            iconColor =
                Cmp.Color.DataDisplay.Tag.Prominent.Icon
                    .color(),
            textColor =
                Cmp.Color.DataDisplay.Tag.Prominent.Text
                    .color(),
            surfaceFill =
                Cmp.Color.DataDisplay.Tag.Prominent.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.DataDisplay.Tag.Prominent.Surface.Stroke
                    .color(),
        )
}
