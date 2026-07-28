package com.ui.core.layout

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.LayoutDirection

/**
 * Composition local for the active [LayoutConfig].
 *
 * Provided at the root by brand themes (`AudiTheme`, `LamborghiniTheme`).
 * Components read this when they need to react to driver-side placement or
 * explicit icon-mirroring decisions beyond what Compose's built-in
 * [androidx.compose.ui.platform.LocalLayoutDirection] covers.
 *
 * Default: [LayoutConfig] with all values at their defaults (LHD, LTR, mirror on).
 */
val LocalLayoutConfig = compositionLocalOf { LayoutConfig() }

// ── Compose conversion ─────────────────────────────────────────────────────────

/**
 * Maps [LayoutConfig] to the Compose [LayoutDirection] that should be provided
 * via [androidx.compose.ui.platform.LocalLayoutDirection].
 *
 * Returns [LayoutDirection.Rtl] when [LayoutConfig.isRtl] is `true` (i.e. either
 * RHD or RTL text direction is active), so that Compose automatically:
 *  - Reverses [androidx.compose.foundation.layout.Row] child placement
 *  - Swaps `start`/`end` padding and alignment
 *  - Flips [androidx.compose.material.icons.Icons.AutoMirrored] icons
 *  - Mirrors [androidx.compose.foundation.layout.Arrangement.Start/End] semantics
 */
val LayoutConfig.effectiveLayoutDirection: LayoutDirection
    get() = if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
