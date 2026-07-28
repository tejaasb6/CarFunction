package com.ui.core.widgets.searchfields

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Per-state colour set for one search-field type-axis combination.
 *
 * Each combination of *(default / filled)* x *(idle / pressed / active / disabled / loading)*
 * is fully specified so the brand implementation never needs conditional logic beyond a
 * simple lookup.
 *
 * @property surfaceFill      Background fill of the field container.
 * @property border           Border / stroke colour.
 * @property borderWidth      Border / stroke width.
 * @property stateLayerFill   Semi-transparent overlay drawn over the surface for pressed etc.
 * @property focusBorder      Focus-ring border colour (D-pad / rotary).
 * @property focusBorderWidth Focus-ring border width.
 * @property placeholderColor Colour of the placeholder text.
 * @property inputTextColor   Colour of user-entered text **and** cursor.
 * @property iconColor        Colour applied to leading / trailing icon slots.
 * @property hintColor        Colour of the hint text below the field.
 */
@Immutable
data class SearchFieldStateColors(
    val surfaceFill: Color,
    val border: Color,
    val borderWidth: Dp,
    val stateLayerFill: Color,
    val focusBorder: Color,
    val focusBorderWidth: Dp,
    val placeholderColor: Color,
    val inputTextColor: Color,
    val iconColor: Color,
    val hintColor: Color,
)

/**
 * Full visual specification for [SearchField].
 *
 * Holds dimensions, typography, and per-variant colour sets covering every
 * combination of fill-state and interaction-state.
 *
 * Override at any level of the composition tree via [LocalSearchFieldStyle]:
 * ```kotlin
 * CompositionLocalProvider(LocalSearchFieldStyle provides customStyle) {
 *     SearchField(...)
 * }
 * ```
 *
 * ### Leading icon sizing
 * Icon width / height are derived from `Cmp.Size.DataDisplay.Icon.MD.*` tokens.
 *
 * ### Trailing button sizing
 * The trailing button is typed as a ComponentButton in the design system.
 * Width / height are derived from `Cmp.Size.Action.ComponentButton.MD.StateLayer.*` tokens.
 *
 * @property fieldHeight                  Height of the search field container.
 * @property cornerRadius                 Corner radius of the field (pill-shaped by default).
 * @property horizontalPadding            Horizontal padding inside the field container.
 * @property iconGap                      Horizontal gap between leading icon and the text area.
 * @property trailingGap                  Horizontal gap between text area and trailing button.
 * @property hintTopPadding               Vertical gap between the field container and hint text.
 * @property hintStartPadding             Start padding for hint text (aligns with caption area).
 * @property leadingIconWidth             Width of the leading icon slot (DataDisplay.Icon.MD).
 * @property leadingIconHeight            Height of the leading icon slot (DataDisplay.Icon.MD).
 * @property trailingButtonWidth          Width of the trailing button slot (ComponentButton.MD).
 * @property trailingButtonHeight         Height of the trailing button slot (ComponentButton.MD).
 * @property inputTextStyle               Typography for user-entered text.
 * @property placeholderTextStyle         Typography for placeholder text.
 * @property hintTextStyle                Typography for hint text below the field.
 * @property defaultIdle                  Colours for default (unfilled) idle state.
 * @property defaultPressed               Colours for default (unfilled) pressed state.
 * @property defaultActive                Colours for default (unfilled) active / editing state.
 * @property defaultDisabled              Colours for default (unfilled) disabled state.
 * @property filledIdle                   Colours for filled idle state.
 * @property filledPressed                Colours for filled pressed state.
 * @property filledActive                 Colours for filled active / editing state.
 * @property filledDisabled               Colours for filled disabled state.
 * @property filledLoading                Colours for filled loading state.
 */
@Immutable
data class SearchFieldStyle(
    // -- Dimensions --
    val fieldHeight: Dp,
    val cornerRadius: Dp,
    val horizontalPadding: Dp,
    val iconGap: Dp,
    val trailingGap: Dp,
    val hintTopPadding: Dp,
    val hintStartPadding: Dp,
    // -- Leading icon (DataDisplay.Icon.MD) --
    val leadingIconWidth: Dp,
    val leadingIconHeight: Dp,
    // -- Trailing button (ComponentButton.MD.StateLayer) --
    val trailingButtonWidth: Dp,
    val trailingButtonHeight: Dp,
    // -- Typography --
    val inputTextStyle: TextStyle,
    val placeholderTextStyle: TextStyle,
    val hintTextStyle: TextStyle,
    // -- Default (unfilled) states --
    val defaultIdle: SearchFieldStateColors,
    val defaultPressed: SearchFieldStateColors,
    val defaultActive: SearchFieldStateColors,
    val defaultDisabled: SearchFieldStateColors,
    // -- Filled states --
    val filledIdle: SearchFieldStateColors,
    val filledPressed: SearchFieldStateColors,
    val filledActive: SearchFieldStateColors,
    val filledDisabled: SearchFieldStateColors,
    val filledLoading: SearchFieldStateColors,
)

/**
 * Composition local for [SearchFieldStyle].
 *
 * Provided by brand themes (e.g. `AudiTheme`, `LamborghiniTheme`).
 * Throws if accessed outside a theme scope.
 */
val LocalSearchFieldStyle =
    compositionLocalOf<SearchFieldStyle> {
        error("No SearchFieldStyle — wrap content in AudiTheme / LamborghiniTheme")
    }
