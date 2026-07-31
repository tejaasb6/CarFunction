/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carfunction.R
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract
import com.ui.core.widgets.dividers.Divider
import com.ui.core.widgets.dividers.DividerConfig
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.toggleswitch.ToggleSwitch
import com.ui.core.widgets.toggleswitch.ToggleSwitchContent
import com.ui.core.widgets.toggleswitch.ToggleSwitchInteractionConfig
import com.ui.core.widgets.toggleswitch.ToggleSwitchState

/**
 * Safety content section.
 *
 * Contains:
 * - Safety toggles: Passenger Airbag, Fond Information tone, Child presence detection
 * - Privacy section: Glovebox PIN toggle with edit icon
 *
 * Uses [IconSource.Resource] with drawable resources — no Material Icons dependency.
 */
@Composable
fun SafetyContent(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val safetyState = state.safetyState

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        // ── Safety Section ─────────────────────────────────────────────────
        SectionHeader(title = "Safety")

        Spacer(modifier = Modifier.height(12.dp))

        // Passenger Airbag
        ToggleSwitch(
            modifier = Modifier.fillMaxWidth(),
            content = ToggleSwitchContent(label = "Passenger Airbag".TR),
            state = ToggleSwitchState(enabled = true, controlLeading = true),
            interactionConfig = ToggleSwitchInteractionConfig(
                selected = safetyState.passengerAirbagEnabled,
                onSelectedChange = {
                    dispatch(ComfortInteriorContract.Intent.TogglePassengerAirbag(it))
                },
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Fond Information Tone
        ToggleSwitch(
            modifier = Modifier.fillMaxWidth(),
            content = ToggleSwitchContent(label = "Fond Information tone".TR),
            state = ToggleSwitchState(enabled = true, controlLeading = true),
            interactionConfig = ToggleSwitchInteractionConfig(
                selected = safetyState.fondInfoToneEnabled,
                onSelectedChange = {
                    dispatch(ComfortInteriorContract.Intent.ToggleFondInfoTone(it))
                },
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Child Presence Detection
        ToggleSwitch(
            modifier = Modifier.fillMaxWidth(),
            content = ToggleSwitchContent(label = "Child presence detection".TR),
            state = ToggleSwitchState(enabled = true, controlLeading = true),
            interactionConfig = ToggleSwitchInteractionConfig(
                selected = safetyState.childPresenceDetectionEnabled,
                onSelectedChange = {
                    dispatch(ComfortInteriorContract.Intent.ToggleChildPresenceDetection(it))
                },
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider(config = DividerConfig(padding = 0.dp))
        Spacer(modifier = Modifier.height(16.dp))

        // ── Privacy Section ────────────────────────────────────────────────
        SectionHeader(title = "Privacy")

        Spacer(modifier = Modifier.height(12.dp))

        // Glovebox PIN with edit icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ToggleSwitch(
                modifier = Modifier.weight(1f),
                content = ToggleSwitchContent(label = "Glovebox PIN".TR),
                state = ToggleSwitchState(enabled = true, controlLeading = true),
                interactionConfig = ToggleSwitchInteractionConfig(
                    selected = safetyState.gloveboxPinEnabled,
                    onSelectedChange = {
                        dispatch(ComfortInteriorContract.Intent.ToggleGloveboxPin(it))
                    },
                ),
            )

            // Edit icon (pencil) for changing existing PIN — only shown when PIN is active
            if (safetyState.gloveboxPinEnabled) {
                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    source = IconSource.Resource(
                        R.drawable.ic_edit,
                        contentDescription = "Edit Glovebox PIN",
                    ),
                    config = IconConfig(size = IconConfig.Size.SM),
                    modifier = Modifier
                        .clickable {
                            dispatch(ComfortInteriorContract.Intent.OpenPinModal)
                        }
                        .padding(8.dp),
                )
            }
        }
    }
}
