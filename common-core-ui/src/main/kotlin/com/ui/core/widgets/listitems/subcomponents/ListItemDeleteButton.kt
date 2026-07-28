package com.ui.core.widgets.listitems.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

/**
 * **Button/Delete** sub-widget for [com.ui.core.widgets.listitems.ListItem] (Delete mode).
 *
 * Renders the trailing destructive delete button **outside** (appended beside)
 * the ListItem surface. All dimensions, radii, and fill colour are resolved
 * from [LocalListItemSubComponentStyle] tokens — nothing is hardcoded.
 *
 * **Note:** Consumers should **not** call this directly. The brand implementation
 * automatically renders this component in Delete mode — placed adjacent to
 * the ListItem surface — using [ListItemConfig.deleteIcon] and
 * [ListItemInteractionConfig.onDelete].
 *
 * ## Usage (internal — invoked by brand implementation)
 * ```kotlin
 * // Consumer API — brand implementation handles the delete button automatically:
 * ListItem(
 *     config = ListItemConfig(
 *         mode = ListItemConfig.Mode.Delete,
 *         deleteIcon = { Icon(Icons.Filled.Delete, "Delete", tint = Color.White) },
 *     ),
 *     interactionConfig = ListItemInteractionConfig(
 *         onDelete = { deleteItem() },
 *     ),
 * )
 * ```
 *
 * @param onClick  Callback invoked when the delete button is tapped.
 * @param icon     Composable slot for the delete icon displayed inside the button.
 * @param modifier Modifier applied to the outer container.
 * @see ListItemSubComponentStyle
 * @see LocalListItemSubComponentStyle
 */
@Composable
fun ListItemDeleteButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val style = LocalListItemSubComponentStyle.current
    val shape = RoundedCornerShape(style.deleteButtonCornerRadius)

    Box(
        modifier =
            modifier
                .width(style.deleteButtonMinWidth)
                .defaultMinSize(minHeight = style.deleteButtonMinHeight)
                .fillMaxHeight()
                .clip(shape)
                .background(style.deleteButtonFillColor, shape)
                .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        icon()
    }
}
