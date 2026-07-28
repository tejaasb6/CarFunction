package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.QuickAccessFeature
import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

class GetQuickAccessFeaturesUseCase(
    private val repository: CarFunctionRepository,
) {
    operator fun invoke(): Flow<List<QuickAccessFeature>> =
        repository.getQuickAccessFeatures()
}
