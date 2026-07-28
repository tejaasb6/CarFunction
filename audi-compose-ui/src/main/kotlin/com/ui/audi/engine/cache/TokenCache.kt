package com.ui.audi.engine.cache

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.ui.audi.engine.model.DesignToken
import com.ui.audi.engine.model.ResolvedValue
import com.ui.audi.engine.model.ShadowDesignToken
import com.ui.audi.engine.model.TypographyDesignToken
import com.ui.audi.engine.resolver.TokenResolver
import com.ui.core.engine.api.BoxShadowData
import com.ui.core.engine.api.ShadowLayer
import com.ui.core.engine.api.TokenStore

/**
 * Audi-specific [TokenStore] implementation.
 *
 * Eagerly warms all tokens at construction time so every Compose render is a
 * plain O(1) HashMap read with zero lock overhead. Write-once on an IO thread;
 * reads happen on the main/render thread after [DesignTokenManager.isLoaded] is
 * set to true, so plain HashMaps are safe without synchronisation.
 */
@Suppress("TooManyFunctions", "TooGenericExceptionCaught")
class TokenCache(
    resolver: TokenResolver,
    tokenMap: Map<String, DesignToken>,
    shadowTokens: Map<String, ShadowDesignToken> = emptyMap(),
    typographyTokens: Map<String, TypographyDesignToken> = emptyMap(),
    private val density: Float,
) : TokenStore {
    private val colorCache = HashMap<String, Color>(tokenMap.size)
    private val dimensionCache = HashMap<String, Float>(tokenMap.size)
    private val fontCache = HashMap<String, Float>(tokenMap.size)
    private val floatCache = HashMap<String, Float>(tokenMap.size)
    private val stringCache = HashMap<String, String>(tokenMap.size)
    private val boolCache = HashMap<String, Boolean>(tokenMap.size)
    private val textStyleCache = HashMap<String, TextStyle>(tokenMap.size)
    private val shadowCache = HashMap<String, BoxShadowData>(shadowTokens.size)

    init {
        // ── Warm regular tokens ───────────────────────────────────────────────
        // Skip typography and boxShadow tokens with cross-map references here;
        // they are resolved in dedicated sections below (Steps 1–2 for typography,
        // Sem shadow warm + Cmp shadow resolution for boxShadow).
        tokenMap.forEach { (path, token) ->
            if (token.type == "typography" || token.type == "boxShadow") return@forEach
            try {
                when (val resolved = resolver.resolve(path)) {
                    is ResolvedValue.ColorValue -> colorCache[path] = Color(resolved.argb)
                    is ResolvedValue.DimensionValue -> {
                        dimensionCache[path] = resolved.px
                        fontCache[path] = resolved.px
                    }
                    is ResolvedValue.OpacityValue -> {
                        @Suppress("MagicNumber")
                        floatCache[path] = resolved.percent / 100f
                    }
                    is ResolvedValue.BooleanValue -> boolCache[path] = resolved.value
                    is ResolvedValue.RawValue -> stringCache[path] = resolved.value
                    is ResolvedValue.StringValue -> stringCache[path] = resolved.value
                    is ResolvedValue.TextStyleValue -> textStyleCache[path] = resolved.value
                }
                stringCache.getOrPut(path) { resolver.resolveRaw(path) }
            } catch (e: Exception) {
                Log.w(TAG, "Warm failed for '$path': ${e.message}")
            }
        }

        // Warm shadow tokens — resolve layer references and build BoxShadowData
        shadowTokens.forEach { (path, token) ->
            try {
                val layers =
                    token.layers.map { layer ->
                        // Shadow colour fields may be references like {Sem.Color.ShadowColor.Primary}
                        val colorArgb =
                            try {
                                val colorRef = layer.color.removeSurrounding("{", "}")
                                val resolved = resolver.resolve(colorRef)
                                if (resolved is ResolvedValue.ColorValue) Color(resolved.argb) else Color.Black
                            } catch (_: Exception) {
                                Color.Black
                            }

                        ShadowLayer(
                            x = layer.x.toFloatOrNull() ?: 0f,
                            y = layer.y.toFloatOrNull() ?: 0f,
                            blur = layer.blur.toFloatOrNull() ?: 0f,
                            spread = layer.spread.toFloatOrNull() ?: 0f,
                            color = colorArgb,
                        )
                    }
                // Derive dp-equivalent elevation from the largest layer's y-offset + blur,
                // halved to approximate Material shadow elevation (Level2 → 4dp).
                val elevation = layers.maxOfOrNull { (it.y + it.blur) / 2f } ?: 0f
                shadowCache[path] = BoxShadowData(layers = layers, elevation = elevation)
            } catch (e: Exception) {
                Log.w(TAG, "Shadow warm failed for '$path': ${e.message}")
            }
        }

        // Resolve Cmp-level boxShadow references (e.g. Cmp.Shadow.Action.Chip.MD.Dragged
        // → {Sem.Shadow.Elevation.Level2}). These are stored in tokenMap as regular
        // DesignTokens with type=boxShadow and a string reference value.
        tokenMap.values.forEach { token ->
            if (token.type == "boxShadow" && token.path !in shadowCache) {
                val ref = token.rawValue.removeSurrounding("{", "}")
                val resolved = shadowCache[ref]
                if (resolved != null) {
                    shadowCache[token.path] = resolved
                } else {
                    Log.w(TAG, "Shadow ref unresolved for '${token.path}': '$ref' not in shadowCache")
                }
            }
        }

        // ── Warm typography tokens → TextStyle ────────────────────────────────
        // Step 1: Build TextStyle from Sem-level typography tokens (have JSONObject values)
        typographyTokens.forEach { (path, typo) ->
            try {
                textStyleCache[path] = buildTextStyle(typo, resolver)
            } catch (e: Exception) {
                Log.w(TAG, "Typography warm failed for '$path': ${e.message}")
            }
        }

        // Step 2: Resolve Cmp-level typography references that point to Sem typography
        // e.g. Cmp.Typography.Forms.FormFields.UserInput → "{Sem.Typography.Label.Order1.Normal}"
        // The referenced Sem token is in typographyTokens (not tokenMap), so we extract
        // the reference path from the raw value and look it up directly in textStyleCache.
        tokenMap.values
            .filter { it.type == "typography" }
            .forEach { token ->
                if (textStyleCache.containsKey(token.path)) return@forEach
                try {
                    // Extract referenced path: "{Sem.Typography.Label.Order1.Normal}" → "Sem.Typography.Label.Order1.Normal"
                    val refPath = REFERENCE_PATTERN.find(token.rawValue)?.groupValues?.get(1)
                    if (refPath != null) {
                        val resolved = textStyleCache[refPath]
                        if (resolved != null) {
                            textStyleCache[token.path] = resolved
                        } else {
                            // Reference might be chained — try resolving through typographyTokens
                            val typo = typographyTokens[refPath]
                            if (typo != null) {
                                val style = buildTextStyle(typo, resolver)
                                textStyleCache[refPath] = style
                                textStyleCache[token.path] = style
                            } else {
                                Log.w(
                                    TAG,
                                    "Typography ref unresolved for '${token.path}': " +
                                        "'$refPath' not in textStyleCache or typographyTokens",
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Typography ref warm failed for '${token.path}': ${e.message}")
                }
            }

        Log.d(TAG, "Warmed ${tokenMap.size} tokens, ${shadowCache.size} shadows")
    }

    /**
     * Resolves a [TypographyDesignToken] into a Compose [TextStyle].
     * Font fields may contain token references like `{Sem.FontSize.200}` that
     * are resolved through [TokenResolver] before converting to sp values.
     */
    @Suppress("MagicNumber")
    private fun buildTextStyle(
        typo: TypographyDesignToken,
        resolver: TokenResolver,
    ): TextStyle {
        val fontSize = resolveToFloat(typo.fontSize, resolver)
        val lineHeight = resolveToFloat(typo.lineHeight, resolver)
        val letterSpacing = resolveToFloat(typo.letterSpacing, resolver)
        val fontWeight = resolveToFloat(typo.fontWeight, resolver)
        // Resolve the fontWeight reference so parseFontWeight sees the final
        // string value (e.g. "Bold") instead of the raw token reference
        // (e.g. "{Sem.FontWeight.Copy.Bold}") which would fail to match any
        // named weight and silently fall back to Normal.
        val resolvedFontWeightRaw = resolver.resolveRawValue(typo.fontWeight)

        return TextStyle(
            fontSize = if (fontSize > 0f) (fontSize / density).sp else TextStyle.Default.fontSize,
            lineHeight = if (lineHeight > 0f) (lineHeight / density).sp else TextStyle.Default.lineHeight,
            letterSpacing = letterSpacing.sp,
            fontWeight = parseFontWeight(fontWeight, resolvedFontWeightRaw),
            textDecoration = parseTextDecoration(typo.textDecoration),
        )
    }

    /** Resolves a raw value (may contain `{Token.Ref}`) to a Float. Handles px, rem, plain numbers. */
    private fun resolveToFloat(
        raw: String,
        resolver: TokenResolver,
    ): Float {
        if (raw.isBlank()) return 0f
        val resolved = resolver.resolveRawValue(raw)
        return PX_PATTERN
            .find(resolved)
            ?.groupValues
            ?.get(1)
            ?.toFloatOrNull()
            ?: REM_PATTERN
                .find(resolved)
                ?.groupValues
                ?.get(1)
                ?.let { it.toFloatOrNull()?.times(16f) }
            ?: resolved.toFloatOrNull()
            ?: 0f
    }

    /** Maps numeric (100–900) or named font-weight values to [FontWeight]. */
    private fun parseFontWeight(
        numericValue: Float,
        raw: String,
    ): FontWeight {
        if (numericValue > 0f) return FontWeight(numericValue.toInt())
        return when (raw.trim().lowercase()) {
            "thin", "100" -> FontWeight.Thin
            "extralight", "200" -> FontWeight.ExtraLight
            "light", "300" -> FontWeight.Light
            "regular", "normal", "400" -> FontWeight.Normal
            "medium", "500" -> FontWeight.Medium
            "semibold", "600" -> FontWeight.SemiBold
            "bold", "700" -> FontWeight.Bold
            "extrabold", "800" -> FontWeight.ExtraBold
            "black", "900" -> FontWeight.Black
            else -> FontWeight.Normal
        }
    }

    /** Maps text-decoration string to [TextDecoration]. */
    private fun parseTextDecoration(raw: String): TextDecoration =
        when (raw.trim().lowercase()) {
            "underline" -> TextDecoration.Underline
            "line-through", "linethrough" -> TextDecoration.LineThrough
            else -> TextDecoration.None
        }

    override fun color(path: String): Color =
        colorCache[path] ?: run {
            Log.w(TAG, "Color token not found: '$path'")
            Color.Unspecified
        }

    override fun dimension(path: String): Float =
        dimensionCache[path] ?: run {
            Log.w(TAG, "Dp token not found: '$path'")
            0F
        }

    override fun font(path: String): Float =
        fontCache[path] ?: run {
            Log.w(TAG, "Sp token not found: '$path'")
            0f
        }

    override fun opacity(path: String): Float =
        floatCache[path] ?: run {
            Log.w(TAG, "Opacity token not found: '$path'")
            1f
        }

    override fun string(path: String): String =
        stringCache[path] ?: run {
            Log.w(TAG, "String token not found: '$path'")
            ""
        }

    override fun boolean(path: String): Boolean =
        boolCache[path] ?: run {
            Log.w(TAG, "Boolean token not found: '$path'")
            false
        }

    override fun typography(path: String): TextStyle =
        textStyleCache[path] ?: run {
            Log.w(TAG, "Text style token not found: '$path'")
            TextStyle.Default
        }

    override fun boxShadow(path: String): BoxShadowData =
        shadowCache[path] ?: run {
            Log.w(TAG, "BoxShadow token not found: '$path'")
            BoxShadowData.None
        }

    companion object {
        private const val TAG = "TokenCache"
        private val PX_PATTERN = Regex("^([\\d.]+)px$")
        private val REM_PATTERN = Regex("^([\\d.]+)rem$")
        private val REFERENCE_PATTERN = Regex("^\\{([^}]+)\\}$")
    }
}
