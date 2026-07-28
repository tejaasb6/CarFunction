package com.ui.audi.widgets.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.icons.IconState
import com.ui.core.widgets.icons.LocalIconStyle
import com.ui.core.widgets.icons.sizeFor
import androidx.compose.material3.Icon as M3Icon

/**
 * Audi-themed brand impl of [com.ui.core.widgets.icons.Icon].
 *
 * Supports both the new [IconSource] API and the legacy composable slot.
 * When [source] is provided, renders the appropriate icon type.
 * When [icon] slot is provided (legacy), renders it directly.
 */
@Composable
internal fun Icon(
    config: IconConfig,
    modifier: Modifier,
    state: IconState,
    source: IconSource?,
    icon: (@Composable () -> Unit)?,
) {
    val style = LocalIconStyle.current
    val sizeDp = style.sizeFor(config.size)

    val disabledAlphaModifier =
        if (!state.enabled) Modifier.alpha(style.disabledOpacity) else Modifier

    Box(
        modifier =
            modifier
                .size(sizeDp)
                .then(disabledAlphaModifier),
        contentAlignment = Alignment.Center,
    ) {
        if (config.hasTintingColors) {
            val parentColor = LocalContentColor.current
            val isParentProvided =
                parentColor != Color.Black && parentColor != Color.Unspecified
            val tint = if (isParentProvided) parentColor else style.tintColor
            CompositionLocalProvider(LocalContentColor provides tint) {
                RenderIcon(source, icon, tint, sizeDp)
            }
        } else {
            RenderIcon(source, icon, tint = null, sizeDp)
        }
    }
}

@Composable
private fun RenderIcon(
    source: IconSource?,
    legacyIcon: (@Composable () -> Unit)?,
    tint: Color?,
    sizeDp: androidx.compose.ui.unit.Dp,
) {
    val sizeModifier = Modifier.size(sizeDp)
    when {
        source != null -> {
            when (source) {
                is IconSource.Vector -> {
                    if (tint != null) {
                        M3Icon(
                            imageVector = source.imageVector,
                            contentDescription = source.contentDescription,
                            tint = tint,
                            modifier = sizeModifier,
                        )
                    } else {
                        M3Icon(
                            imageVector = source.imageVector,
                            contentDescription = source.contentDescription,
                            modifier = sizeModifier,
                        )
                    }
                }

                is IconSource.Resource -> {
                    if (tint != null) {
                        M3Icon(
                            painter = painterResource(source.resId),
                            contentDescription = source.contentDescription,
                            tint = tint,
                            modifier = sizeModifier,
                        )
                    } else {
                        M3Icon(
                            painter = painterResource(source.resId),
                            contentDescription = source.contentDescription,
                            modifier = sizeModifier,
                        )
                    }
                }

                is IconSource.FromPainter -> {
                    if (tint != null) {
                        M3Icon(
                            painter = source.painter,
                            contentDescription = source.contentDescription,
                            tint = tint,
                            modifier = sizeModifier,
                        )
                    } else {
                        M3Icon(
                            painter = source.painter,
                            contentDescription = source.contentDescription,
                            modifier = sizeModifier,
                        )
                    }
                }

                is IconSource.Bitmap -> {
                    Image(
                        bitmap = source.bitmap,
                        contentDescription = source.contentDescription,
                        modifier = sizeModifier,
                    )
                }
            }
        }

        legacyIcon != null -> legacyIcon()
    }
}
