package com.ui.core.engine.api

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Brand-agnostic representation of a resolved boxShadow token.
 *
 * Each shadow token may contain multiple [layers] (e.g. Level2 has two drop-shadow
 * layers with different blur/spread/offset values). The consumer can use [elevation]
 * as a convenient scalar for `graphicsLayer(shadowElevation = …)`.
 *
 * @property layers individual drop-shadow layers resolved from the design token.
 * @property elevation convenience scalar — the maximum Y-offset across all layers (in px).
 */
@Immutable
data class BoxShadowData(
    val layers: List<ShadowLayer>,
    val elevation: Float,
) {
    companion object {
        /** Empty shadow with no layers and zero elevation. */
        val None = BoxShadowData(layers = emptyList(), elevation = 0f)
    }
}

/**
 * A single drop-shadow layer within a [BoxShadowData].
 *
 * @property x      horizontal offset in px.
 * @property y      vertical offset in px.
 * @property blur   blur radius in px.
 * @property spread spread radius in px.
 * @property color  resolved shadow colour.
 */
@Immutable
data class ShadowLayer(
    val x: Float,
    val y: Float,
    val blur: Float,
    val spread: Float,
    val color: Color,
)
