package com.ui.core.widgets.listitems.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * **LeadingContent / Variant=IconLabel** sub-widget for [com.ui.core.widgets.listitems.ListItem].
 *
 * Renders a vertically stacked icon + optional label. The gap between icon and
 * label is resolved from [LocalListItemSubComponentStyle.iconLabelGap] — no
 * hardcoded spacing.
 *
 * ## Usage
 * ```kotlin
 * ListItem(
 *     slots = ListItemSlots(
 *         leadingContent = {
 *             ListItemIconLabel(
 *                 icon = { Icon(config = IconConfig(...)) },
 *                 label = { Text("Wi-Fi") },
 *             )
 *         },
 *     ),
 * )
 * ```
 *
 * @param icon     Composable slot for the icon.
 * @param modifier Modifier applied to the outer Column.
 * @param label    Optional composable slot for the label below the icon.
 * @see ListItemSubComponentStyle
 * @see LocalListItemSubComponentStyle
 */
@Composable
fun ListItemIconLabel(
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
) {
    val style = LocalListItemSubComponentStyle.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(style.iconLabelGap),
    ) {
        icon()
        val lbl = label
        if (lbl != null) {
            lbl()
        }
    }
}
