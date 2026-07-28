package com.ui.core.widgets.listitems

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a list-item widget.
 *
 * Brand implementations provide the concrete rendering logic via
 * [LocalWidgets.ListItem]. The typealias keeps the parameter count ≤ 7
 * by bundling related concerns into [ListItemConfig], [ListItemState],
 * [ListItemContent], [ListItemSlots], and [ListItemInteractionConfig].
 *
 * Slot parameters allow consumers to inject **hmisdk widgets** (ToggleSwitch,
 * Checkbox, RadioButton, Icon, IconButton, Slider, SegmentedControl,
 * ProgressIndicator, etc.) without the ListItem knowing their concrete types.
 */
typealias ListItemWidgetContent = @Composable (
    config: ListItemConfig,
    modifier: Modifier,
    state: ListItemState,
    content: ListItemContent,
    slots: ListItemSlots,
    interactionConfig: ListItemInteractionConfig,
) -> Unit

/**
 * Brand-agnostic list item — the **single public API** for all list-item layouts.
 *
 * Lists are continuous groups of text or images that organise related content
 * in an easy-to-scan format. Each list item may contain primary and supplemental
 * actions, represented through icons and text.
 *
 * ## Content ([ListItemContent])
 * - [ListItemContent.label] — primary text (mandatory, single line, ellipsis).
 * - [ListItemContent.supportingText] — secondary line (single line, ellipsis).
 * - [ListItemContent.trailingText] — right-aligned supplementary text.
 *
 * ## Slots ([ListItemSlots]) — hmisdk Widget Injection Points (Unified API)
 * A **single** `ListItemSlots` data class supports both the standard multi-slot
 * layout and the full-width content layout. The brand implementation selects the
 * layout based on whether [ListItemSlots.fullWidthContent] is non-null.
 *
 * ### Standard multi-slot anatomy (when `fullWidthContent == null`)
 * | Slot | Expected hmisdk Widget | Position |
 * |---|---|---|
 * | `dragMarker` | Drag handle | Leading (Edit mode only) |
 * | `leadingContent` | Cover, Avatar, Icon+Label | Leading, left of text |
 * | `leadingControl` | ToggleSwitch, Checkbox, RadioButton | After leadingContent |
 * | `leadingIcon` | Icon | Below text row |
 * | `sublabelIcon` | Icon | Next to sublabel |
 * | `trailingIcon` | Icon | After trailing text |
 * | `progressIndicator` | ProgressIndicator (Spinner) | Trailing |
 * | `secondaryAction` | IconButton, TextLink | 2nd interaction area |
 * | `tertiaryAction` | IconButton | 3rd interaction area |
 *
 * ### Full-width content layout (when `fullWidthContent != null`)
 * | Slot | Expected hmisdk Widget | Position |
 * |---|---|---|
 * | `fullWidthContent` | Slider, SegmentedControl | Full-width below headline |
 *
 * ## Delete Mode
 * In Delete mode, the delete button is rendered **automatically** by the brand
 * implementation. The consumer provides the icon via [ListItemConfig.deleteIcon]
 * and the callback via [ListItemInteractionConfig.onDelete]. There is no need
 * to manually place `ListItemDeleteButton` in a slot.
 *
 * ## States
 * - **Idle** — default resting state.
 * - **Pressed** — user pressing the main interaction area.
 * - **Disabled** — non-interactive, dimmed via opacity.
 * - **Selected** — checked / highlighted (selected-branch colours).
 * - **Focused** — D-pad / rotary focus ring visible.
 * - **Dragging** — edit-mode drag elevation (shadow).
 *
 * ## Interaction Areas
 * - **Main** — encompasses label, supporting text, leading content → trailing icon.
 * - **Secondary** — after vertical divider (max 3 areas total).
 * - **Tertiary** — after second vertical divider.
 *
 * ## RTL / RHD
 * - RTL: layout is mirrored automatically via Compose's `LayoutDirection`.
 * - RHD: no adaptation required.
 *
 * ## Usage Example
 * ```kotlin
 * // Simple list item with toggle switch
 * var enabled by remember { mutableStateOf(false) }
 *
 * ListItem(
 *     content = ListItemContent(
 *         label = "Wi-Fi",
 *         supportingText = "Connected to HomeNetwork",
 *     ),
 *     slots = ListItemSlots(
 *         leadingControl = {
 *             ToggleSwitch(
 *                 interactionConfig = ToggleSwitchInteractionConfig(
 *                     selected = enabled,
 *                     onSelectedChange = { enabled = it },
 *                 ),
 *             )
 *         },
 *         secondaryAction = {
 *             IconButton(
 *                 config = IconButtonConfig(...),
 *                 interactionConfig = IconButtonInteractionConfig(
 *                     onClick = { showInfo() },
 *                 ),
 *             )
 *         },
 *     ),
 *     interactionConfig = ListItemInteractionConfig(
 *         onClick = { navigateToWifiSettings() },
 *     ),
 * )
 *
 * // Edit-mode list item with drag marker
 * ListItem(
 *     config = ListItemConfig(mode = ListItemConfig.Mode.Edit),
 *     state = ListItemState(isDragging = isDragged),
 *     content = ListItemContent(label = "Reorderable item"),
 *     slots = ListItemSlots(
 *         dragMarker = { DragHandle() },
 *     ),
 * )
 *
 * // Delete mode — delete button auto-rendered by brand implementation
 * ListItem(
 *     config = ListItemConfig(
 *         mode = ListItemConfig.Mode.Delete,
 *         deleteIcon = { Icon(Icons.Filled.Delete, "Delete", tint = Color.White) },
 *     ),
 *     content = ListItemContent(label = "Deletable item"),
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
 * @param config            Static variant configuration (Default / Edit / Delete).
 * @param modifier          Applied to the outermost layout node.
 * @param state             Runtime state flags (enabled, selected, focused, dragging).
 * @param content           Text content (label, supporting text, trailing text).
 * @param slots             Composable slots for hmisdk widget injection.
 * @param interactionConfig Click, selection, focus, and distraction optimization config.
 */
@Composable
fun ListItem(
    config: ListItemConfig = ListItemConfig(),
    modifier: Modifier = Modifier,
    state: ListItemState = ListItemState(),
    content: ListItemContent = ListItemContent(),
    slots: ListItemSlots = ListItemSlots.empty(),
    interactionConfig: ListItemInteractionConfig = ListItemInteractionConfig(),
) {
    LocalWidgets.ListItem.current(
        config,
        modifier,
        state,
        content,
        slots,
        interactionConfig,
    )
}
