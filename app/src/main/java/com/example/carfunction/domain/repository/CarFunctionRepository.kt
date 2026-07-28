package com.example.carfunction.domain.repository

import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.model.VehicleHotspot
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for car function data.
 * Implementation provided by mock or prod source sets.
 */
interface CarFunctionRepository {
    fun getQuickAccessFeatures(): Flow<List<QuickAccessFeature>>
    fun getDriveModes(): Flow<List<DriveMode>>
    fun getMassageState(): Flow<MassageState>
    fun getAmbientLightPresets(): Flow<List<AmbientLightPreset>>
    fun getVehicleHotspots(): Flow<List<VehicleHotspot>>
    suspend fun setDriveMode(mode: DriveMode)
    suspend fun setMassageDriverMode(mode: com.example.carfunction.domain.model.MassageMode)
    suspend fun setMassagePassengerMode(mode: com.example.carfunction.domain.model.MassageMode)
    suspend fun selectAmbientLightPreset(presetId: String)
    suspend fun setDynamicContentEnabled(enabled: Boolean)
    fun isDynamicContentEnabled(): Flow<Boolean>
}
