package com.example.carfunction.domain.model

import androidx.compose.ui.graphics.Color

/**
 * Ambient lighting preset with a display color.
 */
data class AmbientLightPreset(
    val id: String,
    val label: String,
    val color: Color,
)
