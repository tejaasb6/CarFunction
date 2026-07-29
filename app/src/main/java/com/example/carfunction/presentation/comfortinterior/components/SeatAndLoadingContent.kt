package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.carfunction.domain.model.SeatLoadingFunction
import com.example.carfunction.domain.model.SeatLoadingSection
import com.example.carfunction.presentation.comfortinterior.ComfortInteriorContract
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Seat- & Loading content section.
 *
 * Displays two grouped sections — "Seat Functions" and "Loading Functions" —
 * with selectable list items. The selected item shows a black rounded pill highlight.
 */
@Composable
fun SeatAndLoadingContent(
    state: ComfortInteriorContract.State,
    dispatch: (ComfortInteriorContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        SeatLoadingSection.entries.forEach { section ->
            SectionHeader(title = section.label)
            Spacer(modifier = Modifier.height(8.dp))

            SeatLoadingFunction.entries
                .filter { it.section == section }
                .forEach { function ->
                    val isSelected = function == state.selectedSeatLoadingFunction
                    SelectableListItem(
                        label = function.label,
                        isSelected = isSelected,
                        onClick = {
                            dispatch(
                                ComfortInteriorContract.Intent.SelectSeatLoadingFunction(function),
                            )
                        },
                    )
                }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * A selectable list item with a black rounded pill highlight when selected.
 * Matches the Audi MMI design pattern for settings list items.
 */
@Composable
internal fun SelectableListItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(24.dp)
    val bgColor = if (isSelected) Color.Black else Color.Transparent
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(shape)
            .background(bgColor, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            state = TextState(text = label.TR),
            style = TextStyle(
                color = textColor,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            ),
        )
    }
}
