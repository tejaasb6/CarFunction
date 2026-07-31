/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.di

import com.example.carfunction.data.datasource.CarFunctionDataSource
import com.example.carfunction.data.repository.CarFunctionRepositoryImpl
import com.example.carfunction.domain.repository.CarFunctionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module providing data-layer bindings.
 *
 * The [CarFunctionDataSource] is provided by the build-variant-specific
 * [DataSourceModule] (src/mock/ or src/prod/).
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    @Singleton
    fun provideCarFunctionRepository(
        dataSource: CarFunctionDataSource,
    ): CarFunctionRepository = CarFunctionRepositoryImpl(dataSource)
}
