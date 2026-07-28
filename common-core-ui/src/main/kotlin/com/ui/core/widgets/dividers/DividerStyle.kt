package com.ui.core.widgets.dividers

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Full visual specification for [Divider].
 *
 * Brand implementations build this from `Cmp.Color.Global.Divider.*` and
 * `Cmp.Size.Global.Divider.*` tokens inside their `DividerDefaults.style()`
 * composable.
 *
 * Override at any level of the composition tree via [LocalDividerStyle].
 *
 * Example:
 * ```kotlin
 * val style = LocalDividerStyle.current
 * val adjustedStyle = style.copy(color = Color.Red)
 * CompositionLocalProvider(LocalDividerStyle provides adjustedStyle) {
 *     Divider()
 * }
 * ```
 *
 * @property color the fill colour of the divider line.
 * @property horizontalThickness thickness (height) of a horizontal divider.
 * @property verticalThickness thickness (width) of a vertical divider.
 */
@Immutable
data class DividerStyle(
    val color: Color,
    val horizontalThickness: Dp,
    val verticalThickness: Dp,
)

/**
 * Composition local providing the current [DividerStyle].
 *
 * Populated by `AudiTheme` / `LamborghiniTheme`; accessing it outside a brand
 * theme throws an error.
 *
 * Example:
 * ```kotlin
 * val dividerStyle = LocalDividerStyle.current
 * ```
 */
val LocalDividerStyle =
    compositionLocalOf<DividerStyle> {
        error("No DividerStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
