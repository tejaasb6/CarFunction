package com.ui.core.uxrestrictions

/**
 * Bridges a platform UX-restrictions source (e.g. Android Automotive OS
 * `CarUxRestrictionsManager`) into [UxRestrictionsOrchestrator] without coupling
 * the library to any platform-specific API.
 *
 * Constructed once in `Activity.onCreate` with three lightweight lambdas that wrap
 * the platform API; then [start] / [stop] are driven by the host's lifecycle.
 *
 * ---
 *
 * ## Android Automotive OS integration example
 *
 * ```kotlin
 * // In MainActivity.kt (AAOS build variant)
 *
 * private var car: Car? = null
 * private var aaosListener: CarUxRestrictionsManager.OnUxRestrictionsChangedListener? = null
 * private var uxConnector: UxRestrictionsConnector? = null
 *
 * override fun onCreate(savedInstanceState: Bundle?) {
 *     super.onCreate(savedInstanceState)
 *
 *     val car = Car.createCar(this).also { this.car = it }
 *     val uxManager = car.getCarManager(Car.CAR_UX_RESTRICTION_SERVICE)
 *                       as? CarUxRestrictionsManager
 *
 *     if (uxManager != null) {
 *         uxConnector = UxRestrictionsConnector(
 *             fetchCurrent   = { uxManager.currentCarUxRestrictions.toUxRestrictions() },
 *             startListening = { onChange ->
 *                 CarUxRestrictionsManager.OnUxRestrictionsChangedListener { carRestrictions ->
 *                     onChange(carRestrictions.toUxRestrictions())
 *                 }.also { listener ->
 *                     aaosListener = listener
 *                     uxManager.registerListener(listener)
 *                 }
 *             },
 *             stopListening  = {
 *                 aaosListener?.let { uxManager.unregisterListener(it) }
 *                 aaosListener = null
 *             },
 *         )
 *     }
 *
 *     setContent { BrandTheme { AppShell() } }
 * }
 *
 * override fun onStart() { super.onStart(); uxConnector?.start() }
 * override fun onStop()  { super.onStop();  uxConnector?.stop()  }
 *
 * override fun onDestroy() { super.onDestroy(); car?.disconnect() }
 *
 * // ── CarUxRestrictions → domain type ───────────────────────────────────────
 * private fun CarUxRestrictions.toUxRestrictions() = UxRestrictions(
 *     drivingState      = if (isRequiresDistractionOptimization) DrivingState.MOVING
 *                         else DrivingState.IDLING,
 *     isSetupRestricted = isRequiresDistractionOptimization,
 * )
 * ```
 *
 * ---
 *
 * ## Non-AAOS builds
 * Do not create a connector — [UxRestrictionsOrchestrator] stays at its safe default
 * ([DrivingState.PARKED]) so all widgets remain fully interactive.
 *
 * @param fetchCurrent   Returns the restriction state at the moment listening starts.
 *                       Called immediately inside [start] so the UI reflects the real
 *                       state on the first frame before the first callback fires.
 * @param startListening Registers a listener with the platform source. The lambda
 *                       receives an `onChange` callback that the caller must invoke
 *                       whenever the restriction state changes.
 * @param stopListening  Unregisters the listener registered by [startListening].
 *                       Responsible for releasing any platform resources.
 */
class UxRestrictionsConnector(
    private val fetchCurrent: () -> UxRestrictions,
    private val startListening: (onChange: (UxRestrictions) -> Unit) -> Unit,
    private val stopListening: () -> Unit,
) {
    /**
     * Snapshots the current restrictions into [UxRestrictionsOrchestrator] and begins
     * listening for updates.
     *
     * Call from `Activity.onStart()` (or `Fragment.onStart()`).
     */
    fun start() {
        // Push the current state immediately — avoids a one-frame stale-state flash.
        UxRestrictionsOrchestrator.update(fetchCurrent())
        startListening { UxRestrictionsOrchestrator.update(it) }
    }

    /**
     * Stops listening and resets [UxRestrictionsOrchestrator] to the safe unrestricted
     * default so the UI is never left in a permanently-disabled state when the app
     * goes to the background.
     *
     * Call from `Activity.onStop()` (or `Fragment.onStop()`).
     */
    fun stop() {
        stopListening()
        UxRestrictionsOrchestrator.update(UxRestrictions()) // safe PARKED default
    }
}
