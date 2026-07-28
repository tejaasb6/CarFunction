package com.ui.core.widgets.text

import androidx.compose.runtime.Immutable

/**
 * Runtime state for [Text].
 *
 * Bundles the text content and state flags into a single immutable object.
 *
 * ```kotlin
 * // Static text
 * TextState(text = "Hello".TR)
 *
 * // Translatable string resource
 * TextState(text = R.string.hello.TR)
 *
 * // Disabled text
 * TextState(text = "Disabled".TR, enabled = false)
 * ```
 *
 * @property text the text content to display, wrapped as [TextResource].
 * @property enabled when `false` the text renders in the disabled colour.
 * @property maxLines maximum number of lines before truncation.
 */
@Immutable
data class TextState(
    val text: TextResource = EmptyTextResource,
    val enabled: Boolean = true,
    val maxLines: Int = Int.MAX_VALUE,
)
