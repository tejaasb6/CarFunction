package com.ui.core.indication

import androidx.compose.foundation.Indication
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Sem

/**
 * Returns a brand-aware [Indication] (ripple) calibrated to the widget's background.
 *
 * @param darkBackground `true` for filled button types (Prominent, Destructive, Encourage);
 *                       `false` for outlined / ghost types (Primary, Secondary, Tertiary).
 *                       Use [com.ui.core.widgets.buttons.hasDarkBackground] to resolve this.
 */
@Composable
fun rememberBrandIndication(darkBackground: Boolean): Indication {
    val color =
        if (darkBackground) {
            Sem.Color.StateLayer.Medium.Hover
                .color()
        } else {
            Sem.Color.StateLayer.Bold.Hover
                .color()
        }
    return ripple(color = color)
}
