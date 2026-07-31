/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.model

/**
 * Represents a quick-access tile shown in the left pane.
 * Each tile has an icon, label, and optional gear (settings) capability.
 * Icon resource ID is kept as a plain [Int]; the presentation layer
 * resolves it to the platform drawable, keeping this model free of
 * Android framework imports.
 */
data class QuickAccessFeature(
    val id: String,
    val label: String,
    val iconResId: Int,
    val hasSettings: Boolean = false,
    val isEnabled: Boolean = true,
)
