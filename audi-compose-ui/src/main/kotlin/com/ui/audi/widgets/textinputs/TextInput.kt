package com.ui.audi.widgets.textinputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.ui.audi.theme.AudiFont
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.semanticshapes.SemanticShape
import com.ui.core.widgets.semanticshapes.SemanticShapeConfig
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextResource
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.textinputs.LocalTextInputStyle
import com.ui.core.widgets.textinputs.TextInputConfig
import com.ui.core.widgets.textinputs.TextInputContent
import com.ui.core.widgets.textinputs.TextInputInteractionConfig
import com.ui.core.widgets.textinputs.TextInputSlots
import com.ui.core.widgets.textinputs.TextInputState
import com.ui.core.widgets.textinputs.TextInputStyle
import com.ui.core.widgets.textinputs.branchFor
import com.ui.core.widgets.textinputs.stateFor

/**
 * Audi-branded Text Input widget following exact Figma anatomy.
 *
 * Implements 4-section structure from Figma:
 * 1. Label + Appendix (optional)
 * 2. Input field with component-button icons/units + Info button
 * 3. Semantic password strength indicator (Password variant only)
 * 4. Error/Hint/Password-hint text
 *
 * Component buttons (clear, eye, mic, info) use:
 *   Touch target: Cmp.Size.Action.ComponentButton.MD.TouchTarget
 *   Icon size:    IconConfig.Size.MD (48x48 px)
 * All centered vertically within the input container.
 */
