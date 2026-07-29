package com.example.carfunction.presentation.comfortinterior

import com.example.carfunction.core.mvi.MviViewModel
import com.example.carfunction.domain.model.ComfortMassageMode
import com.example.carfunction.domain.model.PanoramaRoofState
import com.example.carfunction.domain.model.SeatPosition
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract.Effect
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract.Intent
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract.State

/**
 * Comfort & Interior ViewModel implementing MVI pattern.
 * Handles all user intents and produces state updates + side effects.
 */
class ComfortInteriorViewModel : MviViewModel<Intent, State, Effect>(State()) {

    init {
        dispatch(Intent.LoadData)
    }

    override suspend fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.LoadData -> loadData()
            is Intent.SelectNavigationTab -> {
                updateState { copy(selectedTab = intent.tab) }
            }
            is Intent.SelectSubSection -> {
                updateState { copy(selectedSubSection = intent.section) }
            }
            is Intent.SearchClicked -> {
                sendEffect(Effect.OpenSearch)
            }

            // ── Seat Massage ───────────────────────────────────────────────
            is Intent.SelectMassageSeat -> {
                updateState { copy(selectedMassageSeat = intent.seat) }
            }
            is Intent.SetMassageMode -> {
                handleSetMassageMode(intent.mode)
            }

            // ── Seat & Loading ─────────────────────────────────────────────
            is Intent.SelectSeatLoadingFunction -> {
                updateState { copy(selectedSeatLoadingFunction = intent.function) }
            }
            is Intent.ToggleSeatSelection -> {
                val current = currentState.selectedSeatIndices
                val updated = if (intent.seatIndex in current) {
                    current - intent.seatIndex
                } else {
                    current + intent.seatIndex
                }
                updateState { copy(selectedSeatIndices = updated) }
            }

            // ── Ambient Light ──────────────────────────────────────────────
            is Intent.ToggleAmbientLight -> {
                updateState {
                    copy(ambientLightState = ambientLightState.copy(masterEnabled = intent.enabled))
                }
            }
            is Intent.SetAmbientTheme -> {
                updateState {
                    copy(ambientLightState = ambientLightState.copy(theme = intent.theme))
                }
            }
            is Intent.SetAmbientBrightness -> {
                updateState {
                    copy(ambientLightState = ambientLightState.copy(brightness = intent.brightness))
                }
            }
            is Intent.ToggleFootwellLighting -> {
                updateState {
                    copy(
                        ambientLightState = ambientLightState.copy(
                            footwellLightingEnabled = intent.enabled,
                        ),
                    )
                }
            }
            is Intent.ToggleAmbientSetting -> {
                val updatedSettings = currentState.ambientLightState.settings.map { item ->
                    if (item.id == intent.settingId) {
                        item.copy(enabled = intent.enabled)
                    } else {
                        item
                    }
                }
                updateState {
                    copy(ambientLightState = ambientLightState.copy(settings = updatedSettings))
                }
            }

            // ── Panorama Roof ──────────────────────────────────────────────
            is Intent.ToggleRoofSegment -> {
                val segments = currentState.panoramaRoofState.segments.toMutableList()
                if (intent.segmentIndex in segments.indices) {
                    segments[intent.segmentIndex] = !segments[intent.segmentIndex]
                    updateState {
                        copy(
                            panoramaRoofState = panoramaRoofState.copy(
                                segments = segments,
                                selectedPresetIndex = -1,
                            ),
                        )
                    }
                }
            }
            is Intent.SelectRoofPreset -> {
                val presetSegments = generateRoofPreset(intent.presetIndex)
                updateState {
                    copy(
                        panoramaRoofState = PanoramaRoofState(
                            segments = presetSegments,
                            selectedPresetIndex = intent.presetIndex,
                        ),
                    )
                }
            }

            // ── Display ────────────────────────────────────────────────────
            is Intent.SelectDisplayTarget -> {
                updateState { copy(selectedDisplayTarget = intent.target) }
            }
            is Intent.SetDisplayBrightness -> {
                val updated = currentState.displayBrightness.toMutableMap()
                updated[currentState.selectedDisplayTarget] = intent.brightness
                updateState { copy(displayBrightness = updated) }
            }

