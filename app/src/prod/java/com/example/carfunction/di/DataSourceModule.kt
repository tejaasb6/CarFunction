/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.di

import com.example.carfunction.data.datasource.CarFunctionDataSource
import com.example.carfunction.data.datasource.ProdCarFunctionDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Production variant: provides [ProdCarFunctionDataSource] as the
 * [CarFunctionDataSource] implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideDataSource(): CarFunctionDataSource = ProdCarFunctionDataSource()
}
