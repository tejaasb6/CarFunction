package com.ui.core.widgets.chips

import androidx.compose.runtime.Immutable

/**
 * Describes which chip variant to render and controls slot visibility.
 *
 * ## Variants
 * | Variant | Leading slot | Trailing slot |
 * |---------|-------------|---------------|
 * | [Variant.Filter] | checkmark (selected only) | optional icon |
 * | [Variant.Input] | optional icon | optional dismiss button |
 * | [Variant.Suggestion] | — | — |
 * | [Variant.Assist] | icon (mandatory) | — |
 *
 * Example:
 * ```kotlin
 * Chip(
 *     config = ChipConfig(variant = ChipConfig.Variant.Filter, showTrailingIcon = true),
 *     interactionConfig = ChipInteractionConfig(onClick = { toggleFilter() }),
 *     label = { Text("Color") },
 * )
 * ```
 *
 * @property variant the chip type — determines anatomy and slot behaviour.
 * @property showLeadingIcon controls visibility of the leading icon slot
 *   (applies to Input variant; Filter shows checkmark automatically when selected).
 * @property showTrailingIcon controls visibility of the trailing icon slot
 *   (applies to Filter variant).
 * @property showTrailingButton controls visibility of the trailing dismiss button
 *   (applies to Input variant).
 */
@Immutable
data class ChipConfig(
    val variant: Variant = Variant.Filter,
    val showLeadingIcon: Boolean = true,
    val showTrailingIcon: Boolean = true,
    val showTrailingButton: Boolean = true,
) {
    /**
     * The chip variant — determines the chip's anatomy.
     */
    enum class Variant {
        /** Toggleable filter with optional trailing icon; checkmark when selected. */
        Filter,

        /** Dismissible tag with optional leading icon and trailing dismiss button. */
        Input,

        /** Text-only dynamic suggestion — no icons. */
        Suggestion,

        /** Smart action with mandatory leading icon. */
        Assist,
    }
}
