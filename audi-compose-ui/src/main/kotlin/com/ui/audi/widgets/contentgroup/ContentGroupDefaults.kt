package com.ui.audi.widgets.contentgroup

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.contentgroup.ContentGroupStyle

/**
 * Provides the Audi brand [ContentGroupStyle] derived from the current composition tokens.
 * Called inside [com.ui.audi.AudiTheme] so the style reacts to token changes.
 */
internal object ContentGroupDefaults {
    @Composable
    fun style(): ContentGroupStyle {
        val contentPadding =
            Cmp.Space.Group.ContentGroup.HasPadding.Padding
                .dimension()
                .pxToDp()
        return ContentGroupStyle(
            cornerRadius =
                Cmp.BorderRadius.Group.ContentGroup.Default
                    .dimension()
                    .pxToDp(),
            borderWidth =
                Cmp.BorderWidth.Group.ContentGroup.Default
                    .dimension()
                    .pxToDp(),
            fillColor =
                Cmp.Color.Group.ContentGroup.Surface.Fill
                    .color(),
            strokeColor =
                Cmp.Color.Group.ContentGroup.Surface.Stroke
                    .color(),
            backgroundBlur =
                Cmp.Blur.Group.ContentGroup.Surface.Default
                    .dimension()
                    .pxToDp(),
            paddingStart = contentPadding,
            paddingEnd = contentPadding,
            paddingTop = contentPadding,
            paddingBottom = contentPadding,
            noPadding =
                Cmp.Space.Group.ContentGroup.NoPadding.Padding
                    .dimension()
                    .pxToDp(),
        )
    }
}
