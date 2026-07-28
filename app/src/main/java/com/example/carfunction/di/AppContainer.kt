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
 */
object AppContainer {

    // Platform capabilities (default to SDV, can be overridden)
    var platformCapabilities: PlatformCapabilities = SdvCapabilities()
        private set

    val oemConfig: OemConfig
        get() = OemConfig(oem = OemType.AUDI, capabilities = platformCapabilities)

    private val dataSource by lazy { DataSourceProvider.provideDataSource() }

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

    fun configurePlatform(capabilities: PlatformCapabilities) {
        platformCapabilities = capabilities
    }
}
