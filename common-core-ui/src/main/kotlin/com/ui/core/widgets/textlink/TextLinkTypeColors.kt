package com.ui.core.widgets.textlink

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Per-state colour set for a single element (label, icon, or underline) of a text link.
 *
 * Example:
 * ```kotlin
 * val labelColors = TextLinkStateColors(
 *     idle = Color.Gray,
 *     pressed = Color.Black,
 *     disabled = Color.LightGray,
 * )
 * ```
 *
 * @property idle colour in the default (rest) state.
 * @property pressed colour while the user's finger is down.
 * @property disabled colour when the text link is not interactive.
 */
@Immutable
data class TextLinkStateColors(
    val idle: Color,
    val pressed: Color,
    val disabled: Color,
)

/**
 * Full per-variant colour specification for a text link.
 *
 * Groups the per-state colours for each visual element (label, icon, underline).
 *
 * Example:
 * ```kotlin
 * val standalone = TextLinkVariantColors(
 *     label = TextLinkStateColors(idle = Color.Gray, pressed = Color.Black, disabled = Color.LightGray),
 *     icon = TextLinkStateColors(idle = Color.Gray, pressed = Color.Black, disabled = Color.LightGray),
 *     underline = TextLinkStateColors(idle = Color.Gray, pressed = Color.Black, disabled = Color.LightGray),
 * )
 * ```
 *
 * @property label per-state colours for the text label.
 * @property icon per-state colours for leading/trailing icons.
 * @property underline per-state colours for the underline bar beneath the label.
 */
@Immutable
data class TextLinkVariantColors(
    val label: TextLinkStateColors,
    val icon: TextLinkStateColors,
    val underline: TextLinkStateColors,
)
