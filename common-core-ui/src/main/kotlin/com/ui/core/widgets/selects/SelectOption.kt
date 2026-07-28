package com.ui.core.widgets.selects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

/**
 * Represents a single selectable option in a [Select] dropdown menu.
 *
 * Uses a slot-based composable [label] for rendering, giving callers full
 * flexibility over what is displayed. The same [label] slot is rendered both
 * in the dropdown menu item and in the select field when this option is selected.
 *
 * ```kotlin
 * val option = SelectOption(
 *     label = { Text(state = TextState(text = "Option 1".TR)) },
 *     icon = { Icon(Icons.Default.Star, contentDescription = null) },
 * )
 * ```
 *
 * @param label composable slot for the option's content, displayed in both the
 *  dropdown menu item and the select field when selected.
 * @param icon optional composable slot for a leading icon in the dropdown menu item.
 * @param enabled whether this option is selectable; when `false` the option is shown
 *  but cannot be selected.
 */
@Immutable
data class SelectOption(
    val label: @Composable () -> Unit,
    val icon: (@Composable () -> Unit)? = null,
    val enabled: Boolean = true,
)
