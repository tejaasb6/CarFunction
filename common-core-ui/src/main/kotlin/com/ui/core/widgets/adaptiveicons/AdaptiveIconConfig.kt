package com.ui.core.widgets.adaptiveicons

import androidx.compose.runtime.Immutable
import com.ui.core.widgets.icons.IconConfig

/**
 * Configuration for [AdaptiveIcon].
 *
 * @property shape the shape of the background container.
 * @property iconSize the size class passed to the internal [com.ui.core.widgets.icons.Icon].
 */
@Immutable
data class AdaptiveIconConfig(
    val shape: Shape = Shape.Circle,
    val iconSize: IconConfig.Size = IconConfig.Size.MD,
) {
    /** Shape of the adaptive icon background container. */
    enum class Shape {
        Circle,
        RoundedSquare,
    }
}
