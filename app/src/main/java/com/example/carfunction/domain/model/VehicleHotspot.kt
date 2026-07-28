package com.example.carfunction.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * An interactive hotspot on the 3D vehicle visualization.
 */
data class VehicleHotspot(
    val id: String,
    val label: String,
    val icon: ImageVector,
    val xFraction: Float,
    val yFraction: Float,
)
