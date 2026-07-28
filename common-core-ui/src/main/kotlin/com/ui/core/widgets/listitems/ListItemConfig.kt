package com.ui.core.widgets.listitems

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

/**
 * Configuration axes for [ListItem].
 *
 * Captures the static **Mode** that the consumer chooses at call-site.
 * The brand implementation reads this to look up the correct token subset
 * and determine which structural elements are present.
 *
 * Derived from the Figma component property `Mode` with three values:
 * `Default`, `Edit`, `Delete`.
 *
 * ```kotlin
 * // Standard list item
 * ListItem(
 *     config = ListItemConfig(mode = ListItemConfig.Mode.Default),
 *     content = ListItemContent(label = "Wi-Fi"),
 * )
 *
 * // Edit mode with drag marker for reorder
 * ListItem(
 *     config = ListItemConfig(mode = ListItemConfig.Mode.Edit),
 *     slots = ListItemSlots(dragMarker = { DragHandle() }),
 * )
 *
 * // Delete mode — delete button auto-rendered via deleteIcon + onDelete
 * ListItem(
 *     config = ListItemConfig(
 *         mode = ListItemConfig.Mode.Delete,
 *         deleteIcon = { Icon(Icons.Filled.Delete, "Delete", tint = Color.White) },
 *     ),
 *     content = ListItemContent(label = "Deletable item"),
 *     interactionConfig = ListItemInteractionConfig(onDelete = { removeItem() }),
 * )
 * ```
 *
 * @param mode The structural mode axis (Default, Edit, or Delete).
 * @param showBottomDivider Whether to show the horizontal divider at the bottom
 *                          of the list item (default `true`, matches Figma `DividerWrapper`).
 * @param deleteIcon        Icon composable rendered inside the auto-generated delete button
 *                          when [Mode.Delete] is active. The delete button is rendered by
 *                          the brand implementation **outside** (appended beside) the
 *                          ListItem surface. If `null` in Delete mode, no delete button
 *                          is shown. Only meaningful when [mode] is [Mode.Delete].
 */
@Immutable
data class ListItemConfig(
    val mode: Mode = Mode.Default,
    val showBottomDivider: Boolean = true,
    val deleteIcon: (@Composable () -> Unit)? = null,
) {
    /**
     * Structural mode axis derived from the Figma component property `Mode`.
     *
     * - [Default] — standard list item layout.
     * - [Edit] — adds a drag-marker handle at the leading edge for reorder.
     *            Supports the `Drag` state with opacity layer + elevation shadow.
     * - [Delete] — the brand implementation auto-renders a trailing delete button
     *              using [ListItemConfig.deleteIcon] and [ListItemInteractionConfig.onDelete].
     *              Does not support `Pressed` state (only Idle/Disabled per spec).
     */
    enum class Mode {
        /** Standard list item — no drag marker, no delete button. */
        Default,

        /** Edit / reorder mode — shows drag-marker handle. Supports `Drag` state. */
        Edit,

        /** Delete mode — shows trailing destructive delete action button. */
        Delete,
    }
}
