package com.ui.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/**
 * Converts a pixel (`Int`) value to density-independent pixels ([Dp]).
 *
 * This is a composable extension as it relies on [LocalDensity] to
 * obtain the current screen density from the composition.
 *
 * Commonly used when working with design tokens or values defined in pixels
 * (e.g., from Figma or platform specs) that need to be adapted to Compose layouts.
 *
 * Example:
 * ```kotlin
 * val cornerRadius = 12.pxToDp()
 * ```
 *
 * @return The equivalent value in [Dp] based on the current density.
 */
@Composable
fun Int.pxToDp(): Dp =
    with(LocalDensity.current) {
        this@pxToDp.toDp()
    }

@Composable
fun Float.pxToDp(): Dp =
    with(LocalDensity.current) {
        this@pxToDp.toDp()
    }

/**
 * Returns a darker version of this color by blending it with black (#000000).
 *
 * This function reduces the brightness of the color by moving its RGB values
 * toward 0 (black) based on the given percentage.
 *
 * @param percent The amount to darken the color (0f to 1f)
 * - 0f → No change (original color)
 * - 1f → Fully black
 *
 * Example:
 * ```
 * val darkerColor = color.darken(0.2f) // 20% darker
 * ```
 *
 * Internally:
 * newChannel = originalChannel * (1 - percent)
 *
 * This is useful for:
 * - Pressed states
 * - Shadow effects
 * - Reducing brightness
 */
fun Color.darken(percent: Float): Color {
    val p = percent.coerceIn(0f, 1f)

    return Color(
        red = red * (1 - p),
        green = green * (1 - p),
        blue = blue * (1 - p),
        alpha = alpha,
    )
}

/**
 * Returns a lighter version of this color by blending it with white (#FFFFFF).
 *
 * This function increases the brightness of the color by moving its RGB values
 * toward 1 (white) based on the given percentage.
 *
 * @param percent The amount to lighten the color (0f to 1f)
 * - 0f → No change (original color)
 * - 1f → Fully white
 *
 * Example:
 * ```
 * val lighterColor = color.lighten(0.3f) // 30% lighter
 * ```
 *
 * Internally:
 * newChannel = originalChannel + (1 - originalChannel) * percent
 *
 * This is useful for:
 * - Highlight states
 * - Disabled backgrounds
 * - Increasing visual emphasis
 */
fun Color.lighten(percent: Float): Color {
    val p = percent.coerceIn(0f, 1f)

    return Color(
        red = red + (1f - red) * p,
        green = green + (1f - green) * p,
        blue = blue + (1f - blue) * p,
        alpha = alpha,
    )
}

/**
 * Returns a copy of this color with the specified alpha applied.
 *
 * Commonly used for disabled states where reduced opacity conveys low emphasis.
 *
 * @param alpha The alpha value to apply (0f–1f).
 */
fun Color.withAlpha(alpha: Float): Color = copy(alpha = alpha)

/**
 * Converts a pixel (`Int`) value to scale-independent pixels ([TextUnit] in `sp`).
 *
 * This is a composable extension as it relies on [LocalDensity] to
 * obtain the current screen density and font scaling from the composition.
 *
 * Commonly used when working with typography values defined in pixels
 * (e.g., from Figma or design tokens) that need to adapt correctly
 * to user font scaling in Compose.
 *
 * Example:
 * ```kotlin
 * val fontSize = 36.pxToSp()
 * ```
 *
 * @return The equivalent value in `sp` based on the current density and font scale.
 */
@Composable
fun Int.pxToSp(): TextUnit =
    with(LocalDensity.current) {
        this@pxToSp.toSp()
    }

@Composable
fun Float.pxToSp(): TextUnit =
    with(LocalDensity.current) {
        this@pxToSp.toSp()
    }
