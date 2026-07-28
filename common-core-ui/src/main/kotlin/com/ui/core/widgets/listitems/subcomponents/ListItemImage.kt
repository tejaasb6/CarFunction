package com.ui.core.widgets.listitems.subcomponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

/**
 * **LeadingContent / Variant=Image** sub-widget for [com.ui.core.widgets.listitems.ListItem].
 *
 * Renders an image container with a configurable aspect ratio. The corner
 * radius is resolved from [LocalListItemSubComponentStyle.imageCornerRadius].
 *
 * ## Usage
 * ```kotlin
 * ListItem(
 *     slots = ListItemSlots(
 *         leadingContent = {
 *             ListItemImage(
 *                 aspectRatio = ListItemImageAspectRatio.Ratio16x9,
 *                 image = { AsyncImage(model = url, ...) },
 *                 label = { Text("Album Art") },
 *             )
 *         },
 *     ),
 * )
 * ```
 *
 * @param image       Composable slot for the image content.
 * @param modifier    Modifier applied to the outer container.
 * @param aspectRatio The aspect ratio constraint for the image container.
 *                    Defaults to [ListItemImageAspectRatio.Ratio16x9].
 * @param label       Optional composable slot for a label below the image.
 * @see ListItemImageAspectRatio
 * @see ListItemSubComponentStyle
 * @see LocalListItemSubComponentStyle
 */
@Composable
fun ListItemImage(
    image: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    aspectRatio: ListItemImageAspectRatio = ListItemImageAspectRatio.Ratio16x9,
    label: (@Composable () -> Unit)? = null,
) {
    val style = LocalListItemSubComponentStyle.current
    val shape = RoundedCornerShape(style.imageCornerRadius)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .aspectRatio(aspectRatio.value)
                    .clip(shape),
            contentAlignment = Alignment.Center,
        ) {
            image()
        }
        val lbl = label
        if (lbl != null) {
            lbl()
        }
    }
}

/**
 * Aspect ratio options for [ListItemImage], derived from Figma
 * `Global/ImageContainer` component property `Aspect Ratio`.
 *
 * @property value The numeric aspect ratio passed to [Modifier.aspectRatio][androidx.compose.foundation.layout.aspectRatio].
 * @see ListItemImage
 */
enum class ListItemImageAspectRatio(
    val value: Float,
) {
    /** 16:9 widescreen aspect ratio. */
    Ratio16x9(16f / 9f),

    /** Free / square aspect ratio (1:1). */
    Free(1f),
}
