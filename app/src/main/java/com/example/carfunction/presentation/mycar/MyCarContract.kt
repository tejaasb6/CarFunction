/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.mycar

import com.example.carfunction.core.mvi.MviEffect
import com.example.carfunction.core.mvi.MviIntent
import com.example.carfunction.core.mvi.MviState
import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.model.CarViewMode
import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.model.NavigationTab
import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.model.VehicleHotspot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * MVI contract for the MyCar screen.
 * Groups all Intent, State, and Effect types.
 */
object MyCarContract {

    // ── Intents (user actions) ─────────────────────────────────────────────
    sealed interface Intent : MviIntent {
        data object LoadData : Intent
        data class SelectNavigationTab(val tab: NavigationTab) : Intent
        data class ToggleCarView(val mode: CarViewMode) : Intent
        data class SelectDriveMode(val mode: DriveMode) : Intent
        data class CycleDriveMode(val forward: Boolean) : Intent
        data class SetMassageDriverMode(val mode: MassageMode) : Intent
        data class SetMassagePassengerMode(val mode: MassageMode) : Intent
        data class SelectAmbientPreset(val presetId: String) : Intent
        data class ToggleDynamicContent(val enabled: Boolean) : Intent
        data class HotspotClicked(val hotspot: VehicleHotspot) : Intent
        data object SearchClicked : Intent
        data object AddQuickAccessClicked : Intent
    }

    // ── State ──────────────────────────────────────────────────────────────
    data class State(
        val isLoading: Boolean = true,
        val selectedTab: NavigationTab = NavigationTab.MY_CAR,
        val carViewMode: CarViewMode = CarViewMode.EXTERIOR,
        val quickAccessFeatures: ImmutableList<QuickAccessFeature> = persistentListOf(),
        val driveModes: ImmutableList<DriveMode> = persistentListOf(),
        val selectedDriveModeIndex: Int = 1, // default to "balanced"
        val massageState: MassageState = MassageState(),
        val ambientLightPresets: ImmutableList<AmbientLightPreset> = persistentListOf(),
        val selectedAmbientPresetId: String = "",
        val vehicleHotspots: ImmutableList<VehicleHotspot> = persistentListOf(),
        val isDynamicContentEnabled: Boolean = true,
        val error: String? = null,
    ) : MviState {
        val selectedDriveMode: DriveMode?
            get() = driveModes.getOrNull(selectedDriveModeIndex)
    }

    // ── Effects (one-shot side-effects) ────────────────────────────────────
    sealed interface Effect : MviEffect {
        data class ShowToast(val message: String) : Effect
        data class NavigateToHotspotDetail(val hotspotId: String) : Effect
        data object OpenSearch : Effect
        data object OpenAddQuickAccess : Effect
    }
}
