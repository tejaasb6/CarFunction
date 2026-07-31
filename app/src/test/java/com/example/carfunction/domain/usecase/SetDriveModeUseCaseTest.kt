/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.model.VehicleHotspot
import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SetDriveModeUseCaseTest {

    private var lastSetMode: DriveMode? = null

    private val fakeRepository = object : CarFunctionRepository {
        override fun getDriveModes(): Flow<List<DriveMode>> = flowOf(emptyList())
        override fun getQuickAccessFeatures(): Flow<List<QuickAccessFeature>> = flowOf(emptyList())
        override fun getMassageState(): Flow<MassageState> = flowOf(MassageState())
        override fun getAmbientLightPresets(): Flow<List<AmbientLightPreset>> = flowOf(emptyList())
        override fun getVehicleHotspots(): Flow<List<VehicleHotspot>> = flowOf(emptyList())
        override suspend fun setDriveMode(mode: DriveMode) {
            lastSetMode = mode
        }
        override suspend fun setMassageDriverMode(mode: MassageMode) = Unit
        override suspend fun setMassagePassengerMode(mode: MassageMode) = Unit
        override suspend fun selectAmbientLightPreset(presetId: String) = Unit
        override suspend fun setDynamicContentEnabled(enabled: Boolean) = Unit
        override fun isDynamicContentEnabled(): Flow<Boolean> = flowOf(false)
    }

    private val useCase = SetDriveModeUseCase(fakeRepository)

    @Test
    fun `invoke delegates SPORT mode to repository`() = runTest {
        useCase(DriveMode.SPORT)

        assertEquals(DriveMode.SPORT, lastSetMode)
    }

    @Test
    fun `invoke delegates COMFORT mode to repository`() = runTest {
        useCase(DriveMode.COMFORT)

        assertEquals(DriveMode.COMFORT, lastSetMode)
    }

    @Test
    fun `invoke delegates DYNAMIC mode to repository`() = runTest {
        useCase(DriveMode.DYNAMIC)

        assertEquals(DriveMode.DYNAMIC, lastSetMode)
    }
}
