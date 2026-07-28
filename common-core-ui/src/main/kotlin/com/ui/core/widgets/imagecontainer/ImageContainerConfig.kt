package com.ui.core.widgets.imagecontainer

/**
 * Configuration for [ImageContainer].
 *
 * Bundles the aspect-ratio selection into a single value object, keeping the
 * public [ImageContainer] signature compact. The actual graphic content is
 * provided via a composable `content` slot — see [ImageContainer].
 *
 * ```kotlin
 * // 16:9 container — caller controls width via modifier
 * ImageContainer(
 *     config = ImageContainerConfig(
 *         aspectRatio = ImageContainerAspectRatio.Ratio16x9,
 *     ),
 *     modifier = Modifier.width(300.dp),
 * ) {
 *     Image(
 *         painter = painterResource(R.drawable.hero),
 *         contentDescription = "Hero banner",
 *         contentScale = ContentScale.Crop,
 *         modifier = Modifier.fillMaxSize(),
 *     )
 * }
 *
 * // Free container — wraps the slot's intrinsic size
 * ImageContainer {
 *     AsyncImage(model = url, contentDescription = null)
 * }
 * ```
 *
 * @param aspectRatio Predefined shape that determines the container's
 *                    width-to-height ratio. Defaults to
 *                    [ImageContainerAspectRatio.Free] (no enforced ratio).
 */
data class ImageContainerConfig(
    val aspectRatio: ImageContainerAspectRatio = ImageContainerAspectRatio.Free,
)
