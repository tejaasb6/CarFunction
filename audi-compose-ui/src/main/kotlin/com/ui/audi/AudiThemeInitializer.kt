package com.ui.audi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.startup.Initializer
import com.ui.audi.engine.cache.TokenCache
import com.ui.audi.engine.fallback.FallbackTokenLoader
import com.ui.audi.engine.parser.DesignTokenParser
import com.ui.audi.engine.resolver.TokenResolver
import com.ui.audi.engine.validator.ThemeValidator
import com.ui.core.ThemeProviderContract
import com.ui.core.engine.compose.BrandThemeRegistry
import com.ui.core.engine.runtime.DesignTokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AudiThemeInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        BrandThemeRegistry.register { isDark, content ->
            AudiTheme(isDarkOverride = isDark) { content() }
        }
        AudiThemeSyncer.start(context.applicationContext)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

internal object AudiThemeSyncer {
    private const val TAG = "AudiThemeSyncer"

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun start(context: Context) {
        // Pre-load fallback tokens synchronously so DesignTokenManager.isLoaded
        // is true before the first Compose frame renders. This eliminates the
        // "No Theme Loaded" flash. The background syncTheme() will then upgrade
        // to live ContentProvider tokens if available — DesignTokenManager.load()
        // hot-swaps seamlessly and triggers recomposition only if tokens differ.
        preloadFallbackTokens(context)
        registerThemeChangeReceiver(context)
        scope.launch { syncTheme(context) }
    }

    /**
     * Synchronously loads the bundled NX-1 fallback tokens from assets so that
     * [DesignTokenManager.isLoaded] is `true` before the first Compose frame.
     *
     * This runs on the main thread during [Initializer.create], which executes
     * before `Activity.onCreate`. The asset read + parse + cache build takes
     * ~50–150 ms on typical hardware — well within AndroidX Startup's budget
     * and far faster than the previous 2 s "No Theme Loaded" flash.
     *
     * If the live ContentProvider theme is available, [syncTheme] (on IO) will
     * immediately overwrite these fallback tokens via [DesignTokenManager.load],
     * triggering a seamless recomposition. If the ContentProvider is absent,
     * the app continues running on this fallback with zero user-visible delay.
     */
    @Suppress("TooGenericExceptionCaught")
    private fun preloadFallbackTokens(context: Context) {
        try {
            val jsonMap = FallbackTokenLoader.load(context)
            if (jsonMap.isEmpty()) {
                Log.w(TAG, "No fallback tokens to preload")
                return
            }

            val parsed = DesignTokenParser().parse(jsonMap)
            val density = context.resources.displayMetrics.density

            val lightStore =
                TokenCache(TokenResolver(parsed.light.tokenMap), parsed.light.tokenMap, parsed.light.shadowTokens, parsed.light.typographyTokens, density)
            val darkStore =
                TokenCache(TokenResolver(parsed.dark.tokenMap), parsed.dark.tokenMap, parsed.dark.shadowTokens, parsed.dark.typographyTokens, density)

            DesignTokenManager.load(light = lightStore, dark = darkStore)
            Log.i(TAG, "Fallback tokens preloaded: ${jsonMap.size} files")
        } catch (e: Exception) {
            Log.e(TAG, "Fallback preload failed — will retry in background syncTheme", e)
        }
    }

    private fun registerThemeChangeReceiver(context: Context) {
        ContextCompat.registerReceiver(
            context,
            object : BroadcastReceiver() {
                override fun onReceive(
                    ctx: Context,
                    intent: Intent,
                ) {
                    if (intent.action == ThemeProviderContract.ACTION_THEME_CHANGED) {
                        scope.launch { syncTheme(ctx) }
                    }
                }
            },
            IntentFilter(ThemeProviderContract.ACTION_THEME_CHANGED),
            ContextCompat.RECEIVER_EXPORTED,
        )
    }

    /**
     * Attempts to load live tokens from the ContentProvider (ThemeProvider + Theme APK).
     *
     * Fallback tokens are **not** re-loaded here — [preloadFallbackTokens] already
     * handles that synchronously during startup. This method only upgrades from
     * fallback to live tokens when the ContentProvider is available.
     *
     * Also called by the [ThemeProviderContract.ACTION_THEME_CHANGED] broadcast
     * receiver when a new theme APK is installed or the active theme changes.
     */
    @Suppress("TooGenericExceptionCaught")
    private fun syncTheme(context: Context) {
        try {
            val jsonMap = fetchRawTokens(context)
            if (jsonMap.isEmpty()) {
                Log.i(TAG, "No live theme from ContentProvider — continuing on fallback tokens")
                return
            }

            val parsed = DesignTokenParser().parse(jsonMap)

            // Validate on debug builds (light tokens represent the full shared set)
            logValidation(ThemeValidator().validate(parsed.light.tokenMap, parsed.light.typographyTokens, parsed.light.shadowTokens))

            val density = context.resources.displayMetrics.density
            // Build brand-specific TokenStore implementations (eager cache warm happens here)
            val lightStore =
                TokenCache(TokenResolver(parsed.light.tokenMap), parsed.light.tokenMap, parsed.light.shadowTokens, parsed.light.typographyTokens, density)
            val darkStore =
                TokenCache(TokenResolver(parsed.dark.tokenMap), parsed.dark.tokenMap, parsed.dark.shadowTokens, parsed.dark.typographyTokens, density)

            // Hand off to DesignTokenManager — common-core has no knowledge of models/resolver
            DesignTokenManager.load(light = lightStore, dark = darkStore)

            Log.i(TAG, "Theme synced from ContentProvider: ${jsonMap.size} JSON files")
        } catch (e: Exception) {
            Log.e(TAG, "Theme sync failed", e)
        }
    }

    @Suppress("MagicNumber")
    private fun logValidation(result: ThemeValidator.ValidationResult) {
        if (result.errors.isNotEmpty()) {
            Log.w(TAG, "Validation errors (${result.errors.size}):")
            result.errors.take(20).forEach { Log.w(TAG, "  ERROR: $it") }
        }
        if (result.warnings.isNotEmpty()) {
            Log.d(TAG, "Validation warnings (${result.warnings.size}):")
            result.warnings.take(10).forEach { Log.d(TAG, "  WARN: $it") }
        }
    }

    private fun fetchRawTokens(context: Context): Map<String, String> {
        val result = mutableMapOf<String, String>()
        context.contentResolver
            .query(ThemeProviderContract.RAW_TOKENS_URI, null, null, null, null)
            ?.use { cursor ->
                val filenameIdx = cursor.getColumnIndex(ThemeProviderContract.COL_FILENAME)
                val jsonIdx = cursor.getColumnIndex(ThemeProviderContract.COL_JSON)
                if (filenameIdx < 0 || jsonIdx < 0) return@use
                while (cursor.moveToNext()) {
                    val filename = cursor.getString(filenameIdx)
                    val json = cursor.getString(jsonIdx)
                    if (filename.isNotEmpty() && json.isNotEmpty()) result[filename] = json
                }
            }
        return result
    }
}
