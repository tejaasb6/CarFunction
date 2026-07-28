package com.ui.audi.engine.resolver

import com.ui.audi.engine.model.DesignToken
import com.ui.audi.engine.model.ResolvedValue

/**
 * Resolves Audi token reference chains ({Token.Path} syntax) to typed values.
 * Brand-specific: lives in audi-compose-ui because reference syntax and value
 * formats (hex, hsla, px, rem) may differ per brand in the future.
 */
@Suppress("TooManyFunctions")
class TokenResolver(
    private val tokenMap: Map<String, DesignToken>,
) {
    private val resolvedCache = HashMap<String, String>(tokenMap.size * 2)

    companion object {
        private val REFERENCE_PATTERN = Regex("\\{([^}]+)\\}")
        private val HEX_COLOR_PATTERN = Regex("^#([0-9A-Fa-f]{6,8})$")
        private val HSLA_PATTERN = Regex("hsla?\\(\\s*([\\d.]+)\\s*,\\s*([\\d.]+)%?\\s*,\\s*([\\d.]+)%?\\s*(?:,\\s*([\\d.]+))?\\s*\\)")
        private val HSL_PATTERN = Regex("hsl\\(\\s*([\\d.]+)\\s*,\\s*([\\d.]+)%?\\s*,\\s*([\\d.]+)%?\\s*(?:,\\s*([\\d.]+))?\\s*\\)")
        private val PX_PATTERN = Regex("^([\\d.]+)px$")
        private val REM_PATTERN = Regex("^([\\d.]+)rem$")
        private val PERCENT_PATTERN = Regex("^([\\d.]+)%$")
        private const val MAX_DEPTH = 50
    }

    fun resolveRaw(path: String): String {
        resolvedCache[path]?.let { return it }
        return resolveInternal(path, mutableSetOf())
    }

    fun resolve(path: String): ResolvedValue {
        val raw = resolveRaw(path)
        return parseValue(raw, tokenMap[path]?.type ?: "")
    }

    fun resolveRawValue(rawValue: String): String {
        if (!rawValue.contains("{")) return rawValue
        return resolveReferences(rawValue, mutableSetOf())
    }

    @Suppress("ThrowsCount")
    private fun resolveInternal(
        path: String,
        visited: MutableSet<String>,
        depth: Int = 0,
    ): String {
        if (depth > MAX_DEPTH) throw TokenResolutionException("Max depth exceeded for: $path")
        resolvedCache[path]?.let { return it }
        if (path in visited) throw TokenResolutionException("Circular reference: $path")

        visited.add(path)
        val token = tokenMap[path] ?: throw TokenResolutionException("Token not found: '$path'")
        val resolved = resolveReferences(token.rawValue, visited, depth)
        resolvedCache[path] = resolved
        visited.remove(path)
        return resolved
    }

    private fun resolveReferences(
        value: String,
        visited: MutableSet<String>,
        depth: Int = 0,
    ): String {
        if (!value.contains("{")) return value
        var result = value
        var last: String
        do {
            last = result
            result =
                REFERENCE_PATTERN.replace(result) { match ->
                    resolveInternal(match.groupValues[1], visited.toMutableSet(), depth + 1)
                }
        } while (result != last && result.contains("{"))
        return result
    }

    fun parseValue(
        raw: String,
        type: String,
    ): ResolvedValue =
        when {
            type == "boolean" -> ResolvedValue.BooleanValue(raw.toBooleanStrictOrNull() ?: false)
            type == "opacity" -> parseOpacity(raw)
            isColor(raw, type) -> parseColor(raw)
            isDimension(raw, type) -> parseDimension(raw)
            else -> ResolvedValue.RawValue(raw)
        }

    private fun isColor(
        raw: String,
        type: String,
    ) = type == "color" || raw.startsWith("#") || raw.startsWith("hsla(") || raw.startsWith("hsl(")

    private fun isDimension(
        raw: String,
        type: String,
    ) = type in setOf("dimension", "sizing", "spacing", "borderRadius", "borderWidth") ||
        PX_PATTERN.matches(raw) ||
        REM_PATTERN.matches(raw)

    @Suppress("MagicNumber", "ReturnCount")
    private fun parseColor(raw: String): ResolvedValue {
        HEX_COLOR_PATTERN.find(raw)?.let { match ->
            val hex = match.groupValues[1]
            val argb =
                when (hex.length) {
                    6 -> 0xFF000000L or hex.toLong(16)
                    8 -> {
                        val r = hex.substring(0, 2).toLong(16)
                        val g = hex.substring(2, 4).toLong(16)
                        val b = hex.substring(4, 6).toLong(16)
                        val a = hex.substring(6, 8).toLong(16)
                        (a shl 24) or (r shl 16) or (g shl 8) or b
                    }
                    else -> 0xFF000000L
                }
            return ResolvedValue.ColorValue(argb)
        }
        val match = HSLA_PATTERN.find(raw) ?: HSL_PATTERN.find(raw)
        match?.let {
            val h = it.groupValues[1].toFloatOrNull() ?: 0f
            val s = it.groupValues[2].toFloatOrNull() ?: 0f
            val l = it.groupValues[3].toFloatOrNull() ?: 0f
            val a = it.groupValues.getOrNull(4)?.toFloatOrNull() ?: 1f
            return ResolvedValue.ColorValue(hslaToArgb(h, s / 100f, l / 100f, a))
        }
        return ResolvedValue.RawValue(raw)
    }

    @Suppress("ReturnCount")
    private fun parseDimension(raw: String): ResolvedValue {
        PX_PATTERN.find(raw)?.let { return ResolvedValue.DimensionValue(it.groupValues[1].toFloat()) }
        REM_PATTERN.find(raw)?.let {
            @Suppress("MagicNumber")
            return ResolvedValue.DimensionValue(it.groupValues[1].toFloat() * 16f)
        }
        raw.toFloatOrNull()?.let { return ResolvedValue.DimensionValue(it) }
        return ResolvedValue.RawValue(raw)
    }

    @Suppress("ReturnCount")
    private fun parseOpacity(raw: String): ResolvedValue {
        PERCENT_PATTERN.find(raw)?.let { return ResolvedValue.OpacityValue(it.groupValues[1].toFloat()) }
        raw.toFloatOrNull()?.let {
            @Suppress("MagicNumber")
            return ResolvedValue.OpacityValue(it * 100f)
        }
        return ResolvedValue.RawValue(raw)
    }

    @Suppress("MagicNumber")
    private fun hslaToArgb(
        h: Float,
        s: Float,
        l: Float,
        a: Float,
    ): Long {
        val c = (1f - kotlin.math.abs(2f * l - 1f)) * s
        val hPrime = h / 60f
        val x = c * (1f - kotlin.math.abs(hPrime % 2f - 1f))
        val m = l - c / 2f
        val (r1, g1, b1) =
            when {
                hPrime < 1f -> Triple(c, x, 0f)
                hPrime < 2f -> Triple(x, c, 0f)
                hPrime < 3f -> Triple(0f, c, x)
                hPrime < 4f -> Triple(0f, x, c)
                hPrime < 5f -> Triple(x, 0f, c)
                else -> Triple(c, 0f, x)
            }
        val alpha = (a * 255f).toInt().coerceIn(0, 255).toLong()
        val red = ((r1 + m) * 255f).toInt().coerceIn(0, 255).toLong()
        val green = ((g1 + m) * 255f).toInt().coerceIn(0, 255).toLong()
        val blue = ((b1 + m) * 255f).toInt().coerceIn(0, 255).toLong()
        return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
    }
}

class TokenResolutionException(
    message: String,
) : RuntimeException(message)
