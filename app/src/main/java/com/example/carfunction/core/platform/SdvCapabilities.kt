/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.core.platform

/** SDV platform – full feature set. All C&I capabilities enabled. */
class SdvCapabilities : PlatformCapabilities {
    // MyCar
    override val platformType = PlatformType.SDV
    override val supports3DModel = true
    override val supportsExteriorInterior = true
    override val supportsMassage = true
    override val supportsAmbientLight = true
    override val supportsDriveSelect = true
    override val maxQuickAccessSlots = 4

    // Comfort & Interior
    override val supportsPanoramaRoof = true
    override val supportsGloveboxPin = true
    override val supportsChildPresenceDetection = true
    override val supportsHeadUpDisplay = true
    override val supportsFavorites = true
}
