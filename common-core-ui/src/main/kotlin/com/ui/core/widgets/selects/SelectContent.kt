package com.ui.core.widgets.selects

import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextResource

/**
 * Text content for the [Select] widget matching Figma text properties.
 *
 * All text fields:
 * - **label** — label text above the select field
 * - **appendix** — optional text next to the label (e.g., "*")
 * - **placeholder** — text shown when no option is selected
 * - **hint** — helper text below the select field
 * - **errorCaption** — error message shown below the hint when in error state
 *
 * ```kotlin
 * Select(
 *     options = options,
 *     selectedValue = selectedValue,
 *     onValueChange = { selectedValue = it },
 *     content = SelectContent(
 *         label = "Country".TR,
 *         placeholder = "Select a country".TR,
 *         hint = "Choose your country of residence".TR,
 *     ),
 * )
 * ```
 */
@Immutable
data class SelectContent(
    /** Label text above the select field. */
    val label: TextResource = EmptyTextResource,
    /** Optional appendix text next to the label (e.g., "*", "optional"). */
    val appendix: TextResource = EmptyTextResource,
    /** Placeholder text shown when no option is selected. */
    val placeholder: TextResource = EmptyTextResource,
    /** Helper hint text below the select field. */
    val hint: TextResource = EmptyTextResource,
    /** Error caption text shown below the hint when [SelectState.error] is `true`. */
    val errorCaption: TextResource = EmptyTextResource,
)
