package com.example.carfunction.di

import com.example.carfunction.data.datasource.CarFunctionDataSource
import com.example.carfunction.data.datasource.MockCarFunctionDataSource

/**
 * Mock variant data source provider.
 * This file lives in src/mock/ and is compiled only for
 * mockDebug and mockRelease build variants.
 */
object DataSourceProvider {
    fun provideDataSource(): CarFunctionDataSource = MockCarFunctionDataSource()
}
