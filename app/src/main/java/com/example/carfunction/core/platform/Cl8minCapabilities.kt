/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.core.platform

/**
 * CL8min cockpit platform – constrained feature set.
 *
 * Per REQ-CI-PLAT-001:
 * - No Panorama Roof
 * - No Head-Up Display
 * - Glovebox PIN, Child Presence Detection, Favorites, Massage, Ambient Light supported
 */
class Cl8minCapabilities : PlatformCapabilities {
    // MyCar
    override val platformType = PlatformType.CL8MIN
    override val supports3DModel = false
    override val supportsExteriorInterior = true
    override val supportsMassage = true
    override val supportsAmbientLight = true
    override val supportsDriveSelect = true
    override val maxQuickAccessSlots = 3

    // Comfort & Interior
    override val supportsPanoramaRoof = false
    override val supportsGloveboxPin = true
    override val supportsChildPresenceDetection = true
    override val supportsHeadUpDisplay = false
    override val supportsFavorites = true
}
