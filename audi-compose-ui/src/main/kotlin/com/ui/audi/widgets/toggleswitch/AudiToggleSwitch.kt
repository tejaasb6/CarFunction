package com.ui.audi.widgets.toggleswitch

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ui.core.engine.api.Sem
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.progressindicators.ProgressIndicator
import com.ui.core.widgets.progressindicators.ProgressIndicatorConfig
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.toggleswitch.LocalToggleSwitchStyle
import com.ui.core.widgets.toggleswitch.ToggleSwitchContent
import com.ui.core.widgets.toggleswitch.ToggleSwitchInteractionConfig
import com.ui.core.widgets.toggleswitch.ToggleSwitchState
import com.ui.core.widgets.toggleswitch.ToggleSwitchStateColors
import com.ui.core.widgets.toggleswitch.ToggleSwitchStyle

/**
 * Audi brand implementation of [com.ui.core.widgets.toggleswitch.ToggleSwitch].
 *
 * **Internal** — app code must not call this directly.
 * Use [com.ui.core.widgets.toggleswitch.ToggleSwitch] instead.
 *
 * Selection state and callback are read from [ToggleSwitchInteractionConfig.selected] and
 * [ToggleSwitchInteractionConfig.onSelectedChange] (inherited from [com.ui.core.interaction.SelectionConfig]).
 *
 * ## State priority (highest to lowest)
 * 1. Loading  — spinner replaces control, non-interactive
 * 2. Disabled — muted via opacity, non-interactive
 * 3. Pressed  — pressed colour set
 * 4. Idle     — default colour set
 */
