@file:Suppress("LongMethod")

package com.ui.audi.widgets.searchfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ui.audi.theme.AudiFont
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.progressindicators.ProgressIndicator
import com.ui.core.widgets.progressindicators.ProgressIndicatorConfig
import com.ui.core.widgets.searchfields.LocalSearchFieldStyle
import com.ui.core.widgets.searchfields.SearchFieldContent
import com.ui.core.widgets.searchfields.SearchFieldInteractionConfig
import com.ui.core.widgets.searchfields.SearchFieldState
import com.ui.core.widgets.searchfields.SearchFieldStateColors
import com.ui.core.widgets.searchfields.SearchFieldStyle
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextConfig
import com.ui.core.widgets.text.TextResource
import com.ui.core.widgets.text.TextState
import kotlinx.coroutines.flow.collectLatest

/**
 * Audi brand implementation of [com.ui.core.widgets.searchfields.SearchField].
 *
 * **Internal** — app code must not call this directly.
 * Use [com.ui.core.widgets.searchfields.SearchField] instead.
 *
 * ## Text rendering
 * - **Placeholder & hint** are rendered using the brand-agnostic [Text] widget
 *   so that Audi typography, colour, and disabled-state behaviour are consistent
 *   with the rest of the design system.
 * - **User-input text** is styled via [BasicTextField.textStyle] with
 *   [AudiFont] applied as the `fontFamily`, ensuring the typed text matches
 *   the Audi brand typeface.
 *
 * ## State priority (highest to lowest)
 * 1. **Disabled** — muted via [Sem.Opacity.Disabled], non-interactive.
 * 2. **Loading** — shows loading indicator (filled variant only).
 * 3. **Pressed** — state-layer overlay covers the **complete** field area.
 * 4. **Active** — field has focus / user is typing.
 * 5. **Idle** — default colour set.
 *
 * ## Focus ring + Active / Pressed layering
 * Both the Active border and the focus ring are applied as concentric
 * [Modifier.border] calls on the **same** field container. The focus ring
 * border is applied **before** the Active border in the modifier chain so
 * it sits visually outside. No sibling overlay or padding inset is needed.
 *
 * @param value             Current text value as [TextResource].
 * @param onValueChange     Callback invoked when the user changes the text.
 * @param modifier          [Modifier] applied to the outermost layout node.
 * @param content           Text and composable content slots (placeholder, hint, icons).
 * @param state             Runtime state flags (enabled, loading).
 * @param interactionConfig Extended interaction configuration. Defaults to all-off.
 */
