package com.ui.core.widgets.chips

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a chip widget.
 *
 * Brand implementations must handle all [ChipConfig.Variant] values.
 * Slot content (icon/label) picks up per-state colours automatically via
 * `LocalContentColor` and `LocalTextStyle` — the brand impl provides them.
 */
typealias ChipWidgetContent = @Composable (
    config: ChipConfig,
    modifier: Modifier,
    state: ChipState,
    interactionConfig: ChipInteractionConfig,
    leadingIcon: (@Composable () -> Unit)?,
    label: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
) -> Unit

/**
 * Brand-agnostic chip — the **single public API** for all chip variants.
 *
 * Uses a **slot-based** content API: pass any composable for the `leadingIcon`,
 * `label`, and `trailingIcon`. Reuse existing design-system widgets:
 *
 * ```kotlin
 * Chip(
 *     config = ChipConfig(variant = ChipConfig.Variant.Filter),
 *     state = ChipState(isSelected = true),
 *     interactionConfig = ChipInteractionConfig(onClick = { toggle() }),
 *     leadingIcon = {
 *         Icon(
 *             config = IconConfig(size = IconConfig.Size.SM),
 *             icon = { androidx.compose.material3.Icon(Icons.Filled.Check, contentDescription = "Selected") },
 *         )
 *     },
 *     label = { Text("Color") },
 * )
 * ```
 *
 * @param config           Chip variant + slot visibility flags.
 * @param modifier         Applied to the outermost layout node.
 * @param state            Interactive state bundle.
 * @param interactionConfig Click/dismiss handlers.
 * @param leadingIcon      Optional leading icon slot.
 * @param label            Optional label slot.
 * @param trailingIcon     Optional trailing icon/button slot.
 */
@Composable
fun Chip(
    config: ChipConfig = ChipConfig(),
    modifier: Modifier = Modifier,
    state: ChipState = ChipState(),
    interactionConfig: ChipInteractionConfig = ChipInteractionConfig(),
    leadingIcon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    LocalWidgets.Chip.current(
        config,
        modifier,
        state,
        interactionConfig,
        leadingIcon,
        label,
        trailingIcon,
    )
}
