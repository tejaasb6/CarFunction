package com.ui.core.widgets.icons

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Full visual specification for [Icon].
 *
 * Dimensions are resolved per-size from `Cmp.Size.DataDisplay.Icon.{SM|MD|LG}`
 * tokens. The tint colour is driven by `Sem.Color.Content.Tertiary` (idle) with
 * disabled fade applied via `Sem.Opacity.Disabled`.
 *
 * Override at any level of the composition tree via [LocalIconStyle].
 *
 * Example:
 * ```kotlin
 * val style = LocalIconStyle.current
 * val adjustedStyle = style.copy(smSize = 24.dp)
 * CompositionLocalProvider(LocalIconStyle provides adjustedStyle) {
 *     Icon(config = IconConfig(size = IconConfig.Size.SM), icon = { /* … */ })
 * }
 * ```
 *
 * @property smSize side length for [IconConfig.Size.SM].
 * @property mdSize side length for [IconConfig.Size.MD].
 * @property lgSize side length for [IconConfig.Size.LG].
 * @property tintColor the colour applied to the icon when [IconConfig.hasTintingColors] is `true`.
 * @property disabledOpacity alpha multiplier when [IconState.enabled] is `false`.
 */
@Immutable
data class IconStyle(
    val smSize: Dp,
    val mdSize: Dp,
    val lgSize: Dp,
    val tintColor: Color,
    val disabledOpacity: Float,
)

/**
 * Returns the resolved side-length [Dp] for the given [size] class.
 *
 * Example:
 * ```kotlin
 * val style = LocalIconStyle.current
 * val dp = style.sizeFor(IconConfig.Size.MD) // e.g. 48.dp on the default theme
 * ```
 */
fun IconStyle.sizeFor(size: IconConfig.Size): Dp =
    when (size) {
        IconConfig.Size.SM -> smSize
        IconConfig.Size.MD -> mdSize
        IconConfig.Size.LG -> lgSize
    }

/**
 * Composition local providing the current [IconStyle].
 *
 * Populated by `AudiTheme` / `LamborghiniTheme`; accessing it outside a brand
 * theme throws an error.
 *
 * Example:
 * ```kotlin
 * val iconStyle = LocalIconStyle.current
 * ```
 */
val LocalIconStyle =
    compositionLocalOf<IconStyle> {
        error("No IconStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
