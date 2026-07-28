package com.ui.audi.widgets.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import com.ui.core.engine.api.Sem
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.chips.ChipConfig
import com.ui.core.widgets.chips.ChipInteractionConfig
import com.ui.core.widgets.chips.ChipState
import com.ui.core.widgets.chips.ChipStyle
import com.ui.core.widgets.chips.ChipTypeColors
import com.ui.core.widgets.chips.LocalChipStyle
import com.ui.core.widgets.chips.colorsForSelection
import com.ui.core.widgets.chips.typographyForSelection

// ── State colour resolver ──────────────────────────────────────────────────────

private data class ResolvedChipColors(
    val surfaceFill: Color,
    val stroke: Color,
    val labelColor: Color,
    val iconColor: Color,
)

private fun resolveChipColors(
    tc: ChipTypeColors,
    enabled: Boolean,
    isPressed: Boolean,
    isDragged: Boolean,
): ResolvedChipColors =
    when {
        !enabled -> ResolvedChipColors(tc.surfaceFill, tc.strokeDisabled, tc.labelDisabled, tc.iconDisabled)
        // Dragged & Pressed use the same overlay pattern: surfaceFill as
        // background + faint StateLayer overlay via matchParentSize().
        isDragged -> ResolvedChipColors(tc.surfaceFill, tc.strokeDragged, tc.labelDragged, tc.iconDragged)
        isPressed -> ResolvedChipColors(tc.surfaceFill, tc.strokePressed, tc.labelPressed, tc.iconPressed)
        else -> ResolvedChipColors(tc.surfaceFill, tc.strokeIdle, tc.labelIdle, tc.iconIdle)
    }

// ── AudiChip ───────────────────────────────────────────────────────────────────

/**
 * Audi brand chip — renders the chip container and delegates content to
 * composable slots (`leadingIcon`, `label`, `trailingIcon`).
 *
 * Slot widgets pick up per-state colours automatically through
 * [LocalContentColor] and [LocalTextStyle] — no hardcoded icons or text.
 */
