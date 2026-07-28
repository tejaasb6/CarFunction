package com.ui.core.widgets.imagecontainer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Brand-agnostic Image Container — the **single public API** for displaying
 * graphic content inside a design-system-compliant container.
 *
 * The Image Container (CC_0052) is a purely presentational component with no
 * interactive states. It clips its [content] slot to the brand's corner-radius
 * token and — when a non-[Free][ImageContainerAspectRatio.Free] aspect ratio
 * is selected — enforces that ratio on the container.
 *
 * The widget does **not** impose its own width or height. Callers control
 * sizing entirely through [modifier] (`Modifier.width(…)`,
 * `Modifier.fillMaxWidth()`, etc.). When an aspect ratio is set, the
 * unspecified dimension is derived automatically.
 *
 * The [content] slot receives the full area of the container after clipping.
 * Any composable can be placed inside — `Image`, `AsyncImage`, `Box`,
 * animated content, etc.
 *
 * ## Supported aspect ratios
 *
 * | Shape | Ratio |
 * |-------|:-----:|
 * | [ImageContainerAspectRatio.Free] | — (wraps content) |
 * | [ImageContainerAspectRatio.Ratio1x1] | 1 : 1 |
 * | [ImageContainerAspectRatio.Ratio16x9] | 16 : 9 |
 * | [ImageContainerAspectRatio.Ratio3x4] | 3 : 4 |
 * | [ImageContainerAspectRatio.Ratio4x3] | 4 : 3 |
 *
 * ## Basic usage
 * ```kotlin
 * // 16:9 at a specific width
 * ImageContainer(
 *     config = ImageContainerConfig(
 *         aspectRatio = ImageContainerAspectRatio.Ratio16x9,
 *     ),
 *     modifier = Modifier.width(300.dp),
 * ) {
 *     Image(
 *         painter = painterResource(R.drawable.hero_banner),
 *         contentDescription = "Hero banner",
 *         contentScale = ContentScale.Crop,
 *         modifier = Modifier.fillMaxSize(),
 *     )
 * }
 *
 * // Free — wraps intrinsic content size
 * ImageContainer {
 *     AsyncImage(model = url, contentDescription = null)
 * }
 * ```
 *
 * ## Corner radius
 * The container has **sharp corners** (border radius = 0 dp), resolved from
 * the `Sem.BorderRadius.None` token. It is **not** caller-configurable —
 * brand implementations apply it automatically from
 * [com.ui.core.styles.ImageContainerStyle].
 *
 * @param config   Aspect-ratio shape selection.
 * @param modifier Applied to the outermost layout node. Use for sizing,
 *                 padding, or alignment constraints.
 * @param content  Composable slot rendered inside the clipped container.
 *                 Typically an [Image] or [AsyncImage] with
 *                 `Modifier.fillMaxSize()` and `ContentScale.Crop`.
 */
@Composable
fun ImageContainer(
    config: ImageContainerConfig = ImageContainerConfig(),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    LocalWidgets.ImageContainer.current(
        config,
        modifier,
        content,
    )
}
