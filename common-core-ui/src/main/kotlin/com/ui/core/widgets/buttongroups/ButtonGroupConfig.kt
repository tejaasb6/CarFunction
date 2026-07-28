package com.ui.core.widgets.buttongroups

import androidx.compose.runtime.Immutable

/**
 * Configuration for [ButtonGroup].
 *
 * ## Button Types
 * A button group can contain either:
 * - **Text buttons** (`iconOnly = false`) — max 5 buttons
 * - **Icon buttons** (`iconOnly = true`) — max 7 buttons
 *
 * Mixing text and icon buttons in the same group is not allowed.
 *
 * ## Alignment
 * - [Alignment.Horizontal] — buttons arranged side by side
 * - [Alignment.Vertical] — buttons stacked top to bottom
 *
 * @property alignment direction in which buttons are arranged.
 * @property iconOnly when `true`, the group contains icon-only buttons (max 7).
 *                    When `false`, the group contains text buttons (max 5).
 */
@Immutable
data class ButtonGroupConfig(
    val alignment: Alignment = Alignment.Horizontal,
    val iconOnly: Boolean = false,
) {
    /** Direction in which buttons are arranged inside a [ButtonGroup]. */
    enum class Alignment {
        Horizontal,
        Vertical,
    }

    companion object {
        /** Maximum number of text buttons allowed in a group. */
        const val MAX_TEXT_BUTTONS = 5

        /** Maximum number of icon buttons allowed in a group. */
        const val MAX_ICON_BUTTONS = 7
    }
}
