package com.example.carfunction.di

import com.example.carfunction.data.datasource.CarFunctionDataSource
import com.example.carfunction.data.datasource.ProdCarFunctionDataSource

/**
 * Production variant data source provider.
 * This file lives in src/prod/ and is compiled only for
 * prodDebug and prodRelease build variants.
 */
object DataSourceProvider {
    fun provideDataSource(): CarFunctionDataSource = ProdCarFunctionDataSource()
}
