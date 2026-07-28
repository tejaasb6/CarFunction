package com.ui.core.engine.runtime

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.ui.core.engine.api.BoxShadowData
import com.ui.core.engine.api.TokenStore

/**
 * Singleton runtime manager for design tokens. Knows nothing about JSON formats,
 * parsers, or resolution logic — those are brand-specific concerns in each brand lib.
 *
 * A brand initializer (e.g. [com.ui.audi.AudiThemeSyncer]) parses the theme JSON using
 * its own [TokenStore] implementation and calls [load] from a background thread.
 *
 * ## How Compose reactivity works
 *
 * [activeStore] is backed by [mutableStateOf]. Every `@Composable` that calls
 * [color], [dimension], [font], etc. reads [activeStore] and is therefore automatically
 * subscribed to it. When [activeStore] is replaced — either because a new theme
 * was loaded ([load]) or because dark/light mode toggled ([updateDarkMode]) —
 * Compose invalidates only those composables that actually read a token, not the
 * entire tree. No manual `themeVersion` counter or `LaunchedEffect` is needed.
 *
 * ## Threading model
 * - [load] is called from a background (IO) thread.
 * - All [TokenStore] implementations must be fully built before being passed here.
 * - [mutableStateOf] writes are safe from any thread; the Compose snapshot system
 *   propagates them to the main thread before the next frame.
 * - [isLoaded] flips to `true` only after [activeStore] is assigned, so Compose
 *   never reads from a partially-initialised store.
 */
object DesignTokenManager {
    private const val TAG = "DesignTokenManager"

    // ── Stores — kept @Volatile for fast non-Compose reads (e.g. unit tests) ──

    @Volatile private var lightStore: TokenStore? = null

    @Volatile private var darkStore: TokenStore? = null

    /**
     * The currently active [TokenStore].
     *
     * Backed by [mutableStateOf]: any `@Composable` that reads a token via [color],
     * [dimension], etc. subscribes to this field. Replacing it (on theme load or dark-mode
     * toggle) automatically invalidates and recomposes only the affected composables.
     */
    private var activeStore: TokenStore? by mutableStateOf(null)

    // ── Compose-observable state ───────────────────────────────────────────────

    /** Whether the active mode is dark. Updated by [DesignTokenTheme] via [updateDarkMode]. */
    var isDarkMode: Boolean by mutableStateOf(false)
        private set

    /** `true` once the first [load] completes successfully. Drives the loading guard in brand themes. */
    var isLoaded: Boolean by mutableStateOf(false)
        private set

    /**
     * Monotonically-increasing counter that increments on every theme load and every
     * dark/light-mode toggle. Brand theme composables ([com.ui.audi.AudiTheme],
     * [com.ui.lamborghini.LamborghiniTheme]) read this to guarantee a full recompose of
     * their subtree whenever anything changes — belt-and-suspenders on top of the
     * individual [activeStore] subscriptions from each token read.
     */
    var revision: Int by mutableStateOf(0)
        private set

    var lastError: String? = null
        private set

    // ── Public API ─────────────────────────────────────────────────────────────

    /**
     * Load (or hot-swap) the theme. Safe to call from any background thread.
     *
     * @param light [TokenStore] with tokens resolved for light mode.
     * @param dark  [TokenStore] with tokens resolved for dark mode.
     *              Pass the same instance as [light] for single-mode themes.
     */
    @Suppress("TooGenericExceptionCaught")
    fun load(
        light: TokenStore,
        dark: TokenStore = light,
    ) {
        try {
            lightStore = light
            darkStore = dark
            // Select the correct store for the current dark/light setting.
            // isDarkMode is already set by DesignTokenTheme before syncTheme runs
            // (SideEffect fires after first composition, syncTheme runs on IO).
            // If there is a race on very fast devices, updateDarkMode() will correct
            // activeStore immediately after the SideEffect fires.
            activeStore = if (isDarkMode) dark else light
            isLoaded = true
            lastError = null
            revision++
            Log.i(TAG, "Theme loaded (rev=$revision isDark=$isDarkMode)")
        } catch (e: Exception) {
            lastError = e.message
            Log.e(TAG, "Theme load failed", e)
        }
    }

    /**
     * Switch between dark and light mode.
     *
     * Called by [com.ui.core.engine.compose.DesignTokenTheme] via [SideEffect], so it
     * runs synchronously after each successful composition where [isDarkTheme] changed.
     * Assigning [activeStore] triggers precise recomposition of only the composables
     * that read design tokens — no manual version counter required.
     */
    fun updateDarkMode(darkMode: Boolean) {
        if (isDarkMode == darkMode) return
        isDarkMode = darkMode
        activeStore = if (darkMode) darkStore else lightStore
        revision++
        Log.i(TAG, "Mode → ${if (darkMode) "DARK" else "LIGHT"} (rev=$revision)")
    }

    // ── Token accessors — O(1) HashMap reads via the active store ─────────────
    // Called from @Composable context → reads mutableStateOf(activeStore) →
    // the calling composable is automatically subscribed to future store swaps.

    fun color(path: String): Color = activeStore?.color(path) ?: Color.LightGray

    fun dimension(path: String): Float = activeStore?.dimension(path) ?: 0f

    fun font(path: String): Float = activeStore?.font(path) ?: 16f

    fun opacity(path: String): Float = activeStore?.opacity(path) ?: 1f

    fun string(path: String): String = activeStore?.string(path) ?: ""

    fun boolean(path: String): Boolean = activeStore?.boolean(path) ?: false

    fun typography(path: String): TextStyle = activeStore?.typography(path) ?: TextStyle.Default

    fun boxShadow(path: String): BoxShadowData = activeStore?.boxShadow(path) ?: BoxShadowData.None
}
