package com.ui.core.widgets.listitems.subcomponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * **DragMarker** sub-widget for [com.ui.core.widgets.listitems.ListItem] (Edit mode).
 *
 * Wraps the consumer-provided drag-handle icon in a container sized to
 * [LocalListItemSubComponentStyle.dragMarkerWidth] — no hardcoded dimensions.
 *
 * ## Usage
 * ```kotlin
 * ListItem(
 *     config = ListItemConfig(mode = ListItemConfig.Mode.Edit),
 *     slots = ListItemSlots(
 *         dragMarker = {
 *             ListItemDragMarker {
 *                 Icon(Icons.Filled.Menu, "Drag", Modifier.size(24.dp))
 *             }
 *         },
 *     ),
 * )
 * ```
 *
 * @param modifier Modifier applied to the outer container.
 * @param icon     Composable slot for the drag-handle icon.
 * @see ListItemSubComponentStyle
 * @see LocalListItemSubComponentStyle
 */
@Composable
fun ListItemDragMarker(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    val style = LocalListItemSubComponentStyle.current
    Box(
        modifier =
            modifier
                .width(style.dragMarkerWidth)
                .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        icon()
    }
}
