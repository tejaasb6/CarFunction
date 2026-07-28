package com.ui.core.widgets.text

import androidx.compose.runtime.Immutable

/**
 * Configuration for [Text].
 *
 * ## Types
 * | Type | Behaviour |
 * |------|-----------|
 * | [Type.Normal] | Default single-line text. |
 * | [Type.Selectable] | Long-press to select and copy. |
 * | [Type.Truncatable] | Single-line with ellipsis overflow. |
 * | [Type.Multiline] | Multi-line with ellipsis overflow. |
 * | [Type.Paragraph] | Block text with paragraph line spacing. |
 * | [Type.Annotated] | Styled spans via [androidx.compose.ui.text.AnnotatedString]. |
 * | [Type.Scrollable] | Vertically scrollable text block. |
 * | [Type.Clickable] | Tappable text with press feedback. |
 *
 * @property type the behavioural variant of the text widget.
 */
@Immutable
data class TextConfig(
    val type: Type = Type.Normal,
) {
    /**
     * Behavioural variant of a text widget.
     */
    enum class Type {
        Normal,
        Selectable,
        Truncatable,
        Multiline,
        Paragraph,
        Annotated,
        Scrollable,
        Clickable,
    }
}