@Suppress("CyclomaticComplexMethod")
@Composable
internal fun AudiSearchField(
    value: TextResource,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: SearchFieldContent = SearchFieldContent(),
    state: SearchFieldState = SearchFieldState(),
    interactionConfig: SearchFieldInteractionConfig = SearchFieldInteractionConfig(),
) {
    val style = LocalSearchFieldStyle.current

    // Resolve TextResource to String for BasicTextField.
    val resolvedValue = value.annotated.text

    // -- Caption gap token for hint spacing --
    val captionGap =
        Cmp.Space.Forms.FormFields.CaptionGroup.Gap
            .dimension()
            .pxToDp()

    // Single interaction source — BasicTextField owns touch handling for the full bar.
    val interactionSource = remember { MutableInteractionSource() }

    // Reliably track pressed state via PressInteraction flow.
    var isPressed by remember { mutableStateOf(false) }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isPressed = true
                is PressInteraction.Release,
                is PressInteraction.Cancel,
                -> isPressed = false
            }
        }
    }

    // Track focused state for Active colour resolution.
    var isActive by remember { mutableStateOf(false) }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> isActive = true
                is FocusInteraction.Unfocus -> isActive = false
                else -> { /* no-op */ }
            }
        }
    }

    // -- UX Restrictions: auto-disable when driving and not distraction-optimised --
    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled =
        state.enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    // -- Resolve colours for current state --
    val isFilled = resolvedValue.isNotEmpty()
    val colors =
        resolveColors(
            style = style,
            isFilled = isFilled,
            enabled = effectiveEnabled,
            isPressed = isPressed,
            isActive = isActive,
            isLoading = state.isLoading,
        )

    // -- Disabled opacity from semantic token --
    val disabledAlpha = if (effectiveEnabled) 1f else Sem.Opacity.Disabled.opacity()

    val fieldShape = RoundedCornerShape(style.cornerRadius)

    // Focus ring — drawn only when consumer explicitly sets isFocused = true.
    val showFocusRing = state.isFocused && effectiveEnabled
    val focusRingWidth =
        Sem.BorderWidth.FocusRing
            .dimension()
            .pxToDp()
    // Gap between the focus ring and the field border so the ring does not
    // overlap the field surface at any corner radius.
    val focusRingGap =
        Sem.Space.Fixed._50
            .dimension()
            .pxToDp()
    // Outer shape must account for the gap so the focus ring tracks the
    // field's curvature without visible gaps at the corners.
    val focusRingShape = RoundedCornerShape(style.cornerRadius + focusRingGap)

    // -- Input text style with AudiFont applied --
    val inputTextStyle =
        style.inputTextStyle.copy(
            color = colors.inputTextColor,
            fontFamily = AudiFont,
            textDecoration = TextDecoration.None,
        )

    // TextFieldValue to control composition — strips the IME composing
    // underline by clearing the composition range on every change.
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = resolvedValue, selection = TextRange(resolvedValue.length)))
    }
    // Sync external value changes into the TextFieldValue.
    LaunchedEffect(resolvedValue) {
        if (textFieldValue.text != resolvedValue) {
            textFieldValue =
                TextFieldValue(
                    text = resolvedValue,
                    selection = TextRange(resolvedValue.length),
                )
        }
    }

    Column(modifier = modifier) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                // Accept the new text but clear composition to remove underline.
                textFieldValue = newValue.copy(composition = null)
                onValueChange(newValue.text)
            },
            enabled = effectiveEnabled,
            readOnly = false,
            textStyle = inputTextStyle,
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush =
                SolidColor(
                    Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Active
                        .color(),
                ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions.Default,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .alpha(disabledAlpha)
                    .then(
                        interactionConfig.focusRequester?.let {
                            Modifier.focusRequester(it)
                        } ?: Modifier,
                    ),
            decorationBox = { innerTextField ->
                // Focus ring wraps the field container. When visible it draws
                // an inward border + padding that pushes the inner field inward
                // on all sides, making both rings visible concentrically.
                // When not visible the wrapper is a plain pass-through.
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .then(
                                if (showFocusRing) {
                                    Modifier
                                        .border(
                                            focusRingWidth,
                                            Sem.Color.Stroke.Signal.Focus
                                                .color(),
                                            focusRingShape,
                                        ).padding(focusRingGap)
                                } else {
                                    Modifier.padding(focusRingGap)
                                },
                            ),
                ) {
                    // -- Field container (surface + Active border + content) --
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(style.fieldHeight)
                                .clip(fieldShape)
                                .background(colors.surfaceFill)
                                .then(
                                    if (colors.borderWidth > 0.dp) {
                                        Modifier.border(
                                            colors.borderWidth,
                                            colors.border,
                                            fieldShape,
                                        )
                                    } else {
                                        Modifier
                                    },
                                ),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        // -- State-layer overlay (pressed) --
                        if (colors.stateLayerFill != Color.Transparent) {
                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .clip(fieldShape)
                                        .background(colors.stateLayerFill),
                            )
                        }

                        // Content row.
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = style.horizontalPadding),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // -- Leading icon --
                            val leadingIcon = content.leadingIcon
                            if (leadingIcon != null) {
                                Box(
                                    modifier =
                                        Modifier.size(
                                            width = style.leadingIconWidth,
                                            height = style.leadingIconHeight,
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CompositionLocalProvider(
                                        LocalContentColor provides colors.iconColor,
                                    ) {
                                        leadingIcon()
                                    }
                                }
                                Spacer(Modifier.width(style.iconGap))
                            }

                            // -- Text input area --
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart,
                            ) {
                                // Placeholder — rendered via brand Text widget.
                                if (resolvedValue.isEmpty()) {
                                    Text(
                                        state =
                                            TextState(
                                                text = content.placeholder,
                                                maxLines = 1,
                                            ),
                                        config =
                                            TextConfig(
                                                type = TextConfig.Type.Truncatable,
                                            ),
                                        overflow = TextOverflow.Ellipsis,
                                        style =
                                            style.placeholderTextStyle.copy(
                                                color = colors.placeholderColor,
                                            ),
                                    )
                                }
                                // User-input text — BasicTextField internal
                                // rendering (cursor, selection, IME).
                                innerTextField()
                            }

                            // -- Trailing area: spinner (loading) or button --
                            if (state.isLoading && effectiveEnabled) {
                                Spacer(Modifier.width(style.trailingGap))
                                Box(
                                    modifier =
                                        Modifier.size(
                                            width = style.trailingButtonWidth,
                                            height = style.trailingButtonHeight,
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
                                val trailingButton = content.trailingButton
                                if (trailingButton != null) {
                                    Spacer(Modifier.width(style.trailingGap))
                                    // Resolve the click handler: clear when
                                    // filled, microphone when empty.
                                    val trailingClick =
                                        if (isFilled) {
                                            interactionConfig.onClearClick
                                        } else {
                                            interactionConfig.onMicrophoneClick
                                        }
                                    Box(
                                        modifier =
                                            Modifier
                                                .size(
                                                    width = style.trailingButtonWidth,
                                                    height = style.trailingButtonHeight,
                                                ).then(
                                                    if (trailingClick != null && effectiveEnabled) {
                                                        Modifier.clickable(
                                                            indication = null,
                                                            interactionSource = remember { MutableInteractionSource() },
                                                            onClick = trailingClick,
                                                        )
                                                    } else {
                                                        Modifier
                                                    },
                                                ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        CompositionLocalProvider(
                                            LocalContentColor provides colors.iconColor,
                                        ) {
                                            trailingButton()
                                        }
                                    }
                                }
                            }
                        }
                    } // end field container
                } // end focus ring wrapper
            },
        )

        // -- Hint text — rendered via brand Text widget --
        if (content.hint != EmptyTextResource) {
            Spacer(Modifier.height(captionGap))
            Text(
                state =
                    TextState(
                        text = content.hint,
                        enabled = effectiveEnabled,
                        maxLines = 1,
                    ),
                config = TextConfig(type = TextConfig.Type.Truncatable),
                modifier = Modifier.padding(start = style.hintStartPadding),
                overflow = TextOverflow.Ellipsis,
                style = style.hintTextStyle.copy(color = colors.hintColor),
            )
        }
    }
}

