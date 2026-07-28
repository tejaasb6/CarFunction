package com.ui.core.interaction

/**
 * Distraction optimization configuration for automotive UX restrictions.
 *
 * Allows widgets to be automatically disabled when the vehicle is in motion.
 * Implement this interface in widget-specific interaction configs.
 */
interface DistractionOptimizationConfig {
    /**
     * When `false`, the widget is automatically disabled while the vehicle is moving.
     * Defaults to `true` (widget stays enabled regardless of motion state).
     */
    val isDistractionOptimized: Boolean
        get() = true
}
