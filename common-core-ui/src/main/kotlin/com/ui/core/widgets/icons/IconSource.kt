package com.ui.core.widgets.icons

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Discriminated union of all supported icon content sources.
 *
 * Pass one of the concrete subclasses to [Icon] instead of a raw composable slot.
 *
 * ```kotlin
 * // Vector icon (material icons, custom ImageVector)
 * Icon(source = IconSource.Vector(Icons.Filled.Add, contentDescription = "Add"))
 *
 * // Drawable resource (R.drawable.*)
 * Icon(source = IconSource.Resource(R.drawable.ic_settings, contentDescription = "Settings"))
 *
 * // Custom Painter
 * Icon(source = IconSource.FromPainter(myPainter, contentDescription = "Custom"))
 *
 * // ImageBitmap
 * Icon(source = IconSource.Bitmap(myBitmap, contentDescription = "Photo"))
 * ```
 */
@Immutable
sealed class IconSource {
    /** Accessibility description for the icon. Null if purely decorative. */
    abstract val contentDescription: String?

    /**
     * A vector icon — [androidx.compose.ui.graphics.vector.ImageVector].
     *
     * Use for Material Icons, custom vector assets, etc.
     *
     * @param imageVector the vector graphic to render.
     * @param contentDescription accessibility label; null if decorative.
     */
    @Immutable
    data class Vector(
        val imageVector: ImageVector,
        override val contentDescription: String? = null,
    ) : IconSource()

    /**
     * A drawable resource — `R.drawable.*`.
     *
     * Supports both vector drawables and raster drawables.
     * Internally uses [androidx.compose.ui.res.painterResource].
     *
     * @param resId the drawable resource ID.
     * @param contentDescription accessibility label; null if decorative.
     */
    @Immutable
    data class Resource(
        @DrawableRes val resId: Int,
        override val contentDescription: String? = null,
    ) : IconSource()

    /**
     * A custom [Painter] — for pre-built or dynamic painters.
     *
     * @param painter the painter to render.
     * @param contentDescription accessibility label; null if decorative.
     */
    @Immutable
    data class FromPainter(
        val painter: Painter,
        override val contentDescription: String? = null,
    ) : IconSource()

    /**
     * An [ImageBitmap] — for raster images used as icons.
     *
     * @param bitmap the bitmap to render.
     * @param contentDescription accessibility label; null if decorative.
     */
    @Immutable
    data class Bitmap(
        val bitmap: ImageBitmap,
        override val contentDescription: String? = null,
    ) : IconSource()
}
