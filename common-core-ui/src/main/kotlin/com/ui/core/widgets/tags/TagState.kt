package com.ui.core.widgets.tags

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for [Tag].
 *
 * Example:
 * ```kotlin
 * Tag(
 *     config = TagConfig(tone = TagConfig.Tone.Default),
 *     state = TagState(enabled = false),
 *     label = { Text(text = "Archived") },
 * )
 * ```
 *
 * @property enabled when `false` the tag is dimmed and non-interactive.
 */
@Immutable
data class TagState(
    val enabled: Boolean = true,
)
