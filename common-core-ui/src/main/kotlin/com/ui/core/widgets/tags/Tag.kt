package com.ui.core.widgets.tags

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a tag widget.
 *
 * Brand implementations must match this signature exactly. The public [Tag]
 * composable delegates to the brand lambda registered in [LocalWidgets.Tag].
 *
 * The slot-based API (`icon`, `label`) allows callers to **reuse existing widgets**:
 * pass the design-system [com.ui.core.widgets.icons.Icon] for the icon slot,
 * [com.ui.core.widgets.text.Text] for a plain label, or
 * [com.ui.core.widgets.textlink.TextLink] for a clickable link label.
 */
typealias TagWidgetContent = @Composable (
    config: TagConfig,
    modifier: Modifier,
    state: TagState,
    icon: (@Composable () -> Unit)?,
    label: (@Composable () -> Unit)?,
) -> Unit

/**
 * Brand-agnostic tag — a compact pill-shaped label used to display metadata,
 * category, or status information.
 *
 * Uses a **slot-based** content API: pass existing design-system widgets
 * as slot content. The brand implementation applies the correct per-tone
 * colours via `CompositionLocalProvider`.
 *
 * ## Default tag with icon and text
 * ```kotlin
 * Tag(
 *     config = TagConfig(tone = TagConfig.Tone.Default),
 *     icon = {
 *         Icon(
 *             config = IconConfig(size = IconConfig.Size.SM),
 *             icon = { Icon(Icons.Filled.Info, contentDescription = null) },
 *         )
 *     },
 *     label = { Text(text = "Info") },
 * )
 * ```
 *
 * ## Link tag — reuses the TextLink widget
 * ```kotlin
 * Tag(
 *     config = TagConfig(tone = TagConfig.Tone.Default),
 *     icon = { Icon(config = IconConfig(), icon = { /* … */ }) },
 *     label = {
 *         TextLink(
 *             config = TextLinkConfig(variant = TextLinkConfig.Variant.Inline),
 *             interactionConfig = TextLinkInteractionConfig(
 *                 onClick = { navigateToDetails() },
 *             ),
 *             label = { Text(text = "View details") },
 *         )
 *     },
 * )
 * ```
 *
 * @param config tone configuration.
 * @param modifier applied to the outermost layout node.
 * @param state runtime flags (enabled).
 * @param icon optional composable slot for a leading icon.
 * @param label optional composable slot for the label — use [com.ui.core.widgets.text.Text]
 *              for plain text, or [com.ui.core.widgets.textlink.TextLink] for a clickable link.
 */
@Composable
fun Tag(
    config: TagConfig = TagConfig(),
    modifier: Modifier = Modifier,
    state: TagState = TagState(),
    icon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
) {
    LocalWidgets.Tag.current(
        config,
        modifier,
        state,
        icon,
        label,
    )
}
