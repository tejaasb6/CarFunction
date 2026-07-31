/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.di

import com.example.carfunction.core.oem.OemConfig
import com.example.carfunction.core.oem.OemType
import com.example.carfunction.core.platform.PlatformCapabilities
import com.example.carfunction.core.platform.SdvCapabilities
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module providing platform-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object PlatformModule {

    @Provides
    @Singleton
    fun providePlatformCapabilities(): PlatformCapabilities = SdvCapabilities()

    @Provides
    @Singleton
    fun provideOemConfig(capabilities: PlatformCapabilities): OemConfig =
        OemConfig(oem = OemType.AUDI, capabilities = capabilities)
}
