package com.ui.core.engine.api

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.ui.core.engine.runtime.DesignTokenManager

/**
 * Base accessor that wraps a token path and provides type-safe value retrieval.
 *
 * Developers never write string paths directly. Instead they navigate the object
 * hierarchy and call a typed terminal method:
 *
 * ```kotlin
 * val bg = Sem.Color.Fill.Canvas.color()
 * val radius = Sem.BorderRadius.MD.dp()
 * ```
 */
open class ThemeTokenAccessor(
    val tokenPath: String,
) {
    /** Resolve this token as a Compose [Color]. */
    fun color(): Color = DesignTokenManager.color(tokenPath)

    /** Resolve this token as a [Dp] dimension. */
    fun dimension(): Float = DesignTokenManager.dimension(tokenPath)

    /** Resolve this token as a [TextUnit] (sp) for font sizing. */
    fun font(): Float = DesignTokenManager.font(tokenPath)

    /** Resolve this token as an opacity float (0f..1f). */
    fun opacity(): Float = DesignTokenManager.opacity(tokenPath)

    /** Resolve this token as a raw string value. */
    fun string(): String = DesignTokenManager.string(tokenPath)

    /** Resolve this token as a boolean. */
    fun boolean(): Boolean = DesignTokenManager.boolean(tokenPath)

    fun typography(): TextStyle = DesignTokenManager.typography(tokenPath)

    /** Resolve this token as a [BoxShadowData] with shadow layers and elevation. */
    fun boxShadow(): BoxShadowData = DesignTokenManager.boxShadow(tokenPath)

    override fun toString(): String = "ThemeToken($tokenPath)"
}
