package com.example.carfunction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfunction.domain.model.CarViewMode
import com.example.carfunction.domain.model.VehicleHotspot
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Right pane: 3D vehicle visualization area with interactive hotspots.
 * Shows marble-textured background with hotspot overlay icons.
 */
@Composable
fun VehicleVisualization(
    carViewMode: CarViewMode,
    hotspots: List<VehicleHotspot>,
    onHotspotClick: (VehicleHotspot) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF5F5F5),
                        Color(0xFFE8E8E8),
                        Color(0xFFF0F0F0),
                    ),
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        // Vehicle placeholder text
        val viewLabel = if (carViewMode == CarViewMode.EXTERIOR) {
            "3D Exterior View"
        } else {
            "3D Interior View"
        }
        Text(
            state = TextState(text = viewLabel.TR),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFF9E9E9E),
            ),
        )

        // Hotspot overlay icons (only in exterior mode)
        if (carViewMode == CarViewMode.EXTERIOR) {
            hotspots.forEach { hotspot ->
                HotspotIcon(
                    hotspot = hotspot,
                    onClick = { onHotspotClick(hotspot) },
                )
            }
        }
    }
}

@Composable
private fun BoxScope.HotspotIcon(
    hotspot: VehicleHotspot,
    onClick: () -> Unit,
) {
    val config = LocalConfiguration.current
    val xOffset = ((hotspot.xFraction - 0.5f) * config.screenWidthDp * 0.4f).dp
    val yOffset = ((hotspot.yFraction - 0.5f) * config.screenHeightDp * 0.4f).dp

    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(x = xOffset, y = yOffset)
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.9f), CircleShape)
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            source = IconSource.Resource(hotspot.iconRes, contentDescription = hotspot.label),
            config = IconConfig(size = IconConfig.Size.SM),
        )
    }
}
