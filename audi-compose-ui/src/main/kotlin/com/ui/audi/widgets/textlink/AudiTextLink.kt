package com.ui.audi.widgets.textlink

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.ui.core.engine.api.Sem
import com.ui.core.focus.focusableWithRing
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.textlink.LocalTextLinkStyle
import com.ui.core.widgets.textlink.TextLinkConfig
import com.ui.core.widgets.textlink.TextLinkInteractionConfig
import com.ui.core.widgets.textlink.TextLinkState
import com.ui.core.widgets.textlink.TextLinkStateColors
import com.ui.core.widgets.textlink.colorsForVariant

/** Audi brand impl of [com.ui.core.widgets.textlink.TextLink]. */
@Suppress("LongParameterList", "LongMethod", "CyclomaticComplexMethod")
@Composable
internal fun TextLink(
    modifier: Modifier = Modifier,
    config: TextLinkConfig,
    state: TextLinkState = TextLinkState(),
    interactionConfig: TextLinkInteractionConfig = TextLinkInteractionConfig(),
    leading: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    val style = LocalTextLinkStyle.current
    val restrictions = LocalUxRestrictions.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val effectiveEnabled =
        state.enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    val variantColors = style.colorsForVariant(config.variant)

    // ── Resolve per-state colours ────────────────────────────────────────────
    val labelColor = resolveColor(variantColors.label, effectiveEnabled, isPressed)
    val iconColor = resolveColor(variantColors.icon, effectiveEnabled, isPressed)
    val underlineColor = resolveColor(variantColors.underline, effectiveEnabled, isPressed)

    // ── Resolve per-state underline thickness ────────────────────────────────
    val underlineThickness =
        when {
            !effectiveEnabled -> style.underlineThicknessIdle
            isPressed -> style.underlineThicknessPressed
            else -> style.underlineThicknessIdle
        }

    // ── Resolve per-state text style ─────────────────────────────────────────
    val textStyle =
        when {
            !effectiveEnabled -> style.idleTextStyle
            isPressed -> style.pressedTextStyle
            else -> style.idleTextStyle
        }

    // ── Disabled opacity modifier ────────────────────────────────────────────
    val disabledAlphaModifier =
        if (!effectiveEnabled) Modifier.alpha(style.disabledOpacity) else Modifier

    // ── Focus ring shape ─────────────────────────────────────────────────────
    val shape =
        RoundedCornerShape(
            Sem.BorderRadius.MD
                .dimension()
                .pxToDp(),
        )

    // ── Focus ring: show only when enabled AND isFocused ─────────────────────
    val showFocusRing = state.isFocused && effectiveEnabled
    val focusRingModifier =
        if (showFocusRing) {
            Modifier.border(
                width = style.focusRingWidth,
                color = style.focusRingColor,
                shape = shape,
            )
        } else {
            Modifier
        }

    // ── Click interaction ────────────────────────────────────────────────────
    val clickOptions =
        ClickOptions(
            onClick = interactionConfig.onClick,
            onLongClick = interactionConfig.onLongClick,
            onDoubleClick = interactionConfig.onDoubleClick,
            debounceMs = interactionConfig.clickDebounceMs,
        )

    val labelEndFocusGap = style.focusRingWidth
    val leadingFocusGap = style.focusRingWidth

    if (config.variant == TextLinkConfig.Variant.Inline) {
        // ── Inline variant ───────────────────────────────────────────────────
        Box(
            modifier =
                modifier
                    .defaultMinSize(minHeight = style.height)
                    .wrapContentWidth(Alignment.Start)
                    .then(disabledAlphaModifier)
                    .then(focusRingModifier)
                    .focusableWithRing(
                        interactionSource = interactionSource,
                        shape = shape,
                        ringColor = style.focusRingColor,
                        ringWidth = style.focusRingWidth,
                        focusRequester = interactionConfig.focusRequester,
                    ).interactiveClickable(
                        clickOptions = clickOptions,
                        interactionSource = interactionSource,
                        enabled = effectiveEnabled,
                        indication = null,
                    ),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                modifier = Modifier.padding(bottom = style.focusRingWidth),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(style.gap),
            ) {
                val trailingSlot = trailing

                // ── Label Box ────────────────────────────────────
                Box(
                    modifier =
                        Modifier.padding(
                            start = style.focusRingWidth,
                            end = if (trailingSlot != null) 0.dp else style.focusRingWidth + labelEndFocusGap,
                        ),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                        Box {
                            CompositionLocalProvider(
                                LocalContentColor provides labelColor,
                                LocalTextStyle provides
                                    textStyle.copy(
                                        color = labelColor,
                                        textDecoration = TextDecoration.None,
                                    ),
                            ) {
                                label?.invoke()
                            }
                        }
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(underlineThickness)
                                    .background(
                                        if (effectiveEnabled) {
                                            underlineColor
                                        } else {
                                            androidx.compose.ui.graphics.Color.Transparent
                                        },
                                    ),
                        )
                    }
                }

                // ── Trailing Icon Box ────────────────────────────
                if (trailingSlot != null) {
                    Box(
                        modifier = Modifier.padding(end = style.focusRingWidth),
                        contentAlignment = Alignment.Center,
                    ) {
                        CompositionLocalProvider(LocalContentColor provides iconColor) {
                            trailingSlot()
                        }
                    }
                }
            }
        }
    } else {
        // ── Standalone variant ───────────────────────────────────────────────
        Box(
            modifier =
                modifier
                    .defaultMinSize(minHeight = style.height)
                    .wrapContentWidth(Alignment.Start)
                    .then(disabledAlphaModifier)
                    .then(focusRingModifier)
                    .focusableWithRing(
                        interactionSource = interactionSource,
                        shape = shape,
                        ringColor = style.focusRingColor,
                        ringWidth = style.focusRingWidth,
                        focusRequester = interactionConfig.focusRequester,
                    ).interactiveClickable(
                        clickOptions = clickOptions,
                        interactionSource = interactionSource,
                        enabled = effectiveEnabled,
                        indication = null,
                    ),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                modifier = Modifier.padding(bottom = style.focusRingWidth),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(style.gap),
            ) {
                val leadingSlot = leading
                val trailingSlot = trailing

                // ── Leading Icon Box ─────────────────────────────
                if (leadingSlot != null) {
                    Box(
                        modifier = Modifier.padding(start = leadingFocusGap),
                        contentAlignment = Alignment.Center,
                    ) {
                        CompositionLocalProvider(LocalContentColor provides iconColor) {
                            leadingSlot()
                        }
                    }
                }

                // ── Label Box ────────────────────────────────────
                Box(
                    modifier =
                        Modifier.padding(
                            start = style.focusRingWidth,
                            end = if (trailingSlot != null) 0.dp else style.focusRingWidth + labelEndFocusGap,
                        ),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                        Box {
                            CompositionLocalProvider(
                                LocalContentColor provides labelColor,
                                LocalTextStyle provides
                                    textStyle.copy(
                                        color = labelColor,
                                        textDecoration = TextDecoration.None,
                                    ),
                            ) {
                                label?.invoke()
                            }
                        }
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(underlineThickness)
                                    .background(
                                        if (effectiveEnabled) {
                                            underlineColor
                                        } else {
                                            androidx.compose.ui.graphics.Color.Transparent
                                        },
                                    ),
                        )
                    }
                }

                // ── Trailing Icon Box ────────────────────────────
                if (trailingSlot != null) {
                    Box(
                        modifier = Modifier.padding(end = style.focusRingWidth),
                        contentAlignment = Alignment.Center,
                    ) {
                        CompositionLocalProvider(LocalContentColor provides iconColor) {
                            trailingSlot()
                        }
                    }
                }
            }
        }
    }
}

private fun resolveColor(
    colors: TextLinkStateColors,
    enabled: Boolean,
    isPressed: Boolean,
): androidx.compose.ui.graphics.Color =
    when {
        !enabled -> colors.disabled
        isPressed -> colors.pressed
        else -> colors.idle
    }
