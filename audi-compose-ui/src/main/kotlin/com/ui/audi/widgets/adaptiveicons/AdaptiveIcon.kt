package com.ui.audi.widgets.adaptiveicons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ui.core.widgets.adaptiveicons.AdaptiveIconConfig
import com.ui.core.widgets.adaptiveicons.LocalAdaptiveIconStyle
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig

/**
 * Audi brand impl of [com.ui.core.widgets.adaptiveicons.AdaptiveIcon].
 *
 * Internally uses the design-system [Icon] widget for token-driven sizing
 * and tinting. The caller only provides the raw icon glyph content.
 */
@Composable
internal fun AdaptiveIcon(
    config: AdaptiveIconConfig,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    val style = LocalAdaptiveIconStyle.current
    val shape =
        when (config.shape) {
            AdaptiveIconConfig.Shape.Circle -> CircleShape
            AdaptiveIconConfig.Shape.RoundedSquare -> RoundedCornerShape(style.cornerRadius)
        }

    Box(
        modifier =
            modifier
                .size(style.containerSize)
                .clip(shape)
                .background(style.backgroundColor)
                .border(style.borderWidth, style.borderColor, shape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            config = IconConfig(size = config.iconSize),
            icon = icon,
        )
    }
}
