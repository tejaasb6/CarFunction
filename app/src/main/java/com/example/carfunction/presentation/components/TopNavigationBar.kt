package com.example.carfunction.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carfunction.domain.model.NavigationTab
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.navigationbars.NavigationBar
import com.ui.core.widgets.navigationbars.NavigationBarConfig
import com.ui.core.widgets.navigationbars.NavigationBarInteractionConfig
import com.ui.core.widgets.navigationbars.NavigationBarItem
import com.ui.core.widgets.navigationbars.NavigationBarState
import com.ui.core.widgets.text.TR

/**
 * Top navigation bar using the design-system [NavigationBar] widget from audi-compose-ui.
 *
 * Renders a standalone search icon followed by scrollable tab labels
 * (MyCar, Charging, etc.). The search icon is placed outside the NavigationBar
 * widget so that it renders correctly with the Label variant.
 * The active tab is rendered with Audi brand styling (filled pill).
 */
@Composable
fun TopNavigationBar(
    selectedTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = NavigationTab.entries
    val selectedIndex = tabs.indexOf(selectedTab).coerceAtLeast(0)

    // Tab-only items for the NavigationBar widget
    val items = tabs.map { tab ->
        NavigationBarItem(label = tab.label.TR)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ── Standalone search icon ─────────────────────────────────────────
        Icon(
            source = IconSource.Vector(Icons.Filled.Search, contentDescription = "Search"),
            config = IconConfig(size = IconConfig.Size.MD),
            modifier = Modifier
                .clickable(onClick = onSearchClick)
                .padding(8.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        // ── Navigation tab bar ─────────────────────────────────────────────
        NavigationBar(
            config = NavigationBarConfig(
                variant = NavigationBarConfig.Variant.Label,
                mode = NavigationBarConfig.Mode.Hug,
                scrollIndicator = false,
            ),
            state = NavigationBarState(enabled = true),
            items = items,
            interactionConfig = NavigationBarInteractionConfig(
                selectedIndex = selectedIndex,
                onSelectedIndexChange = { index ->
                    tabs.getOrNull(index)?.let { onTabSelected(it) }
                },
            ),
        )
    }
}
