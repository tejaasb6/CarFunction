package com.ui.core.uxrestrictions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ── Orchestrator ───────────────────────────────────────────────────────────────

/**
 * App-level singleton for pushing driving-state updates into the composition tree.
 *
 * The host application (typically a Service or ViewModel that holds a
 * `CarUxRestrictionsManager` handle) calls [update] whenever the car's restriction
 * set changes.  Both brand theme composables collect [restrictions] via
 * `collectAsStateWithLifecycle()` and re-provide [LocalUxRestrictions] automatically.
 *
 * ## Android Automotive integration example
 * ```kotlin
 * carUxRestrictionsManager.registerListener { carRestrictions ->
 *     UxRestrictionsOrchestrator.update(
 *         UxRestrictions(
 *             drivingState = when {
 *                 carRestrictions.isRequiresDistractionOptimization -> DrivingState.MOVING
 *                 else -> DrivingState.IDLING
 *             },
 *             isSetupRestricted = carRestrictions.isRequiresDistractionOptimization,
 *         )
 *     )
 * }
 * ```
 *
 * ## ADB testing
 * ```
 * # Simulate car moving
 * adb shell dumpsys car_service inject-ux-restrictions --driving-state MOVING
 * # Return to parked
 * adb shell dumpsys car_service inject-ux-restrictions --driving-state PARKED
 * ```
 */
object UxRestrictionsOrchestrator {
    private val _restrictions = MutableStateFlow(UxRestrictions())

    /** Observable restriction state. Collected by the theme composables. */
    val restrictions: StateFlow<UxRestrictions> = _restrictions.asStateFlow()

    /** Push a full new restriction snapshot. */
    fun update(restrictions: UxRestrictions) {
        _restrictions.value = restrictions
    }

    /** Convenience — update only the driving state, preserving other fields. */
    fun setDrivingState(state: DrivingState) {
        _restrictions.value = _restrictions.value.copy(drivingState = state)
    }
}
