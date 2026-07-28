package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.MassageState
import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

class GetMassageStateUseCase(
    private val repository: CarFunctionRepository,
) {
    operator fun invoke(): Flow<MassageState> =
        repository.getMassageState()
}