@Suppress("CyclomaticComplexMethod", "LongMethod")
@Composable
internal fun Chip(
    config: ChipConfig,
    modifier: Modifier = Modifier,
    state: ChipState = ChipState(),
    interactionConfig: ChipInteractionConfig = ChipInteractionConfig(),
    leadingIcon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val style = LocalChipStyle.current
    val typeColors = style.colorsForSelection(state.isSelected)
    val typography = style.typographyForSelection(state.isSelected)
    val shape = RoundedCornerShape(style.cornerRadius)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled =
        state.enabled &&
            (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    // When disabled, dragged and focus modes should not be available
    // When dragged is enabled, focus should not be available (mutually exclusive)
    val effectiveDragged = state.isDragged && effectiveEnabled
    val effectiveFocused = state.isFocused && effectiveEnabled && !effectiveDragged

    // Drag wins over press — suppress press while dragged
    val effectivePressed = isPressed && !effectiveDragged
    val resolved = resolveChipColors(typeColors, effectiveEnabled, effectivePressed, effectiveDragged)
    val disabledAlpha = if (effectiveEnabled) 1f else Sem.Opacity.Disabled.opacity()
    // No ripple indication - pressed state handled by manual color resolution
    val indication = null

    val clickOptions =
        ClickOptions(
            onClick = interactionConfig.onClick,
            onLongClick = interactionConfig.onLongClick,
            onDoubleClick = interactionConfig.onDoubleClick,
            debounceMs = interactionConfig.clickDebounceMs,
        )

    val focusRingColor =
        Sem.Color.Stroke.Signal.Focus
            .color()
    val focusRingWidth =
        Sem.BorderWidth.FocusRing
            .dimension()
            .pxToDp()

    val activeFocusRequester = if (effectiveEnabled) interactionConfig.focusRequester else null

    // Outer container — draws focus ring OUTSIDE the chip border (not clipped).
    // The focus ring is rendered via drawWithContent on this unclipped Box so it
    // extends beyond the chip's visual boundary.
    Box(
        modifier =
            modifier
                .alpha(disabledAlpha)
                .drawWithContent {
                    drawContent()
                    if (effectiveFocused) {
                        val focusRingWidthPx = focusRingWidth.toPx()
                        // Ring stroke center sits at the border's outer edge — no gap
                        val gap = focusRingWidthPx / 2
                        drawRoundRect(
                            color = focusRingColor,
                            topLeft =
                                androidx.compose.ui.geometry
                                    .Offset(-gap, -gap),
                            size =
                                androidx.compose.ui.geometry.Size(
                                    size.width + gap * 2,
                                    size.height + gap * 2,
                                ),
                            cornerRadius =
                                androidx.compose.ui.geometry.CornerRadius(
                                    style.cornerRadius.toPx() + gap,
                                ),
                            style = Stroke(width = focusRingWidthPx),
                        )
                    }
                },
    ) {
        if (effectiveDragged) {
            // ── Drag: 3-layer Figma structure ─────────────────────────────────
            // Layer 1 — OpacityLayer: canvas-coloured backdrop behind surface
            //   so the shadow is visible against any background.
            Box(
                modifier =
                    Modifier
                        .height(style.minHeight)
                        .clip(shape)
                        .background(style.dragOpacityLayerFill),
                contentAlignment = Alignment.Center,
            ) {
                // Layer 2 — Surface: smaller chip body with shadow
                // Shadow via graphicsLayer respects borderRadius (shape) natively.
                Box(
                    modifier =
                        Modifier
                            .defaultMinSize(minWidth = style.dragSurfaceMinWidth)
                            .height(style.dragSurfaceHeight)
                            .graphicsLayer(
                                shadowElevation = style.draggedShadow.elevation,
                                shape = shape,
                                clip = false,
                            ).clip(shape)
                            .background(if (state.isSelected) resolved.surfaceFill else style.dragOpacityLayerFill)
                            .border(style.borderWidth, resolved.stroke, shape),
                    contentAlignment = Alignment.Center,
                ) {
                    // Layer 3 — StateLayer: faint overlay + padded content
                    Box(modifier = Modifier.matchParentSize().background(typeColors.stateLayerDragged))

                    Box(
                        modifier =
                            Modifier.then(
                                if (config.variant == ChipConfig.Variant.Input) {
                                    val startPad = if (config.showLeadingIcon) style.inputPaddingLeft else style.paddingHorizontal
                                    val endPad = if (config.showTrailingButton) style.inputPaddingRight else style.paddingHorizontal
                                    Modifier.padding(
                                        start = startPad,
                                        end = endPad,
                                        top = style.paddingVertical,
                                        bottom = style.paddingVertical,
                                    )
                                } else {
                                    Modifier.padding(
                                        horizontal = style.paddingHorizontal,
                                        vertical = style.paddingVertical,
                                    )
                                },
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        ChipContent(
                            config = config,
                            style = style,
                            labelColor = resolved.labelColor,
                            iconColor = resolved.iconColor,
                            typography = typography,
                            effectiveEnabled = false,
                            interactionConfig = interactionConfig,
                            leadingIcon = leadingIcon,
                            label = label,
                            trailingIcon = trailingIcon,
                        )
                    }
                }
            }
        } else {
            // ── Normal (idle / pressed / focused / disabled) ──────────────────
            Box(
                modifier =
                    Modifier
                        .defaultMinSize(minHeight = style.minHeight)
                        .clip(shape)
                        .background(resolved.surfaceFill)
                        .border(style.borderWidth, resolved.stroke, shape)
                        .then(
                            if (activeFocusRequester != null) {
                                Modifier.focusRequester(activeFocusRequester)
                            } else {
                                Modifier
                            },
                        ).focusable(interactionSource = interactionSource)
                        .interactiveClickable(
                            clickOptions = clickOptions,
                            interactionSource = interactionSource,
                            enabled = effectiveEnabled,
                            indication = indication,
                        ),
                contentAlignment = Alignment.Center,
            ) {
                // Pressed state-layer overlay (§4.3)
                if (effectivePressed) {
                    Box(modifier = Modifier.matchParentSize().background(typeColors.stateLayerPressed))
                }

                // Padded content
                Box(
                    modifier =
                        Modifier.then(
                            if (config.variant == ChipConfig.Variant.Input) {
                                val startPad = if (config.showLeadingIcon) style.inputPaddingLeft else style.paddingHorizontal
                                val endPad = if (config.showTrailingButton) style.inputPaddingRight else style.paddingHorizontal
                                Modifier.padding(
                                    start = startPad,
                                    end = endPad,
                                    top = style.paddingVertical,
                                    bottom = style.paddingVertical,
                                )
                            } else {
                                Modifier.padding(
                                    horizontal = style.paddingHorizontal,
                                    vertical = style.paddingVertical,
                                )
                            },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    ChipContent(
                        config = config,
                        style = style,
                        labelColor = resolved.labelColor,
                        iconColor = resolved.iconColor,
                        typography = typography,
                        effectiveEnabled = effectiveEnabled,
                        interactionConfig = interactionConfig,
                        leadingIcon = leadingIcon,
                        label = label,
                        trailingIcon = trailingIcon,
                    )
                }
            }
        }
    }
}

// ── Slot-based content renderer ────────────────────────────────────────────────
//
// Applies per-state colours to the composable slots via CompositionLocalProvider.
// The Icon widget picks up `LocalContentColor`; the Text widget picks up
// `LocalTextStyle` and `LocalContentColor`.

@Suppress("CyclomaticComplexMethod", "UnusedParameter")
@Composable
private fun ChipContent(
    config: ChipConfig,
    style: ChipStyle,
    labelColor: Color,
    iconColor: Color,
    typography: androidx.compose.ui.text.TextStyle,
    effectiveEnabled: Boolean,
    interactionConfig: ChipInteractionConfig,
    leadingIcon: (@Composable () -> Unit)?,
    label: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
) {
    val labelTextStyle = typography.copy(color = labelColor)

    // For Input chip with trailing button: use manual spacing (gap between icon and label only, no gap before button)
    // For other variants: use standard gap spacing between all elements
    val useManualSpacing = config.variant == ChipConfig.Variant.Input && config.showTrailingButton && trailingIcon != null

    Row(
        horizontalArrangement = if (useManualSpacing) Arrangement.Start else Arrangement.spacedBy(style.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (config.variant) {
            ChipConfig.Variant.Filter -> {
                // Leading icon — always visible (tinted via Cmp icon token);
                // caller decides what to render (e.g. checkmark when selected)
                if (leadingIcon != null) {
                    CompositionLocalProvider(LocalContentColor provides iconColor) {
                        leadingIcon()
                    }
                }
                // Label
                if (label != null) {
                    CompositionLocalProvider(
                        LocalContentColor provides labelColor,
                        LocalTextStyle provides labelTextStyle,
                    ) {
                        label()
                    }
                }
                // Optional trailing icon
                if (config.showTrailingIcon && trailingIcon != null) {
                    CompositionLocalProvider(LocalContentColor provides iconColor) {
                        trailingIcon()
                    }
                }
            }

            ChipConfig.Variant.Input -> {
                // Optional leading icon
                if (config.showLeadingIcon && leadingIcon != null) {
                    CompositionLocalProvider(LocalContentColor provides iconColor) {
                        leadingIcon()
                    }
                    // Add gap after leading icon when using manual spacing
                    if (useManualSpacing) {
                        Spacer(modifier = Modifier.width(style.gap))
                    }
                }
                // Label
                if (label != null) {
                    CompositionLocalProvider(
                        LocalContentColor provides labelColor,
                        LocalTextStyle provides labelTextStyle,
                    ) {
                        label()
                    }
                }
                // Optional trailing dismiss button - NO gap before it when button is available
                if (config.showTrailingButton && trailingIcon != null) {
                    TrailingDismissButton(
                        style = style,
                        iconColor = iconColor,
                        enabled = effectiveEnabled,
                        onDismiss = interactionConfig.onDismiss,
                        icon = trailingIcon,
                    )
                }
            }

            ChipConfig.Variant.Suggestion -> {
                // Label only — no icons
                if (label != null) {
                    CompositionLocalProvider(
                        LocalContentColor provides labelColor,
                        LocalTextStyle provides labelTextStyle,
                    ) {
                        label()
                    }
                }
            }

            ChipConfig.Variant.Assist -> {
                // Leading icon — shown when provided
                if (leadingIcon != null) {
                    CompositionLocalProvider(LocalContentColor provides iconColor) {
                        leadingIcon()
                    }
                }
                // Label
                if (label != null) {
                    CompositionLocalProvider(
                        LocalContentColor provides labelColor,
                        LocalTextStyle provides labelTextStyle,
                    ) {
                        label()
                    }
                }
            }
        }
    }
}

// ── Trailing dismiss button ────────────────────────────────────────────────────
// ToDo - TrailingDismissButton will get replaced by audi componentButton once the implementation for component button is done.

/**
 * Trailing close/dismiss button for Input chips.
 * Uses the caller-provided [icon] slot (e.g. the codebase IconButton widget)
 * instead of hardcoding a native Icon.
 */
@Composable
private fun TrailingDismissButton(
    style: ChipStyle,
    iconColor: Color,
    enabled: Boolean,
    onDismiss: () -> Unit,
    icon: @Composable () -> Unit,
) {
    val stateLayerShape = RoundedCornerShape(style.trailingButtonCornerRadius)

    // Touch target clipped to borderRadius (Cmp.BorderRadius.Action.ComponentButton.Default)
    // so the click highlight/ripple area is rounded.
    Box(
        modifier =
            Modifier
                .defaultMinSize(
                    minWidth = style.trailingButtonTouchTargetMinWidth,
                    minHeight = style.trailingButtonTouchTargetHeight,
                ).height(style.minHeight)
                .clip(stateLayerShape)
                .background(style.trailingButtonSurfaceFill)
                .border(style.trailingButtonBorderWidth, style.trailingButtonBorderColor, stateLayerShape)
                .clickable(enabled = enabled, onClick = onDismiss),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides iconColor) {
            icon()
        }
    }
}
