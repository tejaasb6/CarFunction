package com.ui.core.widgets.selects

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Per-state colour set for a single select variant (Default or Error).
 *
 * The colour structure distinguishes between:
 * - **Field states**: Idle, Pressed, Active (dropdown open), Disabled, ReadOnly
 * - **Error mode**: Uses separate colour tokens when `SelectState.error == true`
 * - **Menu item states**: Selected/Unselected × Idle/Pressed/Disabled
 *
 * All colours are sourced from design tokens via `Cmp.Color.Forms.Formfields.*`
 * and `Cmp.Color.Forms.Formfields.Menu.Item.*`.
 *
 * @param fieldSurfaceFill background colour of the select field surface.
 * @param fieldStrokeIdle border colour when the field is idle (not focused, not interacting).
 * @param fieldStrokeActive border colour when the dropdown menu is open.
 * @param fieldStrokePressed border colour during press interaction.
 * @param fieldStrokeDisabled border colour when the select is disabled.
 * @param fieldStrokeReadOnly border colour when the select is read-only.
 * @param fieldStateLayerPressed overlay colour applied on press (blended over surface).
 * @param labelIdle colour of the label text when idle.
 * @param labelPressed colour of the label text when pressed.
 * @param labelDisabled colour of the label text when disabled.
 * @param appendixIdle colour of the appendix text when idle.
 * @param appendixPressed colour of the appendix text when pressed.
 * @param hintIdle colour of the hint text when idle.
 * @param hintPressed colour of the hint text when pressed.
 * @param placeholderIdle colour of the placeholder text when idle.
 * @param placeholderActive colour of the placeholder text when active (menu open).
 * @param placeholderPressed colour of the placeholder text when pressed.
 * @param placeholderDisabled colour of the placeholder text when disabled.
 * @param userInputIdle colour of the selected value text when idle.
 * @param userInputActive colour of the selected value text when active (menu open).
 * @param userInputPressed colour of the selected value text when pressed.
 * @param userInputDisabled colour of the selected value text when disabled.
 * @param userInputReadOnly colour of the selected value text when read-only.
 * @param iconIdle colour of field icons (leading/trailing) when idle.
 * @param iconActive colour of field icons when active (menu open).
 * @param iconPressed colour of field icons when pressed.
 * @param iconDisabled colour of field icons when disabled.
 * @param iconReadOnly colour of field icons when read-only.
 * @param menuItemSelectedSurfaceFill background colour of a selected menu item.
 * @param menuItemSelectedStrokeIdle border colour of a selected menu item (idle).
 * @param menuItemSelectedStrokePressed border colour of a selected menu item (pressed).
 * @param menuItemSelectedStrokeDisabled border colour of a selected menu item (disabled).
 * @param menuItemSelectedTextIdle text colour of a selected menu item (idle).
 * @param menuItemSelectedTextPressed text colour of a selected menu item (pressed).
 * @param menuItemSelectedTextDisabled text colour of a selected menu item (disabled).
 * @param menuItemSelectedIconIdle icon colour of a selected menu item (idle).
 * @param menuItemSelectedIconPressed icon colour of a selected menu item (pressed).
 * @param menuItemSelectedIconDisabled icon colour of a selected menu item (disabled).
 * @param menuItemSelectedStateLayerPressed overlay colour for selected item press.
 * @param menuItemUnselectedSurfaceFill background colour of an unselected menu item.
 * @param menuItemUnselectedStrokeIdle border colour of an unselected menu item (idle).
 * @param menuItemUnselectedStrokePressed border colour of an unselected menu item (pressed).
 * @param menuItemUnselectedStrokeDisabled border colour of an unselected menu item (disabled).
 * @param menuItemUnselectedTextIdle text colour of an unselected menu item (idle).
 * @param menuItemUnselectedTextPressed text colour of an unselected menu item (pressed).
 * @param menuItemUnselectedTextDisabled text colour of an unselected menu item (disabled).
 * @param menuItemUnselectedIconIdle icon colour of an unselected menu item (idle).
 * @param menuItemUnselectedIconPressed icon colour of an unselected menu item (pressed).
 * @param menuItemUnselectedIconDisabled icon colour of an unselected menu item (disabled).
 * @param menuItemUnselectedStateLayerPressed overlay colour for unselected item press.
 */
@Immutable
data class SelectTypeColors(
    // Field surface
    val fieldSurfaceFill: Color,
    // Field stroke (border) per state
    val fieldStrokeIdle: Color,
    val fieldStrokeActive: Color,
    val fieldStrokePressed: Color,
    val fieldStrokeDisabled: Color,
    val fieldStrokeReadOnly: Color,
    // Field state layer
    val fieldStateLayerPressed: Color,
    // Label colors
    val labelIdle: Color,
    val labelPressed: Color,
    val labelDisabled: Color,
    // Appendix colors
    val appendixIdle: Color,
    val appendixPressed: Color,
    // Hint colors
    val hintIdle: Color,
    val hintPressed: Color,
    // Placeholder colors
    val placeholderIdle: Color,
    val placeholderActive: Color,
    val placeholderPressed: Color,
    val placeholderDisabled: Color,
    // User input (selected value) colors
    val userInputIdle: Color,
    val userInputActive: Color,
    val userInputPressed: Color,
    val userInputDisabled: Color,
    val userInputReadOnly: Color,
    // Icon colors
    val iconIdle: Color,
    val iconActive: Color,
    val iconPressed: Color,
    val iconDisabled: Color,
    val iconReadOnly: Color,
    // Menu item - Selected
    val menuItemSelectedSurfaceFill: Color,
    val menuItemSelectedStrokeIdle: Color,
    val menuItemSelectedStrokePressed: Color,
    val menuItemSelectedStrokeDisabled: Color,
    val menuItemSelectedTextIdle: Color,
    val menuItemSelectedTextPressed: Color,
    val menuItemSelectedTextDisabled: Color,
    val menuItemSelectedIconIdle: Color,
    val menuItemSelectedIconPressed: Color,
    val menuItemSelectedIconDisabled: Color,
    val menuItemSelectedStateLayerPressed: Color,
    // Menu item - Unselected
    val menuItemUnselectedSurfaceFill: Color,
    val menuItemUnselectedStrokeIdle: Color,
    val menuItemUnselectedStrokePressed: Color,
    val menuItemUnselectedStrokeDisabled: Color,
    val menuItemUnselectedTextIdle: Color,
    val menuItemUnselectedTextPressed: Color,
    val menuItemUnselectedTextDisabled: Color,
    val menuItemUnselectedIconIdle: Color,
    val menuItemUnselectedIconPressed: Color,
    val menuItemUnselectedIconDisabled: Color,
    val menuItemUnselectedStateLayerPressed: Color,
)
