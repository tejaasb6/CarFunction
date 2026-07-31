/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior

import com.example.carfunction.core.mvi.MviEffect
import com.example.carfunction.core.mvi.MviIntent
import com.example.carfunction.core.mvi.MviState
import com.example.carfunction.domain.model.AmbientTheme
import com.example.carfunction.domain.model.ComfortAmbientLightState
import com.example.carfunction.domain.model.ComfortMassageMode
import com.example.carfunction.domain.model.ComfortSubSection
import com.example.carfunction.domain.model.DisplayTarget
import com.example.carfunction.domain.model.NavigationTab
import com.example.carfunction.domain.model.PanoramaRoofState
import com.example.carfunction.domain.model.SafetyPrivacyState
import com.example.carfunction.domain.model.SeatLoadingFunction
import com.example.carfunction.domain.model.SeatPosition
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

/**
 * MVI contract for the Comfort & Interior screen.
 * Groups all Intent, State, and Effect types.
 */
object ComfortInteriorContract {

    // ── Intents (user actions) ─────────────────────────────────────────────
    sealed interface Intent : MviIntent {
        /** Initial data load. */
        data object LoadData : Intent

        /** Top navigation tab changed. */
        data class SelectNavigationTab(val tab: NavigationTab) : Intent

        /** Left sidebar sub-section selection. */
        data class SelectSubSection(val section: ComfortSubSection) : Intent

        // ── Seat Massage ───────────────────────────────────────────────────
        data class SelectMassageSeat(val seat: SeatPosition) : Intent
        data class SetMassageMode(val mode: ComfortMassageMode) : Intent

        // ── Seat & Loading ─────────────────────────────────────────────────
        data class SelectSeatLoadingFunction(val function: SeatLoadingFunction) : Intent
        data class ToggleSeatSelection(val seatIndex: Int) : Intent

        // ── Ambient Light ──────────────────────────────────────────────────
        data class ToggleAmbientLight(val enabled: Boolean) : Intent
        data class SetAmbientTheme(val theme: AmbientTheme) : Intent
        data class SetAmbientBrightness(val brightness: Float) : Intent
        data class ToggleFootwellLighting(val enabled: Boolean) : Intent
        data class ToggleRooflineLighting(val enabled: Boolean) : Intent
        data class TogglePanoramicRoofLighting(val enabled: Boolean) : Intent
        data class ToggleInteractionLight(val enabled: Boolean) : Intent
        data class SetInteractionLightBrightness(val brightness: Float) : Intent
        data class ToggleAmbientSetting(val settingId: String, val enabled: Boolean) : Intent

        // ── Panorama Roof ──────────────────────────────────────────────────
        data class ToggleRoofSegment(val segmentIndex: Int) : Intent
        data class SelectRoofPreset(val presetIndex: Int) : Intent

        // ── Display ────────────────────────────────────────────────────────
        data class SelectDisplayTarget(val target: DisplayTarget) : Intent
        data class SetDisplayBrightness(val brightness: Float) : Intent

        // ── Safety ─────────────────────────────────────────────────────────
        data class TogglePassengerAirbag(val enabled: Boolean) : Intent
        data class ToggleFondInfoTone(val enabled: Boolean) : Intent
        data class ToggleChildPresenceDetection(val enabled: Boolean) : Intent
        data class ToggleGloveboxPin(val enabled: Boolean) : Intent
        data object OpenPinModal : Intent
        data object DismissPinModal : Intent
        data class PinDigitEntered(val digit: Int) : Intent
        data object PinBackspace : Intent
        data object ConfirmAirbagChange : Intent
        data object DismissAirbagConfirmation : Intent

        // ── Search ─────────────────────────────────────────────────────────
        data object SearchClicked : Intent
    }

    // ── State ──────────────────────────────────────────────────────────────
    data class State(
        val isLoading: Boolean = true,
        val selectedTab: NavigationTab = NavigationTab.COMFORT_INTERIOR,
        val selectedSubSection: ComfortSubSection = ComfortSubSection.SEAT_MASSAGE,

        /** Sub-sections visible on the current platform (filtered by capabilities). */
        val visibleSubSections: List<ComfortSubSection> = ComfortSubSection.entries,

        // Seat Massage
        val selectedMassageSeat: SeatPosition = SeatPosition.PASSENGER,
        val driverMassageMode: ComfortMassageMode = ComfortMassageMode.OFF,
        val passengerMassageMode: ComfortMassageMode = ComfortMassageMode.OFF,

        // Seat & Loading
        val selectedSeatLoadingFunction: SeatLoadingFunction = SeatLoadingFunction.ENTRY_AID_3RD_ROW,
        val selectedSeatIndices: Set<Int> = emptySet(),

        // Ambient Light
        val ambientLightState: ComfortAmbientLightState = ComfortAmbientLightState(),

        // Panorama Roof
        val panoramaRoofState: PanoramaRoofState = PanoramaRoofState(),

        // Display
        val visibleDisplayTargets: ImmutableList<DisplayTarget> = persistentListOf(*DisplayTarget.entries.toTypedArray()),
        val selectedDisplayTarget: DisplayTarget = DisplayTarget.VIRTUAL_COCKPIT,
        val displayBrightness: ImmutableMap<DisplayTarget, Float> = persistentMapOf(
            DisplayTarget.HEAD_UP to 0.5f,
            DisplayTarget.VIRTUAL_COCKPIT to 0.5f,
            DisplayTarget.MMI to 0.5f,
        ),

        // Safety
        val safetyState: SafetyPrivacyState = SafetyPrivacyState(),
        val showPinModal: Boolean = false,
        /** Count of entered PIN digits (actual digits are kept in a private VM buffer). */
        val pinEntryDigitCount: Int = 0,
        val showAirbagConfirmation: Boolean = false,
        val pendingAirbagState: Boolean? = null,

        // Error
        val error: String? = null,
    ) : MviState {
        /** Current massage mode for the selected seat. */
        val currentMassageMode: ComfortMassageMode
            get() = when (selectedMassageSeat) {
                SeatPosition.DRIVER -> driverMassageMode
                SeatPosition.PASSENGER -> passengerMassageMode
            }

        /** Current display brightness for the selected target. */
        val currentDisplayBrightness: Float
            get() = displayBrightness[selectedDisplayTarget] ?: 0.5f
    }

    // ── Effects (one-shot side-effects) ────────────────────────────────────
    sealed interface Effect : MviEffect {
        data class ShowToast(val message: String) : Effect
        data object PinSetSuccessfully : Effect
        data object AirbagStateChanged : Effect
        data object OpenSearch : Effect
    }
}
