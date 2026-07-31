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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetDriveModesUseCaseTest {

    private val fakeModes = listOf(DriveMode.COMFORT, DriveMode.SPORT)

    private val fakeRepository = object : CarFunctionRepository {
        override fun getDriveModes(): Flow<List<DriveMode>> = flowOf(fakeModes)
        override fun getQuickAccessFeatures(): Flow<List<QuickAccessFeature>> = flowOf(emptyList())
        override fun getMassageState(): Flow<MassageState> = flowOf(MassageState())
        override fun getAmbientLightPresets(): Flow<List<AmbientLightPreset>> = flowOf(emptyList())
        override fun getVehicleHotspots(): Flow<List<VehicleHotspot>> = flowOf(emptyList())
        override suspend fun setDriveMode(mode: DriveMode) = Unit
        override suspend fun setMassageDriverMode(mode: MassageMode) = Unit
        override suspend fun setMassagePassengerMode(mode: MassageMode) = Unit
        override suspend fun selectAmbientLightPreset(presetId: String) = Unit
        override suspend fun setDynamicContentEnabled(enabled: Boolean) = Unit
        override fun isDynamicContentEnabled(): Flow<Boolean> = flowOf(false)
    }

    private val useCase = GetDriveModesUseCase(fakeRepository)

    @Test
    fun `invoke returns drive modes flow from repository`() = runTest {
        val result = useCase().first()

        assertEquals(fakeModes, result)
    }

    @Test
    fun `invoke returns empty list when repository has no modes`() = runTest {
        val emptyRepo = object : CarFunctionRepository {
            override fun getDriveModes(): Flow<List<DriveMode>> = flowOf(emptyList())
            override fun getQuickAccessFeatures(): Flow<List<QuickAccessFeature>> = flowOf(emptyList())
            override fun getMassageState(): Flow<MassageState> = flowOf(MassageState())
            override fun getAmbientLightPresets(): Flow<List<AmbientLightPreset>> = flowOf(emptyList())
            override fun getVehicleHotspots(): Flow<List<VehicleHotspot>> = flowOf(emptyList())
            override suspend fun setDriveMode(mode: DriveMode) = Unit
            override suspend fun setMassageDriverMode(mode: MassageMode) = Unit
            override suspend fun setMassagePassengerMode(mode: MassageMode) = Unit
            override suspend fun selectAmbientLightPreset(presetId: String) = Unit
            override suspend fun setDynamicContentEnabled(enabled: Boolean) = Unit
            override fun isDynamicContentEnabled(): Flow<Boolean> = flowOf(false)
        }
        val emptyUseCase = GetDriveModesUseCase(emptyRepo)

        val result = emptyUseCase().first()

        assertEquals(emptyList<DriveMode>(), result)
    }
}
