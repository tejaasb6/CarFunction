package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.DriveMode
import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

class GetDriveModesUseCase(
    private val repository: CarFunctionRepository,
) {
    operator fun invoke(): Flow<List<DriveMode>> =
        repository.getDriveModes()
}
