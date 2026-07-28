package com.example.carfunction.core.platform

/** CL8min cockpit platform – constrained feature set. */
class Cl8minCapabilities : PlatformCapabilities {
    override val platformType = PlatformType.CL8MIN
    override val supports3DModel = false
    override val supportsExteriorInterior = true
    override val supportsMassage = true
    override val supportsAmbientLight = true
    override val supportsDriveSelect = true
    override val maxQuickAccessSlots = 3
}
