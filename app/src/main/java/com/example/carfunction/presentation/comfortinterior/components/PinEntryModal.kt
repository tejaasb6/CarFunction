package com.example.carfunction.presentation.comfortinterior.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.carfunction.R
import com.ui.core.widgets.icons.Icon
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.icons.IconSource
import com.ui.core.widgets.text.Text
import com.ui.core.widgets.text.TextState
import com.ui.core.widgets.text.TR

private const val PIN_LENGTH = 4

/**
 * PIN Entry Modal overlay for Glovebox PIN setup.
 *
 * Displays:
 * - Title: "Set Glovebox PIN"
 * - 4 dot indicators showing entry progress
 * - Backspace icon (drawable resource)
 * - 3x4 numeric keypad
 *
 * Uses [IconSource.Resource] — no Material Icons dependency.
 */
@Composable
fun PinEntryModal(
    enteredDigits: List<Int>,
    onDigitEntered: (Int) -> Unit,
    onBackspace: () -> Unit,
    onDismiss: () -> Unit,
) {
    // Semi-transparent overlay
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center,
    ) {
        // Modal card
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .clickable(enabled = false, onClick = {}) // Prevent dismiss on card click
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // ── Title ──────────────────────────────────────────────────────
            Text(
                state = TextState(text = "Set Glovebox PIN".TR),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                ),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── PIN Dots + Backspace ───────────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    repeat(PIN_LENGTH) { index ->
                        val isFilled = index < enteredDigits.size
                        PinDot(isFilled = isFilled)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Backspace icon
                Icon(
                    source = IconSource.Resource(
                        R.drawable.ic_backspace,
                        contentDescription = "Delete",
                    ),
                    config = IconConfig(size = IconConfig.Size.SM),
                    modifier = Modifier
                        .clickable(onClick = onBackspace)
                        .padding(4.dp),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Numeric Keypad (3x4 grid) ──────────────────────────────────
            val keyRows = listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9),
                listOf(-1, 0, -1), // -1 = empty slot
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                keyRows.forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        row.forEach { digit ->
                            if (digit >= 0) {
                                NumericKey(
                                    digit = digit,
                                    onClick = { onDigitEntered(digit) },
                                )
                            } else {
                                // Empty spacer for layout alignment
                                Spacer(modifier = Modifier.size(56.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Single PIN progress dot — filled when entered, outlined when pending.
 */
@Composable
private fun PinDot(isFilled: Boolean) {
    val bgColor = if (isFilled) Color.Black else Color.Transparent
    val borderColor = if (isFilled) Color.Black else Color(0xFFCCCCCC)

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .border(2.dp, borderColor, CircleShape),
    )
}

/**
 * Single numeric keypad button.
 */
@Composable
private fun NumericKey(
    digit: Int,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(shape)
            .background(Color(0xFFF0F0F0), shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            state = TextState(text = "$digit".TR),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
            ),
        )
    }
}
