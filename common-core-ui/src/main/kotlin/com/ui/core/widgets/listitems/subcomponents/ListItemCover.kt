package com.ui.core.widgets.listitems.subcomponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

/**
 * **LeadingContent / Variant=Cover** sub-widget for [com.ui.core.widgets.listitems.ListItem].
 *
 * Renders a cover surface with an icon overlay. All dimensions, radii, and
 * stroke are resolved from [LocalListItemSubComponentStyle] tokens.
 *
 * ## Usage
 * ```kotlin
 * ListItem(
 *     slots = ListItemSlots(
 *         leadingContent = {
 *             ListItemCover(
 *                 icon = { Icon(config = IconConfig(...)) },
 *                 label = { Text("Title") },
 *             )
 *         },
 *     ),
 * )
 * ```
 *
 * @param icon     Composable slot for the overlay icon on the cover surface.
 * @param modifier Modifier applied to the outer container.
 * @param label    Optional composable slot for cover title/label.
 * @see ListItemSubComponentStyle
 * @see LocalListItemSubComponentStyle
 */
@Composable
fun ListItemCover(
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
) {
    val style = LocalListItemSubComponentStyle.current
    val shape = RoundedCornerShape(style.coverCornerRadius)

    Box(
        modifier =
            modifier
                .size(style.coverSize)
                .clip(shape)
                .border(style.coverStrokeWidth, style.coverStrokeColor, shape),
        contentAlignment = Alignment.Center,
    ) {
        icon()
        val lbl = label
        if (lbl != null) {
            lbl()
        }
    }
}
