package com.ui.audi.widgets.semanticshapes

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.semanticshapes.SemanticShapeStyle
import com.ui.core.widgets.semanticshapes.SemanticShapeVariantColors

/**
 * Audi default [SemanticShapeStyle] — resolves every value from the token engine.
 *
 * Token paths:
 * - Sizes: `Cmp.Size.Feedback.SemanticShape.{Container|Ellipse|Rectangle|Triangle}.*`
 * - Colours: `Cmp.Color.Feedback.SemanticShape.<Variant>.Surface.{Fill|Stroke}`
 * - Border: `Cmp.BorderWidth.Feedback.SemanticShape.Default`
 */
internal object SemanticShapeDefaults {
    /** Builds the Audi [SemanticShapeStyle] from the active theme tokens. */
    @Composable
    fun style(): SemanticShapeStyle =
        SemanticShapeStyle(
            containerDimension =
                Cmp.Size.Feedback.SemanticShape.Container.Dimension
                    .dimension()
                    .pxToDp(),
            ellipseWidth =
                Cmp.Size.Feedback.SemanticShape.Ellipse.Width
                    .dimension()
                    .pxToDp(),
            ellipseHeight =
                Cmp.Size.Feedback.SemanticShape.Ellipse.Height
                    .dimension()
                    .pxToDp(),
            rectangleWidth =
                Cmp.Size.Feedback.SemanticShape.Rectangle.Width
                    .dimension()
                    .pxToDp(),
            rectangleHeight =
                Cmp.Size.Feedback.SemanticShape.Rectangle.Height
                    .dimension()
                    .pxToDp(),
            triangleWidth =
                Cmp.Size.Feedback.SemanticShape.Triangle.Width
                    .dimension()
                    .pxToDp(),
            triangleHeight =
                Cmp.Size.Feedback.SemanticShape.Triangle.Height
                    .dimension()
                    .pxToDp(),
            borderWidth =
                Cmp.BorderWidth.Feedback.SemanticShape.Default
                    .dimension()
                    .pxToDp(),
            neutral = neutralColors(),
            informative = informativeColors(),
            positive = positiveColors(),
            advisory = advisoryColors(),
            warning = warningColors(),
            critical = criticalColors(),
        )

    @Composable
    private fun neutralColors(): SemanticShapeVariantColors =
        SemanticShapeVariantColors(
            surfaceFill =
                Cmp.Color.Feedback.SemanticShape.Neutral.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.Feedback.SemanticShape.Neutral.Surface.Stroke
                    .color(),
        )

    @Composable
    private fun informativeColors(): SemanticShapeVariantColors =
        SemanticShapeVariantColors(
            surfaceFill =
                Cmp.Color.Feedback.SemanticShape.Informative.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.Feedback.SemanticShape.Informative.Surface.Stroke
                    .color(),
        )

    @Composable
    private fun positiveColors(): SemanticShapeVariantColors =
        SemanticShapeVariantColors(
            surfaceFill =
                Cmp.Color.Feedback.SemanticShape.Positive.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.Feedback.SemanticShape.Positive.Surface.Stroke
                    .color(),
        )

    @Composable
    private fun advisoryColors(): SemanticShapeVariantColors =
        SemanticShapeVariantColors(
            surfaceFill =
                Cmp.Color.Feedback.SemanticShape.Advisory.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.Feedback.SemanticShape.Advisory.Surface.Stroke
                    .color(),
        )

    @Composable
    private fun warningColors(): SemanticShapeVariantColors =
        SemanticShapeVariantColors(
            surfaceFill =
                Cmp.Color.Feedback.SemanticShape.Warning.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.Feedback.SemanticShape.Warning.Surface.Stroke
                    .color(),
        )

    @Composable
    private fun criticalColors(): SemanticShapeVariantColors =
        SemanticShapeVariantColors(
            surfaceFill =
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Fill
                    .color(),
            surfaceStroke =
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Stroke
                    .color(),
        )
}
