package com.ui.audi.widgets.multitogglebuttons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.ui.core.widgets.buttons.ButtonBranchColors
import com.ui.core.widgets.buttons.ButtonConfig
import com.ui.core.widgets.buttons.ButtonStateColors
import com.ui.core.widgets.buttons.LocalButtonStyle
import com.ui.core.widgets.buttons.colorsForTone
import com.ui.core.widgets.multitogglebuttons.LocalMultiToggleButtonStyle
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonConfig
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonIndicatorColors
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonInteractionConfig
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonState
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonStyle
import com.ui.core.widgets.multitogglebuttons.indicatorColorsForVariant

/**
 * Audi brand implementation of the multi-toggle button widget.
 *
 * Renders a single toggle button that cycles through a fixed number of states
 * (off + N on-levels). The visual appearance adapts to the active [config] mode:
 *
 * - **Icon / IconWithLabel** — circular button with optional arc indicators and an
 *   optional text label beneath the icon.
 * - **LabelHug / LabelFill** — rounded-rectangle button with optional linear
 *   indicator bars at the bottom edge.
 *
 * Selection state, interaction colors, border widths, typography, and focus-ring
 * styling are resolved from the [MultiToggleButtonStyle] provided via
 * [LocalMultiToggleButtonStyle]. Indicator colours can be overridden per-instance
 * through [indicatorColorsOverride].
 *
 * This composable is **internal**; consumers should use the brand-agnostic
 * [com.ui.core.widgets.multitogglebuttons.MultiToggleButton] API instead.
 *
 * @param config              Layout mode, tone, and variant configuration.
 * @param modifier            [Modifier] applied to the outermost layout node.
 * @param state               Runtime state flags (enabled, focused).
 * @param interactionConfig   Callbacks, current state index, and optional focus requester.
 * @param indicatorColorsOverride Optional per-instance indicator colour override;
 *                                when `null` the variant colours from the style are used.
 * @param icon                Optional composable slot for the icon content.
 * @param label               Optional composable slot for the label text.
 */
