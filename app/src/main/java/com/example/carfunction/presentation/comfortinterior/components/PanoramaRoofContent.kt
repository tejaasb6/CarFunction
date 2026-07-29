package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.example.carfunction.domain.model.PanoramaRoofState
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Panorama Roof content section.
 *
 * Displays:
 * - A row of circular segment toggles (opaque/transparent)
 * - A preset pattern selector bar at the bottom
 */
@Composable
fun PanoramaRoofContent(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val roofState = state.panoramaRoofState

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        SectionHeader(title = "Roof Segments")

        Spacer(modifier = Modifier.height(16.dp))

        // ── Segment Toggle Row ─────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            roofState.segments.forEachIndexed { index, isOpen ->
                RoofSegmentDot(
                    isOpen = isOpen,
                    onClick = {
                        dispatch(ComfortInteriorContract.Intent.ToggleRoofSegment(index))
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Instruction text ───────────────────────────────────────────────
        Text(
            state = TextState(text = "Tap segments to toggle opacity".TR),
            style = TextStyle(fontSize = 11.sp, color = Color.Gray),
            modifier = Modifier.padding(start = 4.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ── Preset Selector Bar ────────────────────────────────────────────
        SectionHeader(title = "Presets")

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFF0F0F0))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(PanoramaRoofState.ROOF_PRESET_COUNT) { presetIndex ->
                val isSelected = presetIndex == roofState.selectedPresetIndex
                RoofPresetIcon(
                    presetIndex = presetIndex,
                    isSelected = isSelected,
                    onClick = {
                        dispatch(ComfortInteriorContract.Intent.SelectRoofPreset(presetIndex))
                    },
                )
            }
        }
    }
}

/**
 * Individual roof segment dot — filled when open, hollow when closed.
 */
@Composable
private fun RoofSegmentDot(
    isOpen: Boolean,
    onClick: () -> Unit,
) {
    val bgColor = if (isOpen) Color.White else Color(0xFF333333)
    val borderColor = if (isOpen) Color(0xFFBBBBBB) else Color(0xFF333333)

    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .clickable(onClick = onClick),
    )
}

/**
 * Roof preset icon — a small styled indicator for each preset pattern.
 */
@Composable
private fun RoofPresetIcon(
    presetIndex: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(6.dp)
    val bgColor = if (isSelected) Color.Black else Color.White
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(shape)
            .background(bgColor, shape)
            .border(1.dp, Color(0xFFCCCCCC), shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            state = TextState(text = "${presetIndex + 1}".TR),
            style = TextStyle(
                color = textColor,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            ),
        )
    }
}