@Composable
internal fun AudiToggleSwitch(
    modifier: Modifier = Modifier,
    content: ToggleSwitchContent = ToggleSwitchContent(),
    state: ToggleSwitchState = ToggleSwitchState(),
    interactionConfig: ToggleSwitchInteractionConfig = ToggleSwitchInteractionConfig(),
) {
    val enabled = state.enabled
    val isLoading = state.isLoading
    val controlLeading = state.controlLeading

    val style = LocalToggleSwitchStyle.current

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val selected = interactionConfig.selected

    // ── UX Restrictions ─────────────────────────────────────────────────────
    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled =
        enabled &&
            !isLoading &&
            (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    // ── Resolve colours ─────────────────────────────────────────────────────
    val colors = resolveColors(style, selected, effectiveEnabled, isPressed)

    val disabledAlpha = if (effectiveEnabled) 1f else style.disabledOpacity
    val textAlpha = if (enabled) 1f else style.disabledOpacity

    val trackShape = RoundedCornerShape(style.trackCornerRadius)

    val clickOptions =
        ClickOptions(
            onClick = { interactionConfig.onSelectedChange?.invoke(!selected) },
        )

    val hasLabel = content.label != null
    val hasHint = content.hint != null
    val hasTextContent = hasLabel || hasHint

    Row(
        modifier =
            modifier
                .defaultMinSize(minHeight = style.minHeight)
                .then(
                    if (hasTextContent) Modifier.padding(top = style.paddingTop) else Modifier,
                ).interactiveClickable(
                    clickOptions = clickOptions,
                    interactionSource = interactionSource,
                    enabled = effectiveEnabled,
                    indication = null,
                ),
        // When hint is present, align to Top so the control (or spinner in
        // loading state) sits next to the label row rather than being
        // vertically centred over the full label+hint height. The spinner
        // Box uses the same minHeight as the label row, keeping it
        // center-aligned with the label text.
        verticalAlignment = if (hasHint) Alignment.Top else Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(style.controlLabelSpacing),
    ) {
        if (controlLeading) {
            ToggleControl(
                style = style,
                colors = colors,
                selected = selected,
                isLoading = isLoading,
                enabled = enabled,
                disabledAlpha = disabledAlpha,
                interactionSource = interactionSource,
                focusRequester = interactionConfig.focusRequester,
            )
            if (hasTextContent) {
                TextContent(
                    style = style,
                    content = content,
                    colors = colors,
                    enabled = enabled,
                    disabledAlpha = textAlpha,
                    modifier = Modifier.weight(1f),
                )
            }
        } else {
            if (hasTextContent) {
                TextContent(
                    style = style,
                    content = content,
                    colors = colors,
                    enabled = enabled,
                    disabledAlpha = textAlpha,
                    modifier = Modifier.weight(1f),
                )
            }
            ToggleControl(
                style = style,
                colors = colors,
                selected = selected,
                isLoading = isLoading,
                enabled = enabled,
                disabledAlpha = disabledAlpha,
                interactionSource = interactionSource,
                focusRequester = interactionConfig.focusRequester,
            )
        }
    }
}

// ── Toggle control (track + handle or spinner) ─────────────────────────────────

/**
 * Renders the toggle-switch control — either the animated track + thumb or a
 * loading spinner, depending on [isLoading].
 *
 * When **not loading**, the track draws two IEC power-symbol icons
 * ("I" on the left, "○" on the right) beneath a circular thumb that slides
 * between the selected and unselected positions with a 200 ms tween animation.
 * A focus ring is rendered outside the track when the control receives D-pad /
 * rotary focus.
 *
 * When **loading**, a [ProgressIndicator] (SpinnerInfinite variant) replaces
 * the track and thumb entirely.
 *
 * @param style             The resolved [ToggleSwitchStyle] providing dimensions, colours,
 *                          and spinner tokens.
 * @param colors            The active [ToggleSwitchStateColors] for the current
 *                          selection × interaction state.
 * @param selected          Whether the toggle is currently in the "on" position.
 * @param isLoading         When `true` the spinner is shown instead of the track + thumb.
 * @param enabled           Whether the toggle is enabled; when `false` the control is not focusable.
 * @param disabledAlpha     Opacity multiplier applied to the track when the control is disabled.
 * @param interactionSource Shared [MutableInteractionSource] used to observe focus and press
 *                          states from the parent clickable.
 * @param focusRequester    Optional [FocusRequester] attached to the track for programmatic
 *                          focus; `null` when no external focus control is needed.
 */
@Composable
private fun ToggleControl(
    style: ToggleSwitchStyle,
    colors: ToggleSwitchStateColors,
    selected: Boolean,
    isLoading: Boolean,
    enabled: Boolean,
    disabledAlpha: Float,
    interactionSource: MutableInteractionSource,
    focusRequester: androidx.compose.ui.focus.FocusRequester?,
) {
    val trackShape = RoundedCornerShape(style.trackCornerRadius)

    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRingWidth =
        Sem.BorderWidth.FocusRing
            .dimension()
            .pxToDp()
    val focusRingGap =
        Sem.Space.Fixed._50
            .dimension()
            .pxToDp()
    val focusRingColor =
        Sem.Color.Stroke.Signal.Focus
            .color()

    if (isLoading) {
        // ── Loading spinner ─────────────────────────────────────────────
        // The spinner occupies the exact same space as the toggle control
        // (outer padding + track size) so it aligns identically with the
        // label row — same placement as the track in the non-loading state.
        Box(
            modifier =
                Modifier
                    .padding(focusRingGap)
                    .size(
                        width = style.handleWidth * 2 + style.horizontalPadding * 2,
                        height = style.handleHeight + style.verticalPadding * 2,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            ProgressIndicator(
                config =
                    ProgressIndicatorConfig(
                        variant = ProgressIndicatorConfig.Variant.SpinnerInfinite,
                        size = ProgressIndicatorConfig.Size.MD,
                    ),
                progress = 0f,
            )
        }
    } else {
        // ── Track + handle ──────────────────────────────────────────────
        val handleOffset by animateDpAsState(
            targetValue =
                if (selected) {
                    style.handleWidth + style.horizontalPadding
                } else {
                    style.horizontalPadding
                },
            animationSpec = tween(durationMillis = 200),
            label = "handleOffset",
        )

        // Outer focus ring shape (track corner radius + gap for outer ring)
        val outerShape = RoundedCornerShape(style.trackCornerRadius + focusRingGap)

        // Outer container — draws focus ring outside the track stroke
        Box(
            modifier =
                Modifier
                    .then(
                        if (focusRequester != null) {
                            Modifier.focusRequester(focusRequester)
                        } else {
                            Modifier
                        },
                    ).focusable(enabled = enabled, interactionSource = interactionSource)
                    .then(
                        if (isFocused) {
                            Modifier
                                .border(focusRingWidth, focusRingColor, outerShape)
                                .padding(focusRingGap)
                        } else {
                            Modifier.padding(focusRingGap)
                        },
                    ),
        ) {
            // Track
            Box(
                modifier =
                    Modifier
                        .size(
                            width = style.handleWidth * 2 + style.horizontalPadding * 2,
                            height = style.handleHeight + style.verticalPadding * 2,
                        ).alpha(disabledAlpha)
                        .clip(trackShape)
                        .background(colors.trackFill, trackShape)
                        .then(
                            if (colors.trackStrokeWidth > 0.dp) {
                                Modifier.border(colors.trackStrokeWidth, colors.trackStroke, trackShape)
                            } else {
                                Modifier
                            },
                        ),
            ) {
                // State layer
                if (colors.stateLayerColor != Color.Transparent) {
                    Box(
                        modifier =
                            Modifier
                                .matchParentSize()
                                .background(colors.stateLayerColor, trackShape),
                    )
                }

                // IEC icons on the track — left = "I" (line), right = "○" (circle)
                // The thumb slides over and covers the inactive icon.
                val iconColor = colors.iconTint
                val iconSize = style.handleHeight * 0.45f
                val iconYOffset = (style.handleHeight + style.verticalPadding * 2 - iconSize) / 2

                // Left icon — IEC 5008 "I" (line)
                Canvas(
                    modifier =
                        Modifier
                            .size(iconSize)
                            .offset(
                                x = style.horizontalPadding + (style.handleWidth - iconSize) / 2,
                                y = iconYOffset,
                            ),
                ) {
                    val strokePx = size.minDimension * 0.2f
                    drawLine(
                        color = iconColor,
                        start =
                            androidx.compose.ui.geometry
                                .Offset(center.x, size.height * 0.1f),
                        end =
                            androidx.compose.ui.geometry
                                .Offset(center.x, size.height * 0.9f),
                        strokeWidth = strokePx,
                        cap = androidx.compose.ui.graphics.StrokeCap.Round,
                    )
                }

                // Right icon — IEC 5009 "○" (circle)
                Canvas(
                    modifier =
                        Modifier
                            .size(iconSize)
                            .offset(
                                x = style.handleWidth + style.horizontalPadding + (style.handleWidth - iconSize) / 2,
                                y = iconYOffset,
                            ),
                ) {
                    val strokePx = size.minDimension * 0.2f
                    drawCircle(
                        color = iconColor,
                        radius = (size.minDimension - strokePx) / 2f,
                        style =
                            androidx.compose.ui.graphics.drawscope
                                .Stroke(width = strokePx),
                    )
                }

                // Handle (thumb) — slides over the track, covering one icon
                Box(
                    modifier =
                        Modifier
                            .offset(x = handleOffset, y = style.verticalPadding)
                            .size(width = style.handleWidth, height = style.handleHeight)
                            .clip(CircleShape)
                            .background(colors.handleFill, CircleShape),
                )
            } // Track Box
        } // Outer focus ring Box
    }
}

// ── Text content (label + hint) ────────────────────────────────────────────────

/**
 * Renders the text column for the toggle switch — an optional primary [label][ToggleSwitchContent.label]
 * and an optional supplementary [hint][ToggleSwitchContent.hint].
 *
 * The label row is constrained to a minimum height derived from the handle and
 * padding so that it aligns vertically with the toggle control. The hint is
 * placed below the label with [ToggleSwitchStyle.hintSpacing] between them.
 * Both text slots use the brand-specific [Text][com.ui.core.widgets.text.Text]
 * widget with single-line ellipsis overflow.
 *
 * When the toggle is disabled the entire column is dimmed by [disabledAlpha].
 *
 * @param style         The resolved [ToggleSwitchStyle] providing typography, spacing,
 *                      and handle dimensions for vertical alignment.
 * @param content       The [ToggleSwitchContent] carrying label and hint [TextResource]s.
 * @param colors        The active [ToggleSwitchStateColors] supplying label and hint colours.
 * @param enabled       Whether the toggle switch is enabled; forwarded to [TextState].
 * @param disabledAlpha Opacity multiplier applied to the column when disabled.
 * @param modifier      [Modifier] applied to the outer [Column].
 */
@Composable
private fun TextContent(
    style: ToggleSwitchStyle,
    content: ToggleSwitchContent,
    colors: ToggleSwitchStateColors,
    enabled: Boolean,
    disabledAlpha: Float,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.alpha(disabledAlpha)) {
        // Label row — same height as control, text centered vertically.
        val lbl = content.label
        if (lbl != null) {
            Box(
                modifier =
                    Modifier.defaultMinSize(
                        minHeight = style.handleHeight + style.verticalPadding * 2,
                    ),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(
                    state =
                        TextState(
                            text = lbl,
                            maxLines = 1,
                            enabled = enabled,
                        ),
                    style = style.labelTextStyle.copy(color = colors.labelColor),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        // Hint sits below the label row
        val hnt = content.hint
        if (hnt != null) {
            Spacer(Modifier.height(style.hintSpacing))
            Text(
                state =
                    TextState(
                        text = hnt,
                        maxLines = 1,
                        enabled = enabled,
                    ),
                style = style.hintTextStyle.copy(color = colors.hintColor),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

// ── State colour resolver ──────────────────────────────────────────────────────

/**
 * Resolves the active [ToggleSwitchStateColors] from [style] based on the current
 * combination of [selected], [enabled], and [isPressed] states.
 *
 * Priority order (highest wins):
 * 1. **Disabled** — `selected…Disabled` or `unselected…Disabled`
 * 2. **Pressed**  — `selected…Pressed`  or `unselected…Pressed`
 * 3. **Idle**     — `selected…Idle`     or `unselected…Idle`
 *
 * @param style     The full [ToggleSwitchStyle] containing all six per-state colour sets.
 * @param selected  Whether the toggle is currently on.
 * @param enabled   Whether the toggle is interactive.
 * @param isPressed Whether the user is currently pressing the control.
 * @return The [ToggleSwitchStateColors] matching the resolved interaction state.
 */
private fun resolveColors(
    style: ToggleSwitchStyle,
    selected: Boolean,
    enabled: Boolean,
    isPressed: Boolean,
): ToggleSwitchStateColors =
    when {
        selected && !enabled -> style.selectedDisabled
        selected && isPressed -> style.selectedPressed
        selected -> style.selectedIdle
        !selected && !enabled -> style.unselectedDisabled
        !selected && isPressed -> style.unselectedPressed
        else -> style.unselectedIdle
    }
