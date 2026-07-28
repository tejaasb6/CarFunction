package com.ui.audi.widgets.radiobuttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.radiobuttons.LocalRadioButtonStyle
import com.ui.core.widgets.radiobuttons.RadioButtonContent
import com.ui.core.widgets.radiobuttons.RadioButtonInteractionConfig
import com.ui.core.widgets.radiobuttons.RadioButtonState
import com.ui.core.widgets.radiobuttons.RadioButtonStateColors
import com.ui.core.widgets.radiobuttons.RadioButtonStyle
import com.ui.core.widgets.semanticshapes.LocalSemanticShapeStyle
import com.ui.core.widgets.semanticshapes.SemanticShape
import com.ui.core.widgets.semanticshapes.SemanticShapeConfig
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState

/**
 * Audi brand implementation of [com.ui.core.widgets.radiobuttons.RadioButton].
 *
 * **Internal** — app code must not call this directly.
 * Use [com.ui.core.widgets.radiobuttons.RadioButton] instead.
 *
 * Selection state and callback are read from [RadioButtonInteractionConfig.selected] and
 * [RadioButtonInteractionConfig.onSelectedChange] (inherited from [com.ui.core.interaction.SelectionConfig]).
 *
 * ## Layout structure (matches Figma)
 * ```
 * Surface (VERTICAL, paddingLeft = surfaceLeftPadding)
 *   Control + Label (HORIZONTAL, paddingTop = labelTopPadding, gap = controlLabelSpacing)
 *     ControlWrapper (RadioControl circle)
 *     Label + Appendix (paddingLeft = labelLeftPadding)
 *   HintWrapper (paddingLeft = hintLeftPadding)
 *   ErrorWrapper (paddingLeft = errorLeftPadding, gap = errorIconGap)
 * ```
 *
 * ## State priority (highest -> lowest)
 * 1. Disabled  -- muted via opacity, non-interactive
 * 2. Pressed   -- pressed colour set
 * 3. Idle      -- default colour set
 *
 * ## Interaction stack (applied in order)
 * 1. Focus ring -- drawn outside the control circle via `drawWithContent`
 * 2. [interactiveClickable] -- tap - brand ripple
 *
 * ## UX restrictions
 * When [RadioButtonInteractionConfig.isDistractionOptimized] is `false` and the car is
 * moving, the radio button is automatically disabled regardless of the [state] parameter.
 */
@Suppress("CyclomaticComplexMethod", "LongMethod")
@Composable
internal fun AudiRadioButton(
    modifier: Modifier = Modifier,
    content: RadioButtonContent = RadioButtonContent(),
    state: RadioButtonState = RadioButtonState(),
    interactionConfig: RadioButtonInteractionConfig = RadioButtonInteractionConfig(),
) {
    val style = LocalRadioButtonStyle.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    // UX Restrictions: auto-disable when driving and not distraction-optimized
    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled =
        state.enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    // When disabled, error state is unavailable and cannot be controlled.
    val effectiveError = state.isError && effectiveEnabled

    val colors = resolveColors(style, interactionConfig.selected, effectiveError, effectiveEnabled, isPressed)
    val disabledAlpha = if (effectiveEnabled) 1f else style.disabledOpacity

    val controlShape = CircleShape

    val clickOptions =
        ClickOptions(
            onClick = { if (!interactionConfig.selected) interactionConfig.onSelectedChange?.invoke(true) },
        )

    val hasLabel = content.label != EmptyTextResource
    val hasHint = hasLabel && content.hint != EmptyTextResource
    val hasAppendix = hasLabel && content.appendix != EmptyTextResource
    val hasError = hasLabel && effectiveError && content.error != EmptyTextResource

    // Outer container: matches Figma "Surface" (VERTICAL layout)
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minWidth = style.touchTargetWidth,
                    minHeight = style.touchTargetSize,
                ).interactiveClickable(
                    clickOptions = clickOptions,
                    interactionSource = interactionSource,
                    enabled = effectiveEnabled,
                    indication = null,
                ).padding(start = style.surfaceLeftPadding),
    ) {
        // Control + Label row
        ControlLabelRow(
            style = style,
            colors = colors,
            controlShape = controlShape,
            disabledAlpha = disabledAlpha,
            interactionConfig = interactionConfig,
            interactionSource = interactionSource,
            isFocused = isFocused,
            effectiveEnabled = effectiveEnabled,
            content = content,
            hasLabel = hasLabel,
            hasAppendix = hasAppendix,
        )

        // Hint or spacer row
        HintOrSpacer(
            style = style,
            colors = colors,
            content = content,
            hasHint = hasHint,
            hasError = hasError,
        )

        // Error row
        if (hasError) {
            ErrorRow(style = style, colors = colors, content = content)
        }
    }
}

// ── Extracted layout sections ────────────────────────────────────────────────────

/** Control circle + Label + Appendix row. */
@Composable
private fun ControlLabelRow(
    style: RadioButtonStyle,
    colors: RadioButtonStateColors,
    controlShape: Shape,
    disabledAlpha: Float,
    interactionConfig: RadioButtonInteractionConfig,
    interactionSource: MutableInteractionSource,
    isFocused: Boolean,
    effectiveEnabled: Boolean,
    content: RadioButtonContent,
    hasLabel: Boolean,
    hasAppendix: Boolean,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = style.labelTopPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(style.controlLabelSpacing),
    ) {
        RadioControl(
            style = style,
            colors = colors,
            controlShape = controlShape,
            disabledAlpha = disabledAlpha,
            interactionConfig = interactionConfig,
            interactionSource = interactionSource,
            isFocused = isFocused,
            effectiveEnabled = effectiveEnabled,
        )

        if (hasLabel || hasAppendix) {
            LabelAppendixRow(
                style = style,
                colors = colors,
                content = content,
                hasLabel = hasLabel,
                hasAppendix = hasAppendix,
            )
        }
    }
}

