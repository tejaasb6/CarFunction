package com.example.carfunction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfunction.domain.model.AmbientLightPreset
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Ambient light preset selector showing color circles.
 */
@Composable
fun AmbientLightPresetsRow(
    presets: List<AmbientLightPreset>,
    selectedPresetId: String,
    onPresetSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            presets.forEach { preset ->
                val isSelected = preset.id == selectedPresetId
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(preset.color, CircleShape)
                            .then(
                                if (isSelected) {
                                    Modifier.border(3.dp, Color.Black, CircleShape)
                                } else {
                                    Modifier.border(1.dp, Color(0xFFE0E0E0), CircleShape)
                                }
                            )
                            .clickable { onPresetSelected(preset.id) },
                    )
                    Text(
                        state = TextState(text = preset.label.TR),
                        style = TextStyle(fontSize = 10.sp, color = Color.Black),
                    )
                }
            }
        }

        Text(
            state = TextState(text = "Ambient light presets".TR),
            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}
