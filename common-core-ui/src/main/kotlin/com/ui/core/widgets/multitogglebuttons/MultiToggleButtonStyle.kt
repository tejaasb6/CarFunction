package com.ui.core.widgets.multitogglebuttons

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.ui.core.widgets.buttons.ButtonStyle
import com.ui.core.widgets.iconbuttons.IconButtonStyle

/**
 * Token-derived style for [MultiToggleButton].
 *
 * Controls indicator dimensions and per-variant indicator colours.
 * The button surface itself is styled via the existing [com.ui.core.widgets.buttons.ButtonStyle].
 *
 * ## Label mode (Hug / Fill)
 * Uses linear indicator bars (width × height) with [indicatorCornerRadius].
 *
 * ## Icon mode
 * Uses curved arc indicators drawn inside the circular button perimeter.
 * Arc thickness = [indicatorHeight], gap between arcs = [indicatorGap].
 * The arc radius is derived from [iconModeStateLayerSize] at render time.
 *
 * ## Style overrides
 * Consumers can override [buttonStyleOverride] and [iconButtonStyleOverride]
 * to customise the underlying Button / IconButton styling (e.g. gap between
 * icon button and label, corner radius, padding, colours, etc.).
 * When `null`, the brand-default styles from the composition locals are used.
 */
@Immutable
data class MultiToggleButtonStyle(
    /** Width of each indicator bar. */
    val indicatorWidth: Dp,
    /** Height of each indicator bar (also used as arc stroke thickness in Icon mode). */
    val indicatorHeight: Dp,
    /** Corner radius of each indicator bar. */
    val indicatorCornerRadius: Dp,
    /** Border width of each indicator bar. */
    val indicatorBorderWidth: Dp,
    /** Gap between indicator bars/arcs. */
    val indicatorGap: Dp,
    /** Vertical spacing between content (label/icon) and the indicator row. */
    val contentToIndicatorSpacing: Dp,
    /** Minimum width of the label-mode button surface. */
    val labelModeMinWidth: Dp,
    /** Minimum height of the label-mode button surface. */
    val labelModeMinHeight: Dp,
    /** Horizontal padding inside the label-mode button surface. */
    val labelModeHorizontalPadding: Dp,
    /** Typography for the label text in label mode when selected. */
    val labelModeTypography: TextStyle,
    /** Typography for the label text in label mode when unselected. */
    val labelModeTypographyUnselected: TextStyle,
    /** Corner radius for the label-mode button surface. */
    val labelModeCornerRadius: Dp,
    /** Border width when unselected + idle. */
    val unselectedBorderWidthIdle: Dp,
    /** Border width when unselected + pressed. */
    val unselectedBorderWidthPressed: Dp,
    /** Border width when unselected + disabled. */
    val unselectedBorderWidthDisabled: Dp,
    /** Border width when selected + idle. */
    val selectedBorderWidthIdle: Dp,
    /** Border width when selected + pressed. */
    val selectedBorderWidthPressed: Dp,
    /** Border width when selected + disabled. */
    val selectedBorderWidthDisabled: Dp,
    /** State layer diameter of the icon-mode button (used to derive arc radius). */
    val iconModeStateLayerSize: Dp,
    /** Touch target diameter of the icon-mode button. */
    val iconModeTouchTarget: Dp,
    /** Typography for the icon-mode label text when selected. */
    val iconModeLabelTypography: TextStyle,
    /** Typography for the icon-mode label text when unselected. */
    val iconModeLabelTypographyUnselected: TextStyle,
    /** Label color for the icon-mode label when selected. */
    val iconModeLabelColor: Color,
    /** Label color for the icon-mode label when unselected. */
    val iconModeLabelColorUnselected: Color,
    /** Opacity applied to the entire widget when disabled. */
    val disabledOpacity: Float,
    /** Width of the focus ring drawn around the focused button. */
    val focusRingWidth: Dp,
    /** Color of the focus ring stroke. */
    val focusRingColor: Color,
    /** Gap between the focus ring and the button border. */
    val focusRingGap: Dp,
    /** Indicator colours for [MultiToggleButtonConfig.Variant.Default]. */
    val defaultColors: MultiToggleButtonIndicatorColors,
    /** Indicator colours for [MultiToggleButtonConfig.Variant.Heating]. */
    val heatingColors: MultiToggleButtonIndicatorColors,
    /** Indicator colours for [MultiToggleButtonConfig.Variant.Cooling]. */
    val coolingColors: MultiToggleButtonIndicatorColors,
    /**
     * Optional override for the [ButtonStyle] used in Label Hug / Fill modes.
     * When `null`, the brand-default [com.ui.core.widgets.buttons.LocalButtonStyle] is used.
     */
    val buttonStyleOverride: ButtonStyle? = null,
    /**
     * Optional override for the [IconButtonStyle] used in Icon mode.
     * Consumers can set [IconButtonStyle.gap] here to control the spacing
     * between the icon button circle and the label below it.
     * When `null`, the brand-default [com.ui.core.widgets.iconbuttons.LocalIconButtonStyle] is used.
     */
    val iconButtonStyleOverride: IconButtonStyle? = null,
)

/** Resolves the [MultiToggleButtonIndicatorColors] for the given [variant]. */
fun MultiToggleButtonStyle.indicatorColorsForVariant(variant: MultiToggleButtonConfig.Variant): MultiToggleButtonIndicatorColors =
    when (variant) {
        MultiToggleButtonConfig.Variant.Default -> defaultColors
        MultiToggleButtonConfig.Variant.Heating -> heatingColors
        MultiToggleButtonConfig.Variant.Cooling -> coolingColors
    }

/** Composition-local carrying the brand-resolved [MultiToggleButtonStyle]. */
val LocalMultiToggleButtonStyle =
    compositionLocalOf<MultiToggleButtonStyle> {
        error("No MultiToggleButtonStyle — wrap content in a brand theme")
    }
