/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.carfunction.domain.model.AmbientTheme
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract
import com.ui.core.widgets.sliders.Slider
import com.ui.core.widgets.sliders.SliderConfig
import com.ui.core.widgets.sliders.SliderContent
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.toggleswitch.ToggleSwitch
import com.ui.core.widgets.toggleswitch.ToggleSwitchContent
import com.ui.core.widgets.toggleswitch.ToggleSwitchInteractionConfig
import com.ui.core.widgets.toggleswitch.ToggleSwitchState

/**
 * Ambient Light content section matching the reference Audi MMI design.
 *
 * Three distinct sections per the screenshot reference:
 *
 * 1. **Master Controls**
 *    - Master ambient light toggle (ON/OFF)
 *    - Theme selector (Sky | Horizon | Hearth | Sync)
 *    - Brightness slider
 *
 * 2. **Ambient Light Settings**
 *    - Footwell Lighting toggle
 *    - Roofline Lighting toggle
 *    - Panoramic Roof Lighting toggle
 *
 * 3. **Interaction Light**
 *    - Interaction Light master toggle
 *    - Brightness slider
 *    - Individual feature toggles (Hazard, Charging, Digital Assistant, Navigation, Phone)
 */
@Composable
fun AmbientLightContent(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val ambientState = state.ambientLightState

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        // ════════════════════════════════════════════════════════════════════
        // Section 1: Master Toggle + Theme + Brightness
        // ════════════════════════════════════════════════════════════════════

        // Master Toggle
        ToggleSwitch(
            modifier = Modifier.fillMaxWidth(),
            content = ToggleSwitchContent(label = "Ambient Light".TR),
            state = ToggleSwitchState(enabled = true, controlLeading = true),
            interactionConfig = ToggleSwitchInteractionConfig(
                selected = ambientState.masterEnabled,
                onSelectedChange = {
                    dispatch(ComfortInteriorContract.Intent.ToggleAmbientLight(it))
                },
            ),
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Brightness Section Header
        SectionHeader(title = "Ambientlight Brightness")

        Spacer(modifier = Modifier.height(12.dp))

        // Theme Selector (Sky | Horizon | Hearth | Sync)
        // Custom floating-pill selector matching reference Audi MMI design:
        // bare text labels with thin vertical dividers; selected = black pill.
        AmbientThemeSelector(
            selectedTheme = ambientState.theme,
            onThemeSelected = {
                dispatch(ComfortInteriorContract.Intent.SetAmbientTheme(it))
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Panorama Roof Brightness Slider
        Slider(
            value = ambientState.brightness,
            onValueChange = {
                dispatch(ComfortInteriorContract.Intent.SetAmbientBrightness(it))
            },
            config = SliderConfig(
                alignment = SliderConfig.Alignment.Horizontal,
                mode = SliderConfig.Mode.Single,
                steps = 10,
            ),
            content = SliderContent(
                minLabel = "Min",
                maxLabel = "Max",
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ════════════════════════════════════════════════════════════════════
        // Section 2: Ambient Light Settings
        // ════════════════════════════════════════════════════════════════════

        SectionHeader(title = "Ambient Light Settings")

        Spacer(modifier = Modifier.height(8.dp))

        // Footwell Lighting Toggle
        ToggleSwitch(
            modifier = Modifier.fillMaxWidth(),
            content = ToggleSwitchContent(label = "Footwell Lighting".TR),
            state = ToggleSwitchState(enabled = true, controlLeading = true),
            interactionConfig = ToggleSwitchInteractionConfig(
                selected = ambientState.footwellLightingEnabled,
                onSelectedChange = {
                    dispatch(ComfortInteriorContract.Intent.ToggleFootwellLighting(it))
                },
            ),
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Roofline Lighting Toggle
        ToggleSwitch(
            modifier = Modifier.fillMaxWidth(),
            content = ToggleSwitchContent(label = "Roofline Lighting".TR),
            state = ToggleSwitchState(enabled = true, controlLeading = true),
            interactionConfig = ToggleSwitchInteractionConfig(
                selected = ambientState.rooflineLightingEnabled,
                onSelectedChange = {
                    dispatch(ComfortInteriorContract.Intent.ToggleRooflineLighting(it))
                },
            ),
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Panoramic Roof Lighting Toggle
        ToggleSwitch(
            modifier = Modifier.fillMaxWidth(),
            content = ToggleSwitchContent(label = "Panoramic Roof Lighting".TR),
            state = ToggleSwitchState(enabled = true, controlLeading = true),
            interactionConfig = ToggleSwitchInteractionConfig(
                selected = ambientState.panoramicRoofLightingEnabled,
                onSelectedChange = {
                    dispatch(ComfortInteriorContract.Intent.TogglePanoramicRoofLighting(it))
                },
            ),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ════════════════════════════════════════════════════════════════════
        // Section 3: Interaction Light
        // ════════════════════════════════════════════════════════════════════

        SectionHeader(title = "Interaction Light")

        Spacer(modifier = Modifier.height(8.dp))

        // Interaction Light Master Toggle
        ToggleSwitch(
            modifier = Modifier.fillMaxWidth(),
            content = ToggleSwitchContent(label = "Interaction Light".TR),
            state = ToggleSwitchState(enabled = true, controlLeading = true),
            interactionConfig = ToggleSwitchInteractionConfig(
                selected = ambientState.interactionLightEnabled,
                onSelectedChange = {
                    dispatch(ComfortInteriorContract.Intent.ToggleInteractionLight(it))
                },
            ),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Interaction Light Brightness Slider
        Slider(
            value = ambientState.interactionLightBrightness,
            onValueChange = {
                dispatch(ComfortInteriorContract.Intent.SetInteractionLightBrightness(it))
            },
            config = SliderConfig(
                alignment = SliderConfig.Alignment.Horizontal,
                mode = SliderConfig.Mode.Single,
                steps = 10,
            ),
            content = SliderContent(
                minLabel = "Min",
                maxLabel = "Max",
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Individual Interaction Light Feature Toggles
        ambientState.settings.forEach { setting ->
            ToggleSwitch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                content = ToggleSwitchContent(label = setting.label.TR),
                state = ToggleSwitchState(enabled = true, controlLeading = true),
                interactionConfig = ToggleSwitchInteractionConfig(
                    selected = setting.enabled,
                    onSelectedChange = { enabled ->
                        dispatch(
                            ComfortInteriorContract.Intent.ToggleAmbientSetting(
                                settingId = setting.id,
                                enabled = enabled,
                            ),
                        )
                    },
                ),
            )
        }
    }
}

/**
 * Ambient theme selector matching the reference Audi MMI floating-pill design.
 *
 * Layout: bare text labels separated by thin vertical dividers.
 * The selected theme gets a black capsule/pill with white text;
 * unselected themes are rendered as plain dark text with no background.
 *
 * ┌───────┬─────────┬────────┬──────┐
 * │ Sky●  │ Horizon │ Hearth │ Sync │
 * └───────┴─────────┴────────┴──────┘
 */
@Composable
private fun AmbientThemeSelector(
    selectedTheme: AmbientTheme,
    onThemeSelected: (AmbientTheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AmbientTheme.entries.forEachIndexed { index, theme ->
            if (index > 0) {
                // Thin vertical divider between items
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(1.dp)
                        .background(Color(0xFFCCCCCC)),
                )
            }

            val isSelected = theme == selectedTheme
            val pillShape = RoundedCornerShape(20.dp)

            Box(
                modifier = Modifier
                    .clip(pillShape)
                    .then(
                        if (isSelected) {
                            Modifier.background(Color.Black, pillShape)
                        } else {
                            Modifier
                        },
                    )
                    .clickable { onThemeSelected(theme) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    state = TextState(text = theme.label.TR),
                    style = TextStyle(
                        color = if (isSelected) Color.White else Color.Black,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    ),
                )
            }
        }
    }
}
