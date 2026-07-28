package com.ui.core.engine.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.ui.core.engine.api.ThemeTokenAccessor
import com.ui.core.engine.runtime.DesignTokenManager

val LocalDesignTokenManager = staticCompositionLocalOf { DesignTokenManager }

/**
 * Root composable that syncs dark/light mode with [DesignTokenManager].
 *
 * ## Why SideEffect (not LaunchedEffect)
 *
 * [SideEffect] runs **synchronously after every successful composition** in which
 * [isDarkTheme] changed. This keeps [DesignTokenManager.updateDarkMode] tight to
 * the composition cycle:
 *
 *  1. System dark-mode flips → `isSystemInDarkTheme()` changes → this composable
 *     re-runs with a new [isDarkTheme] value.
 *  2. [SideEffect] fires → [DesignTokenManager.updateDarkMode] swaps [activeStore]
 *     (a `mutableStateOf`) to the dark/light [TokenStore].
 *  3. Any `@Composable` that called a token accessor (`Sem.Color.Fill.Canvas.color()`,
 *     etc.) was already subscribed to `activeStore`. It is invalidated and recomposes
 *     on the **very next frame**, picking up the correct dark/light token values.
 *
 * Using [LaunchedEffect] would introduce an async coroutine dispatch, causing a
 * 1-frame delay and occasionally selecting the wrong store when [load] races with the
 * first composition.
 *
 * ## Reactivity for theme swaps
 *
 * When a new theme APK is applied and [DesignTokenManager.load] is called from the IO
 * thread, `activeStore` (mutableStateOf) is updated. Every composable subscribed to a
 * token accessor is automatically invalidated — no `themeVersion` counter needed.
 */
@Composable
fun DesignTokenTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // Runs synchronously after composition — keeps activeStore in sync with system theme.
    SideEffect {
        DesignTokenManager.updateDarkMode(isDarkTheme)
    }

    CompositionLocalProvider(LocalDesignTokenManager provides DesignTokenManager) {
        content()
    }
}

// ── Convenience composable helpers ────────────────────────────────────────────

@Composable
fun themeColor(token: ThemeTokenAccessor): Color = token.color()

@Composable
fun rememberThemeColor(path: String): Color {
    val tm = LocalDesignTokenManager.current
    return tm.color(path)
}
