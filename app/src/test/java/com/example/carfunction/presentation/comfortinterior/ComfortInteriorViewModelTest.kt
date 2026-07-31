/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior

import androidx.lifecycle.SavedStateHandle
import com.example.carfunction.MainDispatcherRule
import com.example.carfunction.core.platform.PlatformCapabilities
import com.example.carfunction.core.platform.PlatformType
import com.example.carfunction.core.platform.SdvCapabilities
import com.example.carfunction.domain.model.ComfortMassageMode
import com.example.carfunction.domain.model.ComfortSubSection
import com.example.carfunction.domain.model.DisplayTarget
import com.example.carfunction.domain.model.SeatPosition
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract.Intent
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ComfortInteriorViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(
        capabilities: PlatformCapabilities = SdvCapabilities(),
        savedStateHandle: SavedStateHandle = SavedStateHandle(),
    ): ComfortInteriorViewModel =
        ComfortInteriorViewModel(capabilities, savedStateHandle)

    // ── LoadData ────────────────────────────────────────────────────────────

    @Test
    fun `LoadData sets isLoading to false`() = runTest {
        val viewModel = createViewModel()

        viewModel.dispatch(Intent.LoadData)

        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `LoadData populates visible subsections for SDV platform`() = runTest {
        val viewModel = createViewModel(capabilities = SdvCapabilities())

        viewModel.dispatch(Intent.LoadData)

        val state = viewModel.state.value
        // SDV supports all features, so all subsections should be visible
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.SEAT_MASSAGE))
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.AMBIENT_LIGHT))
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.PANORAMA_ROOF))
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.FAVORITES))
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.DISPLAY))
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.SAFETY))
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.SEAT_AND_LOADING))
    }

    @Test
    fun `LoadData filters subsections when platform lacks massage support`() = runTest {
        val limitedCaps = object : PlatformCapabilities {
            override val platformType = PlatformType.SDV
            override val supports3DModel = false
            override val supportsExteriorInterior = false
            override val supportsMassage = false
            override val supportsAmbientLight = false
            override val supportsDriveSelect = false
            override val maxQuickAccessSlots = 2
            override val supportsPanoramaRoof = false
            override val supportsGloveboxPin = false
            override val supportsChildPresenceDetection = false
            override val supportsHeadUpDisplay = false
            override val supportsFavorites = false
        }
        val viewModel = createViewModel(capabilities = limitedCaps)

        viewModel.dispatch(Intent.LoadData)

        val state = viewModel.state.value
        assertFalse(state.visibleSubSections.contains(ComfortSubSection.SEAT_MASSAGE))
        assertFalse(state.visibleSubSections.contains(ComfortSubSection.AMBIENT_LIGHT))
        assertFalse(state.visibleSubSections.contains(ComfortSubSection.PANORAMA_ROOF))
        assertFalse(state.visibleSubSections.contains(ComfortSubSection.FAVORITES))
        // These are always available regardless of capabilities
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.SEAT_AND_LOADING))
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.DISPLAY))
        assertTrue(state.visibleSubSections.contains(ComfortSubSection.SAFETY))
    }

    @Test
    fun `LoadData sets selectedSubSection to first visible section`() = runTest {
        val viewModel = createViewModel(capabilities = SdvCapabilities())

        viewModel.dispatch(Intent.LoadData)

        assertEquals(ComfortSubSection.SEAT_MASSAGE, viewModel.state.value.selectedSubSection)
    }

    @Test
    fun `LoadData clears error`() = runTest {
        val viewModel = createViewModel()

        viewModel.dispatch(Intent.LoadData)

        assertEquals(null, viewModel.state.value.error)
    }

    @Test
    fun `LoadData filters display targets based on HeadUp support`() = runTest {
        val noHeadUpCaps = object : PlatformCapabilities {
            override val platformType = PlatformType.SDV
            override val supports3DModel = true
            override val supportsExteriorInterior = true
            override val supportsMassage = true
            override val supportsAmbientLight = true
            override val supportsDriveSelect = true
            override val maxQuickAccessSlots = 4
            override val supportsPanoramaRoof = true
            override val supportsGloveboxPin = true
            override val supportsChildPresenceDetection = true
            override val supportsHeadUpDisplay = false
            override val supportsFavorites = true
        }
        val viewModel = createViewModel(capabilities = noHeadUpCaps)

        viewModel.dispatch(Intent.LoadData)

        val state = viewModel.state.value
        assertFalse(state.visibleDisplayTargets.contains(DisplayTarget.HEAD_UP))
        assertTrue(state.visibleDisplayTargets.contains(DisplayTarget.VIRTUAL_COCKPIT))
        assertTrue(state.visibleDisplayTargets.contains(DisplayTarget.MMI))
    }

    // ── SelectSubSection ───────────────────────────────────────────────────

    @Test
    fun `SelectSubSection updates selectedSubSection`() = runTest {
        val viewModel = createViewModel()

        viewModel.dispatch(Intent.SelectSubSection(ComfortSubSection.AMBIENT_LIGHT))

        assertEquals(ComfortSubSection.AMBIENT_LIGHT, viewModel.state.value.selectedSubSection)
    }

    @Test
    fun `SelectSubSection to SAFETY updates selectedSubSection`() = runTest {
        val viewModel = createViewModel()

        viewModel.dispatch(Intent.SelectSubSection(ComfortSubSection.SAFETY))

        assertEquals(ComfortSubSection.SAFETY, viewModel.state.value.selectedSubSection)
    }

    // ── SetMassageMode ─────────────────────────────────────────────────────

    @Test
    fun `SetMassageMode updates passengerMassageMode when passenger seat selected`() = runTest {
        val viewModel = createViewModel()
        // Default selectedMassageSeat is PASSENGER
        viewModel.dispatch(Intent.SetMassageMode(ComfortMassageMode.RELAX))

        assertEquals(ComfortMassageMode.RELAX, viewModel.state.value.passengerMassageMode)
        assertEquals(ComfortMassageMode.OFF, viewModel.state.value.driverMassageMode)
    }

    @Test
    fun `SetMassageMode updates driverMassageMode when driver seat selected`() = runTest {
        val viewModel = createViewModel()
        viewModel.dispatch(Intent.SelectMassageSeat(SeatPosition.DRIVER))

        viewModel.dispatch(Intent.SetMassageMode(ComfortMassageMode.ACTIVE))

        assertEquals(ComfortMassageMode.ACTIVE, viewModel.state.value.driverMassageMode)
        assertEquals(ComfortMassageMode.OFF, viewModel.state.value.passengerMassageMode)
    }

    @Test
    fun `SetMassageMode to STRETCH updates mode for selected seat`() = runTest {
        val viewModel = createViewModel()
        viewModel.dispatch(Intent.SelectMassageSeat(SeatPosition.DRIVER))

        viewModel.dispatch(Intent.SetMassageMode(ComfortMassageMode.STRETCH))

        assertEquals(ComfortMassageMode.STRETCH, viewModel.state.value.driverMassageMode)
    }

    // ── hashPin (companion object) ─────────────────────────────────────────

    @Test
    fun `hashPin produces consistent output for same input and salt`() {
        val digits = listOf(1, 2, 3, 4)
        val salt = ComfortInteriorViewModel.generateSalt()

        val hash1 = ComfortInteriorViewModel.hashPin(digits, salt)
        val hash2 = ComfortInteriorViewModel.hashPin(digits, salt)

        assertEquals(hash1, hash2)
    }

    @Test
    fun `hashPin produces different output for different inputs`() {
        val salt = ComfortInteriorViewModel.generateSalt()
        val hash1 = ComfortInteriorViewModel.hashPin(listOf(1, 2, 3, 4), salt)
        val hash2 = ComfortInteriorViewModel.hashPin(listOf(5, 6, 7, 8), salt)

        assertNotEquals(hash1, hash2)
    }

    @Test
    fun `hashPin returns 64 character hex string`() {
        val salt = ComfortInteriorViewModel.generateSalt()
        val hash = ComfortInteriorViewModel.hashPin(listOf(0, 0, 0, 0), salt)

        assertEquals(64, hash.length)
        assertTrue(hash.all { it in '0'..'9' || it in 'a'..'f' })
    }

    // ── Initial state ──────────────────────────────────────────────────────

    @Test
    fun `initial state has isLoading true`() {
        val viewModel = createViewModel()

        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `initial state has default massage mode OFF for both seats`() {
        val viewModel = createViewModel()
        val state = viewModel.state.value

        assertEquals(ComfortMassageMode.OFF, state.driverMassageMode)
        assertEquals(ComfortMassageMode.OFF, state.passengerMassageMode)
    }
}
