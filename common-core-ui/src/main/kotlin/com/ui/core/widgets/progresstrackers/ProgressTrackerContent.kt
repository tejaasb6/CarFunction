package com.ui.core.widgets.progresstrackers

import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextResource

/**
 * Text and configuration content for [ProgressTracker].
 *
 * All text fields use [TextResource] for i18n support via the `.TR` extension:
 * ```kotlin
 * ProgressTrackerContent(
 *     title = "Step 2 of 5".TR,
 *     showStepLabels = true,
 *     steps = listOf(
 *         ProgressTrackerStep(StepState.Finished, "Account"),
 *         ProgressTrackerStep(StepState.InProgress, "Address"),
 *         ProgressTrackerStep(StepState.Unfinished, "Payment"),
 *         ProgressTrackerStep(StepState.Unfinished, "Confirm"),
 *     ),
 * )
 * ```
 *
 * @property title          Optional title displayed above the tracker bar.
 * @property showStepLabels When `true`, step labels are rendered below each indicator.
 * @property steps          Ordered list of steps (min 2, max 8 per design spec).
 */
@Immutable
data class ProgressTrackerContent(
    val title: TextResource = EmptyTextResource,
    val showStepLabels: Boolean = false,
    val steps: List<ProgressTrackerStep> = emptyList(),
)
