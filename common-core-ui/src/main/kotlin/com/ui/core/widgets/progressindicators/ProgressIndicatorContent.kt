package com.ui.core.widgets.progressindicators

import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextResource

/**
 * Content bundle for the [ProgressIndicator] bar variants.
 *
 * All text fields use [TextResource] for i18n support.
 * The bar renders without text when every field is at its default.
 * For spinner variants these fields are ignored.
 *
 * Example:
 * ```kotlin
 * ProgressIndicator(
 *     config = ProgressIndicatorConfig(variant = ProgressIndicatorConfig.Variant.Bar),
 *     progress = 0.45f,
 *     content = ProgressIndicatorContent(
 *         label = "Downloading…".TR,
 *         progressValue = "45 %".TR,
 *         rangeMin = "0".TR,
 *         rangeMax = "100".TR,
 *     ),
 * )
 * ```
 *
 * @property label optional text displayed above the bar.
 * @property progressValue optional formatted progress value (e.g. "45 %").
 * @property rangeMin optional start-of-range label (e.g. "0").
 * @property rangeMax optional end-of-range label (e.g. "100").
 */
@Immutable
data class ProgressIndicatorContent(
    val label: TextResource = EmptyTextResource,
    val progressValue: TextResource = EmptyTextResource,
    val rangeMin: TextResource = EmptyTextResource,
    val rangeMax: TextResource = EmptyTextResource,
)
