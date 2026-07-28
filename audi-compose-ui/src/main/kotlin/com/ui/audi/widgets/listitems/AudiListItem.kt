package com.ui.audi.widgets.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.focus.focusableWithRing
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.dividers.Divider
import com.ui.core.widgets.dividers.DividerConfig
import com.ui.core.widgets.listitems.ListItemBranchColors
import com.ui.core.widgets.listitems.ListItemConfig
import com.ui.core.widgets.listitems.ListItemContent
import com.ui.core.widgets.listitems.ListItemInteractionConfig
import com.ui.core.widgets.listitems.ListItemSlots
import com.ui.core.widgets.listitems.ListItemState
import com.ui.core.widgets.listitems.ListItemStateColors
import com.ui.core.widgets.listitems.ListItemStyle
import com.ui.core.widgets.listitems.LocalListItemStyle
import com.ui.core.widgets.listitems.subcomponents.ListItemDeleteButton
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState

// region — AudiListItem

/**
 * Audi brand implementation of [com.ui.core.widgets.listitems.ListItem].
 *
 * **Internal** — app code must not call this directly.
 * Use [com.ui.core.widgets.listitems.ListItem] instead.
 *
 * ## Modes (from Figma `Mode` axis)
 * | Mode | Structural additions | Supported states |
 * |------|---------------------|------------------|
 * | **Default** | Standard multi-slot layout | Idle, Pressed, Disabled, Focused |
 * | **Edit** | Drag-marker handle at leading edge | Idle, Pressed, Disabled, Focused, Drag |
 * | **Delete** | Trailing delete button after tertiary | Idle, Disabled |
 *
 * ## States (from Figma `State` axis)
 * - **Idle** — default resting state.
 * - **Pressed** — pressed colour set + state-layer overlay (Default & Edit only).
 * - **Disabled** — muted via opacity layer, non-interactive.
 * - **Focused** — D-pad / rotary focus ring visible.
 * - **Drag** — opacity layer underneath + elevation shadow (Edit only).
 *
 * ## Interaction stack (applied in order)
 * 1. [focusableWithRing] — D-pad / rotary focus ring.
 * 2. [interactiveClickable] — tap handling with debounce.
 *
 * ## UX restrictions
 * When [ListItemInteractionConfig.isDistractionOptimized] is `false` and the car is
 * moving, the list item is automatically disabled.
 *
 * @param config            Static variant configuration (Default / Edit / Delete).
 * @param modifier          [Modifier] applied to the outermost container.
 * @param state             Runtime state flags (enabled, selected, focused, dragging).
 * @param content           Text content (label, supporting text, trailing text).
 * @param slots             Composable slots for hmisdk widget injection.
 * @param interactionConfig Click, selection, focus, and distraction optimization config.
 * @see com.ui.core.widgets.listitems.ListItem
 * @see ListItemDefaults.style
 */
