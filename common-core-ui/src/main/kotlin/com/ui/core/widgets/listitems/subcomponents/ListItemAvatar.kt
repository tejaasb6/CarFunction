package com.ui.core.widgets.listitems.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * **LeadingContent / Variant=UserPicture** sub-widget for [com.ui.core.widgets.listitems.ListItem].
 *
 * Renders a circular avatar container. All dimensions are resolved from
 * [LocalListItemSubComponentStyle] design tokens — nothing is hardcoded.
 *
 * When [photo] is provided it fills the clipped container (profile picture, URI-loaded image, etc.).
 * When `null`, [initials] is rendered instead — centred within a [backgroundColor] circle.
 *
 * ## Usage
 * ```kotlin
 * // With photo / profile picture
 * ListItem(
 *     slots = ListItemSlots(
 *         leadingContent = {
 *             ListItemAvatar(
 *                 size = ListItemAvatarSize.XS,
 *                 photo = {
 *                     AsyncImage(model = profileUrl, contentDescription = "Profile")
 *                 },
 *             )
 *         },
 *     ),
 * )
 *
 * // With initials fallback
 * ListItem(
 *     slots = ListItemSlots(
 *         leadingContent = {
 *             ListItemAvatar(
 *                 size = ListItemAvatarSize.XS,
 *                 backgroundColor = Color(0xFFCE93D8),
 *                 initials = { Text(text = "PK", color = Color.White) },
 *             )
 *         },
 *     ),
 * )
 * ```
 *
 * @param modifier        Modifier applied to the outer container.
 * @param size            Avatar size enum — resolved to token dp from [LocalListItemSubComponentStyle].
 * @param photo           Composable slot for the profile photo / picture / URI-loaded image.
 *                        Takes priority over [initials]. Consumer provides `Image`, `AsyncImage`,
 *                        or any composable that fills the circular container.
 * @param initials        Composable slot rendered when [photo] is `null`.
 *                        Typically a `Text` composable with 1–2 character initials.
 *                        Automatically centred both horizontally and vertically.
 * @param backgroundColor Background colour behind [initials]. Ignored when [photo] is provided.
 *                        Defaults to the avatar fill colour from [LocalListItemSubComponentStyle].
 * @see ListItemAvatarSize
 * @see ListItemSubComponentStyle
 * @see LocalListItemSubComponentStyle
 */
@Composable
fun ListItemAvatar(
    modifier: Modifier = Modifier,
    size: ListItemAvatarSize = ListItemAvatarSize.XS,
    photo: (@Composable () -> Unit)? = null,
    initials: (@Composable () -> Unit)? = null,
    backgroundColor: Color = Color.Unspecified,
) {
    val style = LocalListItemSubComponentStyle.current
    val sizeDp = size.resolve(style)
    val shape = RoundedCornerShape(style.avatarCornerRadius)
    val effectiveBg = if (backgroundColor == Color.Unspecified) style.avatarFillColor else backgroundColor

    Box(
        modifier =
            modifier
                .size(sizeDp)
                .clip(shape),
        contentAlignment = Alignment.Center,
    ) {
        val img = photo
        if (img != null) {
            // Photo fills the entire clipped area
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                img()
            }
        } else {
            // Initials: coloured background + centred text
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(effectiveBg),
                contentAlignment = Alignment.Center,
            ) {
                val init = initials
                if (init != null) {
                    init()
                }
            }
        }
    }
}

/**
 * Avatar size axis — resolved to dp via [ListItemSubComponentStyle] tokens.
 *
 * Maps to `Cmp.Size.Navigation.Avatar.{XS,SM,MD}.VisualSize`.
 *
 * @see ListItemAvatar
 * @see ListItemSubComponentStyle
 */
enum class ListItemAvatarSize {
    /** Extra-small avatar — maps to `Cmp.Size.Navigation.Avatar.XS.VisualSize`. */
    XS,

    /** Small avatar — maps to `Cmp.Size.Navigation.Avatar.SM.VisualSize`. */
    S,

    /** Medium avatar — maps to `Cmp.Size.Navigation.Avatar.MD.VisualSize`. */
    MD,

    ;

    /**
     * Resolves the concrete [Dp] dimension from the style's token-derived values.
     *
     * @param style The [ListItemSubComponentStyle] providing avatar size tokens.
     * @return The resolved avatar size in [Dp].
     */
    fun resolve(style: ListItemSubComponentStyle): Dp =
        when (this) {
            XS -> style.avatarSizeXS
            S -> style.avatarSizeS
            MD -> style.avatarSizeMD
        }

    /**
     * Resolves the matching typography [TextStyle][androidx.compose.ui.text.TextStyle] from the style.
     *
     * @param style The [ListItemSubComponentStyle] providing avatar label typography tokens.
     * @return The resolved [TextStyle][androidx.compose.ui.text.TextStyle] for the avatar label.
     */
    @Composable
    fun textStyle(style: ListItemSubComponentStyle): androidx.compose.ui.text.TextStyle =
        when (this) {
            XS -> style.avatarLabelTextStyleXS
            S -> style.avatarLabelTextStyleS
            MD -> style.avatarLabelTextStyleMD
        }
}
