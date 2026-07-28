package com.ui.core.layout

// ── Enums ─────────────────────────────────────────────────────────────────────

/**
 * Which side of the vehicle the driver occupies.
 *
 * Injected into [com.ui.core.tokens.ThemeTokens.driverSide] as the brand-level
 * default and available at runtime via [LocalLayoutConfig].
 *
 *  - [LEFT]  — Left-Hand Drive (Europe, USA, …): standard LTR-friendly layout.
 *  - [RIGHT] — Right-Hand Drive (UK, Japan, Australia, …): the UI mirrors
 *              left↔right so primary controls are on the driver's side.
 */
enum class DriverSide { LEFT, RIGHT }

/**
 * Preferred reading / text direction for the active locale.
 *
 *  - [LTR] — Left-to-right (Latin, Cyrillic, …): default.
 *  - [RTL] — Right-to-left (Arabic, Hebrew, Farsi, …): sets Compose
 *            [androidx.compose.ui.unit.LayoutDirection.Rtl] for full
 *            bidirectional text and layout support.
 */
enum class TextDir { LTR, RTL }

// ── Config data class ─────────────────────────────────────────────────────────

/**
 * Full runtime layout configuration.
 *
 * Driven by [LayoutOrchestrator] at runtime; the brand-level default comes from
 * [com.ui.core.tokens.ThemeTokens.driverSide].
 *
 * @param driverSide   LHD or RHD — mirrors the complete UI when [DriverSide.RIGHT].
 * @param textDir      Text direction — switches Compose [androidx.compose.ui.unit.LayoutDirection].
 * @param mirrorIcons  When `true`, directional icons decorated with
 *                     [mirrorForLayout] flip automatically with the layout.
 *                     `false` leaves icon orientation unchanged (useful when
 *                     icons are already brand-specific and should not flip).
 */
data class LayoutConfig(
    val driverSide: DriverSide = DriverSide.LEFT,
    val textDir: TextDir = TextDir.LTR,
    val mirrorIcons: Boolean = true,
)

// ── Derived helpers ────────────────────────────────────────────────────────────

/**
 * `true` when either RHD or RTL text direction requires a mirrored layout.
 *
 * Used by [com.ui.core.layout.LocalLayoutConfig] consumers and the
 * [mirrorForLayout] modifier to decide whether to apply a horizontal flip.
 */
val LayoutConfig.isRtl: Boolean
    get() = textDir == TextDir.RTL || driverSide == DriverSide.RIGHT
