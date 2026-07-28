package com.ui.core.widgets.semanticshapes

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Per-variant colour set for a [SemanticShape].
 *
 * Each variant has a surface fill and stroke colour resolved from
 * `Cmp.Color.Feedback.SemanticShape.<Variant>.Surface.{Fill|Stroke}` tokens.
 *
 * @property surfaceFill background fill colour of the shape.
 * @property surfaceStroke border stroke colour of the shape.
 */
@Immutable
data class SemanticShapeVariantColors(
    val surfaceFill: Color,
    val surfaceStroke: Color,
)

/**
 * Full visual specification for [SemanticShape].
 *
 * Brand implementations build this from `Cmp.*.Feedback.SemanticShape.*` tokens:
 * - Sizes: `Cmp.Size.Feedback.SemanticShape.{Container|Ellipse|Rectangle|Triangle}.*`
 * - Colours: `Cmp.Color.Feedback.SemanticShape.<Variant>.Surface.{Fill|Stroke}`
 * - Border: `Cmp.BorderWidth.Feedback.SemanticShape.Default`
 *
 * Override at any level of the composition tree via [LocalSemanticShapeStyle].
 *
 * Example:
 * ```kotlin
 * val style = LocalSemanticShapeStyle.current
 * ```
 *
 * @property containerDimension overall container dimension (width/height).
 * @property ellipseWidth width of the ellipse/circle shapes (Neutral, Informative, Positive).
 * @property ellipseHeight height of the ellipse/circle shapes.
 * @property rectangleWidth width of the diamond/rectangle shape (Advisory).
 * @property rectangleHeight height of the diamond/rectangle shape.
 * @property triangleWidth width of the triangle shapes (Warning, Critical).
 * @property triangleHeight height of the triangle shapes.
 * @property borderWidth stroke width for shape outlines.
 * @property neutral colours for [SemanticShapeConfig.Variant.Neutral].
 * @property informative colours for [SemanticShapeConfig.Variant.Informative].
 * @property positive colours for [SemanticShapeConfig.Variant.Positive].
 * @property advisory colours for [SemanticShapeConfig.Variant.Advisory].
 * @property warning colours for [SemanticShapeConfig.Variant.Warning].
 * @property critical colours for [SemanticShapeConfig.Variant.Critical].
 */
@Immutable
data class SemanticShapeStyle(
    val containerDimension: Dp,
    val ellipseWidth: Dp,
    val ellipseHeight: Dp,
    val rectangleWidth: Dp,
    val rectangleHeight: Dp,
    val triangleWidth: Dp,
    val triangleHeight: Dp,
    val borderWidth: Dp,
    val neutral: SemanticShapeVariantColors,
    val informative: SemanticShapeVariantColors,
    val positive: SemanticShapeVariantColors,
    val advisory: SemanticShapeVariantColors,
    val warning: SemanticShapeVariantColors,
    val critical: SemanticShapeVariantColors,
)

/**
 * Returns the [SemanticShapeVariantColors] for the given [variant].
 */
fun SemanticShapeStyle.colorsForVariant(variant: SemanticShapeConfig.Variant): SemanticShapeVariantColors =
    when (variant) {
        SemanticShapeConfig.Variant.Neutral -> neutral
        SemanticShapeConfig.Variant.Informative -> informative
        SemanticShapeConfig.Variant.Positive -> positive
        SemanticShapeConfig.Variant.Advisory -> advisory
        SemanticShapeConfig.Variant.Warning -> warning
        SemanticShapeConfig.Variant.Critical -> critical
    }

/**
 * Composition local providing the current [SemanticShapeStyle].
 *
 * Populated by `AudiTheme` / `LamborghiniTheme`; accessing it outside a brand
 * theme throws an error.
 */
val LocalSemanticShapeStyle =
    compositionLocalOf<SemanticShapeStyle> {
        error("No SemanticShapeStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
