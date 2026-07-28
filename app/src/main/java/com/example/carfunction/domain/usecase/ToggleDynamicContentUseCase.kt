package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

class ToggleDynamicContentUseCase(
    private val repository: CarFunctionRepository,
) {
    suspend fun setEnabled(enabled: Boolean) =
        repository.setDynamicContentEnabled(enabled)

    fun isEnabled(): Flow<Boolean> =
        repository.isDynamicContentEnabled()
}
