/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Reusable gray section header text used throughout the Comfort & Interior screen.
 * Matches the Audi MMI design pattern for settings group labels.
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        state = TextState(text = title.TR),
        style = TextStyle(
            fontSize = 13.sp,
            color = Color(0xFF888888),
        ),
        modifier = modifier.padding(start = 4.dp),
    )
}
