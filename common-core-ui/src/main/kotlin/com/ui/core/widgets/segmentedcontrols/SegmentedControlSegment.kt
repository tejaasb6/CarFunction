package com.ui.core.widgets.segmentedcontrols

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

/**
 * Data describing a single segment inside a [SegmentedControl].
 *
 * The caller is responsible for ensuring all segments use a consistent format
 * matching the [SegmentedControlConfig.Variant]:
 * - [Variant.Label][SegmentedControlConfig.Variant.Label] → provide [label] only.
 * - [Variant.Icon][SegmentedControlConfig.Variant.Icon] → provide [icon] only.
 * - [Variant.LeadingIcon][SegmentedControlConfig.Variant.LeadingIcon] → provide [label] + [icon] (or [leadingIcon]).
 * - [Variant.TrailingIcon][SegmentedControlConfig.Variant.TrailingIcon] → provide [label] + [icon] (or [trailingIcon]).
 * - [Variant.BothIcons][SegmentedControlConfig.Variant.BothIcons] → provide [label] + [leadingIcon] + [trailingIcon].
 *
 * ```kotlin
 * // Label only
 * SegmentedControlSegment(label = "Day")
 *
 * // Icon only
 * SegmentedControlSegment(icon = { Icon(Icons.Filled.Home, contentDescription = "Home") })
 *
 * // Both leading + trailing icons
 * SegmentedControlSegment(
 *     label = "Home",
 *     leadingIcon = { Icon(Icons.Filled.Home, contentDescription = null) },
 *     trailingIcon = { Icon(Icons.Filled.ChevronRight, contentDescription = null) },
 * )
 * ```
 */
@Immutable
data class SegmentedControlSegment(
    /** Label text for this segment. */
    val label: String? = null,
    /**
     * Single icon slot — used by [Variant.Icon], [Variant.LeadingIcon], and
     * [Variant.TrailingIcon]. For [Variant.BothIcons], use [leadingIcon] and
     * [trailingIcon] instead.
     */
    val icon: (@Composable () -> Unit)? = null,
    /** Leading icon slot — takes precedence over [icon] when set. Used by [Variant.BothIcons]. */
    val leadingIcon: (@Composable () -> Unit)? = null,
    /** Trailing icon slot — used by [Variant.BothIcons]. */
    val trailingIcon: (@Composable () -> Unit)? = null,
    /** Per-segment enabled state. Overridden to `false` when the whole control is disabled. */
    val enabled: Boolean = true,
)

/**
 * Compile-time safe container that guarantees the segment count is between 2 and 6.
 *
 * Cannot be constructed directly — use the [segmentsOf] factory functions which
 * accept exactly 2, 3, 4, 5, or 6 [SegmentedControlSegment] arguments.
 *
 * ```kotlin
 * val segments = segmentsOf(
 *     SegmentedControlSegment(label = "Day"),
 *     SegmentedControlSegment(label = "Week"),
 *     SegmentedControlSegment(label = "Month"),
 * )
 * ```
 */
@Immutable
class SegmentedControlSegments internal constructor(
    internal val items: List<SegmentedControlSegment>,
) {
    /** Number of segments (always 2–6). */
    val size: Int get() = items.size

    operator fun get(index: Int): SegmentedControlSegment = items[index]
}

// ── Factory functions: compile-time safe, exactly 2–6 segments ─────────────────

/** Creates a [SegmentedControlSegments] with exactly **2** segments. */
fun segmentsOf(
    first: SegmentedControlSegment,
    second: SegmentedControlSegment,
): SegmentedControlSegments = SegmentedControlSegments(listOf(first, second))

/** Creates a [SegmentedControlSegments] with exactly **3** segments. */
fun segmentsOf(
    first: SegmentedControlSegment,
    second: SegmentedControlSegment,
    third: SegmentedControlSegment,
): SegmentedControlSegments = SegmentedControlSegments(listOf(first, second, third))

/** Creates a [SegmentedControlSegments] with exactly **4** segments. */
fun segmentsOf(
    first: SegmentedControlSegment,
    second: SegmentedControlSegment,
    third: SegmentedControlSegment,
    fourth: SegmentedControlSegment,
): SegmentedControlSegments = SegmentedControlSegments(listOf(first, second, third, fourth))

/** Creates a [SegmentedControlSegments] with exactly **5** segments. */
fun segmentsOf(
    first: SegmentedControlSegment,
    second: SegmentedControlSegment,
    third: SegmentedControlSegment,
    fourth: SegmentedControlSegment,
    fifth: SegmentedControlSegment,
): SegmentedControlSegments = SegmentedControlSegments(listOf(first, second, third, fourth, fifth))

/** Creates a [SegmentedControlSegments] with exactly **6** segments. */
fun segmentsOf(
    first: SegmentedControlSegment,
    second: SegmentedControlSegment,
    third: SegmentedControlSegment,
    fourth: SegmentedControlSegment,
    fifth: SegmentedControlSegment,
    sixth: SegmentedControlSegment,
): SegmentedControlSegments = SegmentedControlSegments(listOf(first, second, third, fourth, fifth, sixth))
