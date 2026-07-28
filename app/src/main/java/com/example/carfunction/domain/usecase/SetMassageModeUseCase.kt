package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.MassageMode
import com.example.carfunction.domain.repository.CarFunctionRepository

class SetMassageModeUseCase(
    private val repository: CarFunctionRepository,
) {
    suspend fun setDriverMode(mode: MassageMode) =
        repository.setMassageDriverMode(mode)

    suspend fun setPassengerMode(mode: MassageMode) =
        repository.setMassagePassengerMode(mode)
}
