/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.di

import com.example.carfunction.core.oem.OemConfig
import com.example.carfunction.core.oem.OemType
import com.example.carfunction.core.platform.PlatformCapabilities
import com.example.carfunction.core.platform.SdvCapabilities
import com.example.carfunction.data.repository.CarFunctionRepositoryImpl
import com.example.carfunction.domain.repository.CarFunctionRepository
import com.example.carfunction.domain.usecase.GetAmbientLightPresetsUseCase
import com.example.carfunction.domain.usecase.GetDriveModesUseCase
import com.example.carfunction.domain.usecase.GetMassageStateUseCase
import com.example.carfunction.domain.usecase.GetQuickAccessFeaturesUseCase
import com.example.carfunction.domain.usecase.GetVehicleHotspotsUseCase
import com.example.carfunction.domain.usecase.SetDriveModeUseCase
import com.example.carfunction.domain.usecase.SetMassageModeUseCase
import com.example.carfunction.domain.usecase.ToggleDynamicContentUseCase

/**
 * Manual dependency injection container.
 * Provides all use cases and configuration, wired to the
 * build-variant-specific DataSourceProvider.
 *
 * **Important:** Call [configurePlatform] before accessing any use case or
 * repository property. Once the lazy graph is initialised, changing the
 * platform capabilities has no effect on already-created instances.
 */
object AppContainer {

    // Platform capabilities (default to SDV, can be overridden)
    var platformCapabilities: PlatformCapabilities = SdvCapabilities()
        private set

    /** Whether the lazy dependency graph has been initialised. */
    @Volatile
    private var graphInitialised = false

    val oemConfig: OemConfig
        get() = OemConfig(oem = OemType.AUDI, capabilities = platformCapabilities)

    private val dataSource by lazy {
        graphInitialised = true
        DataSourceProvider.provideDataSource()
    }

    val repository: CarFunctionRepository by lazy {
        CarFunctionRepositoryImpl(dataSource)
    }

    // Use cases
    val getQuickAccessFeatures by lazy { GetQuickAccessFeaturesUseCase(repository) }
    val getDriveModes by lazy { GetDriveModesUseCase(repository) }
    val getMassageState by lazy { GetMassageStateUseCase(repository) }
    val getAmbientLightPresets by lazy { GetAmbientLightPresetsUseCase(repository) }
    val getVehicleHotspots by lazy { GetVehicleHotspotsUseCase(repository) }
    val setDriveMode by lazy { SetDriveModeUseCase(repository) }
    val setMassageMode by lazy { SetMassageModeUseCase(repository) }
    val toggleDynamicContent by lazy { ToggleDynamicContentUseCase(repository) }

    /**
     * Configures the platform capabilities. Must be called **before** any
     * repository or use case property is accessed; calling it afterwards
     * throws [IllegalStateException] to prevent silent misconfiguration.
     */
    fun configurePlatform(capabilities: PlatformCapabilities) {
        check(!graphInitialised) {
            "configurePlatform() called after the dependency graph was already initialised. " +
                "Call configurePlatform() in Application.onCreate() before accessing any use case."
        }
        platformCapabilities = capabilities
    }
}
