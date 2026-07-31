/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfunction.domain.model.MassageMode
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Massage control with OFF / Active (or OFF / Mobility) segmented toggle.
 *
 * Layout matches the reference design:
 * - The pill-shaped toggle is **horizontally centered** in the card.
 * - The title label ("Massage Driver" / "Massage Passenger") is **left-aligned**
 *   below the toggle.
 */
@Composable
fun MassageControl(
    title: String,
    currentMode: MassageMode,
    options: List<MassageMode>,
    onModeSelected: (MassageMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        // ── Segmented toggle — centered ────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF0F0F0))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                options.forEach { mode ->
                    val isSelected = mode == currentMode
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .then(
                                if (isSelected) Modifier.background(Color.Black) else Modifier
                            )
                            .clickable { onModeSelected(mode) }
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        val label = when (mode) {
                            MassageMode.OFF -> "OFF"
                            MassageMode.ACTIVE -> "Active"
                            MassageMode.MOBILITY -> "Mobility"
                        }
                        Text(
                            state = TextState(text = label.TR),
                            style = TextStyle(
                                color = if (isSelected) Color.White else Color.Black,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            ),
                        )
                    }
                }
            }
        }

        // ── Title label — left-aligned ─────────────────────────────────────
        Text(
            state = TextState(text = title.TR),
            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
            modifier = Modifier.padding(top = 6.dp),
        )
    }
}
