package com.example.carfunction.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Placeholder screens for tabs other than MyCar.
 * Each follows the same pattern — ready for dedicated MVI implementation.
 * Uses the design-system [Text] widget instead of Material3.
 */
@Composable
fun ChargingScreen(modifier: Modifier = Modifier) {
    PlaceholderContent(title = "Charging", modifier = modifier)
}

@Composable
fun DrivingAssistanceScreen(modifier: Modifier = Modifier) {
    PlaceholderContent(title = "Driving Assistance", modifier = modifier)
}

@Composable
fun DrivingExteriorScreen(modifier: Modifier = Modifier) {
    PlaceholderContent(title = "Driving & Exterior", modifier = modifier)
}

@Composable
private fun PlaceholderContent(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            state = TextState(text = title.TR),
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.Black,
            ),
        )
    }
}
