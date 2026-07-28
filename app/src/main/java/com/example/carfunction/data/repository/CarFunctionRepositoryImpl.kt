package com.example.carfunction.data.repository

import com.example.carfunction.data.datasource.CarFunctionDataSource
import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.model.VehicleHotspot
import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Single repository implementation that delegates to
 * the injected [CarFunctionDataSource].
 * The data source is swapped via build variant (mock vs prod).
 */
class CarFunctionRepositoryImpl(
    private val dataSource: CarFunctionDataSource,
) : CarFunctionRepository {

    override fun getQuickAccessFeatures(): Flow<List<QuickAccessFeature>> =
        dataSource.getQuickAccessFeatures()

    override fun getDriveModes(): Flow<List<DriveMode>> =
        dataSource.getDriveModes()

    override fun getMassageState(): Flow<MassageState> =
        dataSource.getMassageState()

    override fun getAmbientLightPresets(): Flow<List<AmbientLightPreset>> =
        dataSource.getAmbientLightPresets()

    override fun getVehicleHotspots(): Flow<List<VehicleHotspot>> =
        dataSource.getVehicleHotspots()

    override suspend fun setDriveMode(mode: DriveMode) =
        dataSource.setDriveMode(mode)

    override suspend fun setMassageDriverMode(mode: MassageMode) =
        dataSource.setMassageDriverMode(mode)

    override suspend fun setMassagePassengerMode(mode: MassageMode) =
        dataSource.setMassagePassengerMode(mode)

    override suspend fun selectAmbientLightPreset(presetId: String) =
        dataSource.selectAmbientLightPreset(presetId)

    override suspend fun setDynamicContentEnabled(enabled: Boolean) =
        dataSource.setDynamicContentEnabled(enabled)

    override fun isDynamicContentEnabled(): Flow<Boolean> =
        dataSource.isDynamicContentEnabled()
}
