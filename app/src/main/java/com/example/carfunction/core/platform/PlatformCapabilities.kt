/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.core.platform

/**
 * Declares what the current platform supports.
 * Each platform adapter implements this to toggle features.
 *
 * Covers both MyCar and Comfort & Interior feature flags so that the
 * presentation layer can hide unsupported sub-sections / controls
 * depending on the vehicle platform (SDV, CL8min, etc.).
 */
interface PlatformCapabilities {
    // ── MyCar features ─────────────────────────────────────────────────────
    val platformType: PlatformType
    val supports3DModel: Boolean
    val supportsExteriorInterior: Boolean
    val supportsMassage: Boolean
    val supportsAmbientLight: Boolean
    val supportsDriveSelect: Boolean
    val maxQuickAccessSlots: Int

    // ── Comfort & Interior features (REQ-CI-PLAT-001) ──────────────────────
    val supportsPanoramaRoof: Boolean
    val supportsGloveboxPin: Boolean
    val supportsChildPresenceDetection: Boolean
    val supportsHeadUpDisplay: Boolean
    val supportsFavorites: Boolean
}
