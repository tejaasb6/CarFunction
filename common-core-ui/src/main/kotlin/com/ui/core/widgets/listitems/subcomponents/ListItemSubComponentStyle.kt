@file:Suppress("ktlint:standard:no-consecutive-comments")

package com.ui.core.widgets.listitems.subcomponents

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Visual specification for all ListItem sub-components.
 *
 * Holds token-resolved dimensions, colours, radii, and typography for
 * Avatar, Cover, Image, IconLabel, DragMarker, and DeleteButton.
 *
 * All values are resolved from Cmp/Sem design tokens in the brand
 * implementation (e.g. `AudiListItemSubComponentDefaults`).
 * Override at any level via [LocalListItemSubComponentStyle].
 *
 * ## Token Mapping Reference
 *
 * ### Avatar
 * | Property | Cmp Token |
 * |---|---|
 * | [avatarSizeXS] | `Cmp.Size.Navigation.Avatar.XS.VisualSize` |
 * | [avatarSizeS] | `Cmp.Size.Navigation.Avatar.SM.VisualSize` |
 * | [avatarSizeMD] | `Cmp.Size.Navigation.Avatar.MD.VisualSize` |
 * | [avatarCornerRadius] | `Cmp.BorderRadius.Navigation.Avatar.Default` |
 * | [avatarFillColor] | `Cmp.Color.Navigation.Avatar.Default.Unselected.Surface.Fill` |
 * | [avatarLabelTextStyleXS] | `Cmp.Typography.Navigation.Avatar.XS.Content.Label` |
 * | [avatarLabelTextStyleS] | `Cmp.Typography.Navigation.Avatar.SM.Content.Label` |
 * | [avatarLabelTextStyleMD] | `Cmp.Typography.Navigation.Avatar.MD.Content.Label` |
 *
 * ### Cover (MediaCover)
 * | Property | Cmp Token |
 * |---|---|
 * | [coverSize] | `Cmp.Size.Global.MediaCover.XS.All` |
 * | [coverCornerRadius] | `Cmp.BorderRadius.Global.MediaCover.Surface` |
 * | [coverFillColor] | `Cmp.Color.Global.MediaCover.Surface.Fill` |
 * | [coverStrokeColor] | `Cmp.Color.Global.MediaCover.Surface.Stroke` |
 * | [coverStrokeWidth] | `Cmp.BorderWidth.Global.MediaCover.Surface` |
 * | [coverIconColor] | `Cmp.Color.Global.MediaCover.Content.Icon` |
 * | [coverTitleColor] | `Cmp.Color.Global.MediaCover.Content.Title` |
 * | [coverTitleTextStyle] | `Cmp.Typography.Global.MediaCover.XS.Title` |
 *
 * ### IconLabel
 * | Property | Cmp Token |
 * |---|---|
 * | [iconLabelGap] | `Cmp.Space.Forms.ListItem.Content.SlotIconContainer.Gap` |
 * | [iconLabelTextStyle] | `Cmp.Typography.Forms.ListItem.LeadingContent.IconLable.Label` |
 * | [iconLabelColor] | Derived from `ListItemStateColors.iconColor` at render time |
 * | [iconLabelTextColor] | Derived from `ListItemStateColors.labelColor` at render time |
 *
 * ### Image
 * | Property | Cmp Token |
 * |---|---|
 * | [imageCornerRadius] | `Cmp.BorderRadius.Global.MediaCover.Surface` (reuse cover) |
 *
 * ### DeleteButton
 * | Property | Cmp Token / Sem Token |
 * |---|---|
 * | [deleteButtonFillColor] | `Sem.Color.Fill.Signal.Critical` |
 * | [deleteButtonCornerRadius] | `Cmp.BorderRadius.Forms.ListItem.Button.BorderRadius` |
 * | [deleteButtonMinWidth] | `Cmp.Size.Forms.ListItem.Button.Width` |
 * | [deleteButtonMinHeight] | `Cmp.Size.Forms.ListItem.Button.MinHeight` |
 *
 * ### DragMarker
 * | Property | Cmp Token |
 * |---|---|
 * | [dragMarkerWidth] | `Cmp.Size.Forms.ListItem.DragMarkerWrapper.Width` |
 *
 * @property avatarSizeXS Extra-small avatar visual size (`Cmp.Size.Navigation.Avatar.XS.VisualSize`).
 * @property avatarSizeS Small avatar visual size (`Cmp.Size.Navigation.Avatar.SM.VisualSize`).
 * @property avatarSizeMD Medium avatar visual size (`Cmp.Size.Navigation.Avatar.MD.VisualSize`).
 * @property avatarCornerRadius Corner radius for the avatar container (`Cmp.BorderRadius.Navigation.Avatar.Default`).
 * @property avatarFillColor Default fill colour for the avatar background when initials are shown.
 * @property avatarLabelTextStyleXS Typography for the initials label at extra-small size.
 * @property avatarLabelTextStyleS Typography for the initials label at small size.
 * @property avatarLabelTextStyleMD Typography for the initials label at medium size.
 * @property coverSize Overall size (width & height) of the cover surface.
 * @property coverCornerRadius Corner radius applied to the cover surface.
 * @property coverFillColor Fill colour of the cover surface background.
 * @property coverStrokeColor Stroke (border) colour of the cover surface.
 * @property coverStrokeWidth Stroke (border) width of the cover surface.
 * @property coverIconColor Tint colour for the icon rendered on the cover.
 * @property coverTitleColor Text colour for the title rendered on the cover.
 * @property coverTitleTextStyle Typography for the cover title.
 * @property iconLabelGap Vertical gap between the icon and label in [ListItemIconLabel].
 * @property iconLabelTextStyle Typography for the label below the icon in [ListItemIconLabel].
 * @property imageCornerRadius Corner radius applied to the image container in [ListItemImage].
 * @property deleteButtonFillColor Fill colour for the delete button background (`Sem.Color.Fill.Signal.Critical`).
 * @property deleteButtonCornerRadius Corner radius for the delete button surface.
 * @property deleteButtonMinWidth Minimum width of the delete button.
 * @property deleteButtonMinHeight Minimum height of the delete button.
 * @property dragMarkerWidth Width of the drag-marker wrapper column in [ListItemDragMarker].
 * @see ListItemAvatar
 * @see ListItemCover
 * @see ListItemImage
 * @see ListItemIconLabel
 * @see ListItemDeleteButton
 * @see ListItemDragMarker
 * @see LocalListItemSubComponentStyle
 */
