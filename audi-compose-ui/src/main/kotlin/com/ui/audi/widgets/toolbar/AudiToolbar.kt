package com.ui.audi.widgets.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ui.core.engine.api.Sem
import com.ui.core.styles.LocalToolbarStyle
import com.ui.core.styles.ToolbarStyle
import com.ui.core.utils.pxToDp
import com.ui.core.utils.pxToSp
import com.ui.core.widgets.text.Text

// ── Default style ──────────────────────────────────────────────────────────────

internal object AudiToolbarDefaults {
    @Composable
    fun style(): ToolbarStyle =
        ToolbarStyle(
            backgroundColor =
                Sem.Color.Fill.Canvas
                    .color(),
            titleColor =
                Sem.Color.Content.Primary
                    .color(),
            titleStyle =
                TextStyle(
                    fontSize =
                        Sem.FontSize._400
                            .font()
                            .pxToSp(),
                    fontWeight = FontWeight.Bold,
                ),
            height = 56.dp,
            paddingHorizontal =
                Sem.Space.Fixed._300
                    .dimension()
                    .pxToDp(),
            elevation = 4.dp,
        )
}

// ── Widget implementation ──────────────────────────────────────────────────────

@Composable
internal fun AudiToolbar(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {},
) {
    val style = LocalToolbarStyle.current

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(style.height)
                .background(style.backgroundColor)
                .padding(horizontal = style.paddingHorizontal),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = style.titleStyle,
            color = style.titleColor,
            modifier = Modifier.weight(1f),
        )
        actions()
    }
}
