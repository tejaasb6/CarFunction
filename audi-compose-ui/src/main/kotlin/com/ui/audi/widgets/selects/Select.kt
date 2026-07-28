package com.ui.audi.widgets.selects

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ui.core.focus.focusableWithRing
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.widgets.dividers.Divider
import com.ui.core.widgets.dividers.LocalDividerStyle
import com.ui.core.widgets.scrollbar.Scrollbar
import com.ui.core.widgets.selects.LocalSelectStyle
import com.ui.core.widgets.selects.SelectConfig
import com.ui.core.widgets.selects.SelectContent
import com.ui.core.widgets.selects.SelectOption
import com.ui.core.widgets.selects.SelectSlots
import com.ui.core.widgets.selects.SelectState
import com.ui.core.widgets.selects.SelectStyle
import com.ui.core.widgets.semanticshapes.LocalSemanticShapeStyle
import com.ui.core.widgets.semanticshapes.SemanticShape
import com.ui.core.widgets.semanticshapes.SemanticShapeConfig
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextResource
import com.ui.core.widgets.text.TextState

@Suppress("LongMethod", "LongParameterList", "CyclomaticComplexMethod")
@Composable
internal fun Select(
    options: List<SelectOption>,
    selectedOption: SelectOption?,
    onOptionSelected: (SelectOption) -> Unit,
    config: SelectConfig,
    modifier: Modifier = Modifier,
    state: SelectState,
    content: SelectContent,
    slots: SelectSlots,
    onExpandedChange: (Boolean) -> Unit,
) {
    val style = LocalSelectStyle.current
    val colors = if (state.error && state.enabled) style.errorColors else style.defaultColors
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val effectiveEnabled = state.enabled && !state.readOnly
    val isActive = state.expanded
    val disabledAlpha = if (!state.enabled) style.disabledOpacity else 1f

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        // ── Label + Appendix (appendix only shown when label is present) ──
        val hasLabel = content.label != EmptyTextResource
        val hasAppendix = content.appendix != EmptyTextResource
        if (hasLabel) {
            Row(
                modifier = Modifier.fillMaxWidth().alpha(disabledAlpha),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val labelColor =
                    when {
                        !state.enabled -> colors.labelDisabled
                        isPressed -> colors.labelPressed
                        else -> colors.labelIdle
                    }
                Text(
                    state = TextState(text = content.label),
                    style = style.labelTextStyle.copy(color = labelColor),
                )
                if (hasAppendix) {
                    Spacer(modifier = Modifier.weight(1f))
                    val appendixColor =
                        when {
                            !state.enabled -> colors.appendixIdle
                            isPressed -> colors.appendixPressed
                            else -> colors.appendixIdle
                        }
                    Text(
                        state = TextState(text = content.appendix),
                        style = style.captionTextStyle.copy(color = appendixColor),
                    )
                }
            }
            Spacer(modifier = Modifier.height(style.labelBottomSpacing))
        }

        // ── Select Field ─────────────────────────────────────────────
        SelectField(
            selectedOption = selectedOption,
            options = options,
            placeholder = content.placeholder,
            isActive = isActive,
            isPressed = isPressed,
            effectiveEnabled = effectiveEnabled,
            state = state,
            style = style,
            colors = colors,
            slots = slots,
            interactionSource = interactionSource,
            onFieldClick = {
                if (effectiveEnabled && !state.readOnly) {
                    onExpandedChange(!state.expanded)
                }
            },
        )

        // ── Dropdown Menu ────────────────────────────────────────────
        if (state.expanded && effectiveEnabled) {
            Spacer(modifier = Modifier.height(4.dp))

            val selectedIndex =
                options.indexOf(selectedOption).takeIf { it >= 0 }
                    ?: 0 // default-highlight first item when nothing selected
            DropdownMenu(
                options = options,
                selectedIndex = selectedIndex,
                onOptionSelected = { option ->
                    onOptionSelected(option)
                    onExpandedChange(false)
                },
                style = style,
                colors = colors,
                showOptionIcon = config.showOptionIcon,
                adaptiveMenuHeight = config.adaptiveMenuHeight,
            )
        }

        // ── Hint (hidden when expanded) ──────────────────────────────
        val hasHint = content.hint != EmptyTextResource
        if (hasHint && !state.expanded) {
            Spacer(modifier = Modifier.height(style.hintTopSpacing))
            val hintColor =
                when {
                    !state.enabled -> colors.hintIdle
                    isPressed -> colors.hintPressed
                    else -> colors.hintIdle
                }
            Box(modifier = Modifier.alpha(disabledAlpha)) {
                Text(
                    state = TextState(text = content.hint),
                    style = style.captionTextStyle.copy(color = hintColor),
                )
            }
        }

        // ── Error Caption with triangle icon (hidden when expanded or disabled) ──
        val hasErrorCaption = content.errorCaption != EmptyTextResource
        val showErrorCaption = state.error && state.enabled && !state.expanded && hasErrorCaption
        if (showErrorCaption) {
            Spacer(modifier = Modifier.height(style.captionErrorTopSpacing))
            val errorCaptionColor = colors.hintIdle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(style.captionErrorGap),
            ) {
                val baseShapeStyle = LocalSemanticShapeStyle.current
                val overriddenShapeStyle =
                    baseShapeStyle.copy(
                        containerDimension = maxOf(style.errorTriangleWidth, style.errorTriangleHeight),
                        triangleWidth = style.errorTriangleWidth,
                        triangleHeight = style.errorTriangleHeight,
                    )
                CompositionLocalProvider(LocalSemanticShapeStyle provides overriddenShapeStyle) {
                    SemanticShape(
                        config = SemanticShapeConfig(variant = SemanticShapeConfig.Variant.Critical),
                    )
                }
                Text(
                    state = TextState(text = content.errorCaption),
                    style = style.captionTextStyle.copy(color = errorCaptionColor),
                )
            }
        }
    }
}