@Suppress("LongMethod", "LongParameterList", "CyclomaticComplexMethod")
@Composable
internal fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    config: TextInputConfig,
    modifier: Modifier,
    state: TextInputState,
    content: TextInputContent,
    slots: TextInputSlots,
    interactionConfig: TextInputInteractionConfig,
) {
    val style = LocalTextInputStyle.current
    val branch = style.branchFor(state.isError && !state.isLoading && !state.isReadOnly)
    val interactionSource = remember { MutableInteractionSource() }
    val isRawPressed by interactionSource.collectIsPressedAsState()
    val isRealFocused by interactionSource.collectIsFocusedAsState()
    val isPressed = isRawPressed && !state.isReadOnly
    val isActive = (isRealFocused || state.isActive) && !state.isLoading && !state.isReadOnly
    val isFocused = state.isFocused

    // ── UX Restrictions: auto-disable when driving and not distraction-optimized ─
    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled = state.enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)
    val isInteractive = effectiveEnabled && !state.isLoading

    val effectiveLoading = state.isLoading && value.isNotEmpty()

    val stateColors =
        branch.stateFor(
            enabled = effectiveEnabled,
            isLoading = effectiveLoading,
            isReadOnly = state.isReadOnly,
            isPressed = isPressed,
            isActive = isActive,
            isFocused = isFocused,
        )

    val shape = RoundedCornerShape(style.cornerRadius)
    val borderWidthDp =
        when {
            !effectiveEnabled ->
                Cmp.BorderWidth.Forms.FormFields.Default.Disabled
                    .dimension()
                    .pxToDp()
            state.isLoading && value.isEmpty() ->
                Cmp.BorderWidth.Forms.FormFields.Default.Idle
                    .dimension()
                    .pxToDp()
            state.isLoading ->
                Cmp.BorderWidth.Forms.FormFields.Default.Loading
                    .dimension()
                    .pxToDp()
            isPressed ->
                Cmp.BorderWidth.Forms.FormFields.Default.Pressed
                    .dimension()
                    .pxToDp()
            isActive ->
                Cmp.BorderWidth.Forms.FormFields.Default.Active
                    .dimension()
                    .pxToDp()
            else ->
                Cmp.BorderWidth.Forms.FormFields.Default.Idle
                    .dimension()
                    .pxToDp()
        }

    val disabledAlphaModifier = if (!effectiveEnabled) Modifier.alpha(Sem.Opacity.Disabled.opacity()) else Modifier
    val focusRingWidth =
        Sem.BorderWidth.FocusRing
            .dimension()
            .pxToDp()
    val focusRingColor =
        Sem.Color.Stroke.Signal.Focus
            .color()
    val componentBtnHeight =
        Cmp.Size.Action.ComponentButton.MD.TouchTarget.Height
            .dimension()
            .pxToDp()
    val componentBtnMinWidth =
        Cmp.Size.Action.ComponentButton.MD.TouchTarget.MinWidth
            .dimension()
            .pxToDp()

    val displayIconHeight =
        Cmp.Size.DataDisplay.Icon.MD.Height
            .dimension()
            .pxToDp()
    val displayIconMinWidth =
        Cmp.Size.DataDisplay.Icon.MD.MinWidth
            .dimension()
            .pxToDp()

    // Cross (clear) / Progress icon size: Cmp.Size.Action.ComponentButton.MD.StateLayer
    val stateLayerIconHeight =
        Cmp.Size.Action.ComponentButton.MD.StateLayer.Height
            .dimension()
            .pxToDp()
    val stateLayerIconMinWidth =
        Cmp.Size.Action.ComponentButton.MD.StateLayer.MinWidth
            .dimension()
            .pxToDp()

    // Password visibility: use state.passwordVisible to determine masking
    val isPasswordMasked = config.variant == TextInputConfig.Variant.Password && !state.passwordVisible

    // Info popover state
    var showInfoPopover by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        // ═══════════════════════════════════════════════════════════════════
        // SECTION 1: Label + Appendix (from Figma)
        // ═══════════════════════════════════════════════════════════════════
        if (config.showLabel && content.label != EmptyTextResource) {
            Box(modifier = disabledAlphaModifier) {
                LabelRow(
                    label = content.label,
                    appendix = if (config.showAppendix) content.appendix else EmptyTextResource,
                    labelColor = stateColors.labelColor,
                    appendixColor = stateColors.appendixColor,
                    appendixTextStyle = style.captionTextStyle,
                    style = style,
                    bottomPadding = style.labelGap,
                )
            }
        }

        // ═══════════════════════════════════════════════════════════════════
        // SECTION 2: Input container + Info button (from Figma)
        // Touch target wraps the surface; no gap before/after component buttons.
        // ═══════════════════════════════════════════════════════════════════
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(style.touchTargetHeight),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                // Input field container — surface height
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(style.minHeight)
                            .then(disabledAlphaModifier)
                            .then(
                                if (interactionConfig.focusRequester != null) {
                                    Modifier.focusRequester(interactionConfig.focusRequester!!)
                                } else {
                                    Modifier
                                },
                            ).clip(shape)
                            .background(stateColors.surfaceFill)
                            .border(borderWidthDp, stateColors.border, shape),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    // State layer for pressed state
                    if (isPressed && isInteractive) {
                        Box(modifier = Modifier.matchParentSize().background(branch.stateLayerPressed))
                    }

                    // Horizontal padding wrapper — fills parent height
                    Row(
                        modifier =
                            Modifier
                                .fillMaxSize(),
                    ) {
                        // Determine which leading elements are present
                        val hasLeadingIcon = config.showLeadingIcon && slots.leadingIcon != null
                        val hasLeadingUnit = config.showLeadingUnit && content.leadingUnit != EmptyTextResource

                        // ── Content Box: Leading Icon + Leading Unit + Input Field ──
                        // Uses Gap spacingt between children, fills parent height
                        Row(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(horizontal = style.paddingHorizontal),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(style.iconSpacing),
                        ) {
                            // ── Leading Icon ──
                            if (hasLeadingIcon) {
                                CompositionLocalProvider(LocalContentColor provides stateColors.iconColor) {
                                    Box(
                                        modifier =
                                            Modifier.size(
                                                width = displayIconMinWidth,
                                                height = displayIconHeight,
                                            ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        slots.leadingIcon?.invoke()
                                    }
                                }
                            }

                            // ── Leading Unit ──
                            if (hasLeadingUnit) {
                                Text(
                                    state = TextState(text = content.leadingUnit),
                                    style = style.unitTextStyle.copy(color = stateColors.unitColor),
                                )
                            }

                            // ── Input field ──
                            Box(modifier = Modifier.weight(1f)) {
                                InputField(
                                    value = value,
                                    onValueChange = onValueChange,
                                    placeholder = content.placeholder,
                                    isInteractive = isInteractive,
                                    isReadOnly = state.isReadOnly,
                                    isPassword = isPasswordMasked,
                                    valueTextStyle = style.valueTextStyle,
                                    placeholderTextStyle = style.placeholderTextStyle,
                                    valueColor = stateColors.valueColor,
                                    placeholderColor = stateColors.placeholderColor,
                                    cursorColor = style.cursorColor,
                                    cursorWidth = style.cursorWidth,
                                    cursorHeight = style.cursorHeight,
                                    interactionSource = interactionSource,
                                )
                            }
                        }

                        // ── Trailing Unit (outside content box, with Gap on left + R-Padding on right) ──
                        if (config.showTrailingUnit && content.trailingUnit != EmptyTextResource) {
                            Text(
                                state = TextState(text = content.trailingUnit),
                                modifier =
                                    Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(
                                            start = style.iconSpacing,
                                            end = style.trailingExtensionRPadding,
                                        ),
                                style = style.unitTextStyle.copy(color = stateColors.unitColor),
                            )
                        }
                        when {
                            state.isLoading && value.isNotEmpty() -> {
                                Row(
                                    modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = style.paddingHorizontal),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    CircularProgressIndicator(
                                        color = style.spinnerTrainColor,
                                        modifier =
                                            Modifier
                                                .align(Alignment.CenterVertically)
                                                .size(style.spinnerSize),
                                        strokeWidth = style.spinnerStrokeWidth,
                                        trackColor = style.spinnerTrackColor,
                                    )
                                }
                            }
                            else -> {
                                // No gap before or after component buttons
                                Row(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    // Password visibility toggle (eye icon)
                                    // Hidden until text input has been made
                                    if (config.showPasswordVisibilityButton &&
                                        config.variant == TextInputConfig.Variant.Password &&
                                        value.isNotEmpty()
                                    ) {
                                        val visBtn = slots.passwordVisibilityButton
                                        if (visBtn != null) {
                                            visBtn()
                                        } else {
                                            val onToggle = interactionConfig.onPasswordVisibilityToggle
                                            if (onToggle != null) {
                                                // TODO need to replace with ComponentButton
                                                ComponentActionButton(
                                                    onClick = onToggle,
                                                    icon =
                                                        if (state.passwordVisible) {
                                                            Icons.Filled.VisibilityOff
                                                        } else {
                                                            Icons.Filled.Visibility
                                                        },
                                                    description = if (state.passwordVisible) "Hide password" else "Show password",
                                                    enabled = isInteractive,
                                                    touchTargetHeight = componentBtnHeight,
                                                    touchTargetMinWidth = componentBtnMinWidth,
                                                    iconHeight = displayIconHeight,
                                                    iconMinWidth = displayIconMinWidth,
                                                )
                                            }
                                        }
                                    }

                                    // Microphone button — visible in all states, disabled when not interactive
                                    if (config.showMicrophoneButton) {
                                        val micBtn = slots.microphoneButton
                                        if (micBtn != null) {
                                            micBtn()
                                        } else {
                                            val onMic = interactionConfig.onMicrophoneClick
                                            // TODO need to replace with ComponentButton
                                            ComponentActionButton(
                                                onClick = onMic ?: {},
                                                icon = Icons.Filled.Mic,
                                                description = "Voice input",
                                                enabled = isInteractive && onMic != null,
                                                touchTargetHeight = componentBtnHeight,
                                                touchTargetMinWidth = componentBtnMinWidth,
                                                iconHeight = stateLayerIconHeight,
                                                iconMinWidth = stateLayerIconMinWidth,
                                            )
                                        }
                                    }

                                    if (config.showClearButton && value.isNotEmpty() && !state.isReadOnly) {
                                        val clearBtn = slots.clearButton
                                        if (clearBtn != null) {
                                            clearBtn()
                                        } else {
                                            val onClear = interactionConfig.onClearClick ?: { onValueChange("") }
                                            // TODO need to replace with ComponentButton
                                            ComponentActionButton(
                                                onClick = onClear,
                                                icon = Icons.Filled.Close,
                                                description = "Clear",
                                                enabled = isInteractive,
                                                touchTargetHeight = componentBtnHeight,
                                                touchTargetMinWidth = componentBtnMinWidth,
                                                iconHeight = stateLayerIconHeight,
                                                iconMinWidth = stateLayerIconMinWidth,
                                            )
                                        }
                                    }

                                    // Custom trailing icon
                                    val trailingIcon = slots.trailingIcon
                                    if (trailingIcon != null) {
                                        CompositionLocalProvider(LocalContentColor provides stateColors.iconColor) {
                                            trailingIcon()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (isFocused) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(style.minHeight + focusRingWidth * 2)
                                .border(focusRingWidth, focusRingColor, shape),
                    )
                }
            } // End Surface + Focus Ring wrapper

            // Only shown for Password variant when config.showInfoButton is true.
            // Not shown for Default variant as per requirements.
            val onInfoClickAction = interactionConfig.onInfoClick
            val showInfo =
                config.showInfoButton && config.variant == TextInputConfig.Variant.Password
            if (showInfo) {
                Box {
                    val infoBtn = slots.infoButton
                    if (infoBtn != null) {
                        infoBtn()
                    } else {
                        // TODO need to replace with ComponentButton
                        ComponentActionButton(
                            onClick = {
                                showInfoPopover = !showInfoPopover
                                onInfoClickAction?.invoke()
                            },
                            icon = Icons.Filled.Info,
                            description = "Info",
                            enabled = isInteractive,
                            touchTargetHeight = componentBtnHeight,
                            touchTargetMinWidth = componentBtnMinWidth,
                            iconHeight = displayIconHeight,
                            iconMinWidth = displayIconMinWidth,
                        )
                    }

                    // Popover on info button click
                    if (showInfoPopover) {
                        val customPopover = slots.infoPopoverContent
                        if (customPopover != null) {
                            customPopover { showInfoPopover = false }
                        } else {
                            PasswordPolicyPopover(
                                content = content.infoPopoverContent,
                                onDismiss = { showInfoPopover = false },
                            )
                        }
                    }
                }
            }
        } // End outer Row

        // ═══════════════════════════════════════════════════════════════════
        // SECTION 3: Semantic Triangle + Strength + Caption (from Figma)
        //
        // Password variant:
        //   - Strength hint with triangle (Cmp.Color.Feedback.Badge.Label)
        //   - Error with triangle (Cmp.Color.Forms.FormFields.Caption.Error)
        //   - Both can show at the same time
        //
        // Default variant:
        //   - Hint: plain text, no triangle
        //   - Error: plain text, no triangle (Cmp.Color.Forms.FormFields.Caption.Error)
        // ═══════════════════════════════════════════════════════════════════
        val isPassword = config.variant == TextInputConfig.Variant.Password
        val hasPasswordHint = isPassword && content.passwordHint != EmptyTextResource

        if (hasPasswordHint && value.isNotEmpty() && !state.isReadOnly) {
            Box(modifier = disabledAlphaModifier) {
                val strengthIndicator = slots.passwordStrengthIndicator
                if (strengthIndicator != null) {
                    strengthIndicator()
                } else {
                    PasswordStrengthWithTriangle(
                        strength = state.passwordStrength,
                        hint = content.passwordHint.annotated.text,
                        style = style,
                    )
                }
            }
        }

        // ═══════════════════════════════════════════════════════════════════
        // SECTION 4: Hint + Error Caption Text
        // ═══════════════════════════════════════════════════════════════════

        val captionErrorTopPadding =
            Cmp.Space.Forms.FormFields.CaptionError.T_Padding
                .dimension()
                .pxToDp()
        val captionErrorGap =
            Cmp.Space.Forms.FormFields.CaptionError.Gap
                .dimension()
                .pxToDp()

        if (config.showHint) {
            // ── Hint text — Default variant only (no hint for Password variant) ──
            // Apply disabled alpha when not enabled
            if (!isPassword && content.hint != EmptyTextResource) {
                val hintText = content.hint.annotated.text
                Text(
                    state = TextState(text = content.hint),
                    modifier =
                        Modifier
                            .then(disabledAlphaModifier)
                            .padding(top = captionErrorTopPadding)
                            .semantics { contentDescription = hintText },
                    style = style.captionTextStyle.copy(color = stateColors.hintColor),
                )
            }

            @Suppress("ComplexCondition")
            if (state.isError && !state.isLoading && !state.isReadOnly && content.error != EmptyTextResource) {
                val errorText = content.error.annotated.text
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .then(disabledAlphaModifier)
                            .padding(top = captionErrorTopPadding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(captionErrorGap),
                ) {
                    SemanticShape(
                        config = SemanticShapeConfig(variant = SemanticShapeConfig.Variant.Critical),
                    )
                    Text(
                        state = TextState(text = content.error),
                        style = style.captionErrorTextStyle.copy(color = stateColors.errorColor),
                        modifier =
                            Modifier.semantics {
                                contentDescription = "Error: $errorText"
                            },
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper Composables
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun LabelRow(
    label: TextResource,
    appendix: TextResource,
    labelColor: Color,
    appendixColor: Color,
    appendixTextStyle: androidx.compose.ui.text.TextStyle,
    style: TextInputStyle,
    bottomPadding: androidx.compose.ui.unit.Dp,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = bottomPadding),
        horizontalArrangement = Arrangement.spacedBy(style.labelItemSpacing),
    ) {
        Text(
            state = TextState(text = label),
            style = style.labelTextStyle.copy(color = labelColor),
            modifier = Modifier.alignByBaseline(),
        )
        if (appendix != EmptyTextResource) {
            Text(
                state = TextState(text = appendix),
                style = appendixTextStyle.copy(color = appendixColor),
                modifier = Modifier.alignByBaseline(),
            )
        }
    }
}

/**
 * Input field using String-based BasicTextField for fast input handling.
 *
 * Cursor tokens:
 * - Color:  Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Active (via cursorBrush)
 * - Height: Cmp.Size.Forms.FormFields.Field.Cursor.Height (via minHeight constraint)
 * - Width:  Cmp.Size.Forms.FormFields.Field.Cursor.Width (via minWidth constraint)
 *
 * TODO: Custom Audi cursor with exact token width/height is not yet implemented.
 *  The String-based BasicTextField does not expose cursor selection offset, so a
 *  custom-drawn cursor cannot track the real cursor position for mid-text edits.
 *  Using TextFieldValue overload enables cursor tracking but causes input lag due
 *  to the TextFieldValue ↔ String sync round-trip on every keystroke.
 *  Options to resolve:
 *    1. Migrate to BasicTextField2 (Foundation 1.6+) which supports cursor customization.
 *    2. Investigate TextFieldValue with snapshot-based state to avoid recomposition lag.
 *  Until resolved, the native Android cursor is used with the correct Audi color token.
 */
@Suppress("ForbiddenComment")
@Composable
private fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: TextResource,
    isInteractive: Boolean,
    isReadOnly: Boolean,
    isPassword: Boolean,
    valueTextStyle: androidx.compose.ui.text.TextStyle,
    placeholderTextStyle: androidx.compose.ui.text.TextStyle,
    valueColor: Color,
    placeholderColor: Color,
    cursorColor: Color,
    cursorWidth: androidx.compose.ui.unit.Dp,
    cursorHeight: androidx.compose.ui.unit.Dp,
    interactionSource: MutableInteractionSource,
) {
    val visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    val keyboardOptions =
        if (isPassword) {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        } else {
            KeyboardOptions.Default
        }

    Box(contentAlignment = Alignment.CenterStart) {
        if (value.isEmpty() && placeholder != EmptyTextResource) {
            Text(
                state = TextState(text = placeholder),
                style = placeholderTextStyle.copy(color = placeholderColor),
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = isInteractive,
            readOnly = isReadOnly,
            singleLine = true,
            textStyle =
                valueTextStyle.copy(
                    color = valueColor,
                    fontFamily = AudiFont,
                    textDecoration = TextDecoration.None,
                ),
            // TODO: Replace with custom Audi cursor once BasicTextField2 or
            //  TextFieldValue performance issue is resolved. Currently uses
            //  native cursor with Audi color token.
            cursorBrush = SolidColor(cursorColor),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            modifier = Modifier.defaultMinSize(minWidth = cursorWidth, minHeight = cursorHeight),
        )
    }
}

/**
 * Component action button matching Figma specification:
 *   Touch target:  Cmp.Size.Action.ComponentButton.MD.StateLayer.Height / MinWidth
 *   Icon inside:   IconConfig.Size.MD (48×48 px)
 *
 * Rendered as a simple clickable Box so the icon is vertically centered
 * within the text input field without extra sizing from IconButton's own
 * touch target / state layer. The parent Row already provides
 * [Alignment.CenterVertically].
 */
@Composable
private fun ComponentActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
    description: String,
    enabled: Boolean,
    touchTargetHeight: androidx.compose.ui.unit.Dp,
    touchTargetMinWidth: androidx.compose.ui.unit.Dp,
    iconHeight: androidx.compose.ui.unit.Dp,
    iconMinWidth: androidx.compose.ui.unit.Dp,
) {
    Box(
        modifier =
            Modifier
                .size(width = touchTargetMinWidth, height = touchTargetHeight)
                .clip(RoundedCornerShape(50))
                .then(
                    if (enabled) {
                        Modifier.clickable(onClick = onClick)
                    } else {
                        Modifier.alpha(Sem.Opacity.Disabled.opacity())
                    },
                ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            source = IconSource.Vector(imageVector = icon, contentDescription = description),
            config = IconConfig(size = IconConfig.Size.MD),
            modifier = Modifier.size(width = iconMinWidth, height = iconHeight),
        )
    }
}

/**
 * Password strength indicator using [SemanticShape] — variant selected by strength level.
 *
 * - strength < 0.33 → Critical (triangle)
 * - strength < 0.66 → Advisory (diamond)
 * - else            → Positive (circle)
 */
@Composable
private fun PasswordStrengthWithTriangle(
    strength: Float,
    hint: String,
    style: TextInputStyle,
) {
    val variant =
        when {
            strength < 0.33f -> SemanticShapeConfig.Variant.Critical
            strength < 0.66f -> SemanticShapeConfig.Variant.Advisory
            else -> SemanticShapeConfig.Variant.Positive
        }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = style.captionGap),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(
                    Cmp.Space.Feedback.Badge.Gap
                        .dimension()
                        .pxToDp(),
                ),
        ) {
            SemanticShape(
                config = SemanticShapeConfig(variant = variant),
            )

            val badgeLabelColor =
                Cmp.Color.Feedback.Badge.Label
                    .color()
            Text(
                state = TextState(text = hint.TR),
                style = style.strengthTextStyle.copy(color = badgeLabelColor),
            )
        }
    }
}

