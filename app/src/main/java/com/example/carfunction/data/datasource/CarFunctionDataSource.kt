/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.data.datasource

import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.model.VehicleHotspot
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction for the data source layer.
 * Mock and Prod each provide their own implementation.
 */
interface CarFunctionDataSource {
    fun getQuickAccessFeatures(): Flow<List<QuickAccessFeature>>
    fun getDriveModes(): Flow<List<DriveMode>>
    fun getMassageState(): Flow<MassageState>
    fun getAmbientLightPresets(): Flow<List<AmbientLightPreset>>
    fun getVehicleHotspots(): Flow<List<VehicleHotspot>>
    suspend fun setDriveMode(mode: DriveMode)
    suspend fun setMassageDriverMode(mode: MassageMode)
    suspend fun setMassagePassengerMode(mode: MassageMode)
    suspend fun selectAmbientLightPreset(presetId: String)
    suspend fun setDynamicContentEnabled(enabled: Boolean)
    fun isDynamicContentEnabled(): Flow<Boolean>
}
