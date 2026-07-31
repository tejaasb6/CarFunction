/*
 * Copyright (C) 2025 - 2025, Audi. All rights reserved.
 */

package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carfunction.domain.model.ComfortSubSection
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

/**
 * Left sidebar vertical navigation menu for the Comfort & Interior screen.
 *
 * Displays all sub-section items vertically with a black rounded pill highlight
 * on the currently selected item. Matches the reference Audi MMI sidebar layout:
 * - White background panel
 * - Items left-aligned with consistent vertical spacing (~24dp between items)
 * - Selected item: black capsule with white text
 * - Unselected items: plain black text
 */
@Composable
fun ComfortSidebar(
    selectedSection: ComfortSubSection,
    visibleSections: List<ComfortSubSection>,
    onSectionSelected: (ComfortSubSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 24.dp),
    ) {
        visibleSections.forEachIndexed { index, section ->
            if (index > 0) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            val isSelected = section == selectedSection
            SidebarItem(
                label = section.label,
                isSelected = isSelected,
                onClick = { onSectionSelected(section) },
            )
        }
    }
}

@Composable
private fun SidebarItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(24.dp)
    val bgColor = if (isSelected) Color.Black else Color.Transparent
    val textColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clip(shape)
            .background(bgColor, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            state = TextState(
                text = label.TR,
                maxLines = 1,
            ),
            style = TextStyle(
                color = textColor,
                fontSize = 15.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            ),
        )
    }
}
