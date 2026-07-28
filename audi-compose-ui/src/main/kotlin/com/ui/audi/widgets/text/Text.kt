package com.ui.audi.widgets.text

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ui.audi.theme.AudiFont
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.text.LocalTextStyleSpec
import com.ui.core.widgets.text.TextConfig
import com.ui.core.widgets.text.TextInteractionConfig
import com.ui.core.widgets.text.TextState

/**
 * Audi brand implementation of [com.ui.core.widgets.text.Text].
 *
 * Receives the **new** API surface — text content, enabled state, and
 * maxLines are bundled inside [TextState]; click handling lives in
 * [TextInteractionConfig].
 */
@Suppress("LongParameterList", "LongMethod", "CyclomaticComplexMethod")
@Composable
internal fun Text(
    config: TextConfig,
    modifier: Modifier = Modifier,
    state: TextState = TextState(),
    interactionConfig: TextInteractionConfig = TextInteractionConfig(),
    overflow: TextOverflow? = null,
    textAlign: TextAlign? = null,
    style: TextStyle? = null,
) {
    val styleSpec = LocalTextStyleSpec.current

    // ── Resolve text style ─────────────────────────────────────────────
    // Priority: explicit `style` > parent-provided LocalTextStyle > theme default
    val parentTextStyle = LocalTextStyle.current
    val hasParentTextStyle =
        parentTextStyle.fontSize.isSp && parentTextStyle.fontSize.value > 0f

    val baseStyle =
        style ?: if (hasParentTextStyle) {
            parentTextStyle
        } else {
            when (config.type) {
                TextConfig.Type.Normal -> styleSpec.normal
                TextConfig.Type.Paragraph -> styleSpec.paragraph
                TextConfig.Type.Multiline,
                TextConfig.Type.Truncatable,
                TextConfig.Type.Scrollable,
                -> styleSpec.body
                TextConfig.Type.Selectable,
                TextConfig.Type.Annotated,
                TextConfig.Type.Clickable,
                -> styleSpec.label
            }
        }

    // ── Interaction ────────────────────────────────────────────────────
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val restrictions = LocalUxRestrictions.current
    val enabled = state.enabled && !restrictions.isMoving

    // ── Resolve colour ─────────────────────────────────────────────────
    // Respect parent-provided LocalContentColor (e.g. from Tag, Button) so that
    // when a parent sets white for Prominent tone, the Text renders in white.
    val parentColor = LocalContentColor.current
    val isParentProvided = parentColor != Color.Unspecified

    val defaultColor = if (isParentProvided) parentColor else styleSpec.enabledColor

    // If the caller passed an explicit colour via style.color, honour it.
    val explicitColor = style?.color?.takeIf { it != Color.Unspecified }

    val resolvedColor =
        when {
            !enabled -> styleSpec.disabledColor
            config.type == TextConfig.Type.Clickable && isPressed ->
                defaultColor.copy(alpha = 0.7f)
            explicitColor != null -> explicitColor
            else -> defaultColor
        }

    // ── Clickable modifier ─────────────────────────────────────────────
    val hasClick = config.type == TextConfig.Type.Clickable
    val clickableModifier =
        if (hasClick) {
            Modifier.interactiveClickable(
                clickOptions =
                    ClickOptions(
                        onClick = interactionConfig.onClick,
                        onLongClick = interactionConfig.onLongClick,
                        onDoubleClick = interactionConfig.onDoubleClick,
                    ),
                interactionSource = interactionSource,
                enabled = enabled,
                indication = null,
            )
        } else {
            Modifier
        }

    // ── Scroll modifier (Scrollable type) ──────────────────────────────
    val scrollModifier =
        if (config.type == TextConfig.Type.Scrollable) {
            Modifier
                .heightIn(max = 120.dp)
                .verticalScroll(rememberScrollState())
        } else {
            Modifier
        }

    // ── Resolve text content from TextState ────────────────────────────
    val finalText = state.text.annotated

    val resolvedMaxLines =
        when (config.type) {
            TextConfig.Type.Truncatable -> 1
            else -> state.maxLines
        }

    val resolvedOverflow =
        overflow ?: when (config.type) {
            TextConfig.Type.Truncatable,
            TextConfig.Type.Multiline,
            -> TextOverflow.Ellipsis
            else -> TextOverflow.Clip
        }

    // ── Render ──────────────────────────────────────────────────────────
    val textContent: @Composable () -> Unit = {
        BasicText(
            text = finalText,
            style =
                baseStyle.copy(
                    color = resolvedColor,
                    textAlign = textAlign ?: baseStyle.textAlign,
                    fontFamily = AudiFont,
                ),
            maxLines = resolvedMaxLines,
            overflow = resolvedOverflow,
        )
    }

    val content: @Composable () -> Unit =
        when (config.type) {
            TextConfig.Type.Selectable -> {
                { SelectionContainer { textContent() } }
            }
            else -> textContent
        }

    Box(
        modifier =
            modifier
                .defaultMinSize(minHeight = 24.dp)
                .then(scrollModifier)
                .then(clickableModifier)
                .padding(vertical = 2.dp),
    ) {
        content()
    }
}