@Immutable
data class ListItemSubComponentStyle(
    // ── Avatar ──────────────────────────────────────────────────────────────
    /** Extra-small avatar visual size — `Cmp.Size.Navigation.Avatar.XS.VisualSize`. */
    val avatarSizeXS: Dp,
    /** Small avatar visual size — `Cmp.Size.Navigation.Avatar.SM.VisualSize`. */
    val avatarSizeS: Dp,
    /** Medium avatar visual size — `Cmp.Size.Navigation.Avatar.MD.VisualSize`. */
    val avatarSizeMD: Dp,
    /** Corner radius for the avatar container — `Cmp.BorderRadius.Navigation.Avatar.Default`. */
    val avatarCornerRadius: Dp,
    /** Default fill colour for the avatar background when initials are displayed. */
    val avatarFillColor: Color,
    /** Typography for the initials label at extra-small avatar size. */
    val avatarLabelTextStyleXS: TextStyle,
    /** Typography for the initials label at small avatar size. */
    val avatarLabelTextStyleS: TextStyle,
    /** Typography for the initials label at medium avatar size. */
    val avatarLabelTextStyleMD: TextStyle,
    // ── Cover (MediaCover) ──────────────────────────────────────────────────
    /** Overall size (width & height) of the cover surface — `Cmp.Size.Global.MediaCover.XS.All`. */
    val coverSize: Dp,
    /** Corner radius applied to the cover surface — `Cmp.BorderRadius.Global.MediaCover.Surface`. */
    val coverCornerRadius: Dp,
    /** Fill colour of the cover surface background — `Cmp.Color.Global.MediaCover.Surface.Fill`. */
    val coverFillColor: Color,
    /** Stroke (border) colour of the cover surface — `Cmp.Color.Global.MediaCover.Surface.Stroke`. */
    val coverStrokeColor: Color,
    /** Stroke (border) width of the cover surface — `Cmp.BorderWidth.Global.MediaCover.Surface`. */
    val coverStrokeWidth: Dp,
    /** Tint colour for the icon rendered on the cover — `Cmp.Color.Global.MediaCover.Content.Icon`. */
    val coverIconColor: Color,
    /** Text colour for the title rendered on the cover — `Cmp.Color.Global.MediaCover.Content.Title`. */
    val coverTitleColor: Color,
    /** Typography for the cover title — `Cmp.Typography.Global.MediaCover.XS.Title`. */
    val coverTitleTextStyle: TextStyle,
    // ── IconLabel ────────────────────────────────────────────────────────────
    /** Vertical gap between the icon and label — `Cmp.Space.Forms.ListItem.Content.SlotIconContainer.Gap`. */
    val iconLabelGap: Dp,
    /** Typography for the label below the icon. */
    val iconLabelTextStyle: TextStyle,
    // ── Image ───────────────────────────────────────────────────────────────
    /** Corner radius applied to the image container — reuses `Cmp.BorderRadius.Global.MediaCover.Surface`. */
    val imageCornerRadius: Dp,
    // ── DeleteButton ─────────────────────────────────────────────────────────
    /** Fill colour for the delete button background — `Sem.Color.Fill.Signal.Critical`. */
    val deleteButtonFillColor: Color,
    /** Corner radius for the delete button surface — `Cmp.BorderRadius.Forms.ListItem.Button.BorderRadius`. */
    val deleteButtonCornerRadius: Dp,
    /** Minimum width of the delete button — `Cmp.Size.Forms.ListItem.Button.Width`. */
    val deleteButtonMinWidth: Dp,
    /** Minimum height of the delete button — `Cmp.Size.Forms.ListItem.Button.MinHeight`. */
    val deleteButtonMinHeight: Dp,
    // ── DragMarker ──────────────────────────────────────────────────────────
    /** Width of the drag-marker wrapper column — `Cmp.Size.Forms.ListItem.DragMarkerWrapper.Width`. */
    val dragMarkerWidth: Dp,
)

/**
 * Composition local for [ListItemSubComponentStyle].
 *
 * Provided by brand themes (e.g. `AudiTheme`).
 * Throws if accessed outside a theme scope.
 */
val LocalListItemSubComponentStyle =
    compositionLocalOf<ListItemSubComponentStyle> {
        error("No ListItemSubComponentStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
