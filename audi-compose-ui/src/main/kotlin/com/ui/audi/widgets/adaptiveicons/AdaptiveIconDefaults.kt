package com.ui.audi.widgets.adaptiveicons

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ui.core.engine.api.Sem
import com.ui.core.widgets.adaptiveicons.AdaptiveIconStyle

/** Audi default [AdaptiveIconStyle]. */
internal object AdaptiveIconDefaults {
    @Composable
    fun style(): AdaptiveIconStyle =
        AdaptiveIconStyle(
            containerSize = 56.dp,
            iconSize = 24.dp,
            backgroundColor =
                Sem.Color.Fill.Primary
                    .color(),
            cornerRadius = 12.dp,
            borderColor =
                Sem.Color.Stroke.Medium
                    .color(),
            borderWidth = 1.dp,
        )
}
