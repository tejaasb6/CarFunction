package com.example.carfunction.domain.model

import androidx.annotation.DrawableRes

/**
 * An interactive hotspot on the 3D vehicle visualization.
 * Uses drawable resource IDs instead of Material Icons for OEM-agnostic design.
 */
data class VehicleHotspot(
    val id: String,
    val label: String,
    @param:DrawableRes val iconRes: Int,
    val xFraction: Float,
    val yFraction: Float,
)
