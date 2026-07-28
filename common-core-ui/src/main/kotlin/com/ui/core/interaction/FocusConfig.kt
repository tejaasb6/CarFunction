package com.ui.core.interaction

import androidx.compose.ui.focus.FocusRequester

/**
 * Focus configuration for focusable widgets (D-pad / Rotary navigation).
 *
 * Provides programmatic focus control via [FocusRequester].
 * Implement this interface in widget-specific interaction configs.
 */
interface FocusConfig {
    /** Optional [FocusRequester] for programmatic focus control (D-pad / Rotary). */
    val focusRequester: FocusRequester?
        get() = null
}
