package com.ui.audi.widgets.steppers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import com.ui.audi.R
import com.ui.core.engine.api.Sem
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.steppers.LocalStepperStyle
import com.ui.core.widgets.steppers.StepperButtonColors
import com.ui.core.widgets.steppers.StepperContent
import com.ui.core.widgets.steppers.StepperInteractionConfig
import com.ui.core.widgets.steppers.StepperState
import com.ui.core.widgets.steppers.StepperStateColors
import com.ui.core.widgets.steppers.StepperStyle

/**
 * Audi brand implementation of the Stepper widget.
 *
 * **RTL:** Compose [Row] auto-mirrors child order, so stepping direction,
 * arrow buttons, label, and optional icon are all mirrored automatically.
 *
 * **RHD (Right-Hand Drive):** No adaptation — the widget is drive-side agnostic.
 */
@Suppress("CyclomaticComplexMethod", "LongParameterList", "LongMethod")
@Composable
internal fun Stepper(
    modifier: Modifier = Modifier,
    content: StepperContent = StepperContent(),
    state: StepperState = StepperState(),
    interactionConfig: StepperInteractionConfig = StepperInteractionConfig(),
) {
    val style = LocalStepperStyle.current
    val shape = RoundedCornerShape(style.cornerRadius)

    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled =
        state.enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    val sc = if (effectiveEnabled) style.colors.idle else style.colors.disabled
    val btn = style.colors.button

    val disabledAlpha =
        if (!effectiveEnabled) Modifier.alpha(Sem.Opacity.Disabled.opacity()) else Modifier

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .defaultMinSize(minWidth = style.minWidth, minHeight = style.minHeight)
                .height(IntrinsicSize.Min)
                .then(disabledAlpha)
                .clip(shape)
                .background(sc.surfaceFill)
                .border(sc.borderWidth, sc.border, shape),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StepperButton(
            iconRes = R.drawable.e71f_button_minus,
            contentDescription = "Decrease",
            enabled = effectiveEnabled && state.decrementEnabled,
            style = style,
            btn = btn,
            onClick = interactionConfig.onDecrement,
            modifier = Modifier.fillMaxHeight(),
        )

        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            ValueContent(
                sc = sc,
                style = style,
                label = content.label,
                leadingIcon = content.leadingIcon,
                trailingIcon = content.trailingIcon,
            )
        }

        StepperButton(
            iconRes = R.drawable.e71e_button_plus,
            contentDescription = "Increase",
            enabled = effectiveEnabled && state.incrementEnabled,
            style = style,
            btn = btn,
            onClick = interactionConfig.onIncrement,
            modifier = Modifier.fillMaxHeight(),
        )
    }
}

@Composable
private fun ValueContent(
    sc: StepperStateColors,
    style: StepperStyle,
    label: (@Composable () -> Unit)?,
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
) {
    val showLeading = leadingIcon != null
    val showTrailing = trailingIcon != null && !showLeading

    Row(
        horizontalArrangement = Arrangement.spacedBy(style.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showLeading) {
            CompositionLocalProvider(LocalContentColor provides sc.iconColor) {
                leadingIcon()
            }
        }

        if (label != null) {
            CompositionLocalProvider(
                LocalContentColor provides sc.labelColor,
                LocalTextStyle provides style.labelTextStyle.copy(color = sc.labelColor),
            ) {
                label()
            }
        }

        if (showTrailing) {
            CompositionLocalProvider(LocalContentColor provides sc.iconColor) {
                trailingIcon()
            }
        }
    }
}

// TODO: Replace StepperButton with the shared ComponentButton widget once it is
//  implemented. The Stepper should then delegate to ComponentButton for the −/+
//  buttons instead of maintaining its own button composable.
// TODO: Once ComponentButton is adopted, remove all ComponentButton-specific tokens
//  from StepperButtonColors, StepperStyle, and StepperDefaults (e.g. surfaceFill,
//  strokeColor/Width, iconColor, stateLayerPressed, disabled* variants,
//  buttonCornerRadius, buttonStateLayer*, buttonTouch*, iconWidth, iconHeight).
@Suppress("LongParameterList", "ForbiddenComment")
@Composable
private fun StepperButton(
    iconRes: Int,
    contentDescription: String,
    enabled: Boolean,
    style: StepperStyle,
    btn: StepperButtonColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val clickOptions = ClickOptions(onClick = onClick)

    val disabledAlpha =
        if (!enabled) Modifier.alpha(Sem.Opacity.Disabled.opacity()) else Modifier

    val btnShape = RoundedCornerShape(style.buttonCornerRadius)

    Box(
        modifier =
            modifier
                .defaultMinSize(
                    minWidth = style.buttonTouchMinWidth,
                    minHeight = style.buttonTouchHeight,
                ).then(disabledAlpha)
                .interactiveClickable(
                    clickOptions = clickOptions,
                    interactionSource = interactionSource,
                    enabled = enabled,
                    indication = null,
                ),
        contentAlignment = Alignment.Center,
    ) {
        val resolvedStroke = if (enabled) btn.strokeColor else btn.disabledStrokeColor
        val resolvedStrokeWidth = if (enabled) btn.strokeWidth else btn.disabledStrokeWidth
        val resolvedIconColor = if (enabled) btn.iconColor else btn.disabledIconColor

        Box(
            modifier =
                Modifier
                    .defaultMinSize(
                        minWidth = style.buttonStateLayerMinWidth,
                        minHeight = style.buttonStateLayerHeight,
                    ).clip(btnShape)
                    .background(btn.surfaceFill)
                    .border(resolvedStrokeWidth, resolvedStroke, btnShape),
            contentAlignment = Alignment.Center,
        ) {
            if (isPressed && enabled) {
                Box(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .background(btn.stateLayerPressed, btnShape),
                )
            }

            Icon(
                painter = painterResource(iconRes),
                contentDescription = contentDescription,
                modifier =
                    Modifier.size(
                        width = style.iconWidth,
                        height = style.iconHeight,
                    ),
                tint = resolvedIconColor,
            )
        }
    }
}
