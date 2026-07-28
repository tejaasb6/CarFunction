package com.ui.core.widgets.navigationbars

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextResource

/**
 * Describes a single navigation destination inside a [NavigationBar].
 *
 * ```kotlin
 * NavigationBarItem(
 *     label = "Home".TR,
 *     icon = IconSource.Resource(R.drawable.ic_home, contentDescription = "Home"),
 * )
 * ```
 *
 * @property label Text label for the item. Used in [NavigationBarConfig.Variant.Label]
 *   and [NavigationBarConfig.Variant.LeadingIcon] layouts.
 * @property icon Icon source for the item. Used in
 *   [NavigationBarConfig.Variant.Icon] and [NavigationBarConfig.Variant.LeadingIcon]
 *   layouts. May be `null` when the variant is [NavigationBarConfig.Variant.Label].
 * @property badge Optional composable slot for a badge / semantic shape overlay
 *   rendered on the item. May be `null` (default).
 */
@Immutable
data class NavigationBarItem(
    val label: TextResource = EmptyTextResource,
    val icon: IconSource? = null,
    val badge: (@Composable () -> Unit)? = null,
)
