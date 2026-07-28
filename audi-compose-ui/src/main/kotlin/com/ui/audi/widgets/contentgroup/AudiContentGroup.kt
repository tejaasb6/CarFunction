package com.ui.audi.widgets.contentgroup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ui.core.widgets.contentgroup.ContentGroupStyle
import com.ui.core.widgets.contentgroup.LocalContentGroupStyle

/**
 * Audi brand implementation of [com.ui.core.widgets.contentgroup.ContentGroup].
 *
 * **Internal** — app code must not call this directly.
 * Use [com.ui.core.widgets.contentgroup.ContentGroup] instead.
 *
 * Renders a non-interactive card container with background fill, border, corner radius,
 * blur, and optional inner padding from [ContentGroupStyle].
 */
@Composable
internal fun AudiContentGroup(
    modifier: Modifier = Modifier,
    hasPadding: Boolean = true,
    content: @Composable () -> Unit,
) {
    val style = LocalContentGroupStyle.current
    val shape = RoundedCornerShape(style.cornerRadius)

    val innerPadding =
        if (hasPadding) {
            PaddingValues(
                start = style.paddingStart,
                end = style.paddingEnd,
                top = style.paddingTop,
                bottom = style.paddingBottom,
            )
        } else {
            PaddingValues(style.noPadding)
        }

    Box(
        modifier =
            modifier
                .clip(shape)
                .background(style.fillColor, shape)
                .then(
                    if (style.borderWidth > 0.dp) {
                        Modifier.border(style.borderWidth, style.strokeColor, shape)
                    } else {
                        Modifier
                    },
                ).then(
                    if (style.backgroundBlur > 0.dp) {
                        Modifier.blur(style.backgroundBlur)
                    } else {
                        Modifier
                    },
                ).padding(innerPadding),
    ) {
        content()
    }
}
