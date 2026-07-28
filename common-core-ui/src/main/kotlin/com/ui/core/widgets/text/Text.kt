package com.ui.core.widgets.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.ui.core.widgets.LocalWidgets

/** Composable function type for a text widget (brand implementation). */
typealias TextWidgetContent = @Composable (
    config: TextConfig,
    modifier: Modifier,
    state: TextState,
    interactionConfig: TextInteractionConfig,
    overflow: TextOverflow?,
    textAlign: TextAlign?,
    style: TextStyle?,
) -> Unit

/**
 * Brand-agnostic Text widget — **recommended API**.
 *
 * Text content, enabled state, and maxLines are bundled in [TextState].
 * Click handling is in [TextInteractionConfig]. Visual overrides via [style].
 *
 * ```kotlin
 * // Simple text
 * Text(state = TextState(text = "Hello".TR))
 *
 * // Translatable string resource
 * Text(state = TextState(text = R.string.hello.TR))
 *
 * // Clickable text
 * Text(
 *     state = TextState(text = "Tap me".TR),
 *     config = TextConfig(type = TextConfig.Type.Clickable),
 *     interactionConfig = TextInteractionConfig(onClick = { println("Tapped!") }),
 * )
 *
 * // Styled text
 * Text(
 *     state = TextState(text = "Styled".TR),
 *     style = TextStyle(fontSize = 20.sp, color = Color.Red),
 * )
 *
 * // Annotated string
 * Text(state = TextState(text = buildAnnotatedString { ... }.TR))
 *
 * // Disabled
 * Text(state = TextState(text = "Disabled".TR, enabled = false))
 * ```
 *
 * @param config behavioural variant configuration.
 * @param modifier applied to the outermost layout node.
 * @param state text content + runtime state flags.
 * @param interactionConfig click handler and interaction options.
 * @param overflow how text overflow is handled visually.
 * @param textAlign horizontal alignment of the text.
 * @param style override text style; when `null` the theme style is used.
 */
@Composable
fun Text(
    state: TextState,
    config: TextConfig = TextConfig(),
    modifier: Modifier = Modifier,
    interactionConfig: TextInteractionConfig = TextInteractionConfig(),
    overflow: TextOverflow? = null,
    textAlign: TextAlign? = null,
    style: TextStyle? = null,
) {
    LocalWidgets.Text.current(
        config,
        modifier,
        state,
        interactionConfig,
        overflow,
        textAlign,
        style,
    )
}

// ── Deprecated API ─────────────────────────────────────────────────────────────

/**
 * Brand-agnostic Text widget — **deprecated API**.
 *
 * Use [Text] with [TextState] instead:
 * ```kotlin
 * // Old:
 * Text(text = "Hello", color = Color.White, onClick = { ... })
 *
 * // New:
 * Text(
 *     state = TextState(text = "Hello".TR),
 *     interactionConfig = TextInteractionConfig(onClick = { ... }),
 *     style = TextStyle(color = Color.White),
 * )
 * ```
 */
@Deprecated(
    message = "Use Text(state = TextState(text = \"...\".TR), ...) instead.",
    replaceWith =
        ReplaceWith(
            "Text(state = TextState(text = text.TR), config = config, modifier = modifier, " +
                "interactionConfig = TextInteractionConfig(onClick = onClick ?: {}), " +
                "style = style, overflow = overflow, textAlign = textAlign)",
            "com.ui.core.widgets.text.TR",
            "com.ui.core.widgets.text.TextState",
            "com.ui.core.widgets.text.TextInteractionConfig",
        ),
)
@Composable
fun Text(
    text: String = "",
    config: TextConfig = TextConfig(),
    modifier: Modifier = Modifier,
    @Suppress("DEPRECATION") enabled: Boolean = true,
    annotatedText: AnnotatedString? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow? = null,
    textAlign: TextAlign? = null,
    color: Color? = null,
    style: TextStyle? = null,
    onClick: (() -> Unit)? = null,
) {
    // Bridge to new API
    val textResource = annotatedText?.TR ?: text.TR
    val resolvedStyle = if (color != null) (style ?: TextStyle.Default).copy(color = color) else style

    Text(
        state = TextState(text = textResource, enabled = enabled, maxLines = maxLines),
        config = config,
        modifier = modifier,
        interactionConfig = TextInteractionConfig(onClick = onClick ?: {}),
        overflow = overflow,
        textAlign = textAlign,
        style = resolvedStyle,
    )
}
