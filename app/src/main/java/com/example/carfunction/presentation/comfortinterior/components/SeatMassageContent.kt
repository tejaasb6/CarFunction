/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.carfunction.domain.model.ComfortMassageMode
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Seat Massage content section.
 *
 * Displays only the horizontal segmented massage mode selector:
 * OFF | Balance | Active | Mobility | Relax | Stretch
 *
 * This composable is placed at the bottom center of the visualization
 * area by [ComfortInteriorScreen.SeatMassageLayout].
 */
@Composable
fun SeatMassageContent(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    MassageModeSelector(
        currentMode = state.currentMassageMode,
        onModeSelected = {
            dispatch(ComfortInteriorContract.Intent.SetMassageMode(it))
        },
        modifier = modifier,
    )
}

/**
 * Horizontal segmented massage mode selector matching the reference design.
 *
 * White pill-shaped container with thin vertical dividers between items.
 * Selected item: black pill with white text.
 * Unselected items: plain dark text.
 *
 * ┌──────┬─────────┬────────┬──────────┬───────┬─────────┐
 * │ OFF● │ Balance │ Active │ Mobility │ Relax │ Stretch │
 * └──────┴─────────┴────────┴──────────┴───────┴─────────┘
 */
@Composable
private fun MassageModeSelector(
    currentMode: ComfortMassageMode,
    onModeSelected: (ComfortMassageMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .background(Color.White)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ComfortMassageMode.entries.forEachIndexed { index, mode ->
                if (index > 0) {
                    // Thin vertical divider
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .padding(horizontal = 1.dp)
                            .background(Color(0xFFD0D0D0))
                            .padding(horizontal = 0.5.dp),
                    )
                }
                val isSelected = mode == currentMode
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .then(if (isSelected) Modifier.background(Color.Black) else Modifier)
                        .clickable { onModeSelected(mode) }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        state = TextState(
                            text = mode.label.TR,
                            maxLines = 1,
                        ),
                        style = TextStyle(
                            color = if (isSelected) Color.White else Color.Black,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        ),
                    )
                }
            }
        }
    }
}
