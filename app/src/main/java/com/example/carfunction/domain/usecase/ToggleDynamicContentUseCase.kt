/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class ToggleDynamicContentUseCase @Inject constructor(
    private val repository: CarFunctionRepository,
) {
    suspend fun setEnabled(enabled: Boolean) =
        repository.setDynamicContentEnabled(enabled)

    fun isEnabled(): Flow<Boolean> =
        repository.isDynamicContentEnabled()
}
