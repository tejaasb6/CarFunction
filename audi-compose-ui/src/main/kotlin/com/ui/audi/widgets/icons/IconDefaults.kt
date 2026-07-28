package com.ui.audi.widgets.icons

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.icons.IconStyle

/**
 * Audi default [com.ui.core.widgets.icons.IconStyle] — pulls sizes from
 * `Cmp.Size.DataDisplay.Icon.{SM|MD|LG}` and tint from `Sem.Color.Content.Tertiary`.
 */
internal object IconDefaults {
    /** Builds the Audi [IconStyle] from the active theme tokens. */
    @Composable
    fun style(): IconStyle =
        IconStyle(
            smSize =
                Cmp.Size.DataDisplay.Icon.SM.Height
                    .dimension()
                    .pxToDp(),
            mdSize =
                Cmp.Size.DataDisplay.Icon.MD.Height
                    .dimension()
                    .pxToDp(),
            lgSize =
                Cmp.Size.DataDisplay.Icon.LG.Height
                    .dimension()
                    .pxToDp(),
            tintColor =
                Sem.Color.Content.Tertiary
                    .color(),
            disabledOpacity = Sem.Opacity.Disabled.opacity(),
        )
}
