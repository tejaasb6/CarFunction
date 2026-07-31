/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract

/**
 * Panorama Roof content section.
 *
 * Per the reference Audi MMI design, this sub-section shows only the
 * sidebar selection (highlighted "Panorama Roof" item) with the 3D roof
 * visualization rendered in the right pane by the OEM rendering engine.
 * No additional settings controls are displayed in the center content area.
 */
@Composable
fun PanoramaRoofContent(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Empty content — per reference screenshots, no controls are shown
    // in the center pane for Panorama Roof; the 3D visualization in the
    // right pane provides interactive roof segment control.
    Box(modifier = modifier.fillMaxWidth())
}
