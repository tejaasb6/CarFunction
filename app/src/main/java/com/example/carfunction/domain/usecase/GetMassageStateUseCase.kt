/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetMassageStateUseCase @Inject constructor(
    private val repository: CarFunctionRepository,
) {
    operator fun invoke(): Flow<MassageState> =
        repository.getMassageState()
}
