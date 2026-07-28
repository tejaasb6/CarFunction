package com.example.carfunction.data.datasource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TireRepair
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.material.icons.filled.Air
import androidx.compose.ui.graphics.Color
import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.model.VehicleHotspot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Mock data source providing hardcoded data for development/testing.
 * Used in mockDebug and mockRelease build variants.
 */
class MockCarFunctionDataSource : CarFunctionDataSource {

    private val quickAccessFeatures = MutableStateFlow(
        listOf(
            QuickAccessFeature(
                id = "traffic_sign",
                label = "Traffic Sign Warning",
                icon = Icons.Filled.Speed,
                hasSettings = true,
            ),
            QuickAccessFeature(
                id = "lane_departure",
                label = "Lane Departure Warning",
                icon = Icons.Filled.DirectionsCar,
            ),
            QuickAccessFeature(
                id = "distraction",
                label = "Distraction Warning",
                icon = Icons.Filled.RemoveRedEye,
            ),
        )
    )

    private val driveModes = MutableStateFlow(DriveMode.entries.toList())

    private val massageState = MutableStateFlow(MassageState())

    private val ambientPresets = MutableStateFlow(
        listOf(
            AmbientLightPreset("cold", "Cold", Color(0xFF8EC8F2)),
            AmbientLightPreset("warm", "Warm", Color(0xFFE8C99B)),
            AmbientLightPreset("day", "Day", Color(0xFFFFF176)),
            AmbientLightPreset("night", "Night", Color(0xFF1A237E)),
        )
    )

    private val selectedPresetId = MutableStateFlow("cold")

    private val hotspots = MutableStateFlow(
        listOf(
            VehicleHotspot("roof", "Roof Control", Icons.Filled.Roofing, 0.35f, 0.15f),
            VehicleHotspot("spoiler_front", "Front Spoiler", Icons.Filled.Air, 0.10f, 0.45f),
            VehicleHotspot("lights", "Lighting", Icons.Filled.Lightbulb, 0.50f, 0.50f),
            VehicleHotspot("charging", "Charging Port", Icons.Filled.BatteryChargingFull, 0.75f, 0.30f),
            VehicleHotspot("spoiler_rear", "Rear Spoiler", Icons.Filled.Settings, 0.90f, 0.40f),
            VehicleHotspot("tire", "Tire Pressure", Icons.Filled.TireRepair, 0.20f, 0.80f),
        )
    )

    private val dynamicContentEnabled = MutableStateFlow(true)

    override fun getQuickAccessFeatures(): Flow<List<QuickAccessFeature>> = quickAccessFeatures

    override fun getDriveModes(): Flow<List<DriveMode>> = driveModes

    override fun getMassageState(): Flow<MassageState> = massageState

    override fun getAmbientLightPresets(): Flow<List<AmbientLightPreset>> = ambientPresets

    override fun getVehicleHotspots(): Flow<List<VehicleHotspot>> = hotspots

    override suspend fun setDriveMode(mode: DriveMode) {
        // Mock: no-op, drive mode is selected on UI side
    }

    override suspend fun setMassageDriverMode(mode: MassageMode) {
        massageState.value = massageState.value.copy(driverMode = mode)
    }

    override suspend fun setMassagePassengerMode(mode: MassageMode) {
        massageState.value = massageState.value.copy(passengerMode = mode)
    }

    override suspend fun selectAmbientLightPreset(presetId: String) {
        selectedPresetId.value = presetId
    }

    override suspend fun setDynamicContentEnabled(enabled: Boolean) {
        dynamicContentEnabled.value = enabled
    }

    override fun isDynamicContentEnabled(): Flow<Boolean> = dynamicContentEnabled
}
