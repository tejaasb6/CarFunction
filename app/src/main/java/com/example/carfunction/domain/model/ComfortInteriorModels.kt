/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.domain.model

/**
 * Sub-section navigation items for the Comfort & Interior screen.
 */
enum class ComfortSubSection(val label: String) {
    SEAT_MASSAGE("Seat Massage"),
    SEAT_AND_LOADING("Seat- & Loading"),
    AMBIENT_LIGHT("Ambient Light"),
    PANORAMA_ROOF("Panorama Roof"),
    DISPLAY("Display"),
    FAVORITES("Favorites"),
    SAFETY("Safety"),
}

/**
 * Seat position identifier for massage and seat functions.
 */
enum class SeatPosition(val label: String) {
    DRIVER("Driver"),
    PASSENGER("Passenger"),
}

/**
 * Extended massage modes for the Comfort & Interior seat massage section.
 * Extends the base [MassageMode] with additional programs.
 */
enum class ComfortMassageMode(val label: String) {
    OFF("OFF"),
    BALANCE("Balance"),
    ACTIVE("Active"),
    MOBILITY("Mobility"),
    RELAX("Relax"),
    STRETCH("Stretch"),
}

/**
 * Ambient lighting theme presets.
 */
enum class AmbientTheme(val label: String) {
    SKY("Sky"),
    HORIZON("Horizon"),
    HEARTH("Hearth"),
    SYNC("Sync"),
}

/**
 * Seat function options under "Seat- & Loading".
 */
enum class SeatLoadingFunction(val label: String, val section: SeatLoadingSection) {
    ENTRY_AID_3RD_ROW("Entry aid for 3rd seat row", SeatLoadingSection.SEAT_FUNCTIONS),
    PASSENGER_FROM_DRIVER("Passenger seat adjustment from the driver's seat", SeatLoadingSection.SEAT_FUNCTIONS),
    PASSENGER_FROM_REAR("Passenger seat adjustment from the rear seat", SeatLoadingSection.SEAT_FUNCTIONS),
    CARGO("Cargo", SeatLoadingSection.LOADING_FUNCTIONS),
}

/**
 * Section grouping for seat & loading functions.
 */
enum class SeatLoadingSection(val label: String) {
    SEAT_FUNCTIONS("Seat Functions"),
    LOADING_FUNCTIONS("Loading Functions"),
}

/**
 * Display targets for the Display brightness sub-section.
 */
enum class DisplayTarget(val label: String) {
    HEAD_UP("Head-Up"),
    VIRTUAL_COCKPIT("Virtual Cockpit"),
    MMI("MMI"),
}

/**
 * Favorites zone labels on the 3D cockpit overlay.
 */
enum class FavoriteZone(val label: String) {
    LEFT_SATELLITE("Left Satellite"),
    RIGHT_SATELLITE("Right Satellite"),
    CLIMATE_FAVORITE("Climate Favorite"),
    CENTER_CONTROL_UNIT("Center Control Unit"),
}

/**
 * Ambient light setting items that can be individually toggled.
 */
data class AmbientLightSettingItem(
    val id: String,
    val label: String,
    val enabled: Boolean = false,
)

/**
 * Full ambient light state for the Comfort & Interior screen.
 *
 * Maps to the reference UI which has three sections:
 * 1. Master toggle + theme selector + brightness slider
 * 2. Ambient Light Settings (Footwell, Roofline, Panoramic Roof Lighting)
 * 3. Interaction Light (master toggle + brightness + individual feature toggles)
 */
data class ComfortAmbientLightState(
    val masterEnabled: Boolean = false,
    val theme: AmbientTheme = AmbientTheme.SKY,
    val brightness: Float = 0.3f,
    val footwellLightingEnabled: Boolean = false,
    val rooflineLightingEnabled: Boolean = false,
    val panoramicRoofLightingEnabled: Boolean = false,
    val interactionLightEnabled: Boolean = false,
    val interactionLightBrightness: Float = 0.5f,
    val settings: List<AmbientLightSettingItem> = defaultAmbientSettings(),
)

/**
 * Panorama roof segment state.
 */
data class PanoramaRoofState(
    val segments: List<Boolean> = List(ROOF_SEGMENT_COUNT) { false },
    val selectedPresetIndex: Int = -1,
) {
    companion object {
        const val ROOF_SEGMENT_COUNT = 10
        const val ROOF_PRESET_COUNT = 8
    }
}

/**
 * Safety & privacy state for the Comfort & Interior screen.
 */
data class SafetyPrivacyState(
    val passengerAirbagEnabled: Boolean = true,
    val fondInfoToneEnabled: Boolean = false,
    val childPresenceDetectionEnabled: Boolean = false,
    val gloveboxPinEnabled: Boolean = false,
)

/**
 * Default ambient light settings list.
 */
fun defaultAmbientSettings(): List<AmbientLightSettingItem> = listOf(
    AmbientLightSettingItem("hazard_lights", "Indicator/Hazard warning lights"),
    AmbientLightSettingItem("charging", "Charging"),
    AmbientLightSettingItem("digital_assistant", "Digital Assistant"),
    AmbientLightSettingItem("navigation", "Navigation"),
    AmbientLightSettingItem("phone", "Phone"),
)
