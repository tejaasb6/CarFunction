package com.ui.core.widgets.textinputs

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
data class TextInputStyle(
    val cornerRadius: Dp,
    val borderWidth: Dp,
    val minHeight: Dp,
    val touchTargetHeight: Dp,
    val paddingHorizontal: Dp,
    val iconSpacing: Dp,
    val labelGap: Dp,
    val labelItemSpacing: Dp,
    val captionGap: Dp,
    val trailingExtensionRPadding: Dp,
    val labelTextStyle: TextStyle,
    val appendixTextStyle: TextStyle,
    val valueTextStyle: TextStyle,
    val placeholderTextStyle: TextStyle,
    val unitTextStyle: TextStyle,
    val captionTextStyle: TextStyle,
    val captionErrorTextStyle: TextStyle,
    val strengthTextStyle: TextStyle,
    val cursorHeight: Dp,
    val cursorWidth: Dp,
    val cursorColor: Color,
    val spinnerTrackColor: Color,
    val spinnerTrainColor: Color,
    val spinnerStrokeWidth: Dp,
    val spinnerSize: Dp,
    val colors: TextInputTypeColors,
)

fun TextInputStyle.branchFor(isError: Boolean): TextInputBranchColors = if (isError) colors.error else colors.default

fun TextInputBranchColors.stateFor(
    enabled: Boolean,
    isLoading: Boolean,
    isReadOnly: Boolean,
    isPressed: Boolean,
    isActive: Boolean,
    isFocused: Boolean,
): TextInputStateColors =
    when {
        !enabled -> disabled
        isLoading -> loading
        isReadOnly -> readOnly
        isPressed -> pressed
        isActive -> active
        isFocused -> focused
        else -> idle
    }

val LocalTextInputStyle =
    compositionLocalOf<TextInputStyle> {
        error("No TextInputStyle — wrap content in a brand theme")
    }
