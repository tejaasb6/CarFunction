package com.ui.core.widgets.listitems

import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextResource

/**
 * Text content slots for [ListItem].
 *
 * Both [label] and [supportingText] are limited to **one line** with ellipsis
 * truncation, as specified in the CC_0015 requirements.
 *
 * ## Usage
 * ```kotlin
 * ListItem(
 *     content = ListItemContent(
 *         label = "Wi-Fi Network".TR,
 *         supportingText = "Connected".TR,
 *         trailingText = "5 GHz".TR,
 *     ),
 * )
 * ```
 *
 * @param label          Primary text — **mandatory** (the only required element per spec).
 *                       Accepts a [TextResource] so consumers can pass a plain string
 *                       (`"Hello".TR`), a string-resource ID (`R.string.hello.TR`),
 *                       or an [AnnotatedString] (`buildAnnotatedString { … }.TR`).
 *                       Limited to 1 line with ellipsis.
 * @param supportingText Secondary text below the label. Limited to 1 line with ellipsis.
 *                       Accepts a [TextResource]. `null` hides the row entirely.
 * @param trailingText   Text aligned to the trailing end (SecondColumn) of the main
 *                       content area. Accepts a [TextResource]. `null` hides the column.
 */
@Immutable
data class ListItemContent(
    val label: TextResource = EmptyTextResource,
    val supportingText: TextResource? = null,
    val trailingText: TextResource? = null,
)
