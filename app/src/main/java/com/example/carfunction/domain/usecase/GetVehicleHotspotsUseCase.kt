package com.example.carfunction.domain.usecase

import com.example.carfunction.domain.model.VehicleHotspot
import com.example.carfunction.domain.repository.CarFunctionRepository
import kotlinx.coroutines.flow.Flow

class GetVehicleHotspotsUseCase(
    private val repository: CarFunctionRepository,
) {
    operator fun invoke(): Flow<List<VehicleHotspot>> =
        repository.getVehicleHotspots()
}
