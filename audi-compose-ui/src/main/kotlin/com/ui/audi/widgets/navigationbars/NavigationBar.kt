package com.ui.audi.widgets.navigationbars

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.indication.rememberBrandIndication
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.navigationbars.LocalNavigationBarStyle
import com.ui.core.widgets.navigationbars.NavigationBarConfig
import com.ui.core.widgets.navigationbars.NavigationBarInteractionConfig
import com.ui.core.widgets.navigationbars.NavigationBarItem
import com.ui.core.widgets.navigationbars.NavigationBarState
import com.ui.core.widgets.navigationbars.NavigationBarStateColors
import com.ui.core.widgets.navigationbars.NavigationBarStyle
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextResource
import com.ui.core.widgets.text.TextState
import kotlinx.coroutines.launch

private const val SEMANTIC_VARIANT_COUNT = 4
private const val SCROLL_STEP = 200

@Suppress("LongMethod", "CyclomaticComplexMethod")
@Composable
internal fun NavigationBar(
    config: NavigationBarConfig,
    modifier: Modifier = Modifier,
    state: NavigationBarState = NavigationBarState(),
    items: List<NavigationBarItem> = emptyList(),
    interactionConfig: NavigationBarInteractionConfig = NavigationBarInteractionConfig(),
) {
    val selectedIndex = interactionConfig.selectedIndex
    val onItemSelected = interactionConfig.onSelectedIndexChange
    val style = LocalNavigationBarStyle.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val canvasColor =
        Sem.Color.Fill.Canvas
            .color()
    val fadeOutHeight =
        Cmp.Size.Navigation.NavigationBar.MD.FadeOutWrapper.Height
            .dimension()
            .pxToDp()
    val fadingWidth =
        Cmp.Size.Global.Fadeout.Fading
            .dimension()
            .pxToDp()
    val hasOverflow = scrollState.maxValue > 0
    val showIndicators = config.scrollIndicator
    val isFillMode = config.mode == NavigationBarConfig.Mode.Fill

    Row(
        modifier = modifier.fillMaxWidth().background(canvasColor),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showIndicators) {
            ScrollArrow(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Scroll left",
                enabled = state.enabled && scrollState.canScrollBackward,
                onClick = {
                    scope.launch {
                        scrollState.animateScrollTo((scrollState.value - SCROLL_STEP).coerceAtLeast(0))
                    }
                },
            )
        }
        Box(
            modifier =
                when {
                    showIndicators && isFillMode -> Modifier.weight(1f, fill = true)
                    showIndicators -> Modifier.weight(1f, fill = false)
                    else -> Modifier.fillMaxWidth()
                }.height(fadeOutHeight),
        ) {
            Row(
                modifier =
                    Modifier
                        .then(
                            if (isFillMode && !hasOverflow) {
                                Modifier.fillMaxWidth()
                            } else {
                                Modifier
                            },
                        ).horizontalScroll(scrollState),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                items.forEachIndexed { index, item ->
                    NavUnit(
                        item = item,
                        index = index,
                        config = config,
                        state = state,
                        style = style,
                        isSelected = index == selectedIndex,
                        isFill = isFillMode && !hasOverflow,
                        onClick = { onItemSelected(index) },
                    )
                }
            }

            if (showIndicators && hasOverflow && scrollState.canScrollBackward) {
                FadingGradient(
                    alignment = Alignment.CenterStart,
                    width = fadingWidth,
                    colors = listOf(canvasColor, Color.Transparent),
                )
            }

            if (showIndicators && hasOverflow && scrollState.canScrollForward) {
                FadingGradient(
                    alignment = Alignment.CenterEnd,
                    width = fadingWidth,
                    colors = listOf(Color.Transparent, canvasColor),
                )
            }
        }

        if (showIndicators) {
            ScrollArrow(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Scroll right",
                enabled = state.enabled && scrollState.canScrollForward,
                onClick = {
                    scope.launch {
                        scrollState.animateScrollTo(
                            (scrollState.value + SCROLL_STEP).coerceAtMost(scrollState.maxValue),
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun BoxScope.FadingGradient(
    alignment: Alignment,
    width: androidx.compose.ui.unit.Dp,
    colors: List<Color>,
) {
    Box(
        modifier =
            Modifier
                .align(alignment)
                .fillMaxHeight()
                .width(width)
                .background(Brush.horizontalGradient(colors = colors)),
    )
}

@Composable
private fun ScrollArrow(
    imageVector: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val src = remember { MutableInteractionSource() }
    val touchTarget =
        Cmp.Size.Navigation.NavigationBar.MD.FadeOutWrapper.Height
            .dimension()
            .pxToDp()

    Box(
        modifier =
            Modifier
                .size(touchTarget)
                .clip(RoundedCornerShape(50))
                .alpha(if (!enabled) Sem.Opacity.Disabled.opacity() else 1f)
                .interactiveClickable(
                    clickOptions = ClickOptions(onClick = onClick),
                    interactionSource = src,
                    enabled = enabled,
                    indication = rememberBrandIndication(false),
                ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides
                Cmp.Color.Action.Button.Tertiary.Unselected.Content.Icon.Idle
                    .color(),
        ) {
            Icon(
                source = IconSource.Vector(imageVector, contentDescription),
                config = IconConfig(size = IconConfig.Size.MD),
            )
        }
    }
}

@Suppress("LongMethod", "CyclomaticComplexMethod", "LongParameterList")
@Composable
private fun NavUnit(
    item: NavigationBarItem,
    index: Int,
    config: NavigationBarConfig,
    state: NavigationBarState,
    style: NavigationBarStyle,
    isSelected: Boolean,
    isFill: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val itemEnabled = state.enabled
    val itemFocused = state.isFocused && index == 0
    val src = remember { MutableInteractionSource() }
    val isPressed by src.collectIsPressedAsState()
    val effectiveSelected = isSelected && itemEnabled
    val branch = if (effectiveSelected) style.colors.selected else style.colors.unselected
    val colors =
        if (!itemEnabled) {
            branch.disabled
        } else if (isPressed) {
            branch.pressed
        } else {
            branch.idle
        }

    val unitMinH =
        Cmp.Size.Navigation.NavigationBar.MD.Unit.Surface.Height
            .dimension()
            .pxToDp()
    val unitMinW =
        Cmp.Size.Navigation.NavigationBar.MD.Unit.Surface.MinWidth
            .dimension()
            .pxToDp()
    val radius =
        Cmp.BorderRadius.Navigation.NavigationBar.Unit.Idle
            .dimension()
            .pxToDp()
    val bw =
        Cmp.BorderWidth.Navigation.NavigationBar.Unit.Surface.Idle
            .dimension()
            .pxToDp()
    val underlineH = style.underlineWidth
    val shape = RoundedCornerShape(radius)
    val focusW =
        Sem.BorderWidth.FocusRing
            .dimension()
            .pxToDp()
    val focusC =
        Sem.Color.Stroke.Signal.Focus
            .color()
    val textStyle = if (effectiveSelected) style.selectedTextStyle else style.unselectedTextStyle

    Box(
        modifier =
            modifier
                .then(if (isFill) Modifier.fillMaxWidth() else Modifier)
                .fillMaxHeight()
                .defaultMinSize(minWidth = unitMinW)
                .then(
                    if (itemFocused && itemEnabled) {
                        Modifier.border(focusW, focusC, shape)
                    } else {
                        Modifier
                    },
                ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .defaultMinSize(minWidth = unitMinW, minHeight = unitMinH)
                    .then(if (isFill) Modifier.fillMaxWidth() else Modifier)
                    .clip(shape)
                    .background(colors.surfaceFill)
                    .then(
                        if (bw.value > 0f) {
                            Modifier.border(bw, colors.surfaceStroke, shape)
                        } else {
                            Modifier
                        },
                    ).alpha(if (!itemEnabled) Sem.Opacity.Disabled.opacity() else 1f)
                    .interactiveClickable(
                        clickOptions = ClickOptions(onClick = onClick),
                        interactionSource = src,
                        enabled = itemEnabled,
                        indication = rememberBrandIndication(darkBackground = false),
                    ).padding(horizontal = style.itemHPadding, vertical = style.itemVPadding)
                    .drawBehind {
                        if (effectiveSelected) {
                            val underlineHeightPx = underlineH.toPx()
                            val gap = 4.dp.toPx()
                            drawRect(
                                color = colors.underlineColor,
                                topLeft = Offset(0f, size.height + gap),
                                size = Size(size.width, underlineHeightPx),
                            )
                        }
                    },
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(style.itemGap),
            ) {
                @Suppress("ForbiddenComment")
                // TODO: Replace SemanticShapeEllipse with the actual Badge widget once implemented.
                if (item.badge != null && itemEnabled) {
                    SemanticShapeEllipse(index = index)
                }
                ItemContent(item, config, style, colors, textStyle)
            }
        }
    }
}

@Composable
private fun ItemContent(
    item: NavigationBarItem,
    config: NavigationBarConfig,
    style: NavigationBarStyle,
    colors: NavigationBarStateColors,
    textStyle: TextStyle,
) {
    val labelResource = item.label

    when (config.variant) {
        NavigationBarConfig.Variant.Label -> {
            LabelContent(labelResource, colors.labelColor, textStyle)
        }

        NavigationBarConfig.Variant.Icon -> {
            val iconSource = item.icon
            if (iconSource != null) {
                CompositionLocalProvider(LocalContentColor provides colors.iconColor) {
                    Icon(
                        source = iconSource,
                        config = IconConfig(size = style.iconSize),
                    )
                }
            }
        }

        NavigationBarConfig.Variant.LeadingIcon -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(style.itemGap),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val iconSource = item.icon
                if (iconSource != null) {
                    CompositionLocalProvider(LocalContentColor provides colors.iconColor) {
                        Icon(
                            source = iconSource,
                            config = IconConfig(size = style.iconSize),
                        )
                    }
                }
                LabelContent(labelResource, colors.labelColor, textStyle)
            }
        }
    }
}

@Composable
private fun LabelContent(
    label: TextResource,
    labelColor: Color,
    textStyle: TextStyle,
) {
    CompositionLocalProvider(LocalContentColor provides labelColor) {
        Text(
            state = TextState(text = label, maxLines = 1),
            overflow = TextOverflow.Clip,
            style = textStyle.copy(color = labelColor),
        )
    }
}

@Composable
private fun SemanticShapeEllipse(index: Int) {
    val ellipseH =
        Cmp.Size.Feedback.SemanticShape.Ellipse.Height
            .dimension()
            .pxToDp()
    val ellipseW =
        Cmp.Size.Feedback.SemanticShape.Ellipse.Width
            .dimension()
            .pxToDp()
    val borderW =
        Cmp.BorderWidth.Feedback.SemanticShape.Default
            .dimension()
            .pxToDp()
    val variant = index % SEMANTIC_VARIANT_COUNT
    val ellipseShape = RoundedCornerShape(50)

    val fillColor =
        when (variant) {
            0 ->
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Fill
                    .color()
            1 ->
                Cmp.Color.Feedback.SemanticShape.Advisory.Surface.Fill
                    .color()
            2 ->
                Cmp.Color.Feedback.SemanticShape.Positive.Surface.Fill
                    .color()
            else ->
                Cmp.Color.Feedback.SemanticShape.Neutral.Surface.Fill
                    .color()
        }
    val strokeColor =
        when (variant) {
            0 ->
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Stroke
                    .color()
            1 ->
                Cmp.Color.Feedback.SemanticShape.Advisory.Surface.Stroke
                    .color()
            2 ->
                Cmp.Color.Feedback.SemanticShape.Positive.Surface.Stroke
                    .color()
            else ->
                Cmp.Color.Feedback.SemanticShape.Neutral.Surface.Stroke
                    .color()
        }

    Box(
        modifier =
            Modifier
                .size(width = ellipseW, height = ellipseH)
                .clip(ellipseShape)
                .background(fillColor, ellipseShape)
                .then(
                    if (borderW.value > 0f) {
                        Modifier.border(borderW, strokeColor, ellipseShape)
                    } else {
                        Modifier
                    },
                ),
    )
}
