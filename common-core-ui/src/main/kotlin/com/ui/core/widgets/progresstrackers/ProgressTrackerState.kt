package com.ui.core.widgets.progresstrackers

import androidx.compose.runtime.Immutable
import com.ui.core.widgets.text.EmptyTextResource
import com.ui.core.widgets.text.TextResource

/**
 * Represents the visual state of a single step inside a [ProgressTracker].
 *
 * The design system specifies six distinct step states:
 * - [Unfinished] — hollow circle; the step has not been reached.
 * - [UnfinishedNumeric] — circle with a step number inside.
 * - [InProgress] — filled circle; the currently active step.
 * - [Finished] — circle containing a checkmark icon.
 * - [Skipped] — circle containing an X icon; the step was deliberately skipped.
 * - [NotPossible] — dimmed circle with X icon;
 *   the step is unreachable in the current flow.
 *
 * ## Example
 * ```kotlin
 * val step = ProgressTrackerStep(
 *     state = StepState.InProgress,
 *     label = "Address".TR,
 * )
 * ```
 */
enum class StepState {
    Unfinished,
    UnfinishedNumeric,
    InProgress,
    Finished,
    Skipped,
    NotPossible,
}

/**
 * Data holder for a single step in the [ProgressTracker].
 *
 * ```kotlin
 * ProgressTrackerStep(
 *     state = StepState.Finished,
 *     label = "Account".TR,
 * )
 * ```
 *
 * @param state  Visual state of this step.
 * @param label  Optional label displayed below the indicator circle.
 *  Uses [TextResource] for i18n support.
 */
@Immutable
data class ProgressTrackerStep(
    val state: StepState = StepState.Unfinished,
    val label: TextResource = EmptyTextResource,
)
