package com.ui.core.widgets.textinputs

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** Colors for a single state inside a Field branch (Default or Error). */
@Immutable
data class TextInputStateColors(
    val surfaceFill: Color,
    val border: Color,
    val valueColor: Color,
    val placeholderColor: Color,
    val iconColor: Color,
    val unitColor: Color,
    val labelColor: Color,
    val appendixColor: Color,
    val hintColor: Color,
    val errorColor: Color,
)

/** A field branch (Default or Error variant) holds StateLayer color + per-state ranges. */
@Immutable
data class TextInputBranchColors(
    val stateLayerPressed: Color,
    val idle: TextInputStateColors,
    val pressed: TextInputStateColors,
    val active: TextInputStateColors,
    val focused: TextInputStateColors,
    val loading: TextInputStateColors,
    val disabled: TextInputStateColors,
    val readOnly: TextInputStateColors,
)

/** Aggregate that gets passed to the brand widget. */
@Immutable
data class TextInputTypeColors(
    val default: TextInputBranchColors,
    val error: TextInputBranchColors,
)