@Composable
internal fun AudiListItem(
    config: ListItemConfig = ListItemConfig(),
    modifier: Modifier = Modifier,
    state: ListItemState = ListItemState(),
    content: ListItemContent = ListItemContent(),
    slots: ListItemSlots = ListItemSlots.empty(),
    interactionConfig: ListItemInteractionConfig = ListItemInteractionConfig(),
) {
    val style = LocalListItemStyle.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // ── UX Restrictions ───────────────────────────────────────────────────────
    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled =
        state.enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    // ── Pressed only valid in Default & Edit modes ────────────────────────────
    val effectivePressed =
        isPressed &&
            config.mode != ListItemConfig.Mode.Delete

    // ── Resolve colours ───────────────────────────────────────────────────────
    val branch = if (state.isSelected) style.colors.selected else style.colors.unselected
    val stateColors = resolveStateColors(branch, effectiveEnabled, effectivePressed)
    val disabledAlpha = if (effectiveEnabled) 1f else style.colors.disabledContentOpacity

    val surfaceShape = RoundedCornerShape(style.surfaceCornerRadius)

    val clickOptions =
        ClickOptions(
            onClick = interactionConfig.onClick,
            onLongClick = interactionConfig.onLongClick,
            onDoubleClick = interactionConfig.onDoubleClick,
            debounceMs = interactionConfig.clickDebounceMs,
        )

    // ── Drag state (Edit mode only): shadow + opacity layer ───────────────────
    val isDragActive = state.isDragging && config.mode == ListItemConfig.Mode.Edit
    val dragShadow =
        Cmp.Shadow.Forms.ListItem.Surface.Drag
            .boxShadow()
    val dragModifier =
        if (isDragActive) {
            Modifier.shadow(elevation = dragShadow.elevation.pxToDp(), shape = surfaceShape)
        } else {
            Modifier
        }

    // ── Outer container ───────────────────────────────────────────────────────
    //
    // The surface modifier chain (clip → background → focusableWithRing →
    // interactiveClickable) MUST be built inline for each branch — never
    // stored in a `val` — because `focusableWithRing` and `interactiveClickable`
    // use `Modifier.composed { }` which creates per-layout-node state.
    // Sharing a single composed-modifier instance across branches breaks
    // focus-ring rendering and press-state tracking.

    // ── Delete mode flag ─────────────────────────────────────────────────
    // In Delete mode the delete button is rendered **outside** the ListItem
    // surface. There is NO gap between the surface and the delete button;
    // the visual separation comes from the horizontal divider's own
    // H-Padding (`Cmp.Space.Forms.ListItem.HorizontalDivider.H-Padding`)
    // which does not extend under the delete button.
    val isDeleteMode = config.mode == ListItemConfig.Mode.Delete

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ── Left side: surface + bottom divider ──────────────────────────
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .then(dragModifier)
                        .defaultMinSize(minHeight = style.minHeight)
                        .clip(surfaceShape)
                        .background(stateColors.surfaceFill, surfaceShape)
                        .focusableWithRing(
                            interactionSource = interactionSource,
                            shape = surfaceShape,
                            ringColor =
                                Sem.Color.Stroke.Signal.Focus
                                    .color(),
                            ringWidth =
                                Sem.BorderWidth.FocusRing
                                    .dimension()
                                    .pxToDp(),
                            focusRequester = interactionConfig.focusRequester,
                        ).interactiveClickable(
                            clickOptions = clickOptions,
                            interactionSource = interactionSource,
                            enabled = effectiveEnabled,
                            indication = null,
                        ),
            ) {
                SurfaceContent(
                    config = config,
                    style = style,
                    content = content,
                    slots = slots,
                    stateColors = stateColors,
                    disabledAlpha = disabledAlpha,
                    isDragActive = isDragActive,
                    surfaceShape = surfaceShape,
                )
            }

            // ── Bottom horizontal divider (DividerWrapper from Figma) ─────
            // Present on all non-drag variants. Hidden during drag state.
            // Lives inside the surface column so it never extends under
            // the delete button in Delete mode.
            if (config.showBottomDivider && !isDragActive) {
                Divider(
                    config =
                        DividerConfig(
                            orientation = DividerConfig.Orientation.Horizontal,
                            padding = style.horizontalDividerHorizontalPadding,
                        ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        // ── Delete button — rendered OUTSIDE the surface ──────────────
        if (isDeleteMode) {
            val icon = config.deleteIcon
            val delete = interactionConfig.onDelete
            if (icon != null && delete != null) {
                ListItemDeleteButton(
                    onClick = delete,
                    icon = icon,
                )
            }
        }
    }
}

// ── Surface content (overlay layers + content layout) ──────────────────────────

/**
 * Renders the overlay layers (drag opacity, state-layer) and delegates to either
 * [FullWidthLayout] or [StandardLayout] based on the slot configuration.
 *
 * Extracted from [AudiListItem] to keep its cyclomatic complexity within threshold.
 *
 * @param config       Static variant configuration driving structural decisions.
 * @param style        Resolved [ListItemStyle] containing dimensions, spacing, and typography.
 * @param content      Text content (label, supporting text, trailing text).
 * @param slots        [ListItemSlots] composable slot definitions.
 * @param stateColors  Resolved [ListItemStateColors] for the current interaction state.
 * @param disabledAlpha Alpha multiplier applied when the item is disabled.
 * @param isDragActive Whether the item is currently being dragged.
 * @param surfaceShape Shape used for background clipping and overlays.
 */
@Composable
private fun BoxScope.SurfaceContent(
    config: ListItemConfig,
    style: ListItemStyle,
    content: ListItemContent,
    slots: ListItemSlots,
    stateColors: ListItemStateColors,
    disabledAlpha: Float,
    isDragActive: Boolean,
    surfaceShape: RoundedCornerShape,
) {
    // Opacity layer (Drag state: dimmed background underneath)
    if (isDragActive) {
        Box(
            modifier =
                Modifier
                    .matchParentSize()
                    .background(style.colors.opacityLayer, surfaceShape),
        )
    }

    // State layer overlay (Pressed / Hover)
    if (stateColors.stateLayerColor != Color.Transparent) {
        Box(
            modifier =
                Modifier
                    .matchParentSize()
                    .background(stateColors.stateLayerColor, surfaceShape),
        )
    }

    // Content
    val fullWidthSlot = slots.fullWidthContent
    if (fullWidthSlot != null) {
        FullWidthLayout(
            style = style,
            content = content,
            fullWidthContent = fullWidthSlot,
            stateColors = stateColors,
            disabledAlpha = disabledAlpha,
        )
    } else {
        StandardLayout(
            config = config,
            style = style,
            content = content,
            slots = slots,
            stateColors = stateColors,
            disabledAlpha = disabledAlpha,
        )
    }
}

// ── Standard multi-slot layout ─────────────────────────────────────────────────

/**
 * Renders the standard multi-slot list item anatomy (leading → trailing).
 *
 * Lays out the following slot areas in a horizontal [Row]:
 * 1. **Drag marker** — Edit mode only.
 * 2. **Leading content** — Cover / Avatar / Icon+Label.
 * 3. **Leading control** — ToggleSwitch / Checkbox / RadioButton (vertically centred).
 * 4. **Main content area** — label, supporting text, trailing text, trailing icon, progress.
 * 5. **Secondary action** — after a vertical divider (all modes).
 * 6. **Tertiary action** — after a second vertical divider (all modes).
 *
 * **Note:** The delete button is **not** part of this layout. In Delete mode it is
 * rendered **outside** the ListItem surface by [AudiListItem], adjacent to this layout.
 *
 * @param config        Static variant configuration driving structural decisions.
 * @param style         Resolved [ListItemStyle] containing dimensions, spacing, and typography.
 * @param content       Text content (label, supporting text, trailing text).
 * @param slots         [ListItemSlots] composable slot definitions.
 * @param stateColors   Resolved [ListItemStateColors] for the current interaction state.
 * @param disabledAlpha Alpha multiplier applied when the item is disabled.
 */
@Suppress("CyclomaticComplexMethod")
@Composable
private fun StandardLayout(
    config: ListItemConfig,
    style: ListItemStyle,
    content: ListItemContent,
    slots: ListItemSlots,
    stateColors: ListItemStateColors,
    disabledAlpha: Float,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = style.minHeight)
                .alpha(disabledAlpha)
                .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ── Drag marker (Edit mode only) ──────────────────────────────────
        val dragMarkerSlot = slots.dragMarker
        if (config.mode == ListItemConfig.Mode.Edit && dragMarkerSlot != null) {
            Box(
                modifier =
                    Modifier
                        .width(style.dragMarkerWrapperWidth)
                        .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                dragMarkerSlot()
            }
        }

        // ── Leading content (Cover / Avatar / Icon+Label) ─────────────────
        // Rendered BEFORE leadingControl per the CC_0015 anatomy spec.
        val leadingSlot = slots.leadingContent
        if (leadingSlot != null) {
            Box(
                modifier =
                    Modifier
                        .width(style.leadingContentWrapperWidth),
                contentAlignment = Alignment.Center,
            ) {
                leadingSlot()
            }
        }

        // ── Leading control (ToggleSwitch / Checkbox / RadioButton) ────────
        val leadingControlSlot = slots.leadingControl
        if (leadingControlSlot != null) {
            Box(
                modifier =
                    Modifier
                        .width(style.leadingContentWrapperWidth)
                        .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                leadingControlSlot()
            }
        }

        // ── Main content area ─────────────────────────────────────────────
        Row(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(
                        horizontal = style.slotIconContainerHorizontalPadding,
                        vertical = style.contentSlotVerticalPadding,
                    ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(style.slotIconContainerGap),
        ) {
            // Leading icon slot
            val leadingIconSlot = slots.leadingIcon
            if (leadingIconSlot != null) {
                leadingIconSlot()
            }

            // Text content column
            Column(modifier = Modifier.weight(1f)) {
                // Primary label — mandatory, single line
                Text(
                    state = TextState(text = content.label, maxLines = 1),
                    overflow = TextOverflow.Ellipsis,
                    style = style.labelTextStyle.copy(color = stateColors.labelColor),
                )

                // Supporting text — optional, single line
                val supportingText = content.supportingText
                if (supportingText != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(style.slotIconContainerGap),
                    ) {
                        val sublabelIconSlot = slots.sublabelIcon
                        if (sublabelIconSlot != null) {
                            sublabelIconSlot()
                        }
                        Text(
                            state = TextState(text = supportingText, maxLines = 1),
                            overflow = TextOverflow.Ellipsis,
                            style = style.sublabelTextStyle.copy(color = stateColors.sublabelColor),
                        )
                    }
                }
            }

            // Trailing text (SecondColumn) — weight(1f) fills remaining space,
            // contentAlignment drives start/end positioning within that space.
            val trailingText = content.trailingText
            if (trailingText != null) {
                val boxAlignment =
                    if (style.trailingTextAlignment == TextAlign.End) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = boxAlignment,
                ) {
                    Text(
                        state = TextState(text = trailingText, maxLines = 1),
                        overflow = TextOverflow.Ellipsis,
                        style = style.sublabelTextStyle.copy(color = stateColors.sublabelColor),
                    )
                }
            }

            // Trailing icon slot
            val trailingIconSlot = slots.trailingIcon
            if (trailingIconSlot != null) {
                trailingIconSlot()
            }

            // Progress indicator slot
            val progressSlot = slots.progressIndicator
            if (progressSlot != null) {
                progressSlot()
            }
        }

        // ── Secondary interaction area (after vertical divider) ──────────
        val secondarySlot = slots.secondaryAction
        if (secondarySlot != null) {
            Divider(
                config =
                    DividerConfig(
                        orientation = DividerConfig.Orientation.Vertical,
                        padding = style.verticalDividerVerticalPadding,
                    ),
                modifier = Modifier.fillMaxHeight(),
            )
            secondarySlot()
        }

        // ── Tertiary interaction area (after second vertical divider) ────
        val tertiarySlot = slots.tertiaryAction
        if (tertiarySlot != null) {
            Divider(
                config =
                    DividerConfig(
                        orientation = DividerConfig.Orientation.Vertical,
                        padding = style.verticalDividerVerticalPadding,
                    ),
                modifier = Modifier.fillMaxHeight(),
            )
            tertiarySlot()
        }
    }
}

