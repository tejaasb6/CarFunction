package com.ui.core.widgets.contentgroup

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Full visual specification for [ContentGroup].
 *
 * Holds dimensions, colours, and blur radius for the card container.
 * Override at any level of the composition tree via [LocalContentGroupStyle].
 *
 * ```kotlin
 * CompositionLocalProvider(LocalContentGroupStyle provides customStyle) {
 *     ContentGroup { /* content */ }
 * }
 * ```
 *
 * @param cornerRadius      Corner radius of the container.
 * @param borderWidth       Stroke width of the container border.
 * @param fillColor         Background fill colour of the container.
 * @param strokeColor       Border stroke colour of the container.
 * @param backgroundBlur        Background blur radius applied to the container.
 * @param paddingStart      Start (leading) padding inside the container.
 * @param paddingEnd        End (trailing) padding inside the container.
 * @param paddingTop        Top padding inside the container.
 * @param paddingBottom     Bottom padding inside the container.
 * @param noPadding         Padding applied when [hasPadding] is `false`.
 */
@Immutable
data class ContentGroupStyle(
    val cornerRadius: Dp,
    val borderWidth: Dp,
    val fillColor: Color,
    val strokeColor: Color,
    val backgroundBlur: Dp,
    val paddingStart: Dp,
    val paddingEnd: Dp,
    val paddingTop: Dp,
    val paddingBottom: Dp,
    val noPadding: Dp,
)

/**
 * Composition local for [ContentGroupStyle].
 *
 * Provided by brand themes (e.g. [com.ui.audi.AudiTheme]).
 * Throws if accessed outside a theme scope.
 */
val LocalContentGroupStyle =
    compositionLocalOf<ContentGroupStyle> {
        error("No ContentGroupStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
