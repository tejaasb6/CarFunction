package com.ui.audi.widgets.listitems

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.listitems.subcomponents.ListItemSubComponentStyle

/** Audi brand token resolution for ListItem sub-component styles. */
internal object ListItemSubComponentDefaults {
    @Composable
    fun style(): ListItemSubComponentStyle =
        ListItemSubComponentStyle(
            // ── Avatar ──────────────────────────────────────────────────
            avatarSizeXS =
                Cmp.Size.Navigation.Avatar.XS.VisualSize
                    .dimension()
                    .pxToDp(),
            avatarSizeS =
                Cmp.Size.Navigation.Avatar.SM.VisualSize
                    .dimension()
                    .pxToDp(),
            avatarSizeMD =
                Cmp.Size.Navigation.Avatar.MD.VisualSize
                    .dimension()
                    .pxToDp(),
            avatarCornerRadius =
                Cmp.BorderRadius.Navigation.Avatar.Default
                    .dimension()
                    .pxToDp(),
            avatarFillColor =
                Cmp.Color.Navigation.Avatar.Default.Unselected.Surface.Fill
                    .color(),
            avatarLabelTextStyleXS =
                Cmp.Typography.Navigation.Avatar.XS.Content.Label
                    .typography(),
            avatarLabelTextStyleS =
                Cmp.Typography.Navigation.Avatar.SM.Content.Label
                    .typography(),
            avatarLabelTextStyleMD =
                Cmp.Typography.Navigation.Avatar.MD.Content.Label
                    .typography(),
            // ── Cover (MediaCover) ──────────────────────────────────────
            coverSize =
                Cmp.Size.Global.MediaCover.XS.All
                    .dimension()
                    .pxToDp(),
            coverCornerRadius =
                Cmp.BorderRadius.Global.MediaCover.Surface
                    .dimension()
                    .pxToDp(),
            coverFillColor =
                Cmp.Color.Global.MediaCover.Surface.Fill
                    .color(),
            coverStrokeColor =
                Cmp.Color.Global.MediaCover.Surface.Stroke
                    .color(),
            coverStrokeWidth =
                Cmp.BorderWidth.Global.MediaCover.Surface
                    .dimension()
                    .pxToDp(),
            coverIconColor =
                Cmp.Color.Global.MediaCover.Content.Icon
                    .color(),
            coverTitleColor =
                Cmp.Color.Global.MediaCover.Content.Title
                    .color(),
            coverTitleTextStyle =
                Cmp.Typography.Global.MediaCover.XS.Title
                    .typography(),
            // ── IconLabel ───────────────────────────────────────────────
            iconLabelGap =
                Sem.Space.Fixed._100
                    .dimension()
                    .pxToDp(),
            iconLabelTextStyle =
                Cmp.Typography.Forms.ListItem.LeadingContent.IconLable.Label
                    .typography(),
            // ── Image ──────────────────────────────────────────────────
            imageCornerRadius =
                Cmp.BorderRadius.Global.MediaCover.Surface
                    .dimension()
                    .pxToDp(),
            // ── DeleteButton ───────────────────────────────────────────
            deleteButtonFillColor =
                Sem.Color.Fill.Signal.Critical
                    .color(),
            deleteButtonCornerRadius =
                Cmp.BorderRadius.Forms.ListItem.Button.BorderRadius
                    .dimension()
                    .pxToDp(),
            deleteButtonMinWidth =
                Cmp.Size.Forms.ListItem.Button.Width
                    .dimension()
                    .pxToDp(),
            deleteButtonMinHeight =
                Cmp.Size.Forms.ListItem.Button.MinHeight
                    .dimension()
                    .pxToDp(),
            // ── DragMarker ─────────────────────────────────────────────
            dragMarkerWidth =
                Cmp.Size.Forms.ListItem.DragMarkerWrapper.Width
                    .dimension()
                    .pxToDp(),
        )
}
