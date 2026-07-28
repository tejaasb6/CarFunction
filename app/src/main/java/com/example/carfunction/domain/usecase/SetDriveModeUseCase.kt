package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.repository.CarFunctionRepository

class SetDriveModeUseCase(
    private val repository: CarFunctionRepository,
) {
    suspend operator fun invoke(mode: DriveMode) =
        repository.setDriveMode(mode)
}
