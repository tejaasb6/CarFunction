/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.repository.CarFunctionRepository

import javax.inject.Inject

class SetDriveModeUseCase @Inject constructor(
    private val repository: CarFunctionRepository,
) {
    suspend operator fun invoke(mode: DriveMode) =
        repository.setDriveMode(mode)
}
