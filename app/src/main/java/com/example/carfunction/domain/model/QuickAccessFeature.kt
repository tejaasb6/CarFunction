package com.example.carfunction.domain.model

import androidx.annotation.DrawableRes

/**
 * Represents a quick-access tile shown in the left pane.
 * Each tile has an icon, label, and optional gear (settings) capability.
 * Uses drawable resource IDs instead of Material Icons for OEM-agnostic design.
 */
data class QuickAccessFeature(
    val id: String,
    val label: String,
    @param:DrawableRes val iconRes: Int,
    val hasSettings: Boolean = false,
    val isEnabled: Boolean = true,
)
