package com.ui.core.widgets.icons

import androidx.compose.runtime.Immutable

/**
 * Descriptor for [Icon]: the sizing class and optional tinting toggle.
 *
 * Example:
 * ```kotlin
 * Icon(
 *     config = IconConfig(size = IconConfig.Size.MD),
 *     icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
 * )
 * ```
 *
 * @property size the physical size class — see [Size].
 * @property hasTintingColors when `true`, the icon is tinted using semantic content
 *  tokens from the active brand theme. When `false`, the icon retains its inherent
 *  colours (e.g. multi-colour SVGs).
 */
@Immutable
data class IconConfig(
    val size: Size = Size.MD,
    val hasTintingColors: Boolean = true,
) {
    /**
     * Physical size class for the Icon widget.
     *
     * Example:
     * ```kotlin
     * IconConfig(size = IconConfig.Size.SM)
     * ```
     *
     * | Value | Design token px | Physical (@160 dpi) |
     * |-------|----------------|---------------------|
     * | SM    | 32 px          | ~5.08 mm            |
     * | MD    | 48 px          | ~7.62 mm            |
     * | LG    | 64 px          | ~10.16 mm           |
     */
    enum class Size {
        SM,
        MD,
        LG,
    }
}