/** Radio control circle with focus ring, state layer, and selection indicator. */
@Composable
private fun RadioControl(
    style: RadioButtonStyle,
    colors: RadioButtonStateColors,
    controlShape: Shape,
    disabledAlpha: Float,
    interactionConfig: RadioButtonInteractionConfig,
    interactionSource: MutableInteractionSource,
    isFocused: Boolean,
    effectiveEnabled: Boolean,
) {
    val focusModifier =
        if (interactionConfig.focusRequester != null) {
            Modifier.focusRequester(interactionConfig.focusRequester!!)
        } else {
            Modifier
        }
    val borderModifier =
        if (colors.controlStrokeWidth > 0.dp) {
            Modifier.border(colors.controlStrokeWidth, colors.controlStroke, controlShape)
        } else {
            Modifier
        }

    Box(
        modifier =
            Modifier
                .size(style.controlSize)
                .alpha(disabledAlpha)
                .then(focusModifier)
                .focusable(interactionSource = interactionSource)
                .drawWithContent {
                    drawContent()
                    if (isFocused && effectiveEnabled) {
                        val ringWidthPx = style.focusRingWidth.toPx()
                        val outerRadius = size.minDimension / 2f + ringWidthPx / 2f
                        drawCircle(
                            color = style.focusRingColor,
                            radius = outerRadius,
                            style = Stroke(width = ringWidthPx),
                        )
                    }
                }.clip(controlShape)
                .background(colors.controlFill)
                .then(borderModifier),
        contentAlignment = Alignment.Center,
    ) {
        if (colors.stateLayerColor != Color.Transparent) {
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .background(colors.stateLayerColor, controlShape),
            )
        }

        if (interactionConfig.selected) {
            Box(
                modifier =
                    Modifier
                        .size(style.indicatorSize)
                        .clip(CircleShape)
                        .background(colors.indicatorTint),
            )
        }
    }
}

/** Label and appendix text laid out in a weighted row. */
@Composable
private fun RowScope.LabelAppendixRow(
    style: RadioButtonStyle,
    colors: RadioButtonStateColors,
    content: RadioButtonContent,
    hasLabel: Boolean,
    hasAppendix: Boolean,
) {
    Row(
        modifier =
            Modifier
                .weight(1f)
                .padding(start = style.labelLeftPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (hasLabel) {
            Text(
                state = TextState(text = content.label, maxLines = 1),
                modifier = Modifier.weight(1f),
                style = style.labelTextStyle.copy(color = colors.labelColor),
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (hasAppendix) {
            Text(
                state = TextState(text = content.appendix, maxLines = 1),
                style = style.appendixTextStyle.copy(color = colors.appendixColor),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/** Hint text row, or a spacer when hint is absent but error is visible. */
@Composable
private fun HintOrSpacer(
    style: RadioButtonStyle,
    colors: RadioButtonStateColors,
    content: RadioButtonContent,
    hasHint: Boolean,
    hasError: Boolean,
) {
    if (hasHint) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = style.hintLeftPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                state = TextState(text = content.hint, maxLines = 1),
                style = style.hintTextStyle.copy(color = colors.hintColor),
                overflow = TextOverflow.Ellipsis,
            )
        }
    } else if (hasError) {
        Spacer(Modifier.height(style.hintTextStyle.lineHeight.value.dp))
    }
}

/** Error icon (semantic shape triangle) and error caption text. */
@Composable
private fun ErrorRow(
    style: RadioButtonStyle,
    colors: RadioButtonStateColors,
    content: RadioButtonContent,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = style.errorLeftPadding),
        horizontalArrangement = Arrangement.spacedBy(style.errorIconGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val baseShapeStyle = LocalSemanticShapeStyle.current
        val overriddenShapeStyle =
            baseShapeStyle.copy(
                containerDimension = style.errorIconContainerSize,
                triangleWidth = style.errorIconWidth,
                triangleHeight = style.errorIconHeight,
            )
        CompositionLocalProvider(LocalSemanticShapeStyle provides overriddenShapeStyle) {
            SemanticShape(
                config = SemanticShapeConfig(variant = SemanticShapeConfig.Variant.Critical),
            )
        }
        Text(
            state = TextState(text = content.error, maxLines = 1),
            style = style.errorTextStyle.copy(color = colors.errorColor),
            overflow = TextOverflow.Ellipsis,
        )
    }
}

// State colour resolver
// Disabled > Error > Pressed > Idle priority, matching the Figma spec.
@Suppress("CyclomaticComplexMethod") // Flat state-matrix lookup — each branch is a direct mapping
private fun resolveColors(
    style: RadioButtonStyle,
    selected: Boolean,
    isError: Boolean,
    enabled: Boolean,
    isPressed: Boolean,
): RadioButtonStateColors =
    when {
        selected && !enabled -> style.selectedDefaultDisabled
        !selected && !enabled -> style.unselectedDefaultDisabled
        selected && isError && isPressed -> style.selectedErrorPressed
        selected && isError -> style.selectedError
        !selected && isError && isPressed -> style.unselectedErrorPressed
        !selected && isError -> style.unselectedError
        selected && isPressed -> style.selectedDefaultPressed
        selected -> style.selectedDefault
        !selected && isPressed -> style.unselectedDefaultPressed
        else -> style.unselectedDefault
    }
