/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Favorites content section.
 *
 * Per the reference Audi MMI design, this sub-section shows only the
 * sidebar selection (highlighted "Favorites" item) with the 3D cockpit
 * visualization rendered in the right pane. Interactive cockpit zone
 * labels (Left Satellite, Right Satellite, Climate Favorite, Center
 * Control Unit) are overlaid on the 3D rendering by the OEM engine.
 * No additional settings controls are displayed in the center content area.
 */
@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
) {
    // Empty content — per reference screenshots, no controls are shown
    // in the center pane for Favorites; the 3D cockpit visualization
    // in the right pane provides the interactive zone selection.
    Box(modifier = modifier.fillMaxWidth())
}