            // ── Safety ─────────────────────────────────────────────────────
            is Intent.TogglePassengerAirbag -> {
                // Require confirmation for safety-critical toggle
                updateState {
                    copy(showAirbagConfirmation = true, pendingAirbagState = intent.enabled)
                }
            }
            is Intent.ConfirmAirbagChange -> {
                val newState = currentState.pendingAirbagState ?: return
                updateState {
                    copy(
                        safetyState = safetyState.copy(passengerAirbagEnabled = newState),
                        showAirbagConfirmation = false,
                        pendingAirbagState = null,
                    )
                }
                sendEffect(Effect.AirbagStateChanged)
            }
            is Intent.DismissAirbagConfirmation -> {
                updateState {
                    copy(showAirbagConfirmation = false, pendingAirbagState = null)
                }
            }
            is Intent.ToggleFondInfoTone -> {
                updateState {
                    copy(safetyState = safetyState.copy(fondInfoToneEnabled = intent.enabled))
                }
            }
            is Intent.ToggleChildPresenceDetection -> {
                updateState {
                    copy(
                        safetyState = safetyState.copy(
                            childPresenceDetectionEnabled = intent.enabled,
                        ),
                    )
                }
            }
            is Intent.ToggleGloveboxPin -> {
                if (intent.enabled) {
                    // Open PIN modal when enabling
                    updateState {
                        copy(showPinModal = true, pinEntryDigits = emptyList())
                    }
                } else {
                    updateState {
                        copy(safetyState = safetyState.copy(gloveboxPinEnabled = false))
                    }
                }
            }
            is Intent.OpenPinModal -> {
                updateState { copy(showPinModal = true, pinEntryDigits = emptyList()) }
            }
            is Intent.DismissPinModal -> {
                updateState { copy(showPinModal = false, pinEntryDigits = emptyList()) }
            }
            is Intent.PinDigitEntered -> {
                val digits = currentState.pinEntryDigits
                if (digits.size < PIN_LENGTH) {
                    val updated = digits + intent.digit
                    updateState { copy(pinEntryDigits = updated) }
                    if (updated.size == PIN_LENGTH) {
                        // PIN complete — save and close
                        updateState {
                            copy(
                                safetyState = safetyState.copy(gloveboxPinEnabled = true),
                                showPinModal = false,
                                pinEntryDigits = emptyList(),
                            )
                        }
                        sendEffect(Effect.PinSetSuccessfully)
                    }
                }
            }
            is Intent.PinBackspace -> {
                val digits = currentState.pinEntryDigits
                if (digits.isNotEmpty()) {
                    updateState { copy(pinEntryDigits = digits.dropLast(1)) }
                }
            }
        }
    }

    private fun loadData() {
        updateState { copy(isLoading = false, error = null) }
    }

    private fun handleSetMassageMode(mode: ComfortMassageMode) {
        when (currentState.selectedMassageSeat) {
            SeatPosition.DRIVER -> updateState { copy(driverMassageMode = mode) }
            SeatPosition.PASSENGER -> updateState { copy(passengerMassageMode = mode) }
        }
    }

    /**
     * Generates a roof segment pattern for the given preset index.
     * Provides 8 different preset configurations.
     */
    private fun generateRoofPreset(presetIndex: Int): List<Boolean> {
        val count = PanoramaRoofState.ROOF_SEGMENT_COUNT
        return when (presetIndex) {
            0 -> List(count) { false }                       // All closed
            1 -> List(count) { true }                        // All open
            2 -> List(count) { it < count / 2 }              // Left half open
            3 -> List(count) { it >= count / 2 }             // Right half open
            4 -> List(count) { it % 2 == 0 }                 // Alternating even
            5 -> List(count) { it % 2 != 0 }                 // Alternating odd
            6 -> List(count) { it in 2..7 }                  // Center open
            7 -> List(count) { it < 2 || it > 7 }            // Edges open
            else -> List(count) { false }
        }
    }

    companion object {
        private const val PIN_LENGTH = 4
    }
}
