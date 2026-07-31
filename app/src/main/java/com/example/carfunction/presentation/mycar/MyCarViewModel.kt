/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.mycar

import androidx.lifecycle.SavedStateHandle
import com.example.carfunction.core.mvi.MviViewModel
import com.example.carfunction.domain.model.CarViewMode
import com.example.carfunction.domain.usecase.GetAmbientLightPresetsUseCase
import com.example.carfunction.domain.usecase.GetDriveModesUseCase
import com.example.carfunction.domain.usecase.GetMassageStateUseCase
import com.example.carfunction.domain.usecase.GetQuickAccessFeaturesUseCase
import com.example.carfunction.domain.usecase.GetVehicleHotspotsUseCase
import com.example.carfunction.domain.usecase.SetDriveModeUseCase
import com.example.carfunction.domain.usecase.SetMassageModeUseCase
import com.example.carfunction.domain.usecase.ToggleDynamicContentUseCase
import com.example.carfunction.presentation.mycar.MyCarContract.Effect
import com.example.carfunction.presentation.mycar.MyCarContract.Intent
import com.example.carfunction.presentation.mycar.MyCarContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * MyCar ViewModel implementing MVI pattern.
 * Handles all user intents and produces state updates + side effects.
 *
 * @Traceability
 * - Requirement ID: SRS-REQ-MYCAR-01
 */
@HiltViewModel
class MyCarViewModel @Inject constructor(
    private val getQuickAccess: GetQuickAccessFeaturesUseCase,
    private val getDriveModes: GetDriveModesUseCase,
    private val getMassageState: GetMassageStateUseCase,
    private val getAmbientPresets: GetAmbientLightPresetsUseCase,
    private val getHotspots: GetVehicleHotspotsUseCase,
    private val setDriveMode: SetDriveModeUseCase,
    private val setMassageMode: SetMassageModeUseCase,
    private val toggleDynamic: ToggleDynamicContentUseCase,
    private val savedStateHandle: SavedStateHandle,
) : MviViewModel<Intent, State, Effect>(State()) {

    /**
     * Called by the Screen composable via `LaunchedEffect(Unit)` to trigger
     * initial data loading. Avoids launching coroutines in `init {}`.
     */
    fun loadInitialData() {
        dispatch(Intent.LoadData)
    }

    override suspend fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.LoadData -> loadAllData()
            is Intent.SelectNavigationTab -> {
                updateState { copy(selectedTab = intent.tab) }
            }
            is Intent.ToggleCarView -> {
                updateState { copy(carViewMode = intent.mode) }
            }
            is Intent.SelectDriveMode -> {
                val index = currentState.driveModes.indexOf(intent.mode)
                if (index >= 0) {
                    updateState { copy(selectedDriveModeIndex = index) }
                    setDriveMode(intent.mode)
                }
            }
            is Intent.CycleDriveMode -> {
                val modes = currentState.driveModes
                if (modes.isNotEmpty()) {
                    val current = currentState.selectedDriveModeIndex
                    val next = if (intent.forward) {
                        (current + 1) % modes.size
                    } else {
                        (current - 1 + modes.size) % modes.size
                    }
                    updateState { copy(selectedDriveModeIndex = next) }
                    modes.getOrNull(next)?.let { setDriveMode(it) }
                }
            }
            is Intent.SetMassageDriverMode -> {
                setMassageMode.setDriverMode(intent.mode)
                val updated = getMassageState().first()
                updateState { copy(massageState = updated) }
            }
            is Intent.SetMassagePassengerMode -> {
                setMassageMode.setPassengerMode(intent.mode)
                val updated = getMassageState().first()
                updateState { copy(massageState = updated) }
            }
            is Intent.SelectAmbientPreset -> {
                updateState { copy(selectedAmbientPresetId = intent.presetId) }
            }
            is Intent.ToggleDynamicContent -> {
                toggleDynamic.setEnabled(intent.enabled)
                updateState { copy(isDynamicContentEnabled = intent.enabled) }
            }
            is Intent.HotspotClicked -> {
                sendEffect(Effect.NavigateToHotspotDetail(intent.hotspot.id))
            }
            is Intent.SearchClicked -> {
                sendEffect(Effect.OpenSearch)
            }
            is Intent.AddQuickAccessClicked -> {
                sendEffect(Effect.OpenAddQuickAccess)
            }
        }
    }

    private suspend fun loadAllData() {
        try {
            val features = getQuickAccess().first()
            val modes = getDriveModes().first()
            val massage = getMassageState().first()
            val presets = getAmbientPresets().first()
            val hotspots = getHotspots().first()
            val dynamic = toggleDynamic.isEnabled().first()

            updateState {
                copy(
                    isLoading = false,
                    quickAccessFeatures = features.toImmutableList(),
                    driveModes = modes.toImmutableList(),
                    massageState = massage,
                    ambientLightPresets = presets.toImmutableList(),
                    selectedAmbientPresetId = presets.firstOrNull()?.id.orEmpty(),
                    vehicleHotspots = hotspots.toImmutableList(),
                    isDynamicContentEnabled = dynamic,
                    error = null,
                )
            }
        } catch (e: Exception) {
            updateState {
                copy(isLoading = false, error = e.message ?: "Unknown error")
            }
        }
    }
}
