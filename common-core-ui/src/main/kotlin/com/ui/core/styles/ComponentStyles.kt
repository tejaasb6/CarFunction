package com.ui.core.styles

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.ui.core.widgets.buttons.ButtonConfig
import com.ui.core.widgets.buttons.ButtonStyle

// ── CardStyle ──────────────────────────────────────────────────────────────────

/** Full visual specification for [com.ui.core.widgets.cards.AppCard]. */
data class CardStyle(
    val backgroundColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
    val cornerRadius: Dp,
    val elevation: Dp,
    val minHeight: Dp,
    val contentPaddingHorizontal: Dp,
    val contentPaddingVertical: Dp,
)

// ── ToolbarStyle ───────────────────────────────────────────────────────────────

/** Full visual specification for [com.ui.core.widgets.toolbar.AppToolbar]. */
data class ToolbarStyle(
    val backgroundColor: Color,
    val titleColor: Color,
    val titleStyle: TextStyle,
    val height: Dp,
    val paddingHorizontal: Dp,
    val elevation: Dp,
)

data class TextStyleConfig(
    val normal: TextStyle,
    val paragraph: TextStyle,
    val body: TextStyle,
    val label: TextStyle,
    val enabledColor: Color,
    val disabledColor: Color,
)

// ── ActionCardStyle ────────────────────────────────────────────────────────────

/**
 * Full visual specification for [com.ui.core.widgets.actioncard.AppActionCard].
 *
 * [actionButtonStyle] is provided to [LocalButtonStyle][com.ui.core.widgets.buttons.LocalButtonStyle]
 * **only inside** the card's composition subtree, so the CTA button uses a different look
 * without affecting the global button style used elsewhere on the screen.
 *
 * [actionButtonType] selects which colour set from [actionButtonStyle] the CTA
 * button renders with. Defaults to [ButtonConfig.Tone.Primary].
 */
data class ActionCardStyle(
    val titleStyle: TextStyle,
    val titleColor: Color,
    val bodyStyle: TextStyle,
    val bodyColor: Color,
    val titleBodySpacing: Dp,
    val bodyButtonSpacing: Dp,
    val actionButtonStyle: ButtonStyle,
    val actionButtonType: ButtonConfig.Tone = ButtonConfig.Tone.Primary,
)

// ── ImageContainerStyle ────────────────────────────────────────────────────────

/**
 * Visual specification for [com.ui.core.widgets.imagecontainer.ImageContainer].
 *
 * The Image Container is a purely presentational widget with a single
 * token-driven property: its corner radius, resolved from
 * `Sem.BorderRadius.Image`.
 *
 * Override at any level of the composition tree via [LocalImageContainerStyle].
 */
@Immutable
data class ImageContainerStyle(
    // ── Shape ───────────────────────────────────────────────────────────────
    // Corner radius applied to all four corners of the container.agi
    val cornerRadius: Dp,
)

// ── Composition locals ────────────────────────────────────────────────────────

val LocalImageContainerStyle =
    compositionLocalOf<ImageContainerStyle> {
        error("No ImageContainerStyle — wrap content in AudiTheme / LamborghiniTheme")
    }

val LocalButtonStyle =
    compositionLocalOf<ButtonStyle> {
        error("No ButtonStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
val LocalCardStyle =
    compositionLocalOf<CardStyle> {
        error("No CardStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
val LocalToolbarStyle =
    compositionLocalOf<ToolbarStyle> {
        error("No ToolbarStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
val LocalActionCardStyle =
    compositionLocalOf<ActionCardStyle> {
        error("No ActionCardStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
val LocalTextStyle =
    compositionLocalOf<TextStyleConfig> {
        error("No TextStyleConfig — wrap content in Theme")
    }
