package com.ui.core.uxrestrictions

import androidx.compose.runtime.compositionLocalOf

// ── Driving state ──────────────────────────────────────────────────────────────

/**
 * Motion state of the vehicle.
 *
 * On Android Automotive OS this is derived from the system `CarUxRestrictions` API.
 * On standard Android builds the orchestrator defaults to [PARKED] so all UI is
 * unrestricted without any host integration.
 */
enum class DrivingState {
    /** Vehicle is stationary; full UI available. */
    PARKED,

    /** Engine running but not moving (e.g. stopped at traffic lights). */
    IDLING,

    /** Vehicle is in motion; distraction-sensitive UI must be restricted. */
    MOVING,
}

// ── Restriction model ──────────────────────────────────────────────────────────

/**
 * Snapshot of the current UX restrictions imposed by the vehicle / platform.
 *
 * Non-automotive builds default to [DrivingState.PARKED] with all restrictions off,
 * so no integration work is required for those targets.
 *
 * @param drivingState      Current vehicle motion state.
 * @param isSetupRestricted When `true`, setup / configuration flows are forbidden
 *                          (maps to `CarUxRestrictions.UX_RESTRICTIONS_NO_SETUP` on AAOS).
 */
data class UxRestrictions(
    val drivingState: DrivingState = DrivingState.PARKED,
    val isSetupRestricted: Boolean = false,
) {
    /** `true` while the car is actively moving. Widgets read this to auto-disable. */
    val isMoving: Boolean get() = drivingState == DrivingState.MOVING
}

// ── CompositionLocal ───────────────────────────────────────────────────────────

/**
 * [androidx.compose.runtime.CompositionLocal] carrying the current [UxRestrictions].
 *
 * Provided by [com.ui.audi.AudiTheme] and [com.ui.lamborghini.LamborghiniTheme], both
 * of which collect from [UxRestrictionsOrchestrator].  Defaults to unrestricted (parked)
 * so previews and non-automotive builds need no setup.
 *
 * ## Widget consumption pattern
 * ```kotlin
 * val restrictions = LocalUxRestrictions.current
 * val effectiveEnabled = enabled && (!restrictions.isMoving || isDistractionOptimized)
 * ```
 */
val LocalUxRestrictions = compositionLocalOf { UxRestrictions() }
