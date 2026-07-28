package com.ui.audi.widgets.listitems

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.listitems.ListItemBranchColors
import com.ui.core.widgets.listitems.ListItemStateColors
import com.ui.core.widgets.listitems.ListItemStyle
import com.ui.core.widgets.listitems.ListItemTypeColors

/**
 * Audi brand defaults for [com.ui.core.widgets.listitems.ListItem].
 *
 * Provides the factory method [style] that resolves all Cmp/Sem design tokens
 * into a fully populated [ListItemStyle] instance. Called by [AudiListItem] via
 * [com.ui.core.widgets.listitems.LocalListItemStyle].
 *
 * @see ListItemStyle
 * @see AudiListItem
 */
internal object ListItemDefaults {
    /**
     * Creates a fully resolved [ListItemStyle] from Audi Cmp design tokens.
     *
     * Reads dimensions, spacing, typography, and colour tokens at composition time
     * and assembles them into a single immutable style object. Must be called
     * within a Composable scope so that token resolution functions (`dimension()`,
     * `typography()`, `color()`, `opacity()`) can access the current theme.
     *
     * @return A complete [ListItemStyle] with all Audi brand token values.
     */
    @Composable
    fun style(): ListItemStyle =
        ListItemStyle(
            // ── Dimensions ──────────────────────────────────────────────────
            surfaceCornerRadius =
                Cmp.BorderRadius.Forms.ListItem.Surface.Default
                    .dimension()
                    .pxToDp(),
            minHeight =
                Cmp.Size.Forms.ListItem.MinHeight
                    .dimension()
                    .pxToDp(),
            buttonMinHeight =
                Cmp.Size.Forms.ListItem.Button.MinHeight
                    .dimension()
                    .pxToDp(),
            buttonWidth =
                Cmp.Size.Forms.ListItem.Button.Width
                    .dimension()
                    .pxToDp(),
            dragMarkerWrapperWidth =
                Cmp.Size.Forms.ListItem.DragMarkerWrapper.Width
                    .dimension()
                    .pxToDp(),
            leadingContentWrapperWidth =
                Cmp.Size.Forms.ListItem.LeadingContentWrapper.Width
                    .dimension()
                    .pxToDp(),
            // ── Spacing ─────────────────────────────────────────────────────
            contentSlotVerticalPadding =
                Cmp.Space.Forms.ListItem.Content.Slot.V_Padding
                    .dimension()
                    .pxToDp(),
            slotIconContainerGap =
                Cmp.Space.Forms.ListItem.Content.SlotIconContainer.Gap
                    .dimension()
                    .pxToDp(),
            slotIconContainerHorizontalPadding =
                Cmp.Space.Forms.ListItem.Content.SlotIconContainer.H_Padding
                    .dimension()
                    .pxToDp(),
            horizontalDividerHorizontalPadding =
                Cmp.Space.Forms.ListItem.HorizontalDivider.H_Padding
                    .dimension()
                    .pxToDp(),
            verticalDividerVerticalPadding =
                Cmp.Space.Forms.ListItem.VerticalDivider.V_Padding
                    .dimension()
                    .pxToDp(),
            // ── Typography ──────────────────────────────────────────────────
            labelTextStyle =
                Cmp.Typography.Forms.ListItem.Content.Label
                    .typography(),
            sublabelTextStyle =
                Cmp.Typography.Forms.ListItem.Content.Sublabel
                    .typography(),
            leadingIconLabelTextStyle =
                Cmp.Typography.Forms.ListItem.LeadingContent.IconLable.Label
                    .typography(),
            // ── Trailing text (SecondColumn) ─────────────────────────────────
            trailingTextAlignment = TextAlign.Start,
            // ── Colours ─────────────────────────────────────────────────────
            colors = colors(),
        )

    // ── Colour model ────────────────────────────────────────────────────────

    /**
     * Assembles the complete [ListItemTypeColors] model from Cmp colour and opacity tokens.
     *
     * Combines the [selectedBranch] and [unselectedBranch] colour sets with the
     * unselected opacity layer and disabled content opacity values.
     *
     * @return A fully populated [ListItemTypeColors] for both selection branches.
     */
    @Composable
    private fun colors(): ListItemTypeColors =
        ListItemTypeColors(
            selected = selectedBranch(),
            unselected = unselectedBranch(),
            opacityLayer =
                Cmp.Color.Forms.ListItem.Unselected.OpacityLayer
                    .color(),
            disabledContentOpacity =
                Cmp.Opacity.Forms.ListItem.Content.Disabled
                    .opacity(),
        )

    // ── Selected branch ─────────────────────────────────────────────────────

