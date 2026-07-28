package com.ui.core.widgets.scrollbar

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type provided by a brand theme for the Scrollbar widget.
 *
 * The brand implementation is registered in [LocalWidgets.Scrollbar] and
 * invoked by the agnostic [Scrollbar] composable below.
 */
typealias ScrollbarWidgetContent = @Composable (
    listState: LazyListState,
    modifier: Modifier,
    interactionConfig: ScrollbarInteractionConfig,
) -> Unit

/**
 * Brand-agnostic scrollbar — delegates entirely to the brand implementation.
 *
 * ## Purpose
 * The Scrollbar is a visual indicator that helps users understand that the content
 * in a view is scrollable and additional content exists beyond the visible area.
 * It supports **vertical scrolling only** and provides continuous feedback as the
 * user scrolls up or down, reflecting the relative position within the scrollable content.
 *
 * ## Non-Interactive Behavior
 * The scrollbar is **not touchable or draggable**. It only moves in response to
 * scrolling the list content itself:
 *
 * - **List scrolls → thumb follows**: The scrollbar reads [LazyListState] to
 *   compute scroll progress automatically.
 * - **Touch interactions disabled**: Users cannot drag the thumb or tap the track.
 *   All scrolling must be done through the list content.
 *
 * ## Automatic Adaptation
 * The scrollbar automatically adapts based on content:
 *  - **Dense** — Long content lists (thumb is small, track is narrow)
 *  - **Regular** — Balanced content (thumb is medium)
 *  - **Sparse** — Short content lists (thumb is large, track is wider)
 *
 * ## Dynamic Item Height Support
 * The scrollbar **automatically supports dynamic/variable item heights**. All metrics
 * (item count, container height, content height, scroll position) are derived from
 * [LazyListState.layoutInfo] at runtime, making it compatible with lists containing:
 *  - Items of different heights
 *  - Headers and footers
 *  - Dynamic content that changes size
 *
 * ## Mandatory Elements
 *  - **Scrollbar Track** — the full vertical guide/container for the scrollbar
 *  - **Scrollbar Thumb** — the visual indicator that moves within the track
 *
 * ## Basic Usage
 * ```kotlin
 * val listState = rememberLazyListState()
 *
 * Row {
 *     LazyColumn(state = listState, modifier = Modifier.weight(1f).height(300.dp)) {
 *         items(50) { index ->
 *             Text(state = TextState(text = "Item $index".TR))
 *         }
 *     }
 *
 *     Scrollbar(listState = listState)
 * }
 * ```
 *
 * ## Visibility behavior
 * The scrollbar is hidden when all content fits within the container.
 * All metrics are automatically derived from [LazyListState].
 *
 * @param listState          The [LazyListState] of the associated [androidx.compose.foundation.lazy.LazyColumn].
 * @param modifier           Applied to the outermost layout node.
 * @param interactionConfig  Optional callbacks for scroll interaction events.
 */
@Composable
fun Scrollbar(
    listState: LazyListState,
    modifier: Modifier = Modifier,
    interactionConfig: ScrollbarInteractionConfig = ScrollbarInteractionConfig(),
) {
    LocalWidgets.Scrollbar.current(
        listState,
        modifier,
        interactionConfig,
    )
}
