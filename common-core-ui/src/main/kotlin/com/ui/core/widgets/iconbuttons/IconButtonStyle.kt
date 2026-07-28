package com.ui.core.widgets.iconbuttons

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Per-state colours for one interactive state of an [IconButton].
 *
 * @property contentColor colour applied to the icon (and spinner in loading state).
 * @property surfaceFill background fill of the circular container.
 * @property border border stroke colour of the container.
 */
@Immutable
data class IconButtonStateColors(
    val contentColor: Color,
    val surfaceFill: Color = Color.Transparent,
    val border: Color = Color.Transparent,
)

/**
 * Colours for all interactive states within one selection branch
 * (selected or unselected) of a single tone.
 *
 * @property surfaceFill resting background fill of the circular container.
 * @property stateLayerPressed overlay applied on press.
 * @property idle colours in the default resting state.
 * @property pressed colours while the button is actively pressed.
 * @property loading colours when the button shows a loading spinner.
 * @property disabled colours when the button is non-interactive.
 */
@Immutable
data class IconButtonBranchColors(
    val surfaceFill: Color = Color.Transparent,
    val stateLayerPressed: Color = Color.Transparent,
    val idle: IconButtonStateColors,
    val pressed: IconButtonStateColors,
    val loading: IconButtonStateColors,
    val disabled: IconButtonStateColors,
)

/**
 * Full colour specification for one [IconButtonConfig.Tone], covering both
 * selected and unselected branches plus loading indicator colours.
 */
@Immutable
data class IconButtonTypeColors(
    val unselected: IconButtonBranchColors,
    val selected: IconButtonBranchColors,
    val loadingIndicator: Color,
    val loadingTrackColour: Color,
)

/**
 * Per-state label colours shared across all tones.
 *
 * Label text sits **outside** the filled icon container, so it must use
 * its own colour tokens (`Cmp.Color.Action.IconButton.{Un}selected.Content.Label.*`)
 * rather than the in-container icon/content colour.
 */
@Immutable
data class IconButtonLabelColors(
    val idle: Color,
    val pressed: Color,
    val loading: Color,
    val disabled: Color,
)

/**
 * Full visual specification for [IconButton].
 *
 * **Dimensions and typography** apply uniformly across all tones.
 * **Per-tone colour sets** hold all interactive-state colours.
 */
@Immutable
data class IconButtonStyle(
    val cornerRadius: Dp,
    val touchTarget: Dp,
    val stateLayerHeight: Dp,
    val stateLayerWidth: Dp,
    val gap: Dp,
    val unselectedLabelStyle: TextStyle,
    val selectedLabelStyle: TextStyle,
    val disabledOpacity: Float,
    val prominent: IconButtonTypeColors,
    val primary: IconButtonTypeColors,
    val secondary: IconButtonTypeColors,
    val tertiary: IconButtonTypeColors,
    val destructive: IconButtonTypeColors,
    val unselectedLabelColors: IconButtonLabelColors,
    val selectedLabelColors: IconButtonLabelColors,
)

/**
 * Returns the [IconButtonTypeColors] corresponding to the given [tone].
 */
fun IconButtonStyle.colorsForTone(tone: IconButtonConfig.Tone): IconButtonTypeColors =
    when (tone) {
        IconButtonConfig.Tone.Prominent -> prominent
        IconButtonConfig.Tone.Primary -> primary
        IconButtonConfig.Tone.Secondary -> secondary
        IconButtonConfig.Tone.Tertiary -> tertiary
        IconButtonConfig.Tone.Destructive -> destructive
    }

/**
 * Resolves the correct label colour for the current interactive state.
 *
 * @param labelColors the [IconButtonLabelColors] for the active selection branch.
 * @param enabled whether the button is interactive.
 * @param isPressed whether the button is currently pressed.
 * @param isLoading whether the button is in a loading state.
 */
fun resolveLabelColor(
    labelColors: IconButtonLabelColors,
    enabled: Boolean,
    isPressed: Boolean,
    isLoading: Boolean,
): Color =
    when {
        !enabled -> labelColors.disabled
        isLoading -> labelColors.loading
        isPressed -> labelColors.pressed
        else -> labelColors.idle
    }

val LocalIconButtonStyle =
    compositionLocalOf<IconButtonStyle> {
        error("No IconButtonStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
