package com.ui.core.interaction

/**
 * Base interaction contract shared by all interactive widget configuration objects.
 *
 * Component-specific configs (e.g. [com.ui.core.widgets.buttons.ButtonInteractionConfig]) implement this interface,
 * allowing shared utilities such as [interactiveClickable] to work with any widget type
 * without knowing the concrete config class.
 *
 * All properties carry safe no-op / off defaults so implementing classes only need to
 * override what they expose.
 *
 */
interface InteractionConfig {
    /** Primary tap handler. Invoked on a single tap (after the debounce window, if set). */
    val onClick: () -> Unit

    /** Optional long-press handler fired after a sustained press (≥ 400 ms by default). */
    val onLongClick: (() -> Unit)?

    /** Optional double-tap handler fired within the system double-tap window (~ 300 ms). */
    val onDoubleClick: (() -> Unit)?

    /**
     * Minimum milliseconds between accepted taps.
     *
     * `0` disables debouncing (default).  Recommended value for automotive: 300 ms.
     */
    val clickDebounceMs: Long
}
