package com.ui.core.engine.api

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * Contract between [com.ui.core.engine.runtime.DesignTokenManager] and a
 * brand-specific token resolution implementation.
 *
 * Each brand lib (audi-compose-ui, lamborghini-compose-ui, …) provides its own
 * implementation (e.g. TokenCache) that knows how to parse and resolve its JSON
 * format. common-core-ui has no compile-time dependency on any parser or model.
 */
interface TokenStore {
    fun color(path: String): Color

    fun dimension(path: String): Float

    fun font(path: String): Float

    fun opacity(path: String): Float

    fun string(path: String): String

    fun boolean(path: String): Boolean

    fun typography(path: String): TextStyle

    /** Resolve a boxShadow token to a [BoxShadowData] with layers and elevation. */
    fun boxShadow(path: String): BoxShadowData
}
