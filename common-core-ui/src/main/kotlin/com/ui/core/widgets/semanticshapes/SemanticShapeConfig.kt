package com.ui.core.widgets.semanticshapes

import androidx.compose.runtime.Immutable

/**
 * Describes the semantic variant of a [SemanticShape].
 *
 * Each variant conveys a specific status meaning, visually differentiated
 * by shape geometry **and** fill colour — ensuring accessibility beyond
 * colour alone.
 *
 * ## Variants
 * | Variant       | Shape         | Purpose                                  |
 * |---------------|---------------|------------------------------------------|
 * | [Variant.Neutral]     | Circle (●)    | Neutral / default status.               |
 * | [Variant.Informative] | Circle (●)    | Informational status.                   |
 * | [Variant.Positive]    | Circle (●)    | Success / positive status.              |
 * | [Variant.Advisory]    | Diamond (◆)   | Advisory / attention-needed status.     |
 * | [Variant.Warning]     | Triangle (▽)  | Warning status — inverted triangle.     |
 * | [Variant.Critical]    | Triangle (△)  | Critical / error status — upward triangle. |
 *
 * Example:
 * ```kotlin
 * SemanticShape(
 *     config = SemanticShapeConfig(variant = SemanticShapeConfig.Variant.Critical),
 * )
 * ```
 *
 * @property variant the semantic status variant controlling shape and colour.
 */
@Immutable
data class SemanticShapeConfig(
    val variant: Variant = Variant.Neutral,
) {
    /**
     * Semantic status variant of the shape indicator.
     *
     * The shape geometry changes per variant to ensure accessibility:
     * - **Circle**: Neutral, Informative, Positive
     * - **Diamond** (rotated square): Advisory
     * - **Inverted Triangle**: Warning
     * - **Upward Triangle**: Critical
     */
    enum class Variant {
        /** Neutral / default — rendered as a filled circle. */
        Neutral,

        /** Informational — rendered as a filled circle with informative colour. */
        Informative,

        /** Success / positive — rendered as a filled circle with positive colour. */
        Positive,

        /** Advisory / attention-needed — rendered as a filled diamond. */
        Advisory,

        /** Warning — rendered as a filled inverted triangle. */
        Warning,

        /** Critical / error — rendered as a filled upward triangle. */
        Critical,
    }
}
