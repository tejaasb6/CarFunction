/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.model

/**
 * An interactive hotspot on the 3D vehicle visualization.
 * Uses a resource key string that the presentation layer maps to a
 * drawable resource ID, keeping the domain free of Android imports.
 */
data class VehicleHotspot(
    val id: String,
    val label: String,
    val iconResId: Int,
    val xFraction: Float,
    val yFraction: Float,
)
