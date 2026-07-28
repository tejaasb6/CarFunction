package com.ui.core.widgets.toggleswitch

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Per-state colour set for one toggle-switch selection state (on / off) × interaction state.
 *
 * Each property describes a single visual attribute that varies with the current
 * combination of **selection** (selected / unselected) and **interaction**
 * (idle / pressed / disabled).
 *
 * Instances are created by brand-specific defaults
 * (e.g. [com.ui.audi.widgets.toggleswitch.ToggleSwitchDefaults]) and bundled
 * inside [ToggleSwitchStyle].
 *
 * @property trackFill        Background fill of the track.
 * @property trackStroke      Stroke colour of the track border.
 * @property trackStrokeWidth Stroke width of the track border.
 * @property handleFill       Fill colour of the thumb / handle.
 * @property iconTint         Tint for the IEC thumb icons (○ / I).
 * @property stateLayerColor  Pressed overlay colour on the track.
 * @property labelColor       Colour of the primary label text.
 * @property hintColor        Colour of the supplementary hint text.
 */
@Immutable
data class ToggleSwitchStateColors(
    val trackFill: Color,
    val trackStroke: Color,
    val trackStrokeWidth: Dp,
    val handleFill: Color,
    val iconTint: Color,
    val stateLayerColor: Color,
    val labelColor: Color,
    val hintColor: Color,
)

/**
 * Full visual specification for [ToggleSwitch].
 *
 * All dimension, typography, opacity, and per-state colour tokens required to
 * render a toggle switch are collected here so that brand themes can supply a
 * single style object via [LocalToggleSwitchStyle].
 *
 * **Track dimensions** are not stored; they are derived at the usage site from
 * [handleWidth], [handleHeight], [horizontalPadding], and [verticalPadding]:
 * - **trackWidth**  = `handleWidth × 2 + horizontalPadding × 2`
 * - **trackHeight** = `handleHeight + verticalPadding × 2`
 *
 * @property trackCornerRadius   Corner radius of the track (fully round).
 * @property handleWidth         Width of the thumb / handle.
 * @property handleHeight        Height of the thumb / handle.
 * @property verticalPadding     Vertical inner padding between track edge and handle.
 * @property horizontalPadding   Horizontal inner padding between track edge and handle.
 * @property touchTargetSize     Minimum touch-target size.
 * @property controlLabelSpacing Spacing between control and label text.
 * @property captionEndPadding   Trailing (end) padding of the caption text area.
 * @property hintSpacing         Vertical spacing between label and hint.
 * @property paddingTop          Top padding applied when a label is present.
 * @property minHeight           Minimum height of the overall toggle-switch row.
 * @property labelTextStyle      Typography for the label.
 * @property hintTextStyle       Typography for the hint.
 * @property disabledOpacity     Opacity applied to disabled surface and label.
 * @property spinnerSize         Size of the brand-specific loading spinner.
 * @property spinnerStrokeWidth  Stroke width of the loading spinner.
 * @property spinnerTrackColor   Track / background colour of the loading spinner.
 * @property unselectedIdle      Colours for off + idle.
 * @property unselectedPressed   Colours for off + pressed.
 * @property unselectedDisabled  Colours for off + disabled.
 * @property selectedIdle        Colours for on + idle.
 * @property selectedPressed     Colours for on + pressed.
 * @property selectedDisabled    Colours for on + disabled.
 */
@Immutable
data class ToggleSwitchStyle(
    // ── Dimensions ──────────────────────────────────────────────────────────
    val trackCornerRadius: Dp,
    val handleWidth: Dp,
    val handleHeight: Dp,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
    val touchTargetSize: Dp,
    val controlLabelSpacing: Dp,
    val captionEndPadding: Dp,
    val hintSpacing: Dp,
    val paddingTop: Dp,
    val minHeight: Dp,
    // ── Typography ──────────────────────────────────────────────────────────
    val labelTextStyle: TextStyle,
    val hintTextStyle: TextStyle,
    // ── Disabled opacity ────────────────────────────────────────────────────
    val disabledOpacity: Float,
    // ── Loading spinner ─────────────────────────────────────────────────────
    val spinnerSize: Dp,
    val spinnerStrokeWidth: Dp,
    val spinnerTrackColor: Color,
    // ── Per-state colour sets ───────────────────────────────────────────────
    val unselectedIdle: ToggleSwitchStateColors,
    val unselectedPressed: ToggleSwitchStateColors,
    val unselectedDisabled: ToggleSwitchStateColors,
    val selectedIdle: ToggleSwitchStateColors,
    val selectedPressed: ToggleSwitchStateColors,
    val selectedDisabled: ToggleSwitchStateColors,
)

/**
 * Composition local for [ToggleSwitchStyle].
 *
 * Provided by brand themes (e.g. [com.ui.audi.AudiTheme]).
 * Throws if accessed outside a theme scope.
 */
val LocalToggleSwitchStyle =
    compositionLocalOf<ToggleSwitchStyle> {
        error("No ToggleSwitchStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
