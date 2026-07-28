package com.ui.core.widgets.tags

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Per-tone colour set for a [Tag].
 *
 * @property iconColor tint applied to the leading icon.
 * @property textColor colour of the label text.
 * @property surfaceFill background fill colour of the tag container.
 * @property surfaceStroke border stroke colour of the tag container.
 */
@Immutable
data class TagToneColors(
    val iconColor: Color,
    val textColor: Color,
    val surfaceFill: Color,
    val surfaceStroke: Color,
)

/**
 * Full visual specification for [Tag].
 *
 * Brand implementations build this from `Cmp.Color.DataDisplay.Tag.*`,
 * `Cmp.Size.DataDisplay.Tag.*`, and `Cmp.Space.DataDisplay.Tag.*` tokens.
 *
 * Override at any level of the composition tree via [LocalTagStyle].
 *
 * Example:
 * ```kotlin
 * val style = LocalTagStyle.current
 * ```
 *
 * @property cornerRadius corner radius of the pill shape.
 * @property borderWidth stroke width of the tag border.
 * @property minHeight minimum height of the tag container.
 * @property paddingHorizontal horizontal content padding.
 * @property paddingVertical vertical content padding.
 * @property gap spacing between icon and label.
 * @property textStyle typography for the label.
 * @property backgroundBlur blur radius applied to the tag background.
 * @property disabledOpacity alpha multiplier when disabled.
 * @property default colours for [TagConfig.Tone.Default].
 * @property onImage colours for [TagConfig.Tone.OnImage].
 * @property prominent colours for [TagConfig.Tone.Prominent].
 */
@Immutable
data class TagStyle(
    val cornerRadius: Dp,
    val borderWidth: Dp,
    val minHeight: Dp,
    val paddingHorizontal: Dp,
    val paddingVertical: Dp,
    val gap: Dp,
    val textStyle: TextStyle,
    val backgroundBlur: Dp,
    val disabledOpacity: Float,
    val default: TagToneColors,
    val onImage: TagToneColors,
    val prominent: TagToneColors,
)

/**
 * Returns the [TagToneColors] for the given [tone].
 */
fun TagStyle.colorsForTone(tone: TagConfig.Tone): TagToneColors =
    when (tone) {
        TagConfig.Tone.Default -> default
        TagConfig.Tone.OnImage -> onImage
        TagConfig.Tone.Prominent -> prominent
    }

/**
 * Composition local providing the current [TagStyle].
 *
 * Populated by `AudiTheme` / `LamborghiniTheme`; accessing it outside a brand
 * theme throws an error.
 */
val LocalTagStyle =
    compositionLocalOf<TagStyle> {
        error("No TagStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
