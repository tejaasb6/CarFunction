package com.ui.core.widgets.progressindicators

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for the brand-provided ProgressIndicator widget.
 *
 * Example:
 * ```kotlin
 * val widget: ProgressIndicatorWidgetContent =
 *     { config, modifier, progress, content ->
 *         // brand implementation
 *     }
 * ```
 */
typealias ProgressIndicatorWidgetContent = @Composable (
    config: ProgressIndicatorConfig,
    modifier: Modifier,
    progress: Float,
    content: ProgressIndicatorContent,
) -> Unit

/**
 * Brand-themed Progress Indicator.
 *
 * Displays a visual feedback element indicating that a process is in progress.
 * Supports circular spinners (determinate and indeterminate) and linear bars
 * (determinate and indeterminate), with optional text labels for the bar
 * variants.
 *
 * Example — determinate spinner:
 * ```kotlin
 * ProgressIndicator(
 *     config = ProgressIndicatorConfig(
 *         variant = ProgressIndicatorConfig.Variant.Spinner,
 *         size = ProgressIndicatorConfig.Size.MD,
 *     ),
 *     progress = 0.6f,
 * )
 * ```
 *
 * Example — indeterminate linear bar with label:
 * ```kotlin
 * ProgressIndicator(
 *     config = ProgressIndicatorConfig(
 *         variant = ProgressIndicatorConfig.Variant.BarInfinite,
 *         theme = ProgressIndicatorConfig.Theme.StaticDark,
 *     ),
 *     content = ProgressIndicatorContent(
 *         label = "Loading…".TR,
 *     ),
 * )
 * ```
 *
 * @param config variant, theme, and size axes.
 * @param modifier caller-supplied modifier.
 * @param progress current progress in 0f..1f. Only used by determinate variants
 *  ([ProgressIndicatorConfig.Variant.Spinner] and [ProgressIndicatorConfig.Variant.Bar]).
 *  Ignored for indeterminate variants.
 * @param content optional text content (label, progress value, range) for bar
 *  variants. Ignored for spinner variants.
 */
@Composable
fun ProgressIndicator(
    config: ProgressIndicatorConfig = ProgressIndicatorConfig(),
    modifier: Modifier = Modifier,
    progress: Float,
    content: ProgressIndicatorContent = ProgressIndicatorContent(),
) {
    LocalWidgets.ProgressIndicator.current(
        config,
        modifier,
        progress,
        content,
    )
}
