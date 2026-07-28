package com.ui.core.engine.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text as M3Text

/**
 * Type alias for a brand theme composable wrapper.
 *
 * Every brand module (Audi, Lamborghini, …) registers one of these via
 * [BrandThemeRegistry.register] at startup. The app then calls [BrandTheme]
 * without importing any brand-specific class.
 */
typealias BrandThemeContent = @Composable (
    isDarkOverride: Boolean?,
    content: @Composable () -> Unit,
) -> Unit

/**
 * Global registry for the active brand theme composable.
 *
 * A brand initializer (e.g. `AudiThemeInitializer`) calls [register] once
 * during `AndroidX Startup`. The app calls [BrandTheme] which delegates to
 * the registered composable — no brand imports needed in app code.
 *
 * Thread-safety: [register] is called from the main thread during
 * `Initializer.create()` before any Compose frame is rendered. Reads happen
 * on the main thread inside `@Composable`. No synchronisation required.
 */
object BrandThemeRegistry {
    @Volatile
    private var themeContent: BrandThemeContent? = null

    /**
     * Register the brand's root theme composable.
     *
     * Call this once from your brand's [androidx.startup.Initializer.create]:
     * ```kotlin
     * BrandThemeRegistry.register { isDark, content ->
     *     AudiTheme(isDarkOverride = isDark) { content() }
     * }
     * ```
     */
    fun register(theme: BrandThemeContent) {
        themeContent = theme
    }

    /** Returns the registered theme, or `null` if no brand has registered. */
    internal fun get(): BrandThemeContent? = themeContent
}

/**
 * Brand-agnostic theme wrapper.
 *
 * Delegates to whichever brand theme was registered via
 * [BrandThemeRegistry.register]. If no brand is registered, shows a
 * fallback message instructing the user to install the theme.
 *
 * ```kotlin
 * // In MainActivity (no brand imports needed):
 * BrandTheme(isDarkOverride = null) {
 *     AppShell()
 * }
 * ```
 *
 * @param isDarkOverride `null` = follow system, `true` = force dark, `false` = force light.
 * @param content the app content to render inside the brand theme.
 */
@Composable
fun BrandTheme(
    isDarkOverride: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val theme = BrandThemeRegistry.get()
    if (theme != null) {
        theme(isDarkOverride, content)
    } else {
        BrandThemeFallback()
    }
}

/**
 * Fallback UI shown when no brand theme has been registered.
 */
@Composable
private fun BrandThemeFallback() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            M3Text(
                text = "No Brand Theme Registered",
                style =
                    TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    ),
            )
            Spacer(Modifier.height(12.dp))
            M3Text(
                text = "Ensure a brand module (Audi / Lamborghini) is included in the build.",
                style =
                    TextStyle(
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                    ),
            )
        }
    }
}
