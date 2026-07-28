package com.ui.audi.widgets.scrollbar

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.scrollbar.ScrollbarColors
import com.ui.core.widgets.scrollbar.ScrollbarStyle

/** Audi-brand default [ScrollbarStyle] factory. */
internal object AudiScrollbarDefaults {
    @Composable
    fun style(): ScrollbarStyle =
        ScrollbarStyle(
            scrollbarWidth =
                Cmp.Size.Global.Scrollbar.Width
                    .dimension()
                    .pxToDp(),
            thumbWidth =
                Cmp.Size.Global.Scrollbar.Thumb.Width
                    .dimension()
                    .pxToDp(),
            trackWidth =
                Cmp.Size.Global.Scrollbar.Track.Width
                    .dimension()
                    .pxToDp(),
            colors = scrollbarColors(),
        )

    @Composable
    private fun scrollbarColors(): ScrollbarColors =
        ScrollbarColors(
            thumbColor =
                Cmp.Color.Global.Scrollbar.Thumb
                    .color(),
            trackColor =
                Cmp.Color.Global.Scrollbar.Track
                    .color(),
        )
}
