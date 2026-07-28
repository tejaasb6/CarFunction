package com.ui.core.widgets.segmentedcontrols

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.ui.core.widgets.buttons.ButtonStyle

/**
 * Token-derived style values for [SegmentedControl].
 *
 * Assembled once inside the brand theme's defaults object and provided via
 * [LocalSegmentedControlStyle].
 *
 * Each segment is rendered using [com.ui.core.widgets.buttons.Button] internally.
 * The [buttonStyle] field controls the appearance of every segment unit. Consumers
 * can override it via the settings sheet or at runtime to tweak corner radius,
 * padding, spacing, and colours without touching the wrapper container.
 *
 * ```kotlin
 * val style = LocalSegmentedControlStyle.current
 * // Override segment corner radius:
 * val tweaked = style.copy(
 *     buttonStyle = style.buttonStyle.copy(cornerRadius = 12.dp),
 * )
 * ```
 */
@Immutable
data class SegmentedControlStyle(
    /** Corner radius of the outer wrapper. */
    val wrapperCornerRadius: Dp,
    /** Border width of the outer wrapper. */
    val wrapperBorderWidth: Dp,
    /** Height of the outer wrapper. */
    val wrapperHeight: Dp,
    /** Gap between segment units inside the wrapper. */
    val wrapperGap: Dp,
    /** Internal padding inside the wrapper container around the segment units. */
    val wrapperPadding: Dp,
    /** Bottom padding below the optional title label. */
    val titleBottomPadding: Dp,
    /** TextStyle for the optional title label above the control. */
    val titleTextStyle: TextStyle,
    /** Container / wrapper colours. */
    val containerColors: SegmentedControlContainerColors,
    /**
     * [ButtonStyle] used by every segment unit rendered as a [Button].
     *
     * Built from SegmentedControl design tokens by the brand defaults object.
     * All six tone slots carry the same colour set because SegmentedControl
     * has no tone axis — the brand impl uses [com.ui.core.widgets.buttons.ButtonConfig.Tone.Primary].
     *
     * Consumers can `.copy()` this to tweak individual properties (corner radius,
     * padding, icon spacing, text style, or even per-state colours).
     */
    val buttonStyle: ButtonStyle,
    /**
     * Typography for the **selected** segment label.
     *
     * Sourced from `Cmp.Typography.Action.SegmentedControl.Unit.Selected.Label`.
     * Applied directly to the label Text inside the selected segment, overriding
     * the default [buttonStyle.textStyle] which uses the unselected typography.
     */
    val selectedTextStyle: TextStyle = TextStyle.Default,
)

/**
 * Colour set for the outer container / wrapper of the entire segmented control.
 */
@Immutable
data class SegmentedControlContainerColors(
    /** Fill colour for the wrapper container. */
    val surfaceFill: Color,
    /** Stroke colour for the wrapper container border. */
    val surfaceStroke: Color,
    /** Colour for the optional title label placed above the control. */
    val titleLabelColor: Color,
)

/**
 * Composition-local that carries the brand-resolved [SegmentedControlStyle].
 */
val LocalSegmentedControlStyle =
    compositionLocalOf<SegmentedControlStyle> {
        error("No SegmentedControlStyle — wrap content in a brand theme")
    }
