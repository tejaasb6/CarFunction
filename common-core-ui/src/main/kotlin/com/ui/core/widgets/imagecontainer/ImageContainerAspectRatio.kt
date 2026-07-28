package com.ui.core.widgets.imagecontainer

import androidx.compose.runtime.Immutable

/**
 * Predefined aspect-ratio shapes for [ImageContainer].
 *
 * Each entry carries a `ratio` value (width / height) that the brand
 * implementation applies via `Modifier.aspectRatio()`. The special [Free]
 * entry has **no** enforced ratio — the container wraps whatever size the
 * [content][ImageContainer] slot produces.
 *
 * The widget itself does **not** impose width or height. Callers size the
 * container through the [Modifier] parameter — e.g. `Modifier.width(200.dp)`
 * or `Modifier.fillMaxWidth()`. The aspect ratio then derives the other
 * dimension automatically (except for [Free]).
 *
 * ```kotlin
 * // 16:9 container at 300 dp wide → height ≈ 169 dp
 * ImageContainer(
 *     config = ImageContainerConfig(
 *         aspectRatio = ImageContainerAspectRatio.Ratio16x9,
 *     ),
 *     modifier = Modifier.width(300.dp),
 * ) {
 *     Image(painter = painterResource(R.drawable.hero), contentDescription = null)
 * }
 * ```
 *
 * | Shape | Ratio (w / h) | Description |
 * |-------|:-------------:|-------------|
 * | [Free] | — | No enforced ratio; wraps content size |
 * | [Ratio1x1] | 1 : 1 | Square |
 * | [Ratio16x9] | 16 : 9 | Wide-screen |
 * | [Ratio3x4] | 3 : 4 | Portrait |
 * | [Ratio4x3] | 4 : 3 | Landscape |
 */
@Immutable
enum class ImageContainerAspectRatio(
    /**
     * The width-to-height ratio (`width / height`).
     *
     * `null` for [Free] — the container has no enforced aspect ratio and
     * wraps the intrinsic size of its [content][ImageContainer] slot.
     */
    val ratio: Float?,
) {
    /**
     * Free-form container — no aspect ratio enforced.
     *
     * The container wraps the intrinsic size of its content slot.
     * Callers may still constrain dimensions via [Modifier].
     */
    Free(ratio = null),

    /**
     * Square 1 : 1 container.
     */
    Ratio1x1(ratio = 1f),

    /**
     * Wide-screen 16 : 9 container.
     */
    Ratio16x9(ratio = 16f / 9f),

    /**
     * Portrait 3 : 4 container.
     */
    Ratio3x4(ratio = 3f / 4f),

    /**
     * Landscape 4 : 3 container.
     */
    Ratio4x3(ratio = 4f / 3f),
}
