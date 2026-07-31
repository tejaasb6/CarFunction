/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.toggleswitch.ToggleSwitch
import com.ui.core.widgets.toggleswitch.ToggleSwitchContent
import com.ui.core.widgets.toggleswitch.ToggleSwitchInteractionConfig
import com.ui.core.widgets.toggleswitch.ToggleSwitchState

/**
 * Toggle for "Show Dynamic Content" using the design-system [ToggleSwitch]
 * widget from audi-compose-ui. The Audi brand implementation renders the
 * green-on/gray-off switch styling automatically via design tokens.
 */
@Composable
fun DynamicContentToggle(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    ToggleSwitch(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        content = ToggleSwitchContent(
            label = "Show Dynamic Content".TR,
        ),
        state = ToggleSwitchState(
            enabled = true,
            controlLeading = true,
        ),
        interactionConfig = ToggleSwitchInteractionConfig(
            selected = isEnabled,
            onSelectedChange = onToggle,
        ),
    )
}