@Suppress("LongParameterList", "LongMethod", "CyclomaticComplexMethod")
@Composable
internal fun MultiToggleButton(
    config: MultiToggleButtonConfig,
    modifier: Modifier = Modifier,
    state: MultiToggleButtonState = MultiToggleButtonState(),
    interactionConfig: MultiToggleButtonInteractionConfig = MultiToggleButtonInteractionConfig(),
    indicatorColorsOverride: MultiToggleButtonIndicatorColors? = null,
    icon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
) {
    val style = LocalMultiToggleButtonStyle.current
    val indicatorColors = indicatorColorsOverride ?: style.indicatorColorsForVariant(config.variant)
    val currentIdx = interactionConfig.currentStateIndex
    val statesCount = interactionConfig.statesCount
    val nextIdx = (currentIdx + 1) % statesCount
    val isSelected = currentIdx > 0
    val indicatorCount = statesCount - 1
    val activeIndicator = currentIdx - 1

    val buttonTone =
        when (config.tone) {
            MultiToggleButtonConfig.Tone.Secondary -> ButtonConfig.Tone.Secondary
            MultiToggleButtonConfig.Tone.Tertiary -> ButtonConfig.Tone.Tertiary
        }

    val labelTypography =
        if (isSelected) style.labelModeTypography else style.labelModeTypographyUnselected
    val baseButtonStyle = style.buttonStyleOverride ?: LocalButtonStyle.current
    val toggleButtonStyle =
        baseButtonStyle.copy(
            minWidth = style.labelModeMinWidth,
            minHeight = style.labelModeMinHeight,
            paddingHorizontal = style.labelModeHorizontalPadding,
            textStyle = labelTypography,
        )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val systemFocused by interactionSource.collectIsFocusedAsState()
    val isFocused = (systemFocused || state.isFocused) && state.enabled

    val typeColors = toggleButtonStyle.colorsForTone(buttonTone)
    val branch = if (isSelected) typeColors.selected else typeColors.unselected
    val stateColors = resolveStateColors(branch, state.enabled, isPressed)
    val borderWidth = resolveBorderWidth(style, isSelected, state.enabled, isPressed)
    val disabledAlpha = if (!state.enabled) Modifier.alpha(style.disabledOpacity) else Modifier

    val focusRingWidth = style.focusRingWidth
    val focusRingColor = style.focusRingColor
    val focusRingGap = style.focusRingGap

    when (config.mode) {
        MultiToggleButtonConfig.Mode.Icon,
        MultiToggleButtonConfig.Mode.IconOnly,
        -> {
            val iconShape = CircleShape

            Column(
                modifier = modifier.then(disabledAlpha),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(style.contentToIndicatorSpacing),
            ) {
                FocusRingWrapper(
                    isFocused = isFocused,
                    enabled = state.enabled,
                    focusRequester = interactionConfig.focusRequester,
                    interactionSource = interactionSource,
                    focusRingWidth = focusRingWidth,
                    focusRingColor = focusRingColor,
                    focusRingGap = focusRingGap,
                    shape = iconShape,
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(style.iconModeStateLayerSize)
                                .background(branch.surfaceFill, iconShape)
                                .border(borderWidth, stateColors.border, iconShape)
                                .clip(iconShape)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    enabled = state.enabled,
                                ) { interactionConfig.onStateChange(nextIdx) },
                        contentAlignment = Alignment.Center,
                    ) {
                        PressedOverlay(isPressed, state.enabled, branch)

                        if (icon != null) {
                            CompositionLocalProvider(LocalContentColor provides stateColors.labelColor) {
                                icon()
                            }
                        }

                        if (isSelected && state.enabled) {
                            ArcIndicatorOverlay(style, indicatorColors, indicatorCount, activeIndicator)
                        }
                    }
                }

                if (label != null && config.mode != MultiToggleButtonConfig.Mode.IconOnly) {
                    val iconLabelColor =
                        if (isSelected) style.iconModeLabelColor else style.iconModeLabelColorUnselected
                    val iconLabelTypography =
                        if (isSelected) style.iconModeLabelTypography else style.iconModeLabelTypographyUnselected
                    CompositionLocalProvider(
                        LocalContentColor provides iconLabelColor,
                        LocalTextStyle provides iconLabelTypography.copy(color = iconLabelColor),
                    ) {
                        label()
                    }
                }
            }
        }

        else -> {
            val shape = RoundedCornerShape(style.labelModeCornerRadius)
            val outerFocusShape = RoundedCornerShape(style.labelModeCornerRadius + focusRingGap)

            val sizeModifier =
                when (config.mode) {
                    MultiToggleButtonConfig.Mode.Fill -> Modifier.fillMaxWidth().heightIn(min = style.labelModeMinHeight)
                    else -> Modifier.widthIn(min = style.labelModeMinWidth).heightIn(min = style.labelModeMinHeight)
                }

            FocusRingWrapper(
                isFocused = isFocused,
                enabled = state.enabled,
                focusRequester = interactionConfig.focusRequester,
                interactionSource = interactionSource,
                focusRingWidth = focusRingWidth,
                focusRingColor = focusRingColor,
                focusRingGap = focusRingGap,
                shape = outerFocusShape,
                modifier = modifier.then(disabledAlpha),
            ) {
                Box(
                    modifier =
                        Modifier
                            .then(sizeModifier)
                            .background(branch.surfaceFill, shape)
                            .border(borderWidth, stateColors.border, shape)
                            .clip(shape)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                enabled = state.enabled,
                            ) { interactionConfig.onStateChange(nextIdx) },
                    contentAlignment = Alignment.Center,
                ) {
                    PressedOverlay(isPressed, state.enabled, branch)

                    Column(
                        modifier =
                            Modifier.padding(
                                horizontal = style.labelModeHorizontalPadding,
                                vertical = toggleButtonStyle.paddingVertical,
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (label != null) {
                            StyledLabel(stateColors, style, isSelected, label)
                        }
                    }

                    if (isSelected && state.enabled) {
                        Box(
                            modifier =
                                Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(
                                        start = style.labelModeHorizontalPadding,
                                        end = style.labelModeHorizontalPadding,
                                        bottom = toggleButtonStyle.paddingVertical,
                                    ),
                        ) {
                            IndicatorRow(style, indicatorColors, indicatorCount, activeIndicator)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Wraps [content] with an optional focus ring border.
 *
 * When [isFocused] is `true` a border of [focusRingWidth] and [focusRingColor] is
 * drawn around the [content] using the given [shape], separated by a [focusRingGap]
 * padding so the ring does not overlap the button surface. An optional
 * [focusRequester] is attached for programmatic focus control.
 *
 * @param isFocused         Whether the focus ring should be visible.
 * @param enabled           Whether the wrapper is focusable.
 * @param focusRequester    Optional [FocusRequester] for programmatic focus.
 * @param interactionSource Shared [MutableInteractionSource] for focus tracking.
 * @param focusRingWidth    Stroke width of the focus ring.
 * @param focusRingColor    Color of the focus ring stroke.
 * @param focusRingGap      Gap between the focus ring and the inner content.
 * @param shape             Shape of the focus ring border.
 * @param modifier          [Modifier] applied to the outer [Box].
 * @param content           The composable content to wrap.
 */
@Composable
private fun FocusRingWrapper(
    isFocused: Boolean,
    enabled: Boolean,
    focusRequester: androidx.compose.ui.focus.FocusRequester?,
    interactionSource: MutableInteractionSource,
    focusRingWidth: Dp,
    focusRingColor: androidx.compose.ui.graphics.Color,
    focusRingGap: Dp,
    shape: Shape,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .then(
                    if (focusRequester != null) {
                        Modifier.focusRequester(focusRequester)
                    } else {
                        Modifier
                    },
                ).focusable(enabled = enabled, interactionSource = interactionSource)
                .then(
                    if (isFocused) {
                        Modifier.border(focusRingWidth, focusRingColor, shape).padding(focusRingGap)
                    } else {
                        Modifier
                    },
                ),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

/**
 * Draws a translucent state-layer overlay when the button is pressed.
 *
 * The overlay fills the parent [Box] using [Modifier.matchParentSize] and is
 * tinted with the [ButtonBranchColors.stateLayerPressed] colour. It is only
 * rendered when both [isPressed] and [enabled] are `true`.
 *
 * @param isPressed Whether the user is currently pressing the button.
 * @param enabled   Whether the button is interactive.
 * @param branch    Colour branch providing the pressed state-layer fill.
 */
@Composable
private fun androidx.compose.foundation.layout.BoxScope.PressedOverlay(
    isPressed: Boolean,
    enabled: Boolean,
    branch: ButtonBranchColors,
) {
    if (isPressed && enabled) {
        Box(modifier = Modifier.matchParentSize().background(branch.stateLayerPressed))
    }
}

/**
 * Renders the [label] composable with the correct typography and colour for the
 * current selection state.
 *
 * Typography is switched between [MultiToggleButtonStyle.labelModeTypography]
 * (selected) and [MultiToggleButtonStyle.labelModeTypographyUnselected]
 * (unselected). The label colour is taken from [ButtonStateColors.labelColor].
 *
 * @param stateColors Resolved state colours for the current interaction state.
 * @param style       The multi-toggle button style carrying typography tokens.
 * @param isSelected  Whether the button is currently in a selected state.
 * @param label       Composable slot for the label text.
 */
@Composable
private fun StyledLabel(
    stateColors: ButtonStateColors,
    style: MultiToggleButtonStyle,
    isSelected: Boolean,
    label: @Composable () -> Unit,
) {
    val typography =
        if (isSelected) style.labelModeTypography else style.labelModeTypographyUnselected
    CompositionLocalProvider(
        LocalContentColor provides stateColors.labelColor,
        LocalTextStyle provides typography.copy(color = stateColors.labelColor),
    ) {
        label()
    }
}

/**
 * Resolves the [ButtonStateColors] for the current interaction state.
 *
 * Priority order: disabled > pressed > idle.
 *
 * @param branch    The selected or unselected colour branch.
 * @param enabled   Whether the button is interactive.
 * @param isPressed Whether the button is currently pressed.
 * @return The [ButtonStateColors] matching the current state.
 */
private fun resolveStateColors(
    branch: ButtonBranchColors,
    enabled: Boolean,
    isPressed: Boolean,
): ButtonStateColors =
    when {
        !enabled -> branch.disabled
        isPressed -> branch.pressed
        else -> branch.idle
    }

/**
 * Resolves the border width for the button surface based on selection and
 * interaction state.
 *
 * Each combination of selected/unselected and disabled/pressed/idle maps to a
 * dedicated token-derived dimension from the [MultiToggleButtonStyle].
 *
 * @param style      The multi-toggle button style carrying border-width tokens.
 * @param isSelected Whether the button is in a selected state.
 * @param enabled    Whether the button is interactive.
 * @param isPressed  Whether the button is currently pressed.
 * @return The resolved border width as [Dp].
 */
private fun resolveBorderWidth(
    style: MultiToggleButtonStyle,
    isSelected: Boolean,
    enabled: Boolean,
    isPressed: Boolean,
): Dp =
    if (isSelected) {
        when {
            !enabled -> style.selectedBorderWidthDisabled
            isPressed -> style.selectedBorderWidthPressed
            else -> style.selectedBorderWidthIdle
        }
    } else {
        when {
            !enabled -> style.unselectedBorderWidthDisabled
            isPressed -> style.unselectedBorderWidthPressed
            else -> style.unselectedBorderWidthIdle
        }
    }

/**
 * Renders a horizontal row of linear indicator bars for label-mode buttons.
 *
 * Each bar represents one on-level of the toggle. The bar at [activeIndex] uses
 * the selected fill/stroke/opacity; all others use the unselected set. Bar
 * dimensions, corner radius, gap, and border width are taken from [style].
 *
 * @param style          The multi-toggle button style with indicator dimensions.
 * @param colors         Indicator colour set (selected and unselected variants).
 * @param indicatorCount Total number of indicator bars to draw.
 * @param activeIndex    Zero-based index of the currently active indicator.
 */
@Composable
private fun IndicatorRow(
    style: MultiToggleButtonStyle,
    colors: MultiToggleButtonIndicatorColors,
    indicatorCount: Int,
    activeIndex: Int,
) {
    val shape = RoundedCornerShape(style.indicatorCornerRadius)
    Row(
        modifier = Modifier.width(style.indicatorWidth),
        horizontalArrangement = Arrangement.spacedBy(style.indicatorGap),
    ) {
        repeat(indicatorCount) { index ->
            val isActive = index == activeIndex
            val fill = if (isActive) colors.selectedFill else colors.unselectedFill
            val stroke = if (isActive) colors.selectedStroke else colors.unselectedStroke
            val opacity = if (isActive) colors.selectedOpacity else colors.unselectedOpacity
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .height(style.indicatorHeight)
                        .alpha(opacity)
                        .clip(shape)
                        .background(fill)
                        .then(
                            if (style.indicatorBorderWidth.value > 0f) {
                                Modifier.border(style.indicatorBorderWidth, stroke, shape)
                            } else {
                                Modifier
                            },
                        ),
            )
        }
    }
}

/**
 * Draws curved arc indicators inside the circular icon-mode button perimeter.
 *
 * Each arc segment represents one on-level of the toggle. The segment at
 * [activeIndex] is rendered with the selected fill and opacity; all other
 * segments use the unselected set. Arc thickness equals [MultiToggleButtonStyle.indicatorHeight],
 * and gaps between arcs equal [MultiToggleButtonStyle.indicatorGap]. The arc
 * radius is derived from the canvas size at render time.
 *
 * @param style          The multi-toggle button style with indicator dimensions.
 * @param colors         Indicator colour set (selected and unselected variants).
 * @param indicatorCount Total number of arc segments to draw.
 * @param activeIndex    Zero-based index of the currently active arc segment.
 */
@Composable
private fun ArcIndicatorOverlay(
    style: MultiToggleButtonStyle,
    colors: MultiToggleButtonIndicatorColors,
    indicatorCount: Int,
    activeIndex: Int,
) {
    val density = LocalDensity.current
    val strokeWidthPx = with(density) { style.indicatorHeight.toPx() }
    val gapPx = with(density) { style.indicatorGap.toPx() }
    val paddingPx = gapPx

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasSize = size.minDimension
        val inset = paddingPx + strokeWidthPx / 2f
        val arcRect = Rect(inset, inset, canvasSize - inset, canvasSize - inset)
        val arcSize = Size(arcRect.width, arcRect.height)
        if (arcSize.width <= 0f || arcSize.height <= 0f) return@Canvas

        val radius = arcRect.width / 2f
        val gapDegrees = if (radius > 0f) (gapPx / (2f * Math.PI.toFloat() * radius)) * 360f else 0f
        val totalGapDegrees = indicatorCount * gapDegrees
        val arcSweep = if (indicatorCount > 0) (360f - totalGapDegrees) / indicatorCount else 0f
        var startAngle =
            when (indicatorCount) {
                2 -> 180f
                3 -> 210f
                else -> 180f
            }

        repeat(indicatorCount) { index ->
            val isActive = index == activeIndex
            drawArc(
                color = if (isActive) colors.selectedFill else colors.unselectedFill,
                startAngle = startAngle,
                sweepAngle = arcSweep,
                useCenter = false,
                topLeft = arcRect.topLeft,
                size = arcSize,
                alpha = if (isActive) colors.selectedOpacity else colors.unselectedOpacity,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
            )
            startAngle += arcSweep + gapDegrees
        }
    }
}