    /**
     * Builds the [ListItemBranchColors] for the **Selected** selection state.
     *
     * Resolves Idle, Pressed, Hover, and Disabled colour sets from
     * `Cmp.Color.Forms.ListItem.Selected.Content.*` and
     * `Cmp.Color.Forms.ListItem.Selected.Surface.*` tokens.
     *
     * @return [ListItemBranchColors] containing per-interaction-state colours for the selected branch.
     */
    @Composable
    private fun selectedBranch(): ListItemBranchColors =
        ListItemBranchColors(
            idle =
                ListItemStateColors(
                    labelColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Label.Idle
                            .color(),
                    sublabelColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Sublabel.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Icon.Idle
                            .color(),
                    sublabelIconColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.SublabelIcon.Idle
                            .color(),
                    surfaceFill =
                        Cmp.Color.Forms.ListItem.Selected.Surface.Fill.Default
                            .color(),
                    stateLayerColor =
                        androidx.compose.ui.graphics.Color.Transparent,
                ),
            pressed =
                ListItemStateColors(
                    labelColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Label.Pressed
                            .color(),
                    sublabelColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Sublabel.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Icon.Pressed
                            .color(),
                    sublabelIconColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.SublabelIcon.Pressed
                            .color(),
                    surfaceFill =
                        Cmp.Color.Forms.ListItem.Selected.Surface.Fill.Default
                            .color(),
                    stateLayerColor =
                        Cmp.Color.Forms.ListItem.Selected.Surface.StateLayer.Pressed
                            .color(),
                ),
            hover =
                ListItemStateColors(
                    labelColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Label.Hover
                            .color(),
                    sublabelColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Sublabel.Hover
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Icon.Hover
                            .color(),
                    sublabelIconColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.SublabelIcon.Hover
                            .color(),
                    surfaceFill =
                        Cmp.Color.Forms.ListItem.Selected.Surface.Fill.Default
                            .color(),
                    stateLayerColor =
                        Cmp.Color.Forms.ListItem.Selected.Surface.StateLayer.Hover
                            .color(),
                ),
            disabled =
                ListItemStateColors(
                    labelColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Label.Disabled
                            .color(),
                    sublabelColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Sublabel.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.Icon.Disabled
                            .color(),
                    sublabelIconColor =
                        Cmp.Color.Forms.ListItem.Selected.Content.SublabelIcon.Disabled
                            .color(),
                    surfaceFill =
                        Cmp.Color.Forms.ListItem.Selected.Surface.Fill.Default
                            .color(),
                    stateLayerColor =
                        androidx.compose.ui.graphics.Color.Transparent,
                ),
        )

    // ── Unselected branch ───────────────────────────────────────────────────

    /**
     * Builds the [ListItemBranchColors] for the **Unselected** selection state.
     *
     * Resolves Idle, Pressed, Hover, and Disabled colour sets from
     * `Cmp.Color.Forms.ListItem.Unselected.Content.*` and
     * `Cmp.Color.Forms.ListItem.Unselected.Surface.*` tokens.
     *
     * @return [ListItemBranchColors] containing per-interaction-state colours for the unselected branch.
     */
    @Composable
    private fun unselectedBranch(): ListItemBranchColors =
        ListItemBranchColors(
            idle =
                ListItemStateColors(
                    labelColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Label.Idle
                            .color(),
                    sublabelColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Sublabel.Idle
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Icon.Idle
                            .color(),
                    sublabelIconColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.SublabelIcon.Idle
                            .color(),
                    surfaceFill =
                        Cmp.Color.Forms.ListItem.Unselected.Surface.Fill.Default
                            .color(),
                    stateLayerColor =
                        androidx.compose.ui.graphics.Color.Transparent,
                ),
            pressed =
                ListItemStateColors(
                    labelColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Label.Pressed
                            .color(),
                    sublabelColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Sublabel.Pressed
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Icon.Pressed
                            .color(),
                    sublabelIconColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.SublabelIcon.Pressed
                            .color(),
                    surfaceFill =
                        Cmp.Color.Forms.ListItem.Unselected.Surface.Fill.Default
                            .color(),
                    stateLayerColor =
                        Cmp.Color.Forms.ListItem.Unselected.Surface.StateLayer.Pressed
                            .color(),
                ),
            hover =
                ListItemStateColors(
                    labelColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Label.Hover
                            .color(),
                    sublabelColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Sublabel.Hover
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Icon.Hover
                            .color(),
                    sublabelIconColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.SublabelIcon.Hover
                            .color(),
                    surfaceFill =
                        Cmp.Color.Forms.ListItem.Unselected.Surface.Fill.Default
                            .color(),
                    stateLayerColor =
                        Cmp.Color.Forms.ListItem.Unselected.Surface.StateLayer.Hover
                            .color(),
                ),
            disabled =
                ListItemStateColors(
                    labelColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Label.Disabled
                            .color(),
                    sublabelColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Sublabel.Disabled
                            .color(),
                    iconColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.Icon.Disabled
                            .color(),
                    sublabelIconColor =
                        Cmp.Color.Forms.ListItem.Unselected.Content.SublabelIcon.Disabled
                            .color(),
                    surfaceFill =
                        Cmp.Color.Forms.ListItem.Unselected.Surface.Fill.Default
                            .color(),
                    stateLayerColor =
                        androidx.compose.ui.graphics.Color.Transparent,
                ),
        )
}
