package com.ui.audi.widgets.imagecontainer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ui.core.engine.api.Sem
import com.ui.core.styles.ImageContainerStyle
import com.ui.core.styles.LocalImageContainerStyle
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.imagecontainer.ImageContainerConfig

// ── Default style ──────────────────────────────────────────────────────────────

/** Audi brand [ImageContainerStyle] derived from the current composition tokens. */
internal object AudiImageContainerDefaults {
    @Composable
    fun style(): ImageContainerStyle =
        ImageContainerStyle(
            cornerRadius =
                Sem.BorderRadius.None
                    .dimension()
                    .pxToDp(),
        )
}

// ── Audi Image Container composable ────────────────────────────────────────────

/**
 * Audi brand implementation of the Image Container widget (CC_0052).
 *
 * Renders the caller-provided [content] slot inside a clipped container.
 * When a non-Free aspect ratio is configured the ratio is enforced; otherwise
 * the container wraps the slot's intrinsic size. The widget never imposes
 * width or height — callers size it via [modifier].
 */
@Composable
internal fun AudiImageContainer(
    config: ImageContainerConfig,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    val style = LocalImageContainerStyle.current
    val shape = RoundedCornerShape(style.cornerRadius)
    val ratio = config.aspectRatio.ratio

    Box(
        modifier =
            modifier
                .then(if (ratio != null) Modifier.aspectRatio(ratio) else Modifier)
                .clip(shape),
    ) {
        content()
    }
}
