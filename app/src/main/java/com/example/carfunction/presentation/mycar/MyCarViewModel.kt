package com.example.carfunction.presentation.mycar

import com.example.carfunction.core.mvi.MviViewModel
import com.example.carfunction.di.AppContainer
import com.example.carfunction.domain.model.CarViewMode
import com.example.carfunction.presentation.mycar.MyCarContract.Effect
import com.example.carfunction.presentation.mycar.MyCarContract.Intent
import com.example.carfunction.presentation.mycar.MyCarContract.State
import kotlinx.coroutines.flow.first

/**
 * MyCar ViewModel implementing MVI pattern.
 * Handles all user intents and produces state updates + side effects.
 */
class MyCarViewModel : MviViewModel<Intent, State, Effect>(State()) {

    private val getQuickAccess = AppContainer.getQuickAccessFeatures
    private val getDriveModes = AppContainer.getDriveModes
    private val getMassageState = AppContainer.getMassageState
    private val getAmbientPresets = AppContainer.getAmbientLightPresets
    private val getHotspots = AppContainer.getVehicleHotspots
    private val setDriveMode = AppContainer.setDriveMode
    private val setMassageMode = AppContainer.setMassageMode
    private val toggleDynamic = AppContainer.toggleDynamicContent

    init {
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
                    quickAccessFeatures = features,
                    driveModes = modes,
                    massageState = massage,
                    ambientLightPresets = presets,
                    selectedAmbientPresetId = presets.firstOrNull()?.id.orEmpty(),
                    vehicleHotspots = hotspots,
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
