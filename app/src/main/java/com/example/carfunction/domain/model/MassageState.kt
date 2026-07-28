package com.example.carfunction.domain.model

/** Massage seat mode. */
enum class MassageMode {
    OFF,
    ACTIVE,
    MOBILITY,
}

/** Combined massage state for driver and passenger. */
data class MassageState(
    val driverMode: MassageMode = MassageMode.OFF,
    val passengerMode: MassageMode = MassageMode.OFF,
)
