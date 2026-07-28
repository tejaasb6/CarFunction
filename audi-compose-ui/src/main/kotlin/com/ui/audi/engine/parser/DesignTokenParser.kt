package com.ui.audi.engine.parser

import android.content.Context
import com.ui.audi.engine.model.DesignToken
import com.ui.audi.engine.model.ParsedTokenData
import com.ui.audi.engine.model.ShadowDesignToken
import com.ui.audi.engine.model.ShadowValue
import com.ui.audi.engine.model.TypographyDesignToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Parses Figma/Tokens Studio design token JSON files for the Audi brand.
 *
 * Produces a [ParsedTokenData] with separate light and dark token maps so that
 * [com.ui.core.engine.runtime.DesignTokenManager] can serve both modes without
 * storing shared tokens twice.
 *
 * Shared tokens (dimensions, typography, Cmp, Core) are parsed once and merged
 * with each mode's color file — reducing memory vs duplicating the full map.
 *
 * If a future brand (e.g. Lamborghini) changes its JSON schema, it provides its
 * own parser in lamborghini-compose-ui without touching this file or common-core-ui.
 */
class DesignTokenParser {
    // ── Public entry points ────────────────────────────────────────────────────

    /**
     * Parse from a map of filename → JSON content (primary runtime path).
     * Automatically splits light/dark color files and builds two token maps
     * that share a common base.
     */
    fun parse(jsonMap: Map<String, String>): DualParsedTokenData {
        val darkFilenames = jsonMap.keys.filter { isDarkFile(it) }.toSet()
        val lightFilenames = jsonMap.keys.filter { isLightFile(it) }.toSet()

        val sharedTokens = parseFiles(jsonMap, exclude = darkFilenames + lightFilenames)

        val lightData =
            if (lightFilenames.isNotEmpty()) {
                val lightColors = parseFiles(jsonMap, include = lightFilenames)
                merge(sharedTokens, lightColors)
            } else {
                sharedTokens
            }

        val darkData =
            if (darkFilenames.isNotEmpty()) {
                val darkColors = parseFiles(jsonMap, include = darkFilenames)
                merge(sharedTokens, darkColors)
            } else {
                lightData
            }

        return DualParsedTokenData(light = lightData, dark = darkData)
    }

    /** Parse from Android assets (useful for local dev / testing). */
    fun parseFromAssets(
        context: Context,
        basePath: String = "",
    ): DualParsedTokenData {
        val jsonMap = mutableMapOf<String, String>()
        collectAssets(context, basePath, jsonMap)
        return parse(jsonMap)
    }

    /** Parse from filesystem directory (OTA theme updates). */
    fun parseFromDirectory(directory: File): DualParsedTokenData {
        val jsonMap =
            directory
                .walkTopDown()
                .filter { it.isFile && it.extension == "json" && !it.name.startsWith("$") }
                .associate { it.relativeTo(directory).path.replace('\\', '/') to it.readText() }
        return parse(jsonMap)
    }

    // ── Private helpers ────────────────────────────────────────────────────────

    private fun isDarkFile(name: String) = name.contains("Colors-Dark", ignoreCase = true)

    private fun isLightFile(name: String) = name.contains("Colors-Light", ignoreCase = true)

    /**
     * Parse a subset of the JSON map.
     * Pass [exclude] to skip certain filenames, or [include] to only process certain filenames.
     */
    @Suppress("LoopWithTooManyJumpStatements")
    private fun parseFiles(
        jsonMap: Map<String, String>,
        exclude: Set<String> = emptySet(),
        include: Set<String>? = null,
    ): ParsedTokenData {
        val tokenMap = mutableMapOf<String, DesignToken>()
        val typographyTokens = mutableMapOf<String, TypographyDesignToken>()
        val shadowTokens = mutableMapOf<String, ShadowDesignToken>()

        for ((filename, content) in jsonMap) {
            if (filename in exclude) continue
            if (include != null && filename !in include) continue
            if (filename.startsWith("$") || content.isBlank() || content == "{}") continue

            runCatching {
                flattenJson(JSONObject(content), "", tokenMap, typographyTokens, shadowTokens)
            }.onFailure {
                android.util.Log.w(TAG, "Failed to parse $filename: ${it.message}")
            }
        }

        return ParsedTokenData(tokenMap, typographyTokens, shadowTokens)
    }

