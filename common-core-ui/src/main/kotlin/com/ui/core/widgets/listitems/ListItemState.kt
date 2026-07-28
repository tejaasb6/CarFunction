package com.ui.core.widgets.listitems

import androidx.compose.runtime.Immutable

/**
 * Bundles the runtime state flags consumed by [ListItem].
 *
 * These flags drive token resolution within the brand implementation:
 * - `enabled = false` → Disabled state (dimmed via opacity layer)
 * - `isSelected = true` → Selected branch colours (surface fill, content colours)
 * - `isFocused = true` → Focus ring / indicator visible
 * - `isDragging = true` → Drag elevation shadow applied (Edit variant only)
 *
 * ```kotlin
 * ListItem(
 *     config = ListItemConfig(mode = ListItemConfig.Mode.Edit),
 *     state = ListItemState(enabled = true, isDragging = isDragged),
 *     content = ListItemContent(label = "Reorderable item"),
 * )
 * ```
 *
 * @param enabled    When `false`, the list item is non-interactive and dimmed.
 * @param isSelected When `true`, the selected-branch colours are used.
 * @param isFocused  When `true`, a focus indicator is rendered.
 * @param isDragging When `true`, drag elevation shadow is applied (Edit variant only).
 */
@Immutable
data class ListItemState(
    val enabled: Boolean = true,
    val isSelected: Boolean = false,
    val isFocused: Boolean = false,
    val isDragging: Boolean = false,
)
