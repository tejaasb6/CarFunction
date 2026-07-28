package com.ui.core.layout

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Runtime singleton for layout configuration.
 *
 * Mirrors the pattern of [com.ui.core.ThemeOrchestrator]:
 *  - `null`  → the active brand theme uses its token-level default
 *              ([com.ui.core.tokens.ThemeTokens.driverSide] + LTR + mirrorIcons = true).
 *  - non-null → the runtime value wins (e.g. the user toggled RHD in the
 *               in-car settings UI).
 *
 * Brand themes observe [config] via `collectAsStateWithLifecycle()` and provide
 * [LocalLayoutConfig] and [androidx.compose.ui.platform.LocalLayoutDirection]
 * accordingly, so the entire composition tree reacts to changes automatically.
 *
 * Usage from app code:
 * ```kotlin
 * // Switch to RHD with RTL text direction
 * LayoutOrchestrator.updateConfig(
 *     LayoutConfig(driverSide = DriverSide.RIGHT, textDir = TextDir.RTL)
 * )
 *
 * // Revert to brand-token default
 * LayoutOrchestrator.updateConfig(null)
 * ```
 */
object LayoutOrchestrator {
    private val _config = MutableStateFlow<LayoutConfig?>(null)

    /** Current layout configuration, or `null` if no runtime override is active. */
    val config: StateFlow<LayoutConfig?> = _config.asStateFlow()

    /**
     * Override the layout configuration at runtime.
     * Pass `null` to revert to the brand-token default.
     */
    fun updateConfig(config: LayoutConfig?) {
        _config.value = config
    }
}
