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
import com.example.carfunction.domain.model.DisplayTarget
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract
import com.ui.core.widgets.sliders.Slider
import com.ui.core.widgets.sliders.SliderConfig
import com.ui.core.widgets.sliders.SliderContent
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Display content section.
 *
 * Displays:
 * - A horizontal set of display target buttons (Head-Up, Virtual Cockpit, MMI)
 * - A brightness slider for the selected display
 */
@Composable
fun DisplayContent(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        SectionHeader(title = "Display Brightness")

        Spacer(modifier = Modifier.height(16.dp))

        // ── Display Target Selector ────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            DisplayTarget.entries.forEach { target ->
                val isSelected = target == state.selectedDisplayTarget
                DisplayTargetChip(
                    label = target.label,
                    isSelected = isSelected,
                    onClick = {
                        dispatch(ComfortInteriorContract.Intent.SelectDisplayTarget(target))
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Brightness Slider ──────────────────────────────────────────────
        Slider(
            value = state.currentDisplayBrightness,
            onValueChange = {
                dispatch(ComfortInteriorContract.Intent.SetDisplayBrightness(it))
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            state = TextState(
                text = "Adjust brightness for ${state.selectedDisplayTarget.label}".TR,
            ),
            style = TextStyle(fontSize = 11.sp, color = Color.Gray),
        )
    }
}

/**
 * Display target chip — pill-shaped button matching the callout label design.
 */
@Composable
private fun DisplayTargetChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(24.dp)
    val bgColor = if (isSelected) Color.Black else Color.White
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(shape)
            .background(bgColor, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            state = TextState(text = label.TR),
            style = TextStyle(
                color = textColor,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            ),
        )
    }
}
