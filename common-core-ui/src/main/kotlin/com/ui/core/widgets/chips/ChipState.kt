package com.ui.core.widgets.chips

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for [Chip].
 *
 * State priority (highest → lowest):
 * `disabled > dragged > pressed > focused > selected/idle`
 *
 * Example:
 * ```kotlin
 * Chip(
 *     config = ChipConfig(variant = ChipConfig.Variant.Filter),
 *     state = ChipState(isSelected = true),
 *     interactionConfig = ChipInteractionConfig(onClick = { toggleFilter() }),
 *     label = { Text("Active") },
 * )
 * ```
 *
 * @property enabled   when `false` the chip is non-interactive and rendered at reduced opacity.
 * @property isSelected when `true` the chip renders in its selected colour branch.
 * @property isDragged  when `true` the chip renders in drag state with elevation.
 * @property isFocused  when `true` the chip renders with a visible focus ring border.
 */
@Immutable
data class ChipState(
    val enabled: Boolean = true,
    val isSelected: Boolean = false,
    val isDragged: Boolean = false,
    val isFocused: Boolean = false,
)
