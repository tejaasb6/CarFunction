package com.ui.core.widgets.buttons

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ButtonStateColors(
    val labelColor: Color,
    val iconColor: Color,
    val border: Color,
)

@Immutable
data class ButtonBranchColors(
    val surfaceFill: Color,
    val stateLayerPressed: Color,
    val idle: ButtonStateColors,
    val pressed: ButtonStateColors,
    val loading: ButtonStateColors,
    val disabled: ButtonStateColors,
)

@Immutable
data class ButtonTypeColors(
    val unselected: ButtonBranchColors,
    val selected: ButtonBranchColors,
    val loadingIndicator: Color,
    val loadingTrackColour: Color,
)
