package com.ui.audi.widgets.checkbox

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ui.core.engine.api.Sem
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.checkbox.CheckboxContent
import com.ui.core.widgets.checkbox.CheckboxInteractionConfig
import com.ui.core.widgets.checkbox.CheckboxState
import com.ui.core.widgets.checkbox.CheckboxStateColors
import com.ui.core.widgets.checkbox.CheckboxStyle
import com.ui.core.widgets.checkbox.LocalCheckboxStyle
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.semanticshapes.LocalSemanticShapeStyle
import com.ui.core.widgets.semanticshapes.SemanticShape
import com.ui.core.widgets.semanticshapes.SemanticShapeConfig
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.text.TextConfig
import com.ui.core.widgets.text.TextState
import com.ui.audi.widgets.text.Text as AudiText

@Suppress("CyclomaticComplexMethod", "LongMethod")
@Composable
internal fun AudiCheckbox(
    modifier: Modifier = Modifier,
    content: CheckboxContent = CheckboxContent(),
    state: CheckboxState = CheckboxState(),
    interactionConfig: CheckboxInteractionConfig = CheckboxInteractionConfig(),
) {
    val style = LocalCheckboxStyle.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val systemFocused by interactionSource.collectIsFocusedAsState()
    val isFocused = systemFocused || interactionConfig.isFocused

    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled =
        state.enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    // When disabled there is no use-case for error – turn it off entirely so
    // the checkbox renders the normal disabled appearance without any error
    // styling or error-row UI.
    val effectiveIsError = state.isError && effectiveEnabled

    val colors = resolveColors(style, interactionConfig.selected, effectiveIsError, effectiveEnabled, isPressed)
    val disabledAlpha = if (effectiveEnabled) 1f else Sem.Opacity.Disabled.opacity()

    val controlShape = RoundedCornerShape(style.controlCornerRadius)

    val clickOptions =
        ClickOptions(
            onClick = { interactionConfig.onSelectedChange?.invoke(!interactionConfig.selected) },
        )

    val hasLabel = content.label != null
    val hasHint = content.hint != null
    val hasAppendix = content.appendix != null
    val hasError = effectiveIsError && content.error != null

    val outerFocusShape = RoundedCornerShape(style.controlCornerRadius + style.focusRingGap)

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
                ).padding(start = style.contentPadding),
    ) {
        // ── Control + Label row (center-aligned as per Figma) ─────────────
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = style.labelTopPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(style.controlLabelSpacing),
        ) {
            // ── Control box ───────────────────────────────────────────────
            Box(
                modifier =
                    Modifier
                        .alpha(disabledAlpha)
                        .then(
                            if (interactionConfig.focusRequester != null) {
                                Modifier.focusRequester(interactionConfig.focusRequester!!)
                            } else {
                                Modifier
                            },
                        ).focusable(enabled = effectiveEnabled, interactionSource = interactionSource)
                        .then(
                            if (isFocused && effectiveEnabled) {
                                Modifier
                                    .border(style.focusRingWidth, style.focusRingColor, outerFocusShape)
                                    .padding(style.focusRingGap)
                            } else {
                                Modifier.padding(style.focusRingGap)
                            },
                        ).then(
                            if (isFocused && effectiveEnabled) {
                                Modifier.border(
                                    style.focusInnerBorderWidth,
                                    style.focusInnerBorderColor,
                                    controlShape,
                                )
                            } else {
                                Modifier
                            },
                        ).size(style.controlSize)
                        .clip(controlShape)
                        .background(colors.controlFill)
                        .then(
                            if (colors.controlStrokeWidth > 0.dp) {
                                Modifier.border(
                                    colors.controlStrokeWidth,
                                    colors.controlStroke,
                                    controlShape,
                                )
                            } else {
                                Modifier
                            },
                        ),
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
                    CompositionLocalProvider(LocalContentColor provides colors.iconTint) {
                        Icon(
                            source =
                                IconSource.Vector(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                ),
                            config =
                                IconConfig(
                                    size = IconConfig.Size.SM,
                                    hasTintingColors = false,
                                ),
                            modifier = Modifier.size(style.controlSize * 0.7f),
                        )
                    }
                }
            }

            // ── Label + Appendix ──────────────────────────────────────────
            if (hasLabel || hasAppendix) {
                Row(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = style.labelLeftPadding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    content.label?.let { labelText ->
                        AudiText(
                            config = TextConfig(type = TextConfig.Type.Truncatable),
                            modifier = Modifier.weight(1f, fill = false),
                            state = TextState(text = labelText, maxLines = 1),
                            overflow = TextOverflow.Ellipsis,
                            style = style.labelTextStyle.copy(color = colors.labelColor),
                        )
                    }
                    content.appendix?.let { appendixText ->
                        AudiText(
                            config = TextConfig(type = TextConfig.Type.Truncatable),
                            state = TextState(text = appendixText, maxLines = 1),
                            overflow = TextOverflow.Ellipsis,
                            style = style.appendixTextStyle.copy(color = colors.appendixColor),
                        )
                    }
                }
            }
        }

        // ── Hint ──────────────────────────────────────────────────────────
        // Always emit the hint area when either hint or error is active so
        // the error row keeps its Figma-defined vertical position even when
        // hint text is toggled off.
        if (hasHint || hasError) {
            Spacer(Modifier.height(style.hintSpacing))
            if (hasHint) {
                content.hint?.let { hintText ->
                    AudiText(
                        config = TextConfig(type = TextConfig.Type.Truncatable),
                        modifier = Modifier.padding(start = style.hintLeftPadding),
                        state = TextState(text = hintText, maxLines = 1),
                        overflow = TextOverflow.Ellipsis,
                        style = style.hintTextStyle.copy(color = colors.hintColor),
                    )
                }
            } else {
                // Hint is off — render an invisible placeholder that occupies
                // exactly the same height as a real hint line so the error
                // row below does not shift upward.
                AudiText(
                    config = TextConfig(type = TextConfig.Type.Truncatable),
                    modifier = Modifier.padding(start = style.hintLeftPadding),
                    state = TextState(text = " ".TR, maxLines = 1),
                    overflow = TextOverflow.Ellipsis,
                    style = style.hintTextStyle.copy(color = Color.Transparent),
                )
            }
        }

        // ── Error row ─────────────────────────────────────────────────────
        if (hasError) {
            Spacer(Modifier.height(style.errorSpacing))
            Row(
                modifier = Modifier.padding(start = style.errorLeftPadding),
                horizontalArrangement = Arrangement.spacedBy(style.errorIconGap),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val baseShapeStyle = LocalSemanticShapeStyle.current
                val overriddenShapeStyle =
                    baseShapeStyle.copy(
                        containerDimension = maxOf(style.errorIconWidth, style.errorIconHeight),
                        triangleWidth = style.errorIconWidth,
                        triangleHeight = style.errorIconHeight,
                    )
                CompositionLocalProvider(LocalSemanticShapeStyle provides overriddenShapeStyle) {
                    SemanticShape(
                        config = SemanticShapeConfig(variant = SemanticShapeConfig.Variant.Critical),
                    )
                }
                content.error?.let { errorText ->
                    AudiText(
                        config = TextConfig(type = TextConfig.Type.Truncatable),
                        state = TextState(text = errorText, maxLines = 1),
                        overflow = TextOverflow.Ellipsis,
                        style = style.errorTextStyle.copy(color = colors.errorColor),
                    )
                }
            }
        }
    }
}

@Suppress("CyclomaticComplexMethod")
private fun resolveColors(
    style: CheckboxStyle,
    checked: Boolean,
    isError: Boolean,
    enabled: Boolean,
    isPressed: Boolean,
): CheckboxStateColors =
    when {
        checked && isError && isPressed -> style.selectedErrorPressed
        checked && isError -> style.selectedError
        checked && !enabled -> style.selectedDefaultDisabled
        checked && isPressed -> style.selectedDefaultPressed
        checked -> style.selectedDefault
        !checked && isError && isPressed -> style.unselectedErrorPressed
        !checked && isError -> style.unselectedError
        !checked && !enabled -> style.unselectedDefaultDisabled
        !checked && isPressed -> style.unselectedDefaultPressed
        else -> style.unselectedDefault
    }
