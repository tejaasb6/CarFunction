package com.ui.audi.widgets.dividers

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.dividers.DividerStyle

/** Audi default [DividerStyle] — resolves colour and thickness from the token engine. */
internal object DividerDefaults {
    /** Builds the Audi [DividerStyle] from the active theme tokens. */
    @Composable
    fun style(): DividerStyle =
        DividerStyle(
            color =
                Cmp.Color.Global.Divider.Surface.Fill
                    .color(),
            horizontalThickness =
                Cmp.Size.Global.Divider.Horizontal.Height
                    .dimension()
                    .pxToDp(),
            verticalThickness =
                Cmp.Size.Global.Divider.Vertical.Width
                    .dimension()
                    .pxToDp(),
        )
}