// ── Select Field ─────────────────────────────────────────────────────────────

@Suppress("LongParameterList", "LongMethod", "CyclomaticComplexMethod")
@Composable
private fun SelectField(
    selectedOption: SelectOption?,
    options: List<SelectOption>,
    placeholder: TextResource,
    isActive: Boolean,
    isPressed: Boolean,
    effectiveEnabled: Boolean,
    state: SelectState,
    style: SelectStyle,
    colors: com.ui.core.widgets.selects.SelectTypeColors,
    slots: SelectSlots,
    interactionSource: MutableInteractionSource,
    onFieldClick: () -> Unit,
) {
    val borderColor =
        when {
            !state.enabled -> colors.fieldStrokeDisabled
            state.readOnly -> colors.fieldStrokeReadOnly
            isActive -> colors.fieldStrokeActive
            isPressed -> colors.fieldStrokePressed
            else -> colors.fieldStrokeIdle
        }
    val borderWidth =
        when {
            !state.enabled -> style.fieldBorderWidthDisabled
            isActive -> style.fieldBorderWidthActive
            isPressed -> style.fieldBorderWidthPressed
            else -> style.fieldBorderWidthIdle
        }
    val iconColor =
        when {
            !state.enabled -> colors.iconDisabled
            state.readOnly -> colors.iconReadOnly
            isActive -> colors.iconActive
            isPressed -> colors.iconPressed
            else -> colors.iconIdle
        }
    val disabledAlpha = if (!state.enabled) style.disabledOpacity else 1f

    Box(
        modifier =
            Modifier
                .defaultMinSize(minHeight = style.fieldHeight)
                .fillMaxWidth()
                .alpha(disabledAlpha)
                .clip(style.fieldBorderRadius)
                .background(colors.fieldSurfaceFill)
                .border(borderWidth, borderColor, style.fieldBorderRadius)
                .focusableWithRing(
                    interactionSource = interactionSource,
                    shape = style.fieldBorderRadius as RoundedCornerShape,
                    ringColor = style.focusRingColor,
                    ringWidth = style.focusRingWidth,
                ).interactiveClickable(
                    clickOptions = ClickOptions(onClick = onFieldClick),
                    interactionSource = interactionSource,
                    enabled = effectiveEnabled,
                    indication = null,
                ),
        contentAlignment = Alignment.CenterStart,
    ) {
        // State layer for pressed state
        if (isPressed && effectiveEnabled) {
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .background(colors.fieldStateLayerPressed),
            )
        }
        Row(
            modifier =
                Modifier.padding(
                    horizontal = style.fieldHorizontalPadding,
                    vertical = style.fieldVerticalPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(style.iconSpacing),
        ) {
            val leadingIcon = slots.leadingIcon
            if (leadingIcon != null) {
                CompositionLocalProvider(LocalContentColor provides iconColor) {
                    leadingIcon()
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                // When readOnly and no selection, show 1st option instead of placeholder
                val effectiveOption = if (state.readOnly && selectedOption == null) options.firstOrNull() else selectedOption
                if (effectiveOption != null) {
                    val textColor =
                        when {
                            !state.enabled -> colors.userInputDisabled
                            state.readOnly -> colors.userInputReadOnly
                            isActive -> colors.userInputActive
                            isPressed -> colors.userInputPressed
                            else -> colors.userInputIdle
                        }
                    ProvideTextStyle(style.userInputTextStyle.copy(color = textColor)) {
                        effectiveOption.label()
                    }
                } else if (placeholder != EmptyTextResource) {
                    val placeholderColor =
                        when {
                            !state.enabled -> colors.placeholderDisabled
                            isActive -> colors.placeholderActive
                            isPressed -> colors.placeholderPressed
                            else -> colors.placeholderIdle
                        }
                    Text(
                        state = TextState(text = placeholder),
                        style = style.placeholderTextStyle.copy(color = placeholderColor),
                    )
                }
            }
            val trailingIcon = slots.trailingIcon
            if (trailingIcon != null) {
                CompositionLocalProvider(LocalContentColor provides iconColor) {
                    trailingIcon()
                }
            }
        }
    }
}

// ── Dropdown Menu ────────────────────────────────────────────────────────────

@Suppress("LongMethod")
@Composable
private fun DropdownMenu(
    options: List<SelectOption>,
    selectedIndex: Int,
    onOptionSelected: (SelectOption) -> Unit,
    style: SelectStyle,
    colors: com.ui.core.widgets.selects.SelectTypeColors,
    showOptionIcon: Boolean,
    adaptiveMenuHeight: Boolean = false,
) {
    val menuPaddingH = style.menuHorizontalPadding
    val menuPaddingV = style.menuVerticalPadding
    val menuFill = style.menuSurfaceFill
    val menuStroke = style.menuSurfaceStroke
    val menuBorderRadius = style.menuBorderRadius
    val menuBorderWidth = style.menuBorderWidth
    val itemTouchTarget = style.menuItemHeight
    val itemVisualHeight = style.menuItemVisualHeight
    val dividerHeight = LocalDividerStyle.current.horizontalThickness

    val maxItemsVisible = style.menuMaxVisibleItems

    // Token-based height for maxItemsVisible items (default cap)
    val tokenBasedMaxHeight =
        (itemTouchTarget * maxItemsVisible) +
            (dividerHeight * (maxItemsVisible - 1).coerceAtLeast(0)) +
            (menuPaddingV * 2)

    // Total height needed for all items (no scroll)
    val totalContentHeight =
        (itemTouchTarget * options.size) +
            (dividerHeight * (options.size - 1).coerceAtLeast(0)) +
            (menuPaddingV * 2)

    // When adaptiveMenuHeight is true, measure menu position on screen and constrain
    // height to available space. When false, only use token-based cap.
    val density = LocalDensity.current
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    var menuTopDp by remember { mutableStateOf(0.dp) }
    val bottomMargin = 8.dp
    val screenAvailable = (screenHeightDp - menuTopDp - bottomMargin).coerceAtLeast(0.dp)

    val effectiveMax =
        if (adaptiveMenuHeight && menuTopDp > 0.dp) {
            minOf(tokenBasedMaxHeight, screenAvailable)
        } else {
            tokenBasedMaxHeight
        }

    val needsScroll = totalContentHeight > effectiveMax
    val maxHeight = if (needsScroll) effectiveMax else Dp.Unspecified

    val listState = rememberLazyListState()
    // Shared state: true when any non-default item is being pressed
    val anyItemPressed = remember { mutableStateOf(false) }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(
                    if (adaptiveMenuHeight) {
                        Modifier.onGloballyPositioned { coordinates ->
                            with(density) {
                                menuTopDp = coordinates.positionInWindow().y.toDp()
                            }
                        }
                    } else {
                        Modifier
                    },
                ).then(if (maxHeight != Dp.Unspecified) Modifier.heightIn(max = maxHeight) else Modifier)
                .graphicsLayer(
                    shadowElevation = style.menuShadow.elevation,
                    shape = menuBorderRadius,
                    clip = false,
                ).clip(menuBorderRadius)
                .background(menuFill)
                .border(menuBorderWidth, menuStroke, menuBorderRadius)
                .padding(horizontal = menuPaddingH, vertical = menuPaddingV),
    ) {
        if (needsScroll) {
            Row(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                ) {
                    itemsIndexed(options) { index, option ->
                        MenuItem(
                            option,
                            index == selectedIndex,
                            anyItemPressed,
                            { if (option.enabled) onOptionSelected(option) },
                            style,
                            colors,
                            itemVisualHeight,
                            itemTouchTarget,
                            showOptionIcon,
                        )
                        if (option != options.last()) {
                            Divider()
                        }
                    }
                }
                Scrollbar(
                    listState = listState,
                    modifier = Modifier.heightIn(max = maxHeight),
                )
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                options.forEachIndexed { index, option ->
                    MenuItem(
                        option,
                        index == selectedIndex,
                        anyItemPressed,
                        { if (option.enabled) onOptionSelected(option) },
                        style,
                        colors,
                        itemVisualHeight,
                        itemTouchTarget,
                        showOptionIcon,
                    )
                    if (index < options.lastIndex) {
                        Divider()
                    }
                }
            }
        }
    }
}

