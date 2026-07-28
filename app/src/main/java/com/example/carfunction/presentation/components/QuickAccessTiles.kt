package com.example.carfunction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfunction.domain.model.QuickAccessFeature
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Row of quick-access feature tiles with an Add button.
 * Displays up to maxSlots features + 1 "Add" tile.
 *
 * All tiles share equal width via [weight] and a square aspect ratio so
 * that icons and multi-line labels are never clipped.
 */
@Composable
fun QuickAccessTiles(
    features: List<QuickAccessFeature>,
    maxSlots: Int,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        features.take(maxSlots).forEach { feature ->
            FeatureTile(
                feature = feature,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
        }

        // Add tile — fills remaining slot(s)
        val remaining = maxSlots - features.take(maxSlots).size
        if (remaining > 0) {
            AddTile(
                onClick = onAddClick,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
        }
    }
}

@Composable
private fun FeatureTile(
    feature: QuickAccessFeature,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape)
            .border(1.dp, Color(0xFFE0E0E0), shape)
            .background(Color.White)
            .padding(10.dp),
    ) {
        Icon(
            source = IconSource.Vector(feature.icon, contentDescription = feature.label),
            config = IconConfig(size = IconConfig.Size.SM),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            state = TextState(text = feature.label.TR, maxLines = 3),
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 9.sp,
                color = Color.Black,
                lineHeight = 12.sp,
            ),
        )
    }
}

@Composable
private fun AddTile(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape)
            .border(1.dp, Color(0xFFE0E0E0), shape)
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(50))
                .border(1.5.dp, Color.Black, RoundedCornerShape(50)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                source = IconSource.Vector(Icons.Filled.Add, contentDescription = "Add"),
                config = IconConfig(size = IconConfig.Size.SM),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            state = TextState(text = "Add".TR),
            style = TextStyle(
                fontSize = 9.sp,
                color = Color.Black,
            ),
        )
    }
}
