package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.AmbientLightPreset
import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

class GetAmbientLightPresetsUseCase(
    private val repository: CarFunctionRepository,
) {
    operator fun invoke(): Flow<List<AmbientLightPreset>> =
        repository.getAmbientLightPresets()
}
