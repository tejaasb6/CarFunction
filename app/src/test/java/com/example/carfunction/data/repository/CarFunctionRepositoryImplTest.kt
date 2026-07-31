/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.data.repository

import com.example.carfunction.data.datasource.CarFunctionDataSource
import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.model.VehicleHotspot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CarFunctionRepositoryImplTest {

    private lateinit var fakeDataSource: FakeCarFunctionDataSource
    private lateinit var repository: CarFunctionRepositoryImpl

    @Before
    fun setup() {
        fakeDataSource = FakeCarFunctionDataSource()
        repository = CarFunctionRepositoryImpl(fakeDataSource)
    }

    @Test
    fun `getDriveModes delegates to dataSource`() = runTest {
        val modes = listOf(DriveMode.COMFORT, DriveMode.SPORT)
        fakeDataSource.driveModesResult = modes

        val result = repository.getDriveModes().first()

        assertEquals(modes, result)
    }

    @Test
    fun `setDriveMode delegates to dataSource`() = runTest {
        repository.setDriveMode(DriveMode.DYNAMIC)

        assertEquals(DriveMode.DYNAMIC, fakeDataSource.lastSetDriveMode)
    }

    @Test
    fun `getQuickAccessFeatures delegates to dataSource`() = runTest {
        val features = listOf(
            QuickAccessFeature(id = "1", label = "Climate", iconResId = 0),
            QuickAccessFeature(id = "2", label = "Lights", iconResId = 0),
        )
        fakeDataSource.quickAccessFeaturesResult = features

        val result = repository.getQuickAccessFeatures().first()

        assertEquals(features, result)
    }

    @Test
    fun `getMassageState delegates to dataSource`() = runTest {
        val state = MassageState(driverMode = MassageMode.ACTIVE, passengerMode = MassageMode.OFF)
        fakeDataSource.massageStateResult = state

        val result = repository.getMassageState().first()

        assertEquals(state, result)
    }

    @Test
    fun `setMassageDriverMode delegates to dataSource`() = runTest {
        repository.setMassageDriverMode(MassageMode.ACTIVE)

        assertEquals(MassageMode.ACTIVE, fakeDataSource.lastSetMassageDriverMode)
    }

    @Test
    fun `setMassagePassengerMode delegates to dataSource`() = runTest {
        repository.setMassagePassengerMode(MassageMode.MOBILITY)

        assertEquals(MassageMode.MOBILITY, fakeDataSource.lastSetMassagePassengerMode)
    }

    @Test
    fun `isDynamicContentEnabled delegates to dataSource`() = runTest {
        fakeDataSource.dynamicContentEnabledResult = true

        val result = repository.isDynamicContentEnabled().first()

        assertEquals(true, result)
    }

    @Test
    fun `setDynamicContentEnabled delegates to dataSource`() = runTest {
        repository.setDynamicContentEnabled(true)

        assertEquals(true, fakeDataSource.lastSetDynamicContentEnabled)
    }
}

/**
 * Fake [CarFunctionDataSource] for testing the repository delegation.
 */
private class FakeCarFunctionDataSource : CarFunctionDataSource {

    var driveModesResult: List<DriveMode> = emptyList()
    var quickAccessFeaturesResult: List<QuickAccessFeature> = emptyList()
    var massageStateResult: MassageState = MassageState()
    var ambientLightPresetsResult: List<AmbientLightPreset> = emptyList()
    var vehicleHotspotsResult: List<VehicleHotspot> = emptyList()
    var dynamicContentEnabledResult: Boolean = false

    var lastSetDriveMode: DriveMode? = null
    var lastSetMassageDriverMode: MassageMode? = null
    var lastSetMassagePassengerMode: MassageMode? = null
    var lastSelectedAmbientPresetId: String? = null
    var lastSetDynamicContentEnabled: Boolean? = null

    override fun getDriveModes(): Flow<List<DriveMode>> = flowOf(driveModesResult)
    override fun getQuickAccessFeatures(): Flow<List<QuickAccessFeature>> = flowOf(quickAccessFeaturesResult)
    override fun getMassageState(): Flow<MassageState> = flowOf(massageStateResult)
    override fun getAmbientLightPresets(): Flow<List<AmbientLightPreset>> = flowOf(ambientLightPresetsResult)
    override fun getVehicleHotspots(): Flow<List<VehicleHotspot>> = flowOf(vehicleHotspotsResult)

    override suspend fun setDriveMode(mode: DriveMode) {
        lastSetDriveMode = mode
    }

    override suspend fun setMassageDriverMode(mode: MassageMode) {
        lastSetMassageDriverMode = mode
    }

    override suspend fun setMassagePassengerMode(mode: MassageMode) {
        lastSetMassagePassengerMode = mode
    }

    override suspend fun selectAmbientLightPreset(presetId: String) {
        lastSelectedAmbientPresetId = presetId
    }

    override suspend fun setDynamicContentEnabled(enabled: Boolean) {
        lastSetDynamicContentEnabled = enabled
    }

    override fun isDynamicContentEnabled(): Flow<Boolean> = flowOf(dynamicContentEnabledResult)
}
