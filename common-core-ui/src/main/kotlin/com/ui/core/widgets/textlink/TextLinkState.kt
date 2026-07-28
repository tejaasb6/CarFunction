package com.ui.core.widgets.textlink

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for [TextLink].
 *
 * Example:
 * ```kotlin
 * TextLink(
 *     state = TextLinkState(enabled = false),
 *     interactionConfig = TextLinkInteractionConfig(onClick = {}),
 *     label = { Text(text = "Disabled link") },
 * )
 * ```
 *
 * @property enabled when `false` the text link renders in the disabled colour and
 *  ignores click events.
 * @property isFocused when `true` a focus ring is drawn around Standalone links
 *  (driven by a forced-focus preview flag, not by the interaction source).
 */
@Immutable
data class TextLinkState(
    val enabled: Boolean = true,
    val isFocused: Boolean = false,
)