// ── State-colour resolver ───────────────────────────────────────────────────────

/**
 * Resolves the [SearchFieldStateColors] for the current combination of interaction flags.
 *
 * Resolution follows the priority order documented in [AudiSearchField]:
 * Disabled → Loading → Pressed → Active → Idle.
 *
 * @param style     Full style specification containing per-state colour sets.
 * @param isFilled  `true` when the text field contains user input.
 * @param enabled   `false` when the field is disabled (or auto-disabled via UX restrictions).
 * @param isPressed `true` while the user is pressing the field.
 * @param isActive  `true` when the field has keyboard focus.
 * @param isLoading `true` when a search operation is in progress.
 * @return The [SearchFieldStateColors] matching the highest-priority active state.
 */
private fun resolveColors(
    style: SearchFieldStyle,
    isFilled: Boolean,
    enabled: Boolean,
    isPressed: Boolean,
    isActive: Boolean,
    isLoading: Boolean,
): SearchFieldStateColors =
    when {
        !enabled && isFilled -> style.filledDisabled
        !enabled -> style.defaultDisabled
        isLoading && isFilled -> style.filledLoading
        isPressed && isFilled -> style.filledPressed
        isPressed -> style.defaultPressed
        isActive && isFilled -> style.filledActive
        isActive -> style.defaultActive
        isFilled -> style.filledIdle
        else -> style.defaultIdle
    }
