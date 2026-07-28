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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carfunction.domain.model.CarViewMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.presentation.components.AmbientLightPresetsRow
import com.example.carfunction.presentation.components.DriveSelectCarousel
import com.example.carfunction.presentation.components.DynamicContentToggle
import com.example.carfunction.presentation.components.ExteriorInteriorToggle
import com.example.carfunction.presentation.components.MassageControl
import com.example.carfunction.presentation.components.QuickAccessTiles
import com.example.carfunction.presentation.components.TopNavigationBar
import com.ui.core.widgets.dividers.Divider
import com.ui.core.widgets.dividers.DividerConfig

/**
 * MyCar screen — three core layout elements:
 *
 * ┌─────────────────────────────────────────────────────────────┐
 * │  [Search] [MyCar] [Charging] [Driving...] [Comfort...]     │  ← Navigation Bar
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
@Composable
fun MyCarScreen(
    viewModel: MyCarViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        // ── 1. Top Navigation Bar ──────────────────────────────────────────
        TopNavigationBar(
            selectedTab = state.selectedTab,
            onTabSelected = { viewModel.dispatch(MyCarContract.Intent.SelectNavigationTab(it)) },
            onSearchClick = { viewModel.dispatch(MyCarContract.Intent.SearchClicked) },
        )

        // ── Spacing between nav bar and content ────────────────────────────
        Spacer(modifier = Modifier.height(16.dp))

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
                        viewModel.dispatch(MyCarContract.Intent.AddQuickAccessClicked)
                    },
                )

                Spacer(modifier = Modifier.height(4.dp))
                Divider(config = DividerConfig(padding = 16.dp))
                Spacer(modifier = Modifier.height(4.dp))

                // ── Drive Select Carousel ──────────────────────────────────
                DriveSelectCarousel(
                    currentMode = state.selectedDriveMode,
                    onPrevious = {
                        viewModel.dispatch(MyCarContract.Intent.CycleDriveMode(forward = false))
                    },
                    onNext = {
                        viewModel.dispatch(MyCarContract.Intent.CycleDriveMode(forward = true))
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
                        viewModel.dispatch(MyCarContract.Intent.SetMassageDriverMode(it))
                    },
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ── Massage Passenger ──────────────────────────────────────
                MassageControl(
                    title = "Massage Passenger",
                    currentMode = state.massageState.passengerMode,
                    options = listOf(MassageMode.OFF, MassageMode.MOBILITY),
                    onModeSelected = {
                        viewModel.dispatch(MyCarContract.Intent.SetMassagePassengerMode(it))
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
                        viewModel.dispatch(MyCarContract.Intent.SelectAmbientPreset(it))
                    },
                )

                Spacer(modifier = Modifier.height(4.dp))
                Divider(config = DividerConfig(padding = 16.dp))
                Spacer(modifier = Modifier.height(4.dp))

                // ── Show Dynamic Content Toggle ────────────────────────────
                DynamicContentToggle(
                    isEnabled = state.isDynamicContentEnabled,
                    onToggle = {
                        viewModel.dispatch(MyCarContract.Intent.ToggleDynamicContent(it))
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
