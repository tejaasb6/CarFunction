package com.ui.core.widgets.listitems

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

/**
 * Composable slot definitions for [ListItem].
 *
 * A **single, unified** slot class that supports all three modes
 * ([ListItemConfig.Mode.Default], [ListItemConfig.Mode.Edit], [ListItemConfig.Mode.Delete])
 * through the same API. The brand implementation uses the mode to decide which
 * slots are rendered and how:
 *
 * ## Standard Multi-Slot Anatomy (Default / Edit)
 * ```
 * ┌──────────────────────────────────────────────────────────────────────────────────────┐
 * │ [drag] │ [content] │ [control] │ [icon] Label   TrailText [trailIcon] [progress]   │
 * │        │           │           │        SubText                                     │
 * │────────│───────────│───────────│────────────────────────────────║ [secondaryAction] │
 * │        │           │           │                                ║ [tertiaryAction]  │
 * └──────────────────────────────────────────────────────────────────────────────────────┘
 * ```
 *
 * ## Delete Mode Anatomy
 * The delete button is rendered **outside** the ListItem surface, appended beside it.
 * The delete icon is configured via [ListItemConfig.deleteIcon], not as a slot:
 * ```
 * ┌─────────────────────────────────────────────────────┐ ┌──────────────┐
 * │ [content] │ [control] │ [icon] Label   TrailText    │ │ [deleteIcon] │
 * │           │           │        SubText              │ │              │
 * └─────────────────────────────────────────────────────┘ └──────────────┘
 *   ← ListItem surface (no secondary/tertiary) →          ← Delete Btn →
 * ```
 *
 * ## Full-Width Content Layout (when [fullWidthContent] is non-null)
 * ```
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │ Label (headline)                                                            │
 * │ ┌────────────────────────────────────────────────────────────────────────┐   │
 * │ │ fullWidthContent — full-width (Slider / SegmentedControl from hmisdk) │   │
 * │ └────────────────────────────────────────────────────────────────────────┘   │
 * └──────────────────────────────────────────────────────────────────────────────┘
 * ```
 *
 * When [fullWidthContent] is non-null, the brand implementation renders the
 * full-width layout (headline + content slot). All other slots are ignored
 * in that case. This is enforced at the rendering level, not at compile time,
 * giving consumers a single unified API.
 *
 * ## Mode-based Slot Visibility
 * - **Default** — standard layout with all applicable slots.
 * - **Edit** — [dragMarker] is shown; supports Drag state.
 * - **Delete** — the delete button is rendered **automatically** by the brand
 *   implementation **outside** the ListItem surface, using
 *   [ListItemInteractionConfig.onDelete] and [ListItemConfig.deleteIcon].
 *   [secondaryAction] and [tertiaryAction] are **ignored** in Delete mode.
 *
 * ## Interaction Area Rules (from CC_0015 requirements)
 * - Max **3** interaction areas per list item, separated by vertical dividers.
 * - Non-interactive Covers, Avatars, Drag Markers, and control elements (ToggleSwitch,
 *   Checkbox, RadioButton) are aligned **left** of text.
 * - All other interactive or non-interactive trailing elements are aligned **right** of text.
 *
 * ## Usage Examples
 * ```kotlin
 * // Standard layout with ToggleSwitch + secondary action
 * ListItem(
 *     content = ListItemContent(label = "Wi-Fi"),
 *     slots = ListItemSlots(
 *         leadingControl = {
 *             ToggleSwitch(
 *                 interactionConfig = ToggleSwitchInteractionConfig(
 *                     selected = isEnabled,
 *                     onSelectedChange = { isEnabled = it },
 *                 ),
 *             )
 *         },
 *         secondaryAction = {
 *             IconButton(
 *                 config = IconButtonConfig(...),
 *                 interactionConfig = IconButtonInteractionConfig(onClick = { showInfo() }),
 *             )
 *         },
 *     ),
 * )
 *
 * // Edit mode with drag marker
 * ListItem(
 *     config = ListItemConfig(mode = ListItemConfig.Mode.Edit),
 *     slots = ListItemSlots(
 *         dragMarker = { DragHandle() },
 *         leadingIcon = { Icon(config = IconConfig(...)) },
 *     ),
 * )
 *
 * // Delete mode — delete button rendered automatically by the ListItem
 * ListItem(
 *     config = ListItemConfig(
 *         mode = ListItemConfig.Mode.Delete,
 *         deleteIcon = { Icon(Icons.Filled.Delete, "Delete", tint = Color.White) },
 *     ),
 *     content = ListItemContent(label = "Swipe to delete"),
 *     interactionConfig = ListItemInteractionConfig(
 *         onDelete = { deleteItem() },
 *     ),
 * )
 *
 * // Full-width content slot (Slider)
 * ListItem(
 *     content = ListItemContent(label = "Volume"),
 *     slots = ListItemSlots(
 *         fullWidthContent = {
 *             Slider(...)  // hmisdk Slider — occupies full width
 *         },
 *     ),
 * )
 * ```
 *
 * @param dragMarker          Drag-marker handle for reorder mode (Edit mode only).
 *                            Consumer provides the drag-handle composable from hmisdk.
 * @param leadingContent      Cover / Avatar / Non-interactive Icon+Label visual element.
 *                            Aligned left of text. Consumer provides the hmisdk widget.
 * @param leadingControl      Interactive control element (ToggleSwitch, Checkbox, RadioButton)
 *                            placed after [leadingContent] (or in its place when [leadingContent]
 *                            is `null`). Both can be visible simultaneously in Default/Edit modes.
 *                            In Delete mode, [leadingControl] requires [leadingContent] to be present.
 * @param leadingIcon         Decorative or interactive icon positioned in the icon slot
 *                            (below the text row area). Consumer provides hmisdk `Icon`.
 * @param sublabelIcon        Icon displayed next to the supporting text line.
 *                            Consumer provides hmisdk `Icon`.
 * @param trailingIcon        Non-interactive icon after the trailing text, right-aligned.
 *                            Consumer provides hmisdk `Icon`.
 * @param progressIndicator   Spinner / progress indicator slot. Consumer provides hmisdk
 *                            `ProgressIndicator` (Spinner variant only).
 * @param secondaryAction     Second interaction area (after vertical divider).
 *                            Consumer provides an hmisdk `IconButton` or similar.
 *                            **Ignored in Delete mode** — the delete button is rendered
 *                            outside the ListItem surface instead.
 * @param tertiaryAction      Third interaction area (after second vertical divider).
 *                            Consumer provides an hmisdk `IconButton` or similar.
 *                            **Ignored in Delete mode.**
 * @param fullWidthContent    Full-width composable content (Slider, SegmentedControl, etc.).
 *                            When non-null, the brand implementation renders the full-width
 *                            layout instead of the standard multi-slot layout.
 *                            All other slots are ignored when this is set.
 */
@Immutable
data class ListItemSlots(
    val dragMarker: (@Composable () -> Unit)? = null,
    val leadingContent: (@Composable () -> Unit)? = null,
    val leadingControl: (@Composable () -> Unit)? = null,
    val leadingIcon: (@Composable () -> Unit)? = null,
    val sublabelIcon: (@Composable () -> Unit)? = null,
    val trailingIcon: (@Composable () -> Unit)? = null,
    val progressIndicator: (@Composable () -> Unit)? = null,
    val secondaryAction: (@Composable () -> Unit)? = null,
    val tertiaryAction: (@Composable () -> Unit)? = null,
    val fullWidthContent: (@Composable () -> Unit)? = null,
) {
    companion object {
        /**
         * Creates a default empty slot configuration.
         *
         * Convenience for the common case where no slots are needed:
         * ```kotlin
         * ListItem(content = ListItemContent(label = "Simple item"))
         * // slots defaults to ListItemSlots.empty()
         * ```
         */
        fun empty(): ListItemSlots = ListItemSlots()
    }
}
