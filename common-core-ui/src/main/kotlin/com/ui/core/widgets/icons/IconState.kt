package com.ui.core.widgets.icons

import androidx.compose.runtime.Immutable

/**
 * Runtime state flags for [Icon].
 *
 * The Icon widget is primarily non-interactive, but it may appear inside
 * interactive parents that drive `enabled` or `isFocused` visually.
 *
 * Example:
 * ```kotlin
 * Icon(
 *     config = IconConfig(),
 *     state = IconState(enabled = false),
 *     icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
 * )
 * ```
 *
 * @property enabled when `false`, the entire icon is rendered at the design
 *  system's disabled opacity (`Sem.Opacity.Disabled`).
 */
@Immutable
data class IconState(
    val enabled: Boolean = true,
)