    /** Merge base tokens with an overlay (overlay wins on key conflict). */
    private fun merge(
        base: ParsedTokenData,
        overlay: ParsedTokenData,
    ) = ParsedTokenData(
        tokenMap = base.tokenMap + overlay.tokenMap,
        typographyTokens = base.typographyTokens + overlay.typographyTokens,
        shadowTokens = base.shadowTokens + overlay.shadowTokens,
    )

    private fun collectAssets(
        context: Context,
        path: String,
        out: MutableMap<String, String>,
    ) {
        val entries = context.assets.list(path) ?: return
        for (entry in entries) {
            if (entry.startsWith("$")) continue
            val fullPath = if (path.isEmpty()) entry else "$path/$entry"
            if (entry.endsWith(".json")) {
                runCatching {
                    out[fullPath] =
                        context.assets
                            .open(fullPath)
                            .bufferedReader()
                            .readText()
                }
            } else {
                collectAssets(context, fullPath, out)
            }
        }
    }

    @Suppress("NestedBlockDepth")
    private fun flattenJson(
        json: JSONObject,
        prefix: String,
        tokenMap: MutableMap<String, DesignToken>,
        typographyTokens: MutableMap<String, TypographyDesignToken>,
        shadowTokens: MutableMap<String, ShadowDesignToken>,
    ) {
        val keys = json.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val child = json.get(key)
            val path = if (prefix.isEmpty()) key else "$prefix.$key"

            when {
                child is JSONObject && child.has("value") && child.has("type") -> {
                    val type = child.getString("type")
                    val description = if (child.has("description")) child.getString("description") else null
                    val rawValue = child.get("value")

                    when {
                        type == "typography" && rawValue is JSONObject ->
                            typographyTokens[path] =
                                TypographyDesignToken(
                                    path = path,
                                    fontFamily = rawValue.optString("fontFamily"),
                                    fontWeight = rawValue.optString("fontWeight"),
                                    fontSize = rawValue.optString("fontSize"),
                                    lineHeight = rawValue.optString("lineHeight"),
                                    letterSpacing = rawValue.optString("letterSpacing"),
                                    textDecoration = rawValue.optString("textDecoration", "None"),
                                    description = description,
                                )

                        type == "boxShadow" && rawValue is JSONArray -> {
                            val layers =
                                (0 until rawValue.length()).map { i ->
                                    rawValue.getJSONObject(i).let { layer ->
                                        ShadowValue(
                                            x = layer.optString("x", "0"),
                                            y = layer.optString("y", "0"),
                                            blur = layer.optString("blur", "0"),
                                            spread = layer.optString("spread", "0"),
                                            color = layer.optString("color"),
                                            type = layer.optString("type", "dropShadow"),
                                        )
                                    }
                                }
                            shadowTokens[path] = ShadowDesignToken(path, layers, description)
                        }

                        else ->
                            tokenMap[path] =
                                DesignToken(
                                    path = path,
                                    rawValue = rawValue.toString(),
                                    type = type,
                                    description = description,
                                )
                    }
                }

                child is JSONObject -> flattenJson(child, path, tokenMap, typographyTokens, shadowTokens)
            }
        }
    }

    companion object {
        private const val TAG = "DesignTokenParser"
    }
}

/** Holds both light and dark [ParsedTokenData] produced by a single parse run. */
data class DualParsedTokenData(
    val light: ParsedTokenData,
    val dark: ParsedTokenData,
)
