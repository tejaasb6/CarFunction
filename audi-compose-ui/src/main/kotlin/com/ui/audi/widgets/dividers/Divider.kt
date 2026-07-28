package com.ui.audi.widgets.dividers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.dividers.DividerConfig
import com.ui.core.widgets.dividers.LocalDividerStyle

/** Audi-themed brand impl of [com.ui.core.widgets.dividers.Divider]. */
@Composable
internal fun Divider(
    config: DividerConfig,
    modifier: Modifier = Modifier,
) {
    val style = LocalDividerStyle.current

    when (config.orientation) {
        DividerConfig.Orientation.Horizontal -> {
            Box(
                modifier =
                    modifier
                        .padding(horizontal = config.padding)
                        .fillMaxWidth()
                        .height(style.horizontalThickness)
                        .background(style.color),
            )
        }
        DividerConfig.Orientation.Vertical -> {
            Box(
                modifier =
                    modifier
                        .padding(vertical = config.padding)
                        .fillMaxHeight()
                        .width(style.verticalThickness)
                        .background(style.color),
            )
        }
    }
}
