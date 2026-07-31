/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.mycar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carfunction.domain.model.CarViewMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.presentation.components.AmbientLightPresetsRow
import com.example.carfunction.presentation.components.DriveSelectCarousel
import com.example.carfunction.presentation.components.DynamicContentToggle
import com.example.carfunction.presentation.components.ExteriorInteriorToggle
import com.example.carfunction.presentation.components.MassageControl
import com.example.carfunction.presentation.components.QuickAccessTiles
import com.ui.core.widgets.dividers.Divider
import com.ui.core.widgets.dividers.DividerConfig

/**
 * MyCar screen — three core layout elements:
 *
 * ┌─────────────────────────────────────────────────────────────┐
 * │  [Search] [MyCar] [Charging] [Driving...] [Comfort...]      │  ← Navigation Bar
 * ├──────────────────────┬──────────────────────────────────────┤
 * │  Quick Access Tiles  │                                      │
 * │  ────────────────    │                                      │
 * │  Drive Select        │         Right content area           │
 * │  ────────────────    │           (marble bg)                │
 * │  Massage Driver      │                                      │
 * │  Massage Passenger   │                          ┌──────────┐│
 * │  ────────────────    │                          │Ext | Int ││ ← Toggle (no func)
 * │  Ambient Presets     │                          └──────────┘│
 * │  ────────────────    │                                      │
 * │  Dynamic Content     │                                      │
 * └──────────────────────┴──────────────────────────────────────┘
 */
/**
 * Screen composable — owns the [MyCarViewModel], collects state, handles
 * one-shot effects, and delegates all layout to [MyCarContent].
 */
@Composable
fun MyCarScreen(
    viewModel: MyCarViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Trigger initial data load once per composition lifetime
    LaunchedEffect(Unit) {
        viewModel.loadInitialData()
    }

    // Collect one-shot effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyCarContract.Effect.ShowToast -> { /* Handle toast */ }
                is MyCarContract.Effect.NavigateToHotspotDetail -> { /* Navigate */ }
                is MyCarContract.Effect.OpenSearch -> { /* Open search */ }
                is MyCarContract.Effect.OpenAddQuickAccess -> { /* Open add dialog */ }
            }
        }
    }

    MyCarContent(
        state = state,
        onDispatch = viewModel::dispatch,
    )
}

/**
 * Layout composable — pure, testable UI that receives immutable [state] and
 * dispatches user interactions via [onDispatch]. No ViewModel reference.
 */
@Composable
fun MyCarContent(
    state: MyCarContract.State,
    onDispatch: (MyCarContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        // ── Main Content: Left Pane + Right Pane ───────────────────────────
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            // ── 2. Left Pane: scrollable list of controls ──────────────────
            Column(
                modifier = Modifier
                    .width(320.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 12.dp),
            ) {
                // ── Quick Access Tiles ─────────────────────────────────────
                QuickAccessTiles(
                    features = state.quickAccessFeatures,
                    maxSlots = 4,
                    onAddClick = {
                        onDispatch(MyCarContract.Intent.AddQuickAccessClicked)
                    },
                )

                Spacer(modifier = Modifier.height(4.dp))
                Divider(config = DividerConfig(padding = 16.dp))
                Spacer(modifier = Modifier.height(4.dp))

                // ── Drive Select Carousel ──────────────────────────────────
                DriveSelectCarousel(
                    currentMode = state.selectedDriveMode,
                    onPrevious = {
                        onDispatch(MyCarContract.Intent.CycleDriveMode(forward = false))
                    },
                    onNext = {
                        onDispatch(MyCarContract.Intent.CycleDriveMode(forward = true))
                    },
                )

                Spacer(modifier = Modifier.height(4.dp))
                Divider(config = DividerConfig(padding = 16.dp))
                Spacer(modifier = Modifier.height(4.dp))

                // ── Massage Driver ─────────────────────────────────────────
                MassageControl(
                    title = "Massage Driver",
                    currentMode = state.massageState.driverMode,
                    options = listOf(MassageMode.OFF, MassageMode.ACTIVE),
                    onModeSelected = {
                        onDispatch(MyCarContract.Intent.SetMassageDriverMode(it))
                    },
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ── Massage Passenger ──────────────────────────────────────
                MassageControl(
                    title = "Massage Passenger",
                    currentMode = state.massageState.passengerMode,
                    options = listOf(MassageMode.OFF, MassageMode.MOBILITY),
                    onModeSelected = {
                        onDispatch(MyCarContract.Intent.SetMassagePassengerMode(it))
                    },
                )

                Spacer(modifier = Modifier.height(4.dp))
                Divider(config = DividerConfig(padding = 16.dp))
                Spacer(modifier = Modifier.height(4.dp))

                // ── Ambient Light Presets ───────────────────────────────────
                AmbientLightPresetsRow(
                    presets = state.ambientLightPresets,
                    selectedPresetId = state.selectedAmbientPresetId,
                    onPresetSelected = {
                        onDispatch(MyCarContract.Intent.SelectAmbientPreset(it))
                    },
                )

                Spacer(modifier = Modifier.height(4.dp))
                Divider(config = DividerConfig(padding = 16.dp))
                Spacer(modifier = Modifier.height(4.dp))

                // ── Show Dynamic Content Toggle ────────────────────────────
                DynamicContentToggle(
                    isEnabled = state.isDynamicContentEnabled,
                    onToggle = {
                        onDispatch(MyCarContract.Intent.ToggleDynamicContent(it))
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // ── 3. Right Pane: marble background + Exterior/Interior toggle
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFF5F5F5),
                                Color(0xFFE8E8E8),
                                Color(0xFFF0F0F0),
                            ),
                        ),
                    ),
            ) {
                // Exterior / Interior toggle (bottom-right, no functionality)
                ExteriorInteriorToggle(
                    selectedMode = CarViewMode.EXTERIOR,
                    onModeSelected = { /* No functionality per spec */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp),
                )
            }
        }
    }
}
