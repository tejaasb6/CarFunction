package com.ui.core.widgets.tags

import androidx.compose.runtime.Immutable

/**
 * Describes the visual tone of a [Tag].
 *
 * ## Tones
 * | Tone | Purpose |
 * |------|---------|
 * | [Tone.Default] | Neutral, subtle background — the standard tag. |
 * | [Tone.OnImage] | Outlined tag for placement on top of imagery. |
 * | [Tone.Prominent] | Bold brand-colour fill — draws strong attention. |
 *
 * ## Link mode
 * To make a tag act as a link, pass a [com.ui.core.widgets.textlink.TextLink]
 * in the `label` slot instead of a plain [com.ui.core.widgets.text.Text].
 * The TextLink widget handles underline, click, and pressed colour automatically.
 *
 * Example:
 * ```kotlin
 * // Non-link tag
 * Tag(
 *     config = TagConfig(tone = TagConfig.Tone.Default),
 *     label = { Text(text = "Category") },
 * )
 *
 * // Link tag — reuses the TextLink widget
 * Tag(
 *     config = TagConfig(tone = TagConfig.Tone.Default),
 *     label = {
 *         TextLink(
 *             config = TextLinkConfig(variant = TextLinkConfig.Variant.Inline),
 *             interactionConfig = TextLinkInteractionConfig(
 *                 onClick = { navigateToDetails() },
 *             ),
 *             label = { Text(text = "View details") },
 *         )
 *     },
 * )
 * ```
 *
 * @property tone the visual colour variant.
 */
@Immutable
data class TagConfig(
    val tone: Tone = Tone.Default,
) {
    /**
     * Visual colour variant of a tag.
     */
    enum class Tone {
        Default,
        OnImage,
        Prominent,
    }
}
