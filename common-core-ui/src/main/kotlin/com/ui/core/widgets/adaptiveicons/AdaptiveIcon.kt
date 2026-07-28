package com.ui.core.widgets.adaptiveicons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/** Composable function type for an adaptive icon widget. */
typealias AdaptiveIconWidgetContent = @Composable (
    config: AdaptiveIconConfig,
    modifier: Modifier,
    icon: @Composable () -> Unit,
) -> Unit

/**
 * Brand-agnostic adaptive icon — an icon inside a shaped background container.
 *
 * Internally uses the design-system [com.ui.core.widgets.icons.Icon] widget for
 * proper token-driven sizing and tinting. The caller only provides the raw icon
 * glyph content:
 *
 * ```kotlin
 * AdaptiveIcon(
 *     config = AdaptiveIconConfig(shape = AdaptiveIconConfig.Shape.Circle),
 *     icon = { Icon(Icons.Filled.Person, contentDescription = "User") },
 * )
 * ```
 *
 * @param config shape and icon size configuration.
 * @param modifier applied to the outermost layout node.
 * @param icon composable slot for the raw icon glyph (e.g. material3 Icon).
 */
@Composable
fun AdaptiveIcon(
    config: AdaptiveIconConfig = AdaptiveIconConfig(),
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    LocalWidgets.AdaptiveIcon.current(config, modifier, icon)
}
