package com.ui.core.widgets.listitems

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp

/**
 * Full visual specification for [ListItem].
 *
 * Holds dimensions, spacing, typography, and per-selection-branch colour sets.
 * All values are resolved from Cmp/Sem design tokens in the brand implementation.
 * Override at any level of the composition tree via [LocalListItemStyle].
 *
 * ## Token Mapping Reference
 *
 * | Property | Cmp Token |
 * |---|---|
 * | [surfaceCornerRadius] | `Cmp.BorderRadius.Forms.ListItem.Surface.Default` |
 * | [minHeight] | `Cmp.Size.Forms.ListItem.MinHeight` |
 * | [buttonMinHeight] | `Cmp.Size.Forms.ListItem.Button.MinHeight` |
 * | [buttonWidth] | `Cmp.Size.Forms.ListItem.Button.Width` |
 * | [dragMarkerWrapperWidth] | `Cmp.Size.Forms.ListItem.DragMarkerWrapper.Width` |
 * | [leadingContentWrapperWidth] | `Cmp.Size.Forms.ListItem.LeadingContentWrapper.Width` |
 * | [contentSlotVerticalPadding] | `Cmp.Space.Forms.ListItem.Content.Slot.V-Padding` |
 * | [slotIconContainerGap] | `Cmp.Space.Forms.ListItem.Content.SlotIconContainer.Gap` |
 * | [slotIconContainerHorizontalPadding] | `Cmp.Space.Forms.ListItem.Content.SlotIconContainer.H-Padding` |
 * | [horizontalDividerHorizontalPadding] | `Cmp.Space.Forms.ListItem.HorizontalDivider.H-Padding` |
 * | [verticalDividerVerticalPadding] | `Cmp.Space.Forms.ListItem.VerticalDivider.V-Padding` |
 * | [labelTextStyle] | `Cmp.Typography.Forms.ListItem.Content.Label` |
 * | [sublabelTextStyle] | `Cmp.Typography.Forms.ListItem.Content.Sublabel` |
 * | [leadingIconLabelTextStyle] | `Cmp.Typography.Forms.ListItem.LeadingContent.IconLable.Label` |
 * | [trailingTextAlignment] | Horizontal alignment of trailing text — `TextAlign.Start` or `TextAlign.End` |
 *
 * ```kotlin
 * CompositionLocalProvider(LocalListItemStyle provides customStyle) {
 *     ListItem(content = ListItemContent(label = "Custom styled"))
 * }
 * ```
 *
 * @param surfaceCornerRadius             Corner radius of the list item container surface.
 * @param minHeight                       Minimum height of the list item container.
 * @param buttonMinHeight                 Minimum height for interaction-area buttons.
 * @param buttonWidth                     Width for interaction-area buttons.
 * @param dragMarkerWrapperWidth          Width allocated for the drag-marker wrapper (Edit variant).
 * @param leadingContentWrapperWidth      Width allocated for leading content (Cover/Avatar/Control).
 * @param contentSlotVerticalPadding      Vertical padding inside the content slot.
 * @param slotIconContainerGap            Gap between elements in the slot icon container.
 * @param slotIconContainerHorizontalPadding Horizontal padding of the slot icon container.
 * @param horizontalDividerHorizontalPadding Horizontal padding for the horizontal divider.
 * @param verticalDividerVerticalPadding  Vertical padding for the vertical divider separator.
 * @param labelTextStyle                  Typography for the primary label text.
 * @param sublabelTextStyle               Typography for the supporting / sublabel text.
 * @param leadingIconLabelTextStyle       Typography for the icon-label text in leading content.
 * @param trailingTextAlignment          Horizontal alignment of trailing text (`TextAlign.Start` or `TextAlign.End`).
 *                                        Consumer can override via `LocalListItemStyle` to change alignment.
 * @param colors                          Complete colour model (Selected / Unselected branches).
 */
@Immutable
data class ListItemStyle(
    // ── Dimensions ──────────────────────────────────────────────────────────
    val surfaceCornerRadius: Dp,
    val minHeight: Dp,
    val buttonMinHeight: Dp,
    val buttonWidth: Dp,
    val dragMarkerWrapperWidth: Dp,
    val leadingContentWrapperWidth: Dp,
    // ── Spacing ─────────────────────────────────────────────────────────────
    val contentSlotVerticalPadding: Dp,
    val slotIconContainerGap: Dp,
    val slotIconContainerHorizontalPadding: Dp,
    val horizontalDividerHorizontalPadding: Dp,
    val verticalDividerVerticalPadding: Dp,
    // ── Typography ──────────────────────────────────────────────────────────
    val labelTextStyle: TextStyle,
    val sublabelTextStyle: TextStyle,
    val leadingIconLabelTextStyle: TextStyle,
    // ── Trailing text (SecondColumn) ─────────────────────────────────────────
    val trailingTextAlignment: TextAlign,
    // ── Colours ─────────────────────────────────────────────────────────────
    val colors: ListItemTypeColors,
)

/**
 * Composition local for [ListItemStyle].
 *
 * Provided by brand themes (e.g. `AudiTheme`, `LamborghiniTheme`).
 * Throws if accessed outside a theme scope.
 *
 * ```kotlin
 * val style = LocalListItemStyle.current
 * ```
 */
val LocalListItemStyle =
    compositionLocalOf<ListItemStyle> {
        error("No ListItemStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
