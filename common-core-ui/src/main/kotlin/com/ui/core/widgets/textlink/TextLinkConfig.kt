package com.ui.core.widgets.textlink

import androidx.compose.runtime.Immutable

/**
 * Configuration for [TextLink].
 *
 * ## Variants
 * | Variant | Description |
 * |---------|-------------|
 * | [Variant.Standalone] | Appears independently; supports leading + trailing icons and focus ring. |
 * | [Variant.Inline] | Embedded within a paragraph; label + optional trailing icon, no focus ring. |
 *
 * Example:
 * ```kotlin
 * TextLink(
 *     config = TextLinkConfig(variant = TextLinkConfig.Variant.Inline),
 *     interactionConfig = TextLinkInteractionConfig(onClick = { /* navigate */ }),
 *     label = { Text(text = "Learn more") },
 * )
 * ```
 *
 * @property variant the visual variant of the text link.
 */
@Immutable
data class TextLinkConfig(
    val variant: Variant = Variant.Standalone,
) {
    /**
     * Visual variant of a text link widget.
     *
     * Example:
     * ```kotlin
     * TextLinkConfig(variant = TextLinkConfig.Variant.Standalone)
     * ```
     */
    enum class Variant {
        /** Standalone link — supports leading icon, trailing icon, and focus ring. */
        Standalone,

        /** Inline link — embedded within text; label + optional trailing icon only. */
        Inline,
    }
}
