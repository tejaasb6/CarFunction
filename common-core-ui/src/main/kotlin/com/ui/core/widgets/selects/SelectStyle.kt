package com.ui.core.widgets.selects

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.ui.core.engine.api.BoxShadowData

/**
 * Brand-specific styling values for the [Select] widget.
 *
 * This data class bundles all visual parameters resolved from design tokens:
 * typography, sizing, spacing, border radii, strokes, shadows, and per-state colours.
 *
 * Brand themes (Audi, Lamborghini) provide instances via `SelectDefaults.style()`
 * and publish them through [LocalSelectStyle].
 *
 * @param labelTextStyle typography style for the label text.
 * @param captionTextStyle typography style for caption text (appendix, hint, and error caption).
 * @param placeholderTextStyle typography style for the placeholder text.
 * @param userInputTextStyle typography style for the selected value text and menu item text.
 * @param fieldHeight visual height of the select field surface.
 * @param fieldBorderRadius corner radius of the field surface.
 * @param fieldBorderWidthIdle stroke width when idle.
 * @param fieldBorderWidthActive stroke width when the dropdown is open.
 * @param fieldBorderWidthPressed stroke width during press.
 * @param fieldBorderWidthDisabled stroke width when disabled.
 * @param fieldHorizontalPadding horizontal padding inside the field surface.
 * @param fieldVerticalPadding vertical padding inside the field surface.
 * @param labelBottomSpacing spacing between the label and the field surface.
 * @param hintTopSpacing spacing between the field surface and the hint text.
 * @param iconSpacing spacing between icons and text within the field.
 * @param disabledOpacity opacity applied to the widget when disabled.
 * @param focusRingColor colour of the focus ring around the field.
 * @param focusRingWidth stroke width of the focus ring.
 * @param menuHorizontalPadding horizontal padding inside the dropdown menu surface.
 * @param menuVerticalPadding vertical padding inside the dropdown menu surface.
 * @param menuSurfaceFill background colour of the dropdown menu surface.
 * @param menuSurfaceStroke border colour of the dropdown menu surface.
 * @param menuBorderRadius corner radius of the dropdown menu surface.
 * @param menuBorderWidth stroke width of the dropdown menu border.
 * @param menuShadow box shadow data applied to the dropdown menu.
 * @param menuItemVisualHeight visual height of each menu item.
 * @param menuItemHeight touch target height of each menu item.
 * @param menuItemBorderRadius corner radius of menu items.
 * @param menuItemBorderWidth stroke width of menu item borders.
 * @param menuItemHorizontalPadding horizontal padding inside menu items.
 * @param menuMaxVisibleItems maximum number of items visible before scrolling.
 * @param menuItemSpacing vertical spacing between menu items.
 * @param captionErrorTopSpacing top spacing between the hint text and the error caption row.
 * @param captionErrorGap horizontal gap between the triangle icon and the error caption text.
 * @param errorTriangleHeight height of the critical semantic triangle icon.
 * @param errorTriangleWidth width of the critical semantic triangle icon.
 * @param errorTriangleBorderWidth stroke width of the triangle icon border.
 * @param errorTriangleFillColor fill colour of the critical semantic triangle icon.
 * @param errorTriangleStrokeColor stroke colour of the critical semantic triangle icon.
 * @param defaultColors colour set used when `SelectState.error == false`.
 * @param errorColors colour set used when `SelectState.error == true`.
 */
@Immutable
data class SelectStyle(
    // Typography
    val labelTextStyle: TextStyle,
    val captionTextStyle: TextStyle,
    val placeholderTextStyle: TextStyle,
    val userInputTextStyle: TextStyle,
    // Field sizing
    val fieldHeight: Dp,
    val fieldBorderRadius: Shape,
    val fieldBorderWidthIdle: Dp,
    val fieldBorderWidthActive: Dp,
    val fieldBorderWidthPressed: Dp,
    val fieldBorderWidthDisabled: Dp,
    // Field spacing
    val fieldHorizontalPadding: Dp,
    val fieldVerticalPadding: Dp,
    val labelBottomSpacing: Dp,
    val hintTopSpacing: Dp,
    val iconSpacing: Dp,
    // Disabled & focus
    val disabledOpacity: Float,
    val focusRingColor: Color,
    val focusRingWidth: Dp,
    // Error caption spacing & triangle sizing
    val captionErrorTopSpacing: Dp,
    val captionErrorGap: Dp,
    // ToDo Semanticshape will be replaced by audi semanticshape after its implementation.
    val errorTriangleHeight: Dp,
    val errorTriangleWidth: Dp,
    val errorTriangleBorderWidth: Dp,
    val errorTriangleFillColor: Color,
    val errorTriangleStrokeColor: Color,
    // Menu styling
    val menuHorizontalPadding: Dp,
    val menuVerticalPadding: Dp,
    val menuSurfaceFill: Color,
    val menuSurfaceStroke: Color,
    val menuBorderRadius: Shape,
    val menuBorderWidth: Dp,
    val menuShadow: BoxShadowData,
    val menuItemVisualHeight: Dp,
    val menuItemHeight: Dp,
    val menuItemBorderRadius: Shape,
    val menuItemBorderWidth: Dp,
    val menuItemHorizontalPadding: Dp,
    val menuMaxVisibleItems: Int,
    val menuItemSpacing: Dp,
    // Colors
    val defaultColors: SelectTypeColors,
    val errorColors: SelectTypeColors,
)

/**
 * CompositionLocal providing the active [SelectStyle] down the composition tree.
 *
 * Brand themes (`AudiTheme`, `LamborghiniTheme`) set this value. The public [Select]
 * widget reads it to apply brand-specific styling.
 */
val LocalSelectStyle =
    staticCompositionLocalOf<SelectStyle> {
        error("No SelectStyle provided. Wrap your composable tree in AudiTheme or LamborghiniTheme.")
    }
