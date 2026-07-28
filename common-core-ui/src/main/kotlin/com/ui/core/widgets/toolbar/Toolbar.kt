package com.ui.core.widgets.toolbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.core.widgets.LocalWidgets

/**
 * Brand-agnostic toolbar.
 *
 * This is the **only** toolbar API that app code should call.
 * The actual rendering is delegated to the brand implementation supplied via
 * [LocalWidgets.Toolbar] — set by `AudiTheme` or `LamborghiniTheme`.
 */
@Composable
fun AppToolbar(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {},
) {
    LocalWidgets.Toolbar.current(title, modifier, actions)
}
