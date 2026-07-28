package com.ui.core.widgets.navigationbars

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.ui.core.widgets.icons.IconConfig

/**
 * Token-derived style bundle for the [NavigationBar] widget.
 *
 * Provided via [LocalNavigationBarStyle] by brand themes.
 *
 * @property barHeight Overall height of the navigation bar container.
 * @property itemMinWidth Minimum width of a single item surface.
 * @property itemSurfaceHeight Height of the item surface (touch area).
 * @property itemStateLayerHeight Height of the state-layer overlay.
 * @property underlineHeight Height of the selected-indicator underline.
 * @property underlineWidth Border width of the underline stroke.
 * @property itemCornerRadiusIdle Corner radius for idle items.
 * @property itemCornerRadiusPressed Corner radius for pressed items.
 * @property itemCornerRadiusDisabled Corner radius for disabled items.
 * @property itemBorderWidthIdle Border width for idle item surfaces.
 * @property itemBorderWidthPressed Border width for pressed item surfaces.
 * @property itemBorderWidthDisabled Border width for disabled item surfaces.
 * @property itemGap Vertical gap between icon and label inside an item.
 * @property itemHPadding Horizontal padding inside an item surface.
 * @property itemVPadding Vertical padding inside an item surface.
 * @property fadeOutWrapperHeight Height of the scroll-fade gradient overlay.
 * @property iconSize Size class for the item icon ([IconConfig.Size]).
 * @property selectedTextStyle Typography for the label of a selected item.
 * @property unselectedTextStyle Typography for the label of an unselected item.
 * @property colors Per-state colour definitions for items.
 */
@Immutable
data class NavigationBarStyle(
    val barHeight: Dp,
    val itemMinWidth: Dp,
    val itemSurfaceHeight: Dp,
    val itemStateLayerHeight: Dp,
    val underlineHeight: Dp,
    val underlineWidth: Dp,
    val itemCornerRadiusIdle: Dp,
    val itemCornerRadiusPressed: Dp,
    val itemCornerRadiusDisabled: Dp,
    val itemBorderWidthIdle: Dp,
    val itemBorderWidthPressed: Dp,
    val itemBorderWidthDisabled: Dp,
    val itemGap: Dp,
    val itemHPadding: Dp,
    val itemVPadding: Dp,
    val fadeOutWrapperHeight: Dp,
    val iconSize: IconConfig.Size,
    val selectedTextStyle: TextStyle,
    val unselectedTextStyle: TextStyle,
    val colors: NavigationBarTypeColors,
)

/**
 * CompositionLocal for the brand-resolved [NavigationBarStyle].
 *
 * Provided inside the brand theme (e.g. `AudiTheme`) — never access
 * before the theme wraps the composition tree.
 */
val LocalNavigationBarStyle =
    staticCompositionLocalOf<NavigationBarStyle> {
        error("No NavigationBarStyle provided. Wrap your composable tree in AudiTheme or LamborghiniTheme.")
    }
