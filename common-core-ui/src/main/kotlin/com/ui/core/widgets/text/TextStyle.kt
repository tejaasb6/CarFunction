package com.ui.core.widgets.text

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * Full visual specification for [Text].
 *
 * Brand implementations build this from `Sem.*` tokens inside their
 * `TextDefaults.style()` composable.
 *
 * @property normal style for [TextConfig.Type.Normal] text.
 * @property paragraph style for [TextConfig.Type.Paragraph] text with line spacing.
 * @property body style for body text variants (Multiline, Truncatable, Scrollable).
 * @property label style for label text variants (Selectable, Annotated, Clickable).
 * @property enabledColor default text colour when enabled.
 * @property disabledColor text colour when disabled.
 */
@Immutable
data class TextStyleSpec(
    val normal: TextStyle,
    val paragraph: TextStyle,
    val body: TextStyle,
    val label: TextStyle,
    val enabledColor: Color,
    val disabledColor: Color,
)

/**
 * Composition local providing the current [TextStyleSpec].
 *
 * Populated by `AudiTheme` / `LamborghiniTheme`; accessing it outside a brand
 * theme throws an error.
 */
val LocalTextStyleSpec =
    compositionLocalOf<TextStyleSpec> {
        error("No TextStyleSpec — wrap content in AudiTheme / LamborghiniTheme")
    }
