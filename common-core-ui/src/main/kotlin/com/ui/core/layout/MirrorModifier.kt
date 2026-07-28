package com.ui.core.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Horizontally mirrors this element when the active [LayoutConfig] requires it.
 *
 * ## When to use
 * Apply to directional icons — forward/back arrows, chevrons, progress steps,
 * breadcrumb separators — that are **not** in the
 * [androidx.compose.material.icons.Icons.AutoMirrored] set and therefore do
 * not flip automatically with [androidx.compose.ui.platform.LocalLayoutDirection].
 *
 * Icons that are declared as `AutoMirrored` do **not** need this modifier; they
 * already respond to `LocalLayoutDirection` provided by the brand theme.
 *
 * ## Behaviour
 * | driverSide  | textDir | mirrorIcons | Result           |
 * |-------------|---------|-------------|------------------|
 * | LEFT        | LTR     | any         | no flip (LTR)    |
 * | RIGHT       | any     | true        | flip (RTL)       |
 * | any         | RTL     | true        | flip (RTL)       |
 * | RIGHT/RTL   | any     | **false**   | no flip (opt-out)|
 *
 * ## Example
 * ```kotlin
 * Icon(
 *     imageVector = Icons.Filled.ArrowForward,
 *     contentDescription = "Next",
 *     modifier = Modifier.mirrorForLayout()
 * )
 * ```
 *
 * @see LocalLayoutConfig
 * @see LayoutConfig.isRtl
 */
@Composable
fun Modifier.mirrorForLayout(): Modifier {
    val config = LocalLayoutConfig.current
    return if (config.isRtl && config.mirrorIcons) {
        // scaleX = -1f flips the drawn content horizontally without affecting
        // the layout bounds — the element still occupies the same space.
        this.graphicsLayer(scaleX = -1f)
    } else {
        this
    }
}
