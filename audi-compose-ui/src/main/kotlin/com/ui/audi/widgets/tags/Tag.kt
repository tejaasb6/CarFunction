package com.ui.audi.widgets.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ui.core.widgets.tags.LocalTagStyle
import com.ui.core.widgets.tags.TagConfig
import com.ui.core.widgets.tags.TagState
import com.ui.core.widgets.tags.colorsForTone
import com.ui.core.widgets.textlink.LocalTextLinkStyle

/**
 * Audi-themed brand impl of [com.ui.core.widgets.tags.Tag].
 *
 * Renders a pill-shaped container with optional icon and label slots.
 * Colours are applied via [CompositionLocalProvider] so the passed-in
 * design-system widgets (Icon, Text, TextLink) pick them up automatically.
 *
 * For link behaviour, pass a [com.ui.core.widgets.textlink.TextLink] in the
 * label slot — it handles underline, click, and pressed colour internally.
 * The Tag overrides [LocalTextLinkStyle] height to `0.dp` so the TextLink's
 * own min-height does not inflate the pill beyond the design-token size.
 */
@Composable
internal fun Tag(
    config: TagConfig,
    modifier: Modifier = Modifier,
    state: TagState = TagState(),
    icon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
) {
    val style = LocalTagStyle.current
    val toneColors = style.colorsForTone(config.tone)
    val shape = RoundedCornerShape(style.cornerRadius)

    val disabledAlpha = if (!state.enabled) Modifier.alpha(style.disabledOpacity) else Modifier

    val hasBorder = toneColors.surfaceStroke != Color.Transparent
    val borderModifier =
        if (hasBorder) {
            Modifier.border(style.borderWidth, toneColors.surfaceStroke, shape)
        } else {
            Modifier
        }

    val labelTextStyle = style.textStyle.copy(color = toneColors.textColor)

    Row(
        modifier =
            modifier
                .then(disabledAlpha)
                .defaultMinSize(minHeight = style.minHeight)
                .clip(shape)
                .background(toneColors.surfaceFill)
                .then(borderModifier)
                .padding(
                    horizontal = style.paddingHorizontal,
                    vertical = style.paddingVertical,
                ),
        horizontalArrangement = Arrangement.spacedBy(style.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            CompositionLocalProvider(LocalContentColor provides toneColors.iconColor) {
                icon()
            }
        }
        if (label != null) {
            // Neutralise the TextLink's own defaultMinSize(minHeight) so it
            // cannot inflate the Tag pill. All other TextLink styling (colours,
            // underline, pressed state) is preserved.
            val tagTextLinkStyle = LocalTextLinkStyle.current.copy(height = 0.dp)

            CompositionLocalProvider(
                LocalContentColor provides toneColors.textColor,
                LocalTextStyle provides labelTextStyle,
                LocalTextLinkStyle provides tagTextLinkStyle,
            ) {
                label()
            }
        }
    }
}