// ── Full-width content slot layout ─────────────────────────────────────────────

/**
 * Renders the full-width content slot layout for larger interactions.
 *
 * Displays the headline label above a full-width composable content area
 * (e.g. Slider, SegmentedControl). This layout is mutually exclusive with
 * [StandardLayout].
 *
 * @param style            Resolved [ListItemStyle] containing dimensions, spacing, and typography.
 * @param content          Text content — [ListItemContent.label] serves as the headline.
 * @param fullWidthContent The full-width composable slot (Slider, SegmentedControl, etc.).
 * @param stateColors      Resolved [ListItemStateColors] for the current interaction state.
 * @param disabledAlpha    Alpha multiplier applied when the item is disabled.
 */
@Composable
private fun FullWidthLayout(
    style: ListItemStyle,
    content: ListItemContent,
    fullWidthContent: @Composable () -> Unit,
    stateColors: ListItemStateColors,
    disabledAlpha: Float,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = style.minHeight)
                .padding(
                    horizontal = style.slotIconContainerHorizontalPadding,
                    vertical = style.contentSlotVerticalPadding,
                ).alpha(disabledAlpha),
    ) {
        Text(
            state = TextState(text = content.label, maxLines = 1),
            overflow = TextOverflow.Ellipsis,
            style = style.labelTextStyle.copy(color = stateColors.labelColor),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(style.contentSlotVerticalPadding))

        Box(modifier = Modifier.fillMaxWidth()) {
            fullWidthContent()
        }
    }
}

// ── State colour resolver ──────────────────────────────────────────────────────

/**
 * Resolves the correct [ListItemStateColors] from a branch (Selected or Unselected)
 * based on the current interaction state.
 *
 * Priority: Disabled > Pressed > Idle.
 *
 * @param branch    The [ListItemBranchColors] for the current selection state.
 * @param enabled   Whether the list item is currently enabled.
 * @param isPressed Whether the list item is currently being pressed.
 * @return The resolved [ListItemStateColors] matching the highest-priority active state.
 */
private fun resolveStateColors(
    branch: ListItemBranchColors,
    enabled: Boolean,
    isPressed: Boolean,
): ListItemStateColors =
    when {
        !enabled -> branch.disabled
        isPressed -> branch.pressed
        else -> branch.idle
    }

// endregion
