package com.ui.audi.widgets.buttongroups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ui.core.widgets.buttongroups.ButtonGroupConfig
import com.ui.core.widgets.buttongroups.LocalButtonGroupStyle
import com.ui.core.widgets.buttongroups.gapForAlignment

/**
 * Audi brand impl of [com.ui.core.widgets.buttongroups.ButtonGroup].
 *
 * In horizontal mode each item receives equal weight so that all buttons
 * share the available width evenly — preventing the last button from
 * being squeezed when the total minimum widths exceed the parent.
 */
@Composable
internal fun ButtonGroup(
    config: ButtonGroupConfig,
    modifier: Modifier = Modifier,
    items: List<@Composable () -> Unit>,
) {
    val style = LocalButtonGroupStyle.current
    val gap = style.gapForAlignment(config.alignment)

    when (config.alignment) {
        ButtonGroupConfig.Alignment.Horizontal -> {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gap),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        item()
                    }
                }
            }
        }
        ButtonGroupConfig.Alignment.Vertical -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(gap),
            ) {
                items.forEach { item -> item() }
            }
        }
    }
}
