package com.ui.audi.widgets.iconbuttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.ui.core.engine.api.Sem
import com.ui.core.focus.focusableWithRing
import com.ui.core.indication.rememberBrandIndication
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.iconbuttons.IconButtonBranchColors
import com.ui.core.widgets.iconbuttons.IconButtonConfig
import com.ui.core.widgets.iconbuttons.IconButtonInteractionConfig
import com.ui.core.widgets.iconbuttons.IconButtonState
import com.ui.core.widgets.iconbuttons.IconButtonStateColors
import com.ui.core.widgets.iconbuttons.LocalIconButtonStyle
import com.ui.core.widgets.iconbuttons.colorsForTone
import com.ui.core.widgets.iconbuttons.resolveLabelColor
import com.ui.core.widgets.progressindicators.ProgressIndicator
import com.ui.core.widgets.progressindicators.ProgressIndicatorConfig

/**
 * Audi brand impl of [com.ui.core.widgets.iconbuttons.IconButton].
 *
 * Renders a **circular** container with tone-specific background fill,
 * optional border, pressed state overlay, and an outer focus ring.
 * The label (when enabled) is placed below the icon container.
 */
@Suppress("CyclomaticComplexMethod", "LongMethod")
@Composable
internal fun IconButton(
    config: IconButtonConfig,
    modifier: Modifier = Modifier,
    state: IconButtonState = IconButtonState(),
    interactionConfig: IconButtonInteractionConfig = IconButtonInteractionConfig(),
    icon: @Composable () -> Unit,
    label: (@Composable () -> Unit)? = null,
    toggle: (@Composable () -> Unit)? = null,
) {
    val style = LocalIconButtonStyle.current
    val shape = CircleShape
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled =
        state.enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    val typeColors = style.colorsForTone(config.tone)
    val branch = if (state.isSelected) typeColors.selected else typeColors.unselected
    val stateColors = resolveStateColors(branch, effectiveEnabled, isPressed, state.isLoading)

    val hasBorder = stateColors.border != Color.Transparent
    val disabledAlpha =
        if (!effectiveEnabled) Modifier.alpha(style.disabledOpacity) else Modifier
    val loadingAlphaModifier =
        if (state.isLoading) Modifier.alpha(style.disabledOpacity) else Modifier

    val indication =
        rememberBrandIndication(
            config.tone == IconButtonConfig.Tone.Prominent ||
                config.tone == IconButtonConfig.Tone.Destructive,
        )
    val clickOptions =
        ClickOptions(
            onClick = interactionConfig.onClick,
            onLongClick = interactionConfig.onLongClick,
            onDoubleClick = interactionConfig.onDoubleClick,
            debounceMs = interactionConfig.clickDebounceMs,
        )

    val focusRingWidth =
        Sem.BorderWidth.FocusRing
            .dimension()
            .pxToDp()
    val focusRingColor =
        Sem.Color.Stroke.Signal.Focus
            .color()

    // Forced focus ring (programmatic isFocused flag)
    val forcedFocusRingModifier =
        if (state.isFocused && effectiveEnabled && !state.isLoading) {
            Modifier.border(width = focusRingWidth, color = focusRingColor, shape = shape)
        } else {
            Modifier
        }

    Column(
        modifier = modifier.then(disabledAlpha),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(style.gap),
    ) {
        // ── Outer wrapper — touch target area ──────────────────────────
        Box(
            modifier =
                Modifier
                    .size(style.touchTarget),
            contentAlignment = Alignment.Center,
        ) {
            // ── Visual container — focus ring sits tight around this ──
            Box(
                modifier =
                    Modifier
                        .size(style.stateLayerWidth, style.stateLayerHeight)
                        .then(forcedFocusRingModifier)
                        .focusableWithRing(
                            interactionSource = interactionSource,
                            shape = shape,
                            ringColor = focusRingColor,
                            ringWidth = focusRingWidth,
                            focusRequester = interactionConfig.focusRequester,
                        ).clip(shape)
                        .interactiveClickable(
                            clickOptions = clickOptions,
                            interactionSource = interactionSource,
                            enabled = effectiveEnabled && !state.isLoading,
                            indication = indication,
                        ),
                contentAlignment = Alignment.Center,
            ) {
                // ── Surface overlay — background, border, click ──
                // Rendered as a matchParentSize() child so that loading
                // opacity only dims the surface, not the spinner on top
                // (same pattern as the regular Button widget).
                Box(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .then(loadingAlphaModifier)
                            .clip(shape)
                            .background(branch.surfaceFill)
                            .then(
                                if (hasBorder) {
                                    Modifier.border(1.dp, stateColors.border, shape)
                                } else {
                                    Modifier
                                },
                            ),
                ) {
                    // Pressed state overlay
                    if (isPressed && effectiveEnabled && !state.isLoading) {
                        Box(
                            modifier =
                                Modifier
                                    .matchParentSize()
                                    .background(branch.stateLayerPressed),
                        )
                    }
                }

                // Content: icon (hidden during loading) or toggle slot
                if (!state.isLoading) {
                    CompositionLocalProvider(LocalContentColor provides stateColors.contentColor) {
                        icon()
                    }
                }

                // ── Spinner — rendered on top so it stays fully visible ──
                if (state.isLoading) {
                    ProgressIndicator(
                        config =
                            ProgressIndicatorConfig(
                                variant = ProgressIndicatorConfig.Variant.SpinnerInfinite,
                                size = ProgressIndicatorConfig.Size.SM,
                            ),
                        progress = 0f,
                    )
                }

                // ── Toggle slot — inside the circle, clipped by shape ──
                if (toggle != null) {
                    Box(
                        modifier = Modifier.matchParentSize(),
                    ) {
                        toggle()
                    }
                }
            }
        }

        // ── Label — outside the container, stays visible during loading ──
        if (config.showLabel && label != null) {
            val labelBranchColors =
                if (state.isSelected) style.selectedLabelColors else style.unselectedLabelColors
            val labelColor =
                resolveLabelColor(
                    labelColors = labelBranchColors,
                    enabled = effectiveEnabled,
                    isPressed = isPressed,
                    isLoading = state.isLoading,
                )
            val labelTextStyle =
                if (state.isSelected) style.selectedLabelStyle else style.unselectedLabelStyle
            val mergedLabelStyle = labelTextStyle.merge(TextStyle(color = labelColor))
            CompositionLocalProvider(
                LocalContentColor provides labelColor,
                LocalTextStyle provides mergedLabelStyle,
            ) {
                label()
            }
        }
    }
}

private fun resolveStateColors(
    branch: IconButtonBranchColors,
    enabled: Boolean,
    isPressed: Boolean,
    isLoading: Boolean,
): IconButtonStateColors =
    when {
        !enabled -> branch.disabled
        isLoading -> branch.loading
        isPressed -> branch.pressed
        else -> branch.idle
    }
