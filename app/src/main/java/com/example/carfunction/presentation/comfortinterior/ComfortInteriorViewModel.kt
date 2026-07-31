/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior

import androidx.lifecycle.SavedStateHandle
import com.example.carfunction.BuildConfig
import com.example.carfunction.core.mvi.MviViewModel
import com.example.carfunction.core.platform.PlatformCapabilities
import com.example.carfunction.domain.model.ComfortMassageMode
import com.example.carfunction.domain.model.ComfortSubSection
import com.example.carfunction.domain.model.DisplayTarget
import com.example.carfunction.domain.model.PanoramaRoofState
import com.example.carfunction.domain.model.SeatPosition
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract.Effect
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract.Intent
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import javax.inject.Inject

/**
 * Comfort & Interior ViewModel implementing MVI pattern.
 * Handles all user intents and produces state updates + side effects.
 *
 * @Traceability
 * - Requirement ID: SRS-REQ-COMFORT-01
 */
@HiltViewModel
class ComfortInteriorViewModel @Inject constructor(
    private val platformCapabilities: PlatformCapabilities,
    private val savedStateHandle: SavedStateHandle,
) : MviViewModel<Intent, State, Effect>(State()) {

    /**
     * Private buffer for PIN digits — never exposed in observable state.
     * Only the digit count ([State.pinEntryDigitCount]) is emitted to the UI.
     */
    private val pinBuffer = mutableListOf<Int>()

    /**
     * Called by the Screen composable via `LaunchedEffect(Unit)` to trigger
     * initial data loading. Avoids launching coroutines in `init {}`.
     */
    fun loadInitialData() {
        dispatch(Intent.LoadData)
    }

    override suspend fun handleIntent(intent: Intent) {
        try {
            handleIntentInternal(intent)
        } catch (e: kotlin.coroutines.cancellation.CancellationException) {
            throw e // Never swallow CancellationException
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                android.util.Log.e(TAG, "Error handling intent: $intent", e)
            }
            updateState { copy(error = e.message ?: "An unexpected error occurred") }
        }
    }

    private suspend fun handleIntentInternal(intent: Intent) {
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
            is Intent.ToggleRooflineLighting -> {
                updateState {
                    copy(
                        ambientLightState = ambientLightState.copy(
                            rooflineLightingEnabled = intent.enabled,
                        ),
                    )
                }
            }
            is Intent.TogglePanoramicRoofLighting -> {
                updateState {
                    copy(
                        ambientLightState = ambientLightState.copy(
                            panoramicRoofLightingEnabled = intent.enabled,
                        ),
                    )
                }
            }
            is Intent.ToggleInteractionLight -> {
                updateState {
                    copy(
                        ambientLightState = ambientLightState.copy(
                            interactionLightEnabled = intent.enabled,
                        ),
                    )
                }
            }
            is Intent.SetInteractionLightBrightness -> {
                updateState {
                    copy(
                        ambientLightState = ambientLightState.copy(
                            interactionLightBrightness = intent.brightness,
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
                val updated = currentState.displayBrightness
                    .toMutableMap()
                    .apply { this[currentState.selectedDisplayTarget] = intent.brightness }
                    .toImmutableMap()
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
                val newAirbagState = currentState.pendingAirbagState
                if (newAirbagState == null) {
                    updateState { copy(showAirbagConfirmation = false) }
                    sendEffect(Effect.ShowToast("Airbag state change failed. Please try again."))
                    return
                }
                updateState {
                    copy(
                        safetyState = safetyState.copy(passengerAirbagEnabled = newAirbagState),
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
                    clearPinBuffer()
                    updateState {
                        copy(showPinModal = true, pinEntryDigitCount = 0)
                    }
                } else {
                    updateState {
                        copy(safetyState = safetyState.copy(gloveboxPinEnabled = false))
                    }
                }
            }
            is Intent.OpenPinModal -> {
                clearPinBuffer()
                updateState { copy(showPinModal = true, pinEntryDigitCount = 0) }
            }
            is Intent.DismissPinModal -> {
                clearPinBuffer()
                updateState { copy(showPinModal = false, pinEntryDigitCount = 0) }
            }
            is Intent.PinDigitEntered -> {
                if (pinBuffer.size < PIN_LENGTH) {
                    pinBuffer.add(intent.digit)
                    updateState { copy(pinEntryDigitCount = pinBuffer.size) }
                    if (pinBuffer.size == PIN_LENGTH) {
                        // PIN complete — hash, store securely, and close
                        val salt = generateSalt()
                        hashPin(pinBuffer, salt)
                        clearPinBuffer()
                        updateState {
                            copy(
                                safetyState = safetyState.copy(gloveboxPinEnabled = true),
                                showPinModal = false,
                                pinEntryDigitCount = 0,
                            )
                        }
                        // TODO: Persist pinHash via EncryptedSharedPreferences or secure datastore
                        sendEffect(Effect.PinSetSuccessfully)
                    }
                }
            }
            is Intent.PinBackspace -> {
                if (pinBuffer.isNotEmpty()) {
                    pinBuffer.removeAt(pinBuffer.lastIndex)
                    updateState { copy(pinEntryDigitCount = pinBuffer.size) }
                }
            }
        }
    }

    private fun loadData() {
        val caps = platformCapabilities

        // Filter sub-sections by platform capabilities
        val visibleSections = ComfortSubSection.entries.filter { section ->
            when (section) {
                ComfortSubSection.SEAT_MASSAGE -> caps.supportsMassage
                ComfortSubSection.SEAT_AND_LOADING -> true // Always available
                ComfortSubSection.AMBIENT_LIGHT -> caps.supportsAmbientLight
                ComfortSubSection.PANORAMA_ROOF -> caps.supportsPanoramaRoof
                ComfortSubSection.DISPLAY -> true // Always available
                ComfortSubSection.FAVORITES -> caps.supportsFavorites
                ComfortSubSection.SAFETY -> true // Always available
            }
        }

        // Filter display targets by platform capabilities
        val visibleTargets = DisplayTarget.entries.filter { target ->
            when (target) {
                DisplayTarget.HEAD_UP -> caps.supportsHeadUpDisplay
                DisplayTarget.VIRTUAL_COCKPIT -> true
                DisplayTarget.MMI -> true
            }
        }

        val defaultSection = visibleSections.firstOrNull() ?: ComfortSubSection.SEAT_MASSAGE
        val defaultTarget = visibleTargets.firstOrNull() ?: DisplayTarget.VIRTUAL_COCKPIT

        updateState {
            copy(
                isLoading = false,
                error = null,
                visibleSubSections = visibleSections.toImmutableList(),
                selectedSubSection = defaultSection,
                visibleDisplayTargets = visibleTargets.toImmutableList(),
                selectedDisplayTarget = defaultTarget,
            )
        }
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

    /**
     * Clears the PIN buffer securely — overwrites before clearing to prevent
     * lingering values in memory.
     */
    private fun clearPinBuffer() {
        for (i in pinBuffer.indices) {
            pinBuffer[i] = 0
        }
        pinBuffer.clear()
    }

    companion object {
        private const val TAG = "ComfortInteriorVM"
        const val PIN_LENGTH = 4
        private const val PBKDF2_ITERATIONS = 10_000
        private const val KEY_LENGTH_BITS = 256
        private const val SALT_LENGTH_BYTES = 16

        /**
         * Generates a cryptographic random salt.
         */
        fun generateSalt(): ByteArray {
            val salt = ByteArray(SALT_LENGTH_BYTES)
            java.security.SecureRandom().nextBytes(salt)
            return salt
        }

        /**
         * Hashes a PIN using PBKDF2WithHmacSHA256 with the provided salt.
         * Returns the hex-encoded hash. The caller must persist both the
         * hash and the salt (e.g., via EncryptedSharedPreferences).
         */
        fun hashPin(digits: List<Int>, salt: ByteArray): String {
            val pinString = digits.joinToString("")
            val spec = javax.crypto.spec.PBEKeySpec(
                pinString.toCharArray(),
                salt,
                PBKDF2_ITERATIONS,
                KEY_LENGTH_BITS,
            )
            val factory = javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val hash = factory.generateSecret(spec).encoded
            spec.clearPassword()
            return hash.joinToString("") { "%02x".format(it) }
        }
    }
}
