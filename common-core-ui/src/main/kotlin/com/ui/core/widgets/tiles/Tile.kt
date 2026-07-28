package com.ui.core.widgets.tiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type provided by a brand theme for the Tile widget.
 *
 * The brand implementation is registered in [LocalWidgets.Tile] and invoked
 * by the agnostic [Tile] composable below.
 */
typealias TileWidgetContent = @Composable (
    config: TileConfig,
    modifier: Modifier,
    state: TileState,
    interactionConfig: TileInteractionConfig,
    content: @Composable () -> Unit,
) -> Unit

/**
 * A brand-agnostic Tile composable.
 *
 * A tile is a **single-action interactive container** designed for richer,
 * non-interactive content than a button. It can include multiple elements
 * (text, icons, status indicators) that are laid out by the caller inside
 * the [content] slot.
 *
 * The actual rendering is delegated to the brand implementation wired into
 * [LocalWidgets.Tile].
 *
 * ```kotlin
 * Tile(
 *     config = TileConfig(mode = TileConfig.Mode.Hug),
 *     state = TileState(enabled = true, isSelected = false),
 *     interactionConfig = TileInteractionConfig(
 *         onClick = { navigateToClimate() },
 *     ),
 * ) {
 *     Column {
 *         Text(state = TextState(text = "Climate".TR))
 *         Text(state = TextState(text = "22 °C".TR))
 *     }
 * }
 * ```
 *
 * @param config            Sizing and layout configuration.
 * @param modifier          Optional [Modifier] applied to the tile root.
 * @param state             Preview-state flags (enabled, selected, focused).
 * @param interactionConfig Tap / long-press / debounce configuration.
 * @param content           The non-interactive content rendered inside the tile.
 */
@Composable
fun Tile(
    config: TileConfig = TileConfig(),
    modifier: Modifier = Modifier,
    state: TileState = TileState(),
    interactionConfig: TileInteractionConfig = TileInteractionConfig(),
    content: @Composable () -> Unit = {},
) {
    LocalWidgets.Tile.current(
        config,
        modifier,
        state,
        interactionConfig,
        content,
    )
}
