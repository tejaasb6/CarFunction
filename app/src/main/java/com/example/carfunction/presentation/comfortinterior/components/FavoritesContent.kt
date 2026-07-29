package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfunction.domain.model.FavoriteZone
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Favorites content section.
 *
 * Displays the interactive cockpit zone labels:
 * - Left Satellite
 * - Right Satellite
 * - Climate Favorite
 * - Center Control Unit
 *
 * In the full implementation, these labels would overlay the 3D cockpit
 * visualization on the right pane. Here they are listed as selectable items.
 */
@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        SectionHeader(title = "Cockpit Zones")

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            state = TextState(
                text = "Tap a zone on the 3D cockpit to configure its controls.".TR,
            ),
            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
        )

        Spacer(modifier = Modifier.height(16.dp))

        FavoriteZone.entries.forEach { zone ->
            SelectableListItem(
                label = zone.label,
                isSelected = false,
                onClick = { /* Navigate to zone detail in future */ },
            )
        }
    }
}
