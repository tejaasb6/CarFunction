package com.ui.core.widgets.imagecontainer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Composable function type for the ImageContainer widget.
 *
 * Brand implementations must render the caller-provided [content] slot inside
 * a container that:
 * 1. When the aspect ratio is not [Free][ImageContainerAspectRatio.Free],
 *    enforces the ratio via `Modifier.aspectRatio()`.
 * 2. Clips its children to a rounded shape whose corner radius is resolved
 *    from the design-system token `Sem.BorderRadius.None`.
 * 3. Does **not** impose its own width or height — sizing is caller-driven.
 */
typealias ImageContainerWidgetContent = @Composable (
    config: ImageContainerConfig,
    modifier: Modifier,
    content: @Composable () -> Unit,
) -> Unit