// ── Menu Item ────────────────────────────────────────────────────────────────

@Composable
private fun MenuItem(
    option: SelectOption,
    isDefaultHighlight: Boolean,
    anyItemPressed: MutableState<Boolean>,
    onSelect: () -> Unit,
    style: SelectStyle,
    colors: com.ui.core.widgets.selects.SelectTypeColors,
    itemVisualHeight: Dp,
    itemTouchTarget: Dp,
    showOptionIcon: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Once any item is pressed, suppress the default highlight for the
    // rest of this menu session (state resets when menu closes/reopens).
    if (isPressed && !isDefaultHighlight) anyItemPressed.value = true

    val itemPaddingH = style.menuItemHorizontalPadding
    val itemGap = style.menuItemSpacing
    val itemBorderRadius = style.menuItemBorderRadius
    val itemBorderWidth = style.menuItemBorderWidth

    // Show fill when: this item is pressed, OR default-highlighted AND no other item is pressed
    val showFill = isPressed || (isDefaultHighlight && !anyItemPressed.value)
    // Darker pressed state when pressing the already-highlighted item
    val showPressedOverlay = isPressed && isDefaultHighlight && !anyItemPressed.value
    val surfaceFill = if (showFill) colors.menuItemSelectedSurfaceFill else colors.menuItemUnselectedSurfaceFill
    val surfaceStroke = if (showFill) colors.menuItemSelectedStrokeIdle else colors.menuItemUnselectedStrokeIdle
    val textColor = if (showFill) colors.menuItemSelectedTextIdle else colors.menuItemUnselectedTextIdle
    val disabledAlpha = if (!option.enabled) style.disabledOpacity else 1f

    // Outer = TouchTarget (transparent, no indication)
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minWidth = itemTouchTarget, minHeight = itemTouchTarget)
                .alpha(disabledAlpha)
                .interactiveClickable(
                    clickOptions = ClickOptions(onClick = onSelect),
                    interactionSource = interactionSource,
                    enabled = option.enabled,
                    indication = null,
                ),
        contentAlignment = Alignment.Center,
    ) {
        // Inner = visual surface (only this gets fill color)
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = itemVisualHeight)
                    .clip(itemBorderRadius)
                    .background(surfaceFill)
                    .border(itemBorderWidth, surfaceStroke, itemBorderRadius),
            contentAlignment = Alignment.CenterStart,
        ) {
            // Pressed overlay covers the entire surface (before padding)
            if (showPressedOverlay) {
                Box(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .background(colors.menuItemSelectedStateLayerPressed),
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = itemPaddingH),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(itemGap),
            ) {
                if (showOptionIcon) {
                    val icon = option.icon
                    if (icon != null) {
                        CompositionLocalProvider(LocalContentColor provides textColor) {
                            icon()
                        }
                    }
                }
                ProvideTextStyle(style.userInputTextStyle.copy(color = textColor)) {
                    option.label()
                }
            }
        }
    }
}
