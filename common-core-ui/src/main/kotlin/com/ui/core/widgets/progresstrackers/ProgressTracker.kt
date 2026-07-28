package com.ui.core.widgets.progresstrackers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Composable function type for the brand-specific progress tracker widget.
 */
typealias ProgressTrackerWidgetContent = @Composable (
    modifier: Modifier,
    content: ProgressTrackerContent,
) -> Unit

/**
 * Brand-agnostic progress tracker — the **single public API**.
 *
 * A progress tracker is a **non-interactive** feedback component used in
 * setup wizards or explain wizards to display the user's current progress
 * within a multi-step process.
 *
 * **Mandatory elements:** Step indicator circles, connector lines.
 *
 * **Optional elements:**
 *  - **title** — single-line label above the tracker bar.
 *  - **step labels** — text labels below each indicator circle.
 *
 * Each step can be in one of six states:
 *  - [StepState.Unfinished] — hollow circle.
 *  - [StepState.UnfinishedNumeric] — circle with step number.
 *  - [StepState.InProgress] — larger filled circle (current step).
 *  - [StepState.Finished] — circle with checkmark icon.
 *  - [StepState.Skipped] — circle with X icon.
 *  - [StepState.NotPossible] — dimmed circle with dashed connector.
 *
 * Maximum of **8 steps** are allowed per design spec.
 *
 * ## Layout adaptations
 * - **RTL:** Steps are mirrored; progress starts from the right.
 *   Handled automatically by Compose [Row] layout reversal.
 * - **RHD (Right-Hand Drive):** No adaptation.
 *
 * ## Example
 * ```kotlin
 * ProgressTracker(
 *     content = ProgressTrackerContent(
 *         title = "Setup Wizard".TR,
 *         showStepLabels = true,
 *         steps = listOf(
 *             ProgressTrackerStep(StepState.Finished, "Account"),
 *             ProgressTrackerStep(StepState.InProgress, "Address"),
 *             ProgressTrackerStep(StepState.Unfinished, "Payment"),
 *             ProgressTrackerStep(StepState.Unfinished, "Confirm"),
 *         ),
 *     ),
 * )
 * ```
 */
@Composable
fun ProgressTracker(
    modifier: Modifier = Modifier,
    content: ProgressTrackerContent = ProgressTrackerContent(),
) {
    LocalWidgets.ProgressTracker.current(
        modifier,
        content,
    )
}
