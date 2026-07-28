package com.example.carfunction.core.platform

/**
 * Declares what the current platform supports.
 * Each platform adapter implements this to toggle features.
 */
interface PlatformCapabilities {
    val platformType: PlatformType
    val supports3DModel: Boolean
    val supportsExteriorInterior: Boolean
    val supportsMassage: Boolean
    val supportsAmbientLight: Boolean
    val supportsDriveSelect: Boolean
    val maxQuickAccessSlots: Int
}
