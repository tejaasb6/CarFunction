package com.ui.audi.widgets.scrollbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import com.ui.core.widgets.scrollbar.LocalScrollbarStyle
import com.ui.core.widgets.scrollbar.ScrollbarInteractionConfig
import kotlin.math.max

/**
 * Audi brand scrollbar — visual indicator for vertical scroll position.
 *
 * **Internal** — app code must not call this directly.
 * Use [com.ui.core.widgets.scrollbar.Scrollbar] instead.
 *
 * ## Non-Interactive Behavior
 * - **List scrolls → thumb follows**: Reads [LazyListState] to compute scroll progress.
 * - **No touch interactions**: The scrollbar cannot be touched, dragged, or tapped.
 *   All scrolling must be done through the list content itself.
 *
 * ## Behavior
 * - **Track**: Full vertical guide showing scrollable area extent
 * - **Thumb**: Visual indicator showing current scroll position
 * - **Hidden**: Automatically hidden when content fits in container (no scroll needed)
 *
 * ## Automatic Data Derivation
 * All metrics are derived from `LazyListState.layoutInfo`:
 * - `itemCount` → `totalItemsCount`
 * - `containerHeight` → `viewportSize.height`
 * - `contentHeight` → calculated from visible items and scroll offsets
 * - Supports **dynamic item heights** automatically
 *
 * @param listState          The [LazyListState] of the associated LazyColumn.
 * @param modifier           Applied to the outermost layout node.
 * @param interactionConfig  Callbacks for scroll interactions (currently unused).
 */
@Suppress("UnusedParameter")
@Composable
internal fun AudiScrollbar(
    listState: LazyListState,
    modifier: Modifier = Modifier,
    interactionConfig: ScrollbarInteractionConfig = ScrollbarInteractionConfig(),
) {
    val style = LocalScrollbarStyle.current

    // ── Derive from LazyListState ─────────────────────────────────────────────
    val layoutInfo = remember { derivedStateOf { listState.layoutInfo } }
    val itemCount = layoutInfo.value.totalItemsCount
    val containerHeightPx =
        layoutInfo.value.viewportSize.height
            .toFloat()

    // ── Estimate total content height from layout info ───────────────────────
    // Calculate average item height from visible items for accurate estimation
    val estimatedContentHeightPx =
        if (layoutInfo.value.visibleItemsInfo.isNotEmpty()) {
            val visibleItems = layoutInfo.value.visibleItemsInfo
            val averageItemHeight = visibleItems.sumOf { it.size } / visibleItems.size.toFloat()
            itemCount * averageItemHeight
        } else {
            containerHeightPx // Fallback: assume content fits if no items visible
        }

    // ── Show/hide logic: hide scrollbar if content fits ───────────────────────
    val isScrollable = estimatedContentHeightPx > containerHeightPx
    if (!isScrollable) return

    // ── Compute scroll progress from LazyListState (list → thumb) ─────────────
    val scrollProgress =
        run {
            val maxScrollablePx = (estimatedContentHeightPx - containerHeightPx).coerceAtLeast(1f)

            // Calculate current scroll position from first visible item
            val currentScrollPx =
                if (layoutInfo.value.visibleItemsInfo.isNotEmpty()) {
                    // Use average item height to estimate scroll position
                    val visibleItems = layoutInfo.value.visibleItemsInfo
                    val averageItemHeight = visibleItems.sumOf { it.size } / visibleItems.size.toFloat()

                    // Height of all items before first visible item
                    val itemsBeforeHeight = listState.firstVisibleItemIndex * averageItemHeight

                    // Add scroll offset within the first visible item
                    val currentScrollPx = itemsBeforeHeight + listState.firstVisibleItemScrollOffset.toFloat()
                    currentScrollPx
                } else {
                    0f
                }

            (currentScrollPx / maxScrollablePx).coerceIn(0f, 1f)
        }

    // ── Design Tokens ─────────────────────────────────────────────────────────
    val thumbWidth = style.thumbWidth
    val trackWidth = style.trackWidth
    val trackColor = style.colors.trackColor
    val thumbColor = style.colors.thumbColor

    // ── Scroll state ──────────────────────────────────────────────────────────
    val clampedProgress = scrollProgress.coerceIn(0f, 1f)
    var trackHeightPx by remember { mutableFloatStateOf(0f) }

    // ── Thumb dimensions ──────────────────────────────────────────────────────
    val thumbHeightRatio = (containerHeightPx / estimatedContentHeightPx).coerceIn(0.05f, 1f)
    val thumbHeightPx = trackHeightPx * thumbHeightRatio

    // ── Thumb position ────────────────────────────────────────────────────────
    val maxThumbOffsetPx = max(0.1f, trackHeightPx - thumbHeightPx)
    val thumbOffsetPx = (clampedProgress * maxThumbOffsetPx).coerceAtMost(maxThumbOffsetPx)

    // ── Scrollbar Container ───────────────────────────────────────────────────
    Box(
        modifier =
            modifier
                .fillMaxHeight()
                .width(style.scrollbarWidth)
                .onSizeChanged { size -> trackHeightPx = size.height.toFloat() },
        contentAlignment = Alignment.Center,
    ) {
        // ── Track ─────────────────────────────────────────────────────────────
        Box(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(trackWidth)
                    .background(trackColor),
        )

        // ── Thumb ─────────────────────────────────────────────────────────────
        Box(
            modifier =
                Modifier
                    .align(Alignment.TopCenter)
                    .width(thumbWidth)
                    .fillMaxHeight(thumbHeightRatio)
                    .graphicsLayer { translationY = thumbOffsetPx.coerceIn(0f, maxThumbOffsetPx) }
                    .background(thumbColor),
        )
    }
}
