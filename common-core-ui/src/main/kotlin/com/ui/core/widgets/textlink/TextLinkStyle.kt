package com.ui.core.widgets.textlink

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Full visual specification for [TextLink].
 *
 * Brand implementations build this from `Cmp.*` / `Sem.*` tokens inside their
 * `TextLinkDefaults.style()` composable.
 *
 * Example:
 * ```kotlin
 * val style = LocalTextLinkStyle.current
 * val adjustedStyle = style.copy(gap = 12.dp)
 * CompositionLocalProvider(LocalTextLinkStyle provides adjustedStyle) {
 *     TextLink(
 *         interactionConfig = TextLinkInteractionConfig(onClick = {}),
 *         label = { Text(text = "Custom-spaced link") },
 *     )
 * }
 * ```
 *
 * @property standalone per-variant colour set for the Standalone variant.
 * @property inline per-variant colour set for the Inline variant.
 * @property idleTextStyle typography for idle labels.
 * @property pressedTextStyle typography for pressed labels (resolved from
 *  `Cmp.Typography.Navigation.TextLink.MD.Content.Label.Pressed`).
 * @property disabledTextStyle typography for disabled labels.
 * @property height touch-target height for Standalone links.
 * @property gap horizontal spacing between icon and label slots.
 * @property underlineOffset vertical offset between label baseline and underline.
 * @property underlineThicknessIdle underline thickness in idle state.
 * @property underlineThicknessPressed underline thickness in pressed state.
 * @property underlineThicknessDisabled underline thickness in disabled state.
 * @property focusRingColor colour of the focus ring (Standalone only).
 * @property focusRingWidth stroke width of the focus ring.
 * @property disabledOpacity alpha multiplier when disabled.
 */
@Immutable
data class TextLinkStyleSpec(
    val standalone: TextLinkVariantColors,
    val inline: TextLinkVariantColors,
    val idleTextStyle: TextStyle,
    val pressedTextStyle: TextStyle,
    val disabledTextStyle: TextStyle,
    val height: Dp,
    val gap: Dp,
    val underlineOffset: Dp,
    val underlineThicknessIdle: Dp,
    val underlineThicknessPressed: Dp,
    val underlineThicknessDisabled: Dp,
    val focusRingColor: Color,
    val focusRingWidth: Dp,
    val disabledOpacity: Float,
)

/**
 * Returns the [TextLinkVariantColors] for the given [variant].
 *
 * Example:
 * ```kotlin
 * val colors = style.colorsForVariant(TextLinkConfig.Variant.Standalone)
 * ```
 */
fun TextLinkStyleSpec.colorsForVariant(variant: TextLinkConfig.Variant): TextLinkVariantColors =
    when (variant) {
        TextLinkConfig.Variant.Standalone -> standalone
        TextLinkConfig.Variant.Inline -> inline
    }

/**
 * Composition local providing the current [TextLinkStyleSpec].
 *
 * Populated by `AudiTheme` / `LamborghiniTheme`; accessing it outside a brand
 * theme throws an error.
 *
 * Example:
 * ```kotlin
 * val textLinkStyle = LocalTextLinkStyle.current
 * ```
 */
val LocalTextLinkStyle =
    compositionLocalOf<TextLinkStyleSpec> {
        error("No TextLinkStyleSpec — wrap content in AudiTheme / LamborghiniTheme")
    }
