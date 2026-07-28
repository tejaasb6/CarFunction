package com.ui.core.widgets.selects

import androidx.compose.runtime.Immutable

/**
 * Configuration for the [Select] widget's interaction mode and menu behaviour.
 *
 * ```kotlin
 * SelectConfig(
 *     showOptionIcon = true,
 *     adaptiveMenuHeight = true,
 * )
 * ```
 *
 * @param showOptionIcon whether to display the optional icon in dropdown menu items.
 * @param adaptiveMenuHeight when `true`, the dropdown menu adjusts its height based
 *  on the available screen space and applies a scrollbar if the options overflow.
 *  When `false` (default), the menu uses the token-based maximum visible item count
 *  and scrollbar logic only.
 */
@Immutable
data class SelectConfig(
    val showOptionIcon: Boolean = true,
    val adaptiveMenuHeight: Boolean = false,
)
