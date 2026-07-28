package com.ui.core.widgets.selects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

/**
 * Composable slot bundle for the [Select] widget's optional decorative elements.
 *
 * When a slot is non-null, the corresponding element is rendered. When `null`,
 * the element is omitted from the layout.
 *
 * ```kotlin
 * SelectSlots(
 *     leadingIcon = {
 *         Icon(Icons.Default.LocationOn, contentDescription = null)
 *     },
 *     trailingIcon = {
 *         Icon(
 *             painter = painterResource(
 *                 id = if (expanded) R.drawable.arrowup else R.drawable.arrowdown
 *             ),
 *             contentDescription = null
 *         )
 *     },
 * )
 * ```
 *
 * @param leadingIcon optional icon displayed before the selected value text in the field.
 * @param trailingIcon optional icon displayed after the selected value text; commonly
 *  used for a dropdown chevron indicator.
 */
@Immutable
data class SelectSlots(
    val leadingIcon: (@Composable () -> Unit)? = null,
    val trailingIcon: (@Composable () -> Unit)? = null,
)
