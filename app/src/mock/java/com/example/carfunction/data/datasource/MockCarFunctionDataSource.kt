/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.data.datasource

import com.example.carfunction.R
import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.model.VehicleHotspot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Mock data source providing hardcoded data for development/testing.
 * Used in mockDebug and mockRelease build variants.
 * Uses drawable resource IDs — no Material Icons dependency.
 */
class MockCarFunctionDataSource : CarFunctionDataSource {

    private val quickAccessFeatures = MutableStateFlow(
        listOf(
            QuickAccessFeature(
                id = "traffic_sign",
                label = "Traffic Sign Warning",
                iconResId = R.drawable.ic_speed,
                hasSettings = true,
            ),
            QuickAccessFeature(
                id = "lane_departure",
                label = "Lane Departure Warning",
                iconResId = R.drawable.ic_car,
            ),
            QuickAccessFeature(
                id = "distraction",
                label = "Distraction Warning",
                iconResId = R.drawable.ic_eye,
            ),
        )
    )

    private val driveModes = MutableStateFlow(DriveMode.entries.toList())

    private val massageState = MutableStateFlow(MassageState())

    private val ambientPresets = MutableStateFlow(
        listOf(
            AmbientLightPreset("cold", "Cold", 0xFF8EC8F2),
            AmbientLightPreset("warm", "Warm", 0xFFE8C99B),
            AmbientLightPreset("day", "Day", 0xFFFFF176),
            AmbientLightPreset("night", "Night", 0xFF1A237E),
        )
    )

    private val selectedPresetId = MutableStateFlow("cold")

    private val hotspots = MutableStateFlow(
        listOf(
            VehicleHotspot("roof", "Roof Control", R.drawable.ic_roofing, 0.35f, 0.15f),
            VehicleHotspot("spoiler_front", "Front Spoiler", R.drawable.ic_air, 0.10f, 0.45f),
            VehicleHotspot("lights", "Lighting", R.drawable.ic_lightbulb, 0.50f, 0.50f),
            VehicleHotspot("charging", "Charging Port", R.drawable.ic_battery_charging, 0.75f, 0.30f),
            VehicleHotspot("spoiler_rear", "Rear Spoiler", R.drawable.ic_settings, 0.90f, 0.40f),
            VehicleHotspot("tire", "Tire Pressure", R.drawable.ic_tire, 0.20f, 0.80f),

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
