/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.repository.CarFunctionRepository

import javax.inject.Inject

class SetMassageModeUseCase @Inject constructor(
    private val repository: CarFunctionRepository,
) {
    suspend fun setDriverMode(mode: MassageMode) =
        repository.setMassageDriverMode(mode)

    suspend fun setPassengerMode(mode: MassageMode) =
        repository.setMassagePassengerMode(mode)
}
