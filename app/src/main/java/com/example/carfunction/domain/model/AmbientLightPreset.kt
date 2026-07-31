/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.model

/**
 * Ambient lighting preset with a display color.
 * Color is stored as an ARGB hex long (e.g., 0xFF8EC8F2) so the domain
 * layer stays free of Android/Compose dependencies.
 */
data class AmbientLightPreset(
    val id: String,
    val label: String,
    val colorArgb: Long,
)
