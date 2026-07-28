package com.ui.audi.widgets.semanticshapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.ui.core.widgets.semanticshapes.LocalSemanticShapeStyle
import com.ui.core.widgets.semanticshapes.SemanticShapeConfig
import com.ui.core.widgets.semanticshapes.colorsForVariant

/**
 * Audi-themed brand impl of [com.ui.core.widgets.semanticshapes.SemanticShape].
 *
 * Renders a small status indicator shape — the geometry depends on the variant:
 * - **Neutral / Informative / Positive** → filled ellipse (circle)
 * - **Advisory** → filled diamond (45° rotated rectangle)
 * - **Warning** → filled inverted triangle (▽)
 * - **Critical** → filled upward triangle (△)
 *
 * All sizes and colours are resolved from `Cmp.*.Feedback.SemanticShape.*` tokens.
 */
@Composable
internal fun SemanticShape(
    config: SemanticShapeConfig,
    modifier: Modifier = Modifier,
) {
    val style = LocalSemanticShapeStyle.current
    val colors = style.colorsForVariant(config.variant)

    val accessibilityLabel = "${config.variant.name} status"

    Box(
        modifier =
            modifier
                .size(style.containerDimension)
                .semantics { contentDescription = accessibilityLabel },
        contentAlignment = Alignment.Center,
    ) {
        when (config.variant) {
            SemanticShapeConfig.Variant.Neutral,
            SemanticShapeConfig.Variant.Informative,
            SemanticShapeConfig.Variant.Positive,
            -> {
                EllipseShape(
                    width = style.ellipseWidth,
                    height = style.ellipseHeight,
                    fillColor = colors.surfaceFill,
                    strokeColor = colors.surfaceStroke,
                    strokeWidth = style.borderWidth,
                )
            }

            SemanticShapeConfig.Variant.Advisory -> {
                DiamondShape(
                    width = style.rectangleWidth,
                    height = style.rectangleHeight,
                    fillColor = colors.surfaceFill,
                    strokeColor = colors.surfaceStroke,
                    strokeWidth = style.borderWidth,
                )
            }

            SemanticShapeConfig.Variant.Warning -> {
                TriangleShape(
                    width = style.triangleWidth,
                    height = style.triangleHeight,
                    fillColor = colors.surfaceFill,
                    strokeColor = colors.surfaceStroke,
                    strokeWidth = style.borderWidth,
                    inverted = true,
                )
            }

            SemanticShapeConfig.Variant.Critical -> {
                TriangleShape(
                    width = style.triangleWidth,
                    height = style.triangleHeight,
                    fillColor = colors.surfaceFill,
                    strokeColor = colors.surfaceStroke,
                    strokeWidth = style.borderWidth,
                    inverted = false,
                )
            }
        }
    }
}

// ── Shape drawing composables ─────────────────────────────────────────────────

/**
 * Draws a filled ellipse (or circle when width == height).
 */
@Composable
private fun EllipseShape(
    width: Dp,
    height: Dp,
    fillColor: Color,
    strokeColor: Color,
    strokeWidth: Dp,
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }

    Canvas(modifier = Modifier.size(width, height)) {
        drawOval(
            color = fillColor,
            topLeft = Offset.Zero,
            size = Size(size.width, size.height),
            style = Fill,
        )
        if (strokeColor != Color.Transparent && strokeWidthPx > 0f) {
            drawOval(
                color = strokeColor,
                topLeft = Offset.Zero,
                size = Size(size.width, size.height),
                style = Stroke(width = strokeWidthPx),
            )
        }
    }
}

/**
 * Draws a filled diamond (45° rotated rectangle).
 */
@Composable
private fun DiamondShape(
    width: Dp,
    height: Dp,
    fillColor: Color,
    strokeColor: Color,
    strokeWidth: Dp,
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }

    Canvas(modifier = Modifier.size(width, height)) {
        val path = diamondPath(size)
        drawPath(path = path, color = fillColor, style = Fill)
        if (strokeColor != Color.Transparent && strokeWidthPx > 0f) {
            drawPath(path = path, color = strokeColor, style = Stroke(width = strokeWidthPx))
        }
    }
}

/**
 * Draws a filled triangle.
 *
 * @param inverted when `true`, draws a downward-pointing triangle (▽ — Warning);
 *                 when `false`, draws an upward-pointing triangle (△ — Critical).
 */
@Composable
private fun TriangleShape(
    width: Dp,
    height: Dp,
    fillColor: Color,
    strokeColor: Color,
    strokeWidth: Dp,
    inverted: Boolean,
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }

    Canvas(modifier = Modifier.size(width, height)) {
        val path = trianglePath(size, inverted)
        drawPath(path = path, color = fillColor, style = Fill)
        if (strokeColor != Color.Transparent && strokeWidthPx > 0f) {
            drawPath(path = path, color = strokeColor, style = Stroke(width = strokeWidthPx))
        }
    }
}

// ── Path builders ─────────────────────────────────────────────────────────────

private fun DrawScope.diamondPath(size: Size): Path =
    Path().apply {
        val cx = size.width / 2f
        val cy = size.height / 2f
        moveTo(cx, 0f) // top
        lineTo(size.width, cy) // right
        lineTo(cx, size.height) // bottom
        lineTo(0f, cy) // left
        close()
    }

private fun DrawScope.trianglePath(
    size: Size,
    inverted: Boolean,
): Path =
    Path().apply {
        if (inverted) {
            // Downward-pointing triangle (▽) — Warning
            moveTo(0f, 0f) // top-left
            lineTo(size.width, 0f) // top-right
            lineTo(size.width / 2f, size.height) // bottom-center
        } else {
            // Upward-pointing triangle (△) — Critical
            moveTo(size.width / 2f, 0f) // top-center
            lineTo(size.width, size.height) // bottom-right
            lineTo(0f, size.height) // bottom-left
        }
        close()
    }
