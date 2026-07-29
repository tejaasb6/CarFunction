package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carfunction.domain.model.AmbientTheme
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract
import com.ui.core.widgets.segmentedcontrols.SegmentedControl
import com.ui.core.widgets.segmentedcontrols.SegmentedControlConfig
import com.ui.core.widgets.segmentedcontrols.SegmentedControlInteractionConfig
import com.ui.core.widgets.segmentedcontrols.SegmentedControlSegment
import com.ui.core.widgets.segmentedcontrols.segmentsOf
import com.ui.core.widgets.sliders.Slider
import com.ui.core.widgets.sliders.SliderConfig
import com.ui.core.widgets.sliders.SliderContent
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.toggleswitch.ToggleSwitch
import com.ui.core.widgets.toggleswitch.ToggleSwitchContent
import com.ui.core.widgets.toggleswitch.ToggleSwitchInteractionConfig
import com.ui.core.widgets.toggleswitch.ToggleSwitchState

/**
 * Ambient Light content section.
 *
 * Contains:
 * - Master ambient light toggle
 * - Theme selector (Sky, Horizon, Hearth, Sync)
 * - Brightness slider
 * - Footwell lighting toggle
 * - Individual ambient setting toggles (Hazard, Charging, Digital Assistant, etc.)
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
            .padding(16.dp),
    ) {
        // ── Master Toggle ──────────────────────────────────────────────────
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

        // ── Brightness Section ─────────────────────────────────────────────
        SectionHeader(title = "Ambientlight Brightness")

        Spacer(modifier = Modifier.height(12.dp))

        // Theme Selector (Sky | Horizon | Hearth | Sync)
        val themes = AmbientTheme.entries
        val selectedThemeIndex = themes.indexOf(ambientState.theme).coerceAtLeast(0)

        SegmentedControl(
            config = SegmentedControlConfig(
                variant = SegmentedControlConfig.Variant.Label,
                orientation = SegmentedControlConfig.Orientation.Horizontal,
            ),
            segments = segmentsOf(
                SegmentedControlSegment(label = AmbientTheme.SKY.label),
                SegmentedControlSegment(label = AmbientTheme.HORIZON.label),
                SegmentedControlSegment(label = AmbientTheme.HEARTH.label),
                SegmentedControlSegment(label = AmbientTheme.SYNC.label),
            ),
            interactionConfig = SegmentedControlInteractionConfig(
                selectedIndex = selectedThemeIndex,
                onSelectedIndexChange = { index ->
                    themes.getOrNull(index)?.let {
                        dispatch(ComfortInteriorContract.Intent.SetAmbientTheme(it))
                    }
                },
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Brightness Slider
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

        Spacer(modifier = Modifier.height(20.dp))

        // ── Ambient Light Settings ─────────────────────────────────────────
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

        // Individual Ambient Setting Toggles
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
