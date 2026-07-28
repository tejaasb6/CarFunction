package com.ui.core.widgets.contentgroup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a content group widget.
 *
 * Brand implementations must render a styled card container and place the
 * caller-provided [content] slot inside it. Padding is applied when
 * [hasPadding] is `true`, using values from [ContentGroupStyle].
 */
typealias ContentGroupWidgetContent = @Composable (
    modifier: Modifier,
    hasPadding: Boolean,
    content: @Composable () -> Unit,
) -> Unit

/**
 * Brand-agnostic content group — a **non-interactive card container** that visually
 * groups related content with a styled background, border, corner radius, and blur.
 *
 * The container itself has no interactive states (no pressed, selected, disabled).
 * Child content placed inside the [content] slot may be interactive independently.
 *
 * ## Usage
 * ```kotlin
 * ContentGroup {
 *     Text("Grouped content goes here")
 * }
 * ```
 *
 * ## Without padding
 * ```kotlin
 * ContentGroup(hasPadding = false) {
 *     Image(painter = painterResource(R.drawable.hero), contentDescription = null)
 * }
 * ```
 *
 * ## Full-width card
 * ```kotlin
 * ContentGroup(modifier = Modifier.fillMaxWidth()) {
 *     Column {
 *         Text("Title")
 *         Text("Description text inside a content group card")
 *     }
 * }
 * ```
 *
 * @param modifier    Applied to the outermost container node.
 * @param hasPadding  When `true` (default), inner padding from [ContentGroupStyle] is applied.
 *                    Set to `false` for edge-to-edge content (e.g. images).
 * @param content     Composable slot for the card body.
 */
@Composable
fun ContentGroup(
    modifier: Modifier = Modifier,
    hasPadding: Boolean = true,
    content: @Composable () -> Unit,
) {
    LocalWidgets.ContentGroup.current(
        modifier,
        hasPadding,
        content,
    )
}
