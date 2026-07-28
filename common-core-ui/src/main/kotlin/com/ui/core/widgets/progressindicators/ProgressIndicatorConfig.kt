package com.ui.core.widgets.progressindicators

import androidx.compose.runtime.Immutable

/**
 * Descriptor for [ProgressIndicator]: selects the visual variant, theme, and
 * size axes that drive the widget's appearance.
 *
 * Example:
 * ```kotlin
 * ProgressIndicator(
 *     config = ProgressIndicatorConfig(
 *         variant = ProgressIndicatorConfig.Variant.Spinner,
 *         theme = ProgressIndicatorConfig.Theme.Dynamic,
 *         size = ProgressIndicatorConfig.Size.MD,
 *     ),
 *     progress = 0.6f,
 * )
 * ```
 *
 * @property variant the visual shape — circular spinner or linear bar.
 * @property theme colour scheme — adapts to surface ([Theme.Dynamic]) or always
 *  dark ([Theme.StaticDark]).
 * @property size spinner diameter axis — applies only to [Variant.Spinner] and
 *  [Variant.SpinnerInfinite]. Ignored for bar variants.
 */
@Immutable
data class ProgressIndicatorConfig(
    val variant: Variant = Variant.Spinner,
    val theme: Theme = Theme.Dynamic,
    val size: Size = Size.MD,
) {
    /**
     * Shape variant of the progress indicator.
     *
     * Example:
     * ```kotlin
     * ProgressIndicatorConfig(variant = ProgressIndicatorConfig.Variant.Bar)
     * ```
     */
    enum class Variant {
        /** Circular determinate spinner — shows a percentage arc. */
        Spinner,

        /** Circular indeterminate spinner — continuous rotating animation. */
        SpinnerInfinite,

        /** Linear determinate bar — fills left-to-right with optional labels. */
        Bar,

        /** Linear indeterminate bar — continuous forward/backward animation. */
        BarInfinite,
    }

    /**
     * Colour-theme axis.
     *
     * Example:
     * ```kotlin
     * ProgressIndicatorConfig(theme = ProgressIndicatorConfig.Theme.StaticDark)
     * ```
     */
    enum class Theme {
        /** Adapts to the current surface (light or dark). */
        Dynamic,

        /** Always uses the inverted (dark-on-light / light-on-dark) palette. */
        StaticDark,
    }

    /**
     * Size axis — controls the spinner diameter.
     *
     * Example:
     * ```kotlin
     * ProgressIndicatorConfig(size = ProgressIndicatorConfig.Size.SM)
     * ```
     */
    enum class Size {
        /** Small spinner. */
        SM,

        /** Medium spinner (default). */
        MD,
    }
}
