@file:Suppress("ForbiddenComment")

package com.ui.core.widgets.searchfields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.text.TextResource

/**
 * Text and composable content slots for [SearchField].
 *
 * All text fields use [TextResource] so they support static strings,
 * Android string resources (translatable), and annotated strings via the
 * `.TR` extension property.
 *
 * **Keyboard options** are intentionally **not** exposed here. The search
 * field always uses `ImeAction.Search`; the brand implementation hard-codes
 * it so callers cannot accidentally break the search-specific IME behaviour.
 *
 * ```kotlin
 * SearchField(
 *     value = query.TR,
 *     onValueChange = { query = it },
 *     content = SearchFieldContent(
 *         placeholder = "Search".TR,
 *         hint = "Type to search for items".TR,
 *         leadingIcon = { Icon(source = IconSource.Vector(Icons.Filled.Search, "Search")) },
 *     ),
 * )
 * ```
 *
 * @property placeholder      Placeholder text shown when the field is empty (single line, ellipsis).
 * @property hint             Optional hint text displayed below the field (single line).
 *                            [EmptyTextResource] means no hint is shown.
 * @property leadingIcon      Optional composable slot for a leading icon (e.g. magnifying glass).
 *                            The brand implementation is responsible for sizing and tinting.
 * @property trailingButton   Optional composable slot for a trailing button / icon area
 *                            (e.g. voice icon when empty, clear button when filled, spinner
 *                            when loading). This slot will be replaced by ComponentButton once
 *                            that widget is implemented. The brand implementation is responsible
 *                            for sizing and tinting.
 */
@Immutable
data class SearchFieldContent(
    val placeholder: TextResource = "Search".TR,
    val hint: TextResource = EmptyTextResource,
    val leadingIcon: (@Composable () -> Unit)? = null,
    // TODO: Replace trailing button slot with ComponentButton once implemented.
    val trailingButton: (@Composable () -> Unit)? = null,
)
