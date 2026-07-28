package com.example.carfunction.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a quick-access tile shown in the left pane.
 * Each tile has an icon, label, and optional gear (settings) capability.
 */
data class QuickAccessFeature(
    val id: String,
    val label: String,
    val icon: ImageVector,
    val hasSettings: Boolean = false,
    val isEnabled: Boolean = true,
)
