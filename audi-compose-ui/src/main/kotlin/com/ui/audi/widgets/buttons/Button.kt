package com.ui.audi.widgets.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.graphics.Color
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.focus.focusableWithRing
import com.ui.core.indication.rememberBrandIndication
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.buttons.ButtonBranchColors
import com.ui.core.widgets.buttons.ButtonConfig
import com.ui.core.widgets.buttons.ButtonInteractionConfig
import com.ui.core.widgets.buttons.ButtonState
import com.ui.core.widgets.buttons.ButtonStateColors
import com.ui.core.widgets.buttons.ButtonStyle
import com.ui.core.widgets.buttons.LocalButtonStyle
import com.ui.core.widgets.buttons.colorsForTone
import com.ui.core.widgets.buttons.hasDarkBackground
import com.ui.core.widgets.progressindicators.ProgressIndicator
import com.ui.core.widgets.progressindicators.ProgressIndicatorConfig

@Suppress("CyclomaticComplexMethod", "LongParameterList", "LongMethod")
@Composable
internal fun Button(
    config: ButtonConfig,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState(),
    interactionConfig: ButtonInteractionConfig = ButtonInteractionConfig(),
    leading: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    toggle: (@Composable () -> Unit)? = null,
) {
    val enabled = state.enabled
    val isLoading = state.isLoading
    val isSelected = state.isSelected
    val isFocused = state.isFocused
    val style = LocalButtonStyle.current
    val typeColors = style.colorsForTone(config.tone)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // ── UX Restrictions: auto-disable when driving and not distraction-optimized ─
    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled = enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    val branch = if (isSelected) typeColors.selected else typeColors.unselected
    val stateColors = resolveStateColors(branch, effectiveEnabled, isPressed, isLoading)
    val hasBorder =
        stateColors.border !=
            Sem.Color.Stroke.Transparent
                .color() &&
            stateColors.border != Color.Transparent

    // ── Brand-aware ripple — light on filled, dark on outlined/ghost ──────────
    val indication = rememberBrandIndication(config.tone.hasDarkBackground())

    // ── Bundle all click interactions ─────────────────────────────────────────
    val clickOptions =
        ClickOptions(
            onClick = interactionConfig.onClick,
            onLongClick = interactionConfig.onLongClick,
            onDoubleClick = interactionConfig.onDoubleClick,
            debounceMs = interactionConfig.clickDebounceMs,
        )

    val touchTargetDp =
        Sem.Size.TouchTarget.MD
            .dimension()
            .pxToDp()
    val resolvedMinHeight = maxOf(style.minHeight, touchTargetDp)
    val resolvedMinWidth = maxOf(style.minWidth, touchTargetDp)
    val sizeModifier =
        when (config.mode) {
            ButtonConfig.Mode.Hug ->
                Modifier
                    .heightIn(min = resolvedMinHeight)
                    .widthIn(min = resolvedMinWidth)

            ButtonConfig.Mode.Fill ->
                Modifier
                    .fillMaxWidth()
                    .height(resolvedMinHeight)
        }

    val shape = RoundedCornerShape(style.cornerRadius)

    val borderModifier =
        if (hasBorder) {
            val tok =
                if (isSelected) {
                    when {
                        !effectiveEnabled -> Cmp.BorderWidth.Action.Button.Selected.Surface.Disabled
                        isLoading -> Cmp.BorderWidth.Action.Button.Selected.Surface.Loading
                        isPressed -> Cmp.BorderWidth.Action.Button.Selected.Surface.Pressed
                        else -> Cmp.BorderWidth.Action.Button.Selected.Surface.Idle
                    }
                } else {
                    when {
                        !effectiveEnabled -> Cmp.BorderWidth.Action.Button.Unselected.Surface.Disabled
                        isLoading -> Cmp.BorderWidth.Action.Button.Unselected.Surface.Loading
                        isPressed -> Cmp.BorderWidth.Action.Button.Unselected.Surface.Pressed
                        else -> Cmp.BorderWidth.Action.Button.Unselected.Surface.Idle
                    }
                }
            Modifier.border(tok.dimension().pxToDp(), stateColors.border, shape)
        } else {
            Modifier
        }

    val disabledAlphaModifier =
        if (!effectiveEnabled) Modifier.alpha(Sem.Opacity.Disabled.opacity()) else Modifier
    val loadingAlphaModifier =
        if (isLoading) Modifier.alpha(Sem.Opacity.Disabled.opacity()) else Modifier

    val focusRingWidth =
        Sem.BorderWidth.FocusRing
            .dimension()
            .pxToDp()
    val focusRingColor =
        Sem.Color.Stroke.Signal.Focus
            .color()

    val forcedFocusRingModifier =
        if (isFocused && effectiveEnabled && !isLoading) {
            Modifier.border(width = focusRingWidth, color = focusRingColor, shape = shape)
        } else {
            Modifier
        }

    // Outer Box — focus ring sits OUTSIDE the clipped button surface.
    // Content (padded label/icons) is a direct measurable child so the Box
    // grows to wrap it, producing the correct pill shape.  The surface
    // background / border / click overlay uses matchParentSize().
    Box(
        modifier =
            modifier
                .then(sizeModifier)
                .then(disabledAlphaModifier)
                .then(forcedFocusRingModifier)
                .focusableWithRing(
                    interactionSource = interactionSource,
                    shape = shape,
                    ringColor = focusRingColor,
                    ringWidth = focusRingWidth,
                    focusRequester = interactionConfig.focusRequester,
                ),
        contentAlignment = Alignment.Center,
    ) {
        // Surface overlay — background, border, click (does NOT drive sizing)
        Box(
            modifier =
                Modifier
                    .matchParentSize()
                    .then(loadingAlphaModifier)
                    .clip(shape)
                    .background(branch.surfaceFill)
                    .then(borderModifier)
                    .interactiveClickable(
                        clickOptions = clickOptions,
                        interactionSource = interactionSource,
                        enabled = effectiveEnabled && !isLoading,
                        indication = indication,
                    ),
        ) {
            if (isPressed && effectiveEnabled && !isLoading) {
                Box(modifier = Modifier.matchParentSize().background(branch.stateLayerPressed))
            }
        }

        // Content — drives the outer Box sizing → pill shape
        Box(
            modifier = Modifier.padding(horizontal = style.paddingHorizontal, vertical = style.paddingVertical),
            contentAlignment = Alignment.Center,
        ) {
            // Invisible content placeholder — keeps width stable during loading
            Box(modifier = if (isLoading) Modifier.alpha(0f) else Modifier) {
                ButtonContent(style, stateColors.labelColor, stateColors.iconColor, leading, label, trailing)
            }
        }

        // Spinner — rendered on top so it stays fully visible during loading
        if (isLoading) {
            ProgressIndicator(
                config =
                    ProgressIndicatorConfig(
                        variant = ProgressIndicatorConfig.Variant.SpinnerInfinite,
                        size = ProgressIndicatorConfig.Size.MD,
                    ),
                progress = 0f,
            )
        }

        // Toggle slot — aligned at the bottom of the button surface
        if (toggle != null) {
            Box(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(
                            start = style.paddingHorizontal,
                            end = style.paddingHorizontal,
                            bottom = style.paddingVertical,
                        ),
            ) {
                toggle()
            }
        }
    }
}

@Composable
private fun ButtonContent(
    style: ButtonStyle,
    labelColor: Color,
    iconColor: Color,
    leading: (@Composable () -> Unit)?,
    label: (@Composable () -> Unit)?,
    trailing: (@Composable () -> Unit)?,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(style.iconSpacing), verticalAlignment = Alignment.CenterVertically) {
        if (leading != null) CompositionLocalProvider(LocalContentColor provides iconColor) { leading() }
        if (label !=
            null
        ) {
            CompositionLocalProvider(
                LocalContentColor provides labelColor,
                LocalTextStyle provides style.textStyle.copy(color = labelColor),
            ) {
                label()
            }
        }
        if (trailing != null) CompositionLocalProvider(LocalContentColor provides iconColor) { trailing() }
    }
}

private fun resolveStateColors(
    branch: ButtonBranchColors,
    enabled: Boolean,
    isPressed: Boolean,
    isLoading: Boolean,
): ButtonStateColors =
    when {
        !enabled -> branch.disabled
        isLoading -> branch.loading
        isPressed -> branch.pressed
        else -> branch.idle
    }
