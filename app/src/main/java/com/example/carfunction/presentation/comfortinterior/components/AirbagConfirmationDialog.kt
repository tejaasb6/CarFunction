/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfunction.R
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Passenger Airbag ON/OFF confirmation dialog.
 *
 * Matches the reference Audi MMI design with:
 * - Title: "Passenger Airbag ON/OFF"
 * - Close (×) button in top-left corner
 * - Red DANGER / GEFAHR warning banner with warning triangle icon
 * - Warning description text explaining the safety implication
 * - Confirm / Cancel action buttons
 *
 * This is a safety-critical dialog per ISO 26262 (ASIL-B):
 * the airbag state SHALL NOT change unless the user explicitly confirms.
 */
@Composable
fun AirbagConfirmationDialog(
    pendingEnabled: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val actionLabel = if (pendingEnabled) "activate" else "deactivate"

    // Semi-transparent overlay — tapping dismisses
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onDismiss,
            )
            .semantics {
                contentDescription = "Airbag confirmation overlay, tap to dismiss"
            },
        contentAlignment = Alignment.Center,
    ) {
        // Modal card
        Column(
            modifier = Modifier
                .width(420.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}, // Consume click to prevent dismiss
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // ── Title Row with Close Button ────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Close icon
                Icon(
                    source = IconSource.Resource(
                        R.drawable.ic_close,
                        contentDescription = "Close dialog",
                    ),
                    config = IconConfig(size = IconConfig.Size.SM),
                    modifier = Modifier
                        .clickable(onClick = onDismiss)
                        .padding(4.dp),
                )

                // Title
                Text(
                    state = TextState(text = "Passenger Airbag ON/OFF".TR),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 28.dp), // offset for close icon balance
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Red Warning Banner ─────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFD32F2F))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                // Warning triangle icon
                Icon(
                    source = IconSource.Resource(
                        R.drawable.ic_warning,
                        contentDescription = "Danger warning",
                    ),
                    config = IconConfig(size = IconConfig.Size.MD),
                    modifier = Modifier.size(24.dp),
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    state = TextState(text = "DANGER".TR),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Warning Description ────────────────────────────────────────
            Text(
                state = TextState(
                    text = buildString {
                        append("Only $actionLabel the passenger airbag in exceptional ")
                        append("cases, e.g. for rear-facing child seats on the ")
                        append("passenger seat.")
                    }.TR,
                ),
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                ),
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Action Buttons ─────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // Cancel button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFF0F0F0))
                        .clickable(onClick = onDismiss)
                        .padding(vertical = 14.dp)
                        .semantics { contentDescription = "Cancel airbag change" },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        state = TextState(text = "Cancel".TR),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                        ),
                    )
                }

                // Confirm button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.Black)
                        .clickable(onClick = onConfirm)
                        .padding(vertical = 14.dp)
                        .semantics {
                            contentDescription = "Confirm $actionLabel passenger airbag"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        state = TextState(text = "Confirm".TR),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                        ),
                    )
                }
            }
        }
    }
}