/**
 * Popover shown when the info button is clicked.
 * Uses Figma Popover tokens for styling:
 *   BorderRadius: Cmp.BorderRadius.Layer.Popover.Default
 *   BorderWidth:  Cmp.BorderWidth.Layer.Popover.Default
 *   Fill:         Cmp.Color.Layer.Popover.Surface.Fill
 *   Stroke:       Cmp.Color.Layer.Popover.Surface.Stroke
 *   Padding:      Cmp.Space.Layer.Popover.Padding
 *   Typography:   Cmp.Color.Layer.Popover.Content.Copy
 *
 * @param content Text content to display in popover (from TextInputContent.infoPopoverContent)
 * @param onDismiss Callback when popover is dismissed
 */
@Composable
private fun PasswordPolicyPopover(
    content: TextResource,
    onDismiss: () -> Unit,
) {
    val popoverRadius =
        Cmp.BorderRadius.Layer.Popover.Default
            .dimension()
            .pxToDp()
    val popoverBorder =
        Cmp.BorderWidth.Layer.Popover.Default
            .dimension()
            .pxToDp()
    val popoverFill =
        Cmp.Color.Layer.Popover.Surface.Fill
            .color()
    val popoverStroke =
        Cmp.Color.Layer.Popover.Surface.Stroke
            .color()
    val popoverPadding =
        Cmp.Space.Layer.Popover.Padding
            .dimension()
            .pxToDp()
    val copyColor =
        Cmp.Color.Layer.Popover.Content.Copy
            .color()
    val popoverShadow =
        Cmp.Shadow.Layer.Popover.Default
            .boxShadow()

    val shape = RoundedCornerShape(popoverRadius)

    // TODO replace with Popover widget
    Popup(
        alignment = Alignment.TopStart,
        offset = IntOffset(0, 8),
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = true),
    ) {
        Box(
            modifier =
                Modifier
                    .graphicsLayer(
                        shadowElevation = popoverShadow.elevation,
                        shape = shape,
                        clip = false,
                    ).clip(shape)
                    .background(popoverFill, shape)
                    .border(popoverBorder, popoverStroke, shape)
                    .padding(popoverPadding),
        ) {
            Text(
                state = TextState(text = content),
                style = LocalTextInputStyle.current.captionTextStyle.copy(color = copyColor),
            )
        }
    }
}
