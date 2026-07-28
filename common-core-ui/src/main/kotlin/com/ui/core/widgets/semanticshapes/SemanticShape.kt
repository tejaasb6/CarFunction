package com.ui.core.widgets.semanticshapes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for a brand-themed SemanticShape widget.
 *
 * Brand implementations must match this signature exactly. The public
 * [SemanticShape] composable delegates to the brand lambda registered in
 * [LocalWidgets.SemanticShape].
 */
typealias SemanticShapeWidgetContent = @Composable (
    config: SemanticShapeConfig,
    modifier: Modifier,
) -> Unit

/**
 * Brand-agnostic Semantic Shape — a small, contextual UI element used to convey
 * status through **both** colour and shape geometry.
 *
 * Six variants are supported, each with a distinct shape to ensure accessibility
 * beyond colour alone:
 * - **Neutral / Informative / Positive** → filled circle (●)
 * - **Advisory** → filled diamond (◆)
 * - **Warning** → filled inverted triangle (▽)
 * - **Critical** → filled upward triangle (△)
 *
 * ## Basic usage
 * ```kotlin
 * SemanticShape(
 *     config = SemanticShapeConfig(variant = SemanticShapeConfig.Variant.Positive),
 * )
 * ```
 *
 * @param config variant configuration — controls shape and colour.
 * @param modifier applied to the outermost layout node.
 */
@Composable
fun SemanticShape(
    config: SemanticShapeConfig = SemanticShapeConfig(),
    modifier: Modifier = Modifier,
) {
    LocalWidgets.SemanticShape.current(config, modifier)
}
