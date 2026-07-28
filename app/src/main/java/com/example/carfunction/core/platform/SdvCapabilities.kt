package com.example.carfunction.core.platform

/** SDV platform – full feature set. */
class SdvCapabilities : PlatformCapabilities {
    override val platformType = PlatformType.SDV
    override val supports3DModel = true
    override val supportsExteriorInterior = true
    override val supportsMassage = true
    override val supportsAmbientLight = true
    override val supportsDriveSelect = true
    override val maxQuickAccessSlots = 4
}
