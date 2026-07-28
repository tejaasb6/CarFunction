package com.ui.core.widgets.textlink

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a brand-themed TextLink widget.
 *
 * Brand implementations must match this signature exactly. The public [TextLink]
 * composable delegates to the brand lambda registered in [LocalWidgets.TextLink].
 *
 * Example:
 * ```kotlin
 * val myTextLink: TextLinkWidgetContent =
 *     { config, modifier, state, interactionConfig, leading, label, trailing ->
 *         // brand-specific rendering
 *     }
 * ```
 */
typealias TextLinkWidgetContent = @Composable (
    config: TextLinkConfig,
    modifier: Modifier,
    state: TextLinkState,
    interactionConfig: TextLinkInteractionConfig,
    leading: (@Composable () -> Unit)?,
    label: (@Composable () -> Unit)?,
    trailing: (@Composable () -> Unit)?,
) -> Unit

/**
 * Brand-agnostic TextLink widget — the **single public API** for rendering themed
 * navigational text links.
 *
 * Links take the user to related information or another view. They can appear on
 * their own ([TextLinkConfig.Variant.Standalone]) or inline within a paragraph
 * ([TextLinkConfig.Variant.Inline]).
 *
 * ## Anatomy
 * - **Leading slot** — optional icon (Standalone only).
 * - **Label slot** — the link text (mandatory).
 * - **Trailing slot** — optional external-link icon.
 *
 * ## States
 * | State | Description |
 * |-------|-------------|
 * | Idle | Default rest state. |
 * | Pressed | Finger down — bolder underline + colour shift. |
 * | Disabled | Non-interactive; reduced opacity. |
 * | Focus | Visible focus ring for D-pad / keyboard (Standalone only). |
 *
 * Example:
 * ```kotlin
 * TextLink(
 *     config = TextLinkConfig(variant = TextLinkConfig.Variant.Standalone),
 *     interactionConfig = TextLinkInteractionConfig(
 *         onClick = { navController.navigate("details") },
 *     ),
 *     leading = {
 *         Icon(
 *             config = IconConfig(size = IconConfig.Size.SM),
 *             icon = { Icon(Icons.Outlined.Link, contentDescription = null) },
 *         )
 *     },
 *     label = { Text(text = "View details") },
 * )
 * ```
 *
 * @param config variant configuration (Standalone / Inline).
 * @param modifier applied to the outermost layout node.
 * @param state runtime state flags (enabled, isFocused).
 * @param interactionConfig click interaction handlers and debounce settings.
 * @param leading optional leading composable slot (icon; Standalone only).
 * @param label the link text composable slot.
 * @param trailing optional trailing composable slot (external-link icon).
 */
@Composable
fun TextLink(
    config: TextLinkConfig = TextLinkConfig(),
    modifier: Modifier = Modifier,
    state: TextLinkState = TextLinkState(),
    interactionConfig: TextLinkInteractionConfig = TextLinkInteractionConfig(),
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
) {
    LocalWidgets.TextLink.current(
        config,
        modifier,
        state,
        interactionConfig,
        leading,
        label,
        trailing,
    )
}
