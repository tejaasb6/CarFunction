@file:Suppress("ForbiddenComment")

package com.ui.core.widgets.searchfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets
import com.ui.core.widgets.text.TextResource

/**
 * Composable function type for a brand-specific search field widget.
 *
 * Brand implementations (e.g. `AudiSearchField`, `LamborghiniSearchField`) must conform
 * to this signature.
 *
 * @see SearchField         The brand-agnostic public API that delegates to this type.
 * @see SearchFieldState    Runtime state flags (enabled, loading).
 * @see SearchFieldContent  Text and composable content slots.
 */
typealias SearchFieldWidgetContent = @Composable (
    value: TextResource,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    content: SearchFieldContent,
    state: SearchFieldState,
    interactionConfig: SearchFieldInteractionConfig,
) -> Unit

/**
 * Brand-agnostic search field — the **single public API** for all search field layouts.
 *
 * The Search Field is a specialised single-line input for search queries, supporting
 * text and voice input. When empty it displays a magnifying glass icon and a
 * context-specific placeholder; when filled the placeholder is replaced by the
 * entered text and the trailing button switches (e.g. voice icon to clear).
 *
 * ## Content ([SearchFieldContent])
 * All text content uses [TextResource] for i18n support:
 * - [SearchFieldContent.placeholder] — placeholder text shown when the field is empty.
 * - [SearchFieldContent.hint] — optional hint text below the field.
 * - [SearchFieldContent.leadingIcon] — composable slot for a leading icon.
 * - [SearchFieldContent.trailingButton] — composable slot for a trailing action.
 *   TODO: Replace with ComponentButton once implemented.
 *
 * ## State ([SearchFieldState])
 * Runtime state flags matching the Figma State axis:
 * - [SearchFieldState.enabled] — whether the field is interactive.
 * - [SearchFieldState.isLoading] — whether a search operation is in progress.
 *
 * ## Extended interactions ([SearchFieldInteractionConfig])
 * ```kotlin
 * SearchField(
 *     value = query.TR,
 *     onValueChange = { query = it },
 *     content = SearchFieldContent(
 *         placeholder = "Search vehicles".TR,
 *         hint = "Enter model or VIN".TR,
 *     ),
 *     interactionConfig = SearchFieldInteractionConfig(
 *         isDistractionOptimized = false,
 *     ),
 * )
 * ```
 *
 * @param value             The current text value as [TextResource].
 * @param onValueChange     Callback invoked when the user changes the text.
 * @param modifier          [Modifier] applied to the outermost layout node.
 * @param content           Text and composable content slots (placeholder, hint, icons).
 * @param state             Runtime state flags (enabled, loading).
 * @param interactionConfig Extended interaction configuration. Defaults to all-off.
 */
@Composable
fun SearchField(
    value: TextResource,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: SearchFieldContent = SearchFieldContent(),
    state: SearchFieldState = SearchFieldState(),
    interactionConfig: SearchFieldInteractionConfig = SearchFieldInteractionConfig(),
) {
    LocalWidgets.SearchField.current(
        value,
        onValueChange,
        modifier,
        content,
        state,
        interactionConfig,
    )
}
