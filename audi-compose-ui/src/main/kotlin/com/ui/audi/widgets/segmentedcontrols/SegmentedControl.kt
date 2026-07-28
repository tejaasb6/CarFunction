package com.ui.audi.widgets.segmentedcontrols

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.buttons.Button
import com.ui.core.widgets.buttons.ButtonConfig
import com.ui.core.widgets.buttons.ButtonInteractionConfig
import com.ui.core.widgets.buttons.ButtonState
import com.ui.core.widgets.buttons.LocalButtonStyle
import com.ui.core.widgets.segmentedcontrols.LocalSegmentedControlStyle
import com.ui.core.widgets.segmentedcontrols.SegmentedControlConfig
import com.ui.core.widgets.segmentedcontrols.SegmentedControlInteractionConfig
import com.ui.core.widgets.segmentedcontrols.SegmentedControlSegment
import com.ui.core.widgets.segmentedcontrols.SegmentedControlSegments
import com.ui.core.widgets.segmentedcontrols.SegmentedControlState
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.Text as DesignText

/** Audi brand implementation of SegmentedControl, internally delegating to [Button]. */
@Suppress("LongParameterList", "LongMethod")
@Composable
internal fun SegmentedControl(
    config: SegmentedControlConfig,
    modifier: Modifier = Modifier,
    state: SegmentedControlState = SegmentedControlState(),
    segments: SegmentedControlSegments,
    interactionConfig: SegmentedControlInteractionConfig = SegmentedControlInteractionConfig(),
) {
    val style = LocalSegmentedControlStyle.current
    val restrictions = LocalUxRestrictions.current
    val globalEnabled =
        state.enabled &&
            (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    val selectedIndex = interactionConfig.selectedIndex
    val onSelectedIndexChange = interactionConfig.onSelectedIndexChange

    val wrapperShape = RoundedCornerShape(style.wrapperCornerRadius)
    val containerColors = style.containerColors

    // ── Outer Column: optional title + wrapper ─────────────────────────────────
    Column(modifier = modifier) {
        // ── Optional title label ───────────────────────────────────────────────
        val title = config.title
        if (title != null) {
            DesignText(
                state = TextState(text = title.TR, maxLines = 1),
                overflow = TextOverflow.Ellipsis,
                style = style.titleTextStyle.copy(color = containerColors.titleLabelColor),
                modifier = Modifier.padding(bottom = style.titleBottomPadding),
            )
        }

        // ── Wrapper container ──────────────────────────────────────────────────
        val wrapperBorderModifier =
            if (style.wrapperBorderWidth.value > 0f) {
                Modifier.border(
                    width = style.wrapperBorderWidth,
                    color = containerColors.surfaceStroke,
                    shape = wrapperShape,
                )
            } else {
                Modifier
            }

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(wrapperShape)
                    .background(containerColors.surfaceFill)
                    .then(wrapperBorderModifier)
                    .defaultMinSize(minHeight = style.wrapperHeight),
            contentAlignment = Alignment.Center,
        ) {
            // Provide the segment-specific ButtonStyle so each Button reads it
            CompositionLocalProvider(LocalButtonStyle provides style.buttonStyle) {
                when (config.orientation) {
                    SegmentedControlConfig.Orientation.Horizontal -> {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(style.wrapperPadding),
                            horizontalArrangement = Arrangement.spacedBy(style.wrapperGap),
                        ) {
                            for (index in 0 until segments.size) {
                                SegmentButton(
                                    config = config,
                                    segment = segments[index],
                                    isSelected = index == selectedIndex,
                                    enabled = globalEnabled && segments[index].enabled,
                                    isFocused = state.isFocused && index == selectedIndex,
                                    onClick = { onSelectedIndexChange(index) },
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }

                    SegmentedControlConfig.Orientation.Vertical -> {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(style.wrapperPadding),
                            verticalArrangement = Arrangement.spacedBy(style.wrapperGap),
                        ) {
                            for (index in 0 until segments.size) {
                                SegmentButton(
                                    config = config,
                                    segment = segments[index],
                                    isSelected = index == selectedIndex,
                                    enabled = globalEnabled && segments[index].enabled,
                                    isFocused = state.isFocused && index == selectedIndex,
                                    onClick = { onSelectedIndexChange(index) },
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ────────────────────────────────────────────────────────────────────────────────
// Individual segment — delegates to the public Button composable (no ripple)
// ────────────────────────────────────────────────────────────────────────────────

@Composable
private fun SegmentButton(
    config: SegmentedControlConfig,
    segment: SegmentedControlSegment,
    isSelected: Boolean,
    enabled: Boolean,
    isFocused: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val segStyle = LocalSegmentedControlStyle.current
    val labelTextStyle = if (isSelected) segStyle.selectedTextStyle else null

    val labelSlot: (@Composable () -> Unit)? =
        segment.label?.let { text ->
            {
                DesignText(
                    state = TextState(text = text.TR, maxLines = 1),
                    overflow = TextOverflow.Ellipsis,
                    style = labelTextStyle,
                )
            }
        }
    val iconSlot: (@Composable () -> Unit)? = segment.icon

    val leading: (@Composable () -> Unit)?
    val trailing: (@Composable () -> Unit)?

    when (config.variant) {
        SegmentedControlConfig.Variant.Label -> {
            leading = null
            trailing = null
        }
        SegmentedControlConfig.Variant.Icon -> {
            leading = null
            trailing = null
        }
        SegmentedControlConfig.Variant.LeadingIcon -> {
            leading = segment.leadingIcon ?: iconSlot
            trailing = null
        }
        SegmentedControlConfig.Variant.TrailingIcon -> {
            leading = null
            trailing = segment.trailingIcon ?: iconSlot
        }
        SegmentedControlConfig.Variant.BothIcons -> {
            leading = segment.leadingIcon ?: iconSlot
            trailing = segment.trailingIcon
        }
    }

    val effectiveLabel =
        when (config.variant) {
            SegmentedControlConfig.Variant.Icon -> iconSlot
            else -> labelSlot
        }

    Button(
        config =
            ButtonConfig(
                tone = ButtonConfig.Tone.Primary,
                mode = ButtonConfig.Mode.Hug,
            ),
        modifier = modifier,
        state =
            ButtonState(
                enabled = enabled,
                isLoading = false,
                isSelected = isSelected,
                isFocused = isFocused,
            ),
        interactionConfig =
            ButtonInteractionConfig(
                onClick = onClick,
            ),
        leading = leading,
        label = effectiveLabel,
        trailing = trailing,
    )
}
