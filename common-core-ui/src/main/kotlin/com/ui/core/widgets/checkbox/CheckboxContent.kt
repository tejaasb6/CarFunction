package com.ui.core.widgets.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.ui.core.R
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.text.TextResource

/**
 * Text and icon content for [Checkbox].
 *
 * All text fields use [TextResource] for i18n support via the `.TR` extension:
 * ```kotlin
 * CheckboxContent(
 *     label = "Accept terms".TR,
 *     hint  = R.string.checkbox_hint.TR,
 *     error = "Validation failed".TR,
 * )
 * ```
 *
 * @property label     Primary label next to the control (truncated if overlapping appendix).
 * @property hint      Supplementary hint text below the label.
 * @property appendix  Additional text at the trailing end of the label row.
 * @property error     Error message shown when [CheckboxState.isError] is `true`.
 * @property errorIcon Optional composable slot for an icon before the error text.
 */
@Immutable
data class CheckboxContent(
    val label: TextResource? = null,
    val hint: TextResource? = null,
    val appendix: TextResource? = null,
    val error: TextResource? = null,
    val errorIcon: (@Composable () -> Unit)? = DefaultCheckboxErrorIcon,
)

/**
 * Default error icon — uses the brand-agnostic [Icon] widget with
 * [IconSource.Resource] so it is rendered by the active brand theme
 * (Audi / Lamborghini) instead of raw material3.
 */
internal val DefaultCheckboxErrorIcon: @Composable () -> Unit = {
    Icon(
        source =
            IconSource.Resource(
                resId = R.drawable.ic_checkbox_warning,
                contentDescription = "Error",
            ),
        config = IconConfig(size = IconConfig.Size.SM, hasTintingColors = false),
    )
}
