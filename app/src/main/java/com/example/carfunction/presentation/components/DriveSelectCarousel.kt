/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfunction.R
import com.example.carfunction.domain.model.DriveMode
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Drive select carousel with left/right arrows.
 *
 * Layout matches the reference design:
 * - Chevrons at left/right edges, mode name large & centered between them.
 * - "Drive Select" label below, **left-aligned** to the section's start.
 *
 * Uses [IconSource.Resource] with drawable resources — no Material Icons dependency.
 */
@Composable
fun DriveSelectCarousel(
    currentMode: DriveMode?,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        // Left-align children so the "Drive Select" label sits at start
        horizontalAlignment = Alignment.Start,
    ) {
        // ── Carousel row: ‹  balanced  › ───────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                source = IconSource.Resource(
                    R.drawable.ic_chevron_left,
                    contentDescription = "Previous mode",
                ),
                config = IconConfig(size = IconConfig.Size.MD),
                modifier = Modifier.clickable(onClick = onPrevious),
            )

            Text(
                state = TextState(text = (currentMode?.label ?: "—").TR),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                ),
            )

            Icon(
                source = IconSource.Resource(
                    R.drawable.ic_chevron_right,
                    contentDescription = "Next mode",
                ),
                config = IconConfig(size = IconConfig.Size.MD),
                modifier = Modifier.clickable(onClick = onNext),
            )
        }

        // ── Section label — left-aligned ───────────────────────────────────
        Text(
            state = TextState(text = "Drive Select".TR),
            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
            modifier = Modifier.padding(top = 6.dp),
        )
    }
}
