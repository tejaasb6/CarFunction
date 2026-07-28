package com.ui.core.widgets.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/** Composable function type for a brand-themed Icon widget. */
typealias IconWidgetContent = @Composable (
    config: IconConfig,
    modifier: Modifier,
    state: IconState,
    source: IconSource?,
    icon: (@Composable () -> Unit)?,
) -> Unit

/**
 * Brand-themed Icon — **new API** using [IconSource].
 *
 * Accepts a typed [IconSource] describing the icon content — supports vector
 * icons, drawable resources, custom painters, and bitmaps.
 *
 * ```kotlin
 * Icon(source = IconSource.Vector(Icons.Filled.Search, contentDescription = "Search"))
 *
 * Icon(
 *     config = IconConfig(size = IconConfig.Size.LG),
 *     source = IconSource.Resource(R.drawable.ic_settings, contentDescription = "Settings"),
 * )
 * ```
 *
 * @param source the icon content — vector, resource, painter, or bitmap.
 * @param config descriptor for the icon's size class and tinting toggle.
 * @param modifier caller-supplied modifier.
 * @param state runtime flags: `enabled`.
 */
@Composable
fun Icon(
    source: IconSource,
    config: IconConfig = IconConfig(),
    modifier: Modifier = Modifier,
    state: IconState = IconState(),
) {
    LocalWidgets.Icon.current(config, modifier, state, source, null)
}

/**
 * Brand-themed Icon — **legacy slot API** (deprecated).
 *
 * Use [Icon] with [IconSource] instead for type-safe icon content.
 *
 * ```kotlin
 * // Old way (deprecated):
 * Icon(config = IconConfig(), icon = { M3Icon(Icons.Filled.Add, ...) })
 *
 * // New way:
 * Icon(source = IconSource.Vector(Icons.Filled.Add, contentDescription = "Add"))
 * ```
 *
 * @param config descriptor for the icon's size class and tinting toggle.
 * @param modifier caller-supplied modifier.
 * @param state runtime flags: `enabled`.
 * @param icon composable slot for the raw icon content.
 */
@Deprecated(
    message = "Use Icon(source = IconSource.Vector/Resource/...) instead",
    replaceWith = ReplaceWith("Icon(source = IconSource.Vector(...), config = config, modifier = modifier, state = state)"),
)
@Composable
fun Icon(
    config: IconConfig = IconConfig(),
    modifier: Modifier = Modifier,
    state: IconState = IconState(),
    icon: @Composable () -> Unit,
) {
    LocalWidgets.Icon.current(config, modifier, state, null, icon)
}
