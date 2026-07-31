/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.carfunction.domain.model.CarViewMode
import com.ui.core.widgets.dividers.Divider
import com.ui.core.widgets.dividers.DividerConfig
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Pill-shaped Exterior / Interior toggle matching the reference design:
 *
 *  ┌──────────────┬──────────────┐
 *  │  ●Exterior●  │  Interior   │
 *  └──────────────┴──────────────┘
 *
 * - Outer container: pill shape with thin light-gray border, white background.
 * - Selected segment: solid black pill, white text.
 * - Unselected segment: transparent, black text.
 * - A thin vertical divider separates the two segments.
 * - Currently non-functional (no callback wired) per spec.
 */
@Composable
fun ExteriorInteriorToggle(
    selectedMode: CarViewMode,
    onModeSelected: (CarViewMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    val outerShape = RoundedCornerShape(50)
    val segmentShape = RoundedCornerShape(50)

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(outerShape)
            .background(Color.White, outerShape)
            .border(1.dp, Color(0xFFD0D0D0), outerShape)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ── Exterior segment ───────────────────────────────────────────────
        ToggleSegment(
            label = "Exterior",
            isSelected = selectedMode == CarViewMode.EXTERIOR,
            shape = segmentShape,
            onClick = { /* No functionality per spec */ },
        )

        // ── Vertical divider ───────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 6.dp)
                .width(1.dp)
                .background(Color(0xFFD0D0D0)),
        )

        // ── Interior segment ───────────────────────────────────────────────
        ToggleSegment(
            label = "Interior",
            isSelected = selectedMode == CarViewMode.INTERIOR,
            shape = segmentShape,
            onClick = { /* No functionality per spec */ },
        )
    }
}

@Composable
private fun ToggleSegment(
    label: String,
    isSelected: Boolean,
    shape: RoundedCornerShape,
    onClick: () -> Unit,
) {
    val bgColor = if (isSelected) Color.Black else Color.Transparent
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(shape)
            .background(bgColor, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            state = TextState(text = label.TR),
            style = androidx.compose.ui.text.TextStyle(
                color = textColor,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            ),
        )
    }
}
