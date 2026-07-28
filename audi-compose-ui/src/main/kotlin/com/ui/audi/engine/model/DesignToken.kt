package com.ui.audi.engine.model

import androidx.compose.ui.text.TextStyle

data class DesignToken(
    val path: String,
    val rawValue: String,
    val type: String,
    val description: String? = null,
)

data class TypographyDesignToken(
    val path: String,
    val fontFamily: String,
    val fontWeight: String,
    val fontSize: String,
    val lineHeight: String,
    val letterSpacing: String,
    val textDecoration: String,
    val description: String? = null,
)

data class ShadowValue(
    val x: String,
    val y: String,
    val blur: String,
    val spread: String,
    val color: String,
    val type: String,
)

data class ShadowDesignToken(
    val path: String,
    val layers: List<ShadowValue>,
    val description: String? = null,
)

sealed class ResolvedValue {
    data class ColorValue(
        val argb: Long,
    ) : ResolvedValue()

    data class DimensionValue(
        val px: Float,
    ) : ResolvedValue()

    data class OpacityValue(
        val percent: Float,
    ) : ResolvedValue()

    data class BooleanValue(
        val value: Boolean,
    ) : ResolvedValue()

    data class StringValue(
        val value: String,
    ) : ResolvedValue()

    data class RawValue(
        val value: String,
    ) : ResolvedValue()

    data class TextStyleValue(
        val value: TextStyle,
    ) : ResolvedValue()
}

data class ParsedTokenData(
    val tokenMap: Map<String, DesignToken>,
    val typographyTokens: Map<String, TypographyDesignToken>,
    val shadowTokens: Map<String, ShadowDesignToken>,
)
