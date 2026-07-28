package com.ui.audi.widgets.tiles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.focus.focusableWithRing
import com.ui.core.indication.rememberBrandIndication
import com.ui.core.interaction.ClickOptions
import com.ui.core.interaction.interactiveClickable
import com.ui.core.utils.pxToDp
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.tiles.LocalTileStyle
import com.ui.core.widgets.tiles.TileBranchColors
import com.ui.core.widgets.tiles.TileConfig
import com.ui.core.widgets.tiles.TileInteractionConfig
import com.ui.core.widgets.tiles.TileState
import com.ui.core.widgets.tiles.TileStateColors

/** Audi brand implementation of the Tile widget. See [com.ui.core.widgets.tiles.Tile]. */
@Suppress("CyclomaticComplexMethod", "LongParameterList", "LongMethod")
@Composable
internal fun Tile(
    config: TileConfig,
    modifier: Modifier = Modifier,
    state: TileState = TileState(),
    interactionConfig: TileInteractionConfig = TileInteractionConfig(),
    content: @Composable () -> Unit = {},
) {
    val enabled = state.enabled
    val isSelected = state.isSelected
    val isFocused = state.isFocused
    val style = LocalTileStyle.current
    val typeColors = style.colors
    val shape = RoundedCornerShape(style.cornerRadius)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val restrictions = LocalUxRestrictions.current
    val effectiveEnabled = enabled && (!restrictions.isMoving || interactionConfig.isDistractionOptimized)

    val branch = if (isSelected) typeColors.selected else typeColors.unselected
    val stateColors = resolveStateColors(branch, effectiveEnabled, isPressed)
    val hasBorder =
        stateColors.border !=
            Sem.Color.Stroke.Transparent
                .color() &&
            stateColors.border != Color.Transparent

    val indication = rememberBrandIndication(darkBackground = false)
    val clickOptions =
        ClickOptions(
            onClick = interactionConfig.onClick,
            onLongClick = interactionConfig.onLongClick,
            onDoubleClick = interactionConfig.onDoubleClick,
            debounceMs = interactionConfig.clickDebounceMs,
        )

    val touchTargetDp =
        Sem.Size.TouchTarget.MD
            .dimension()
            .pxToDp()
    val sizeModifier =
        when (config.mode) {
            TileConfig.Mode.Hug ->
                Modifier.defaultMinSize(
                    minWidth = maxOf(style.minWidth, touchTargetDp),
                    minHeight = maxOf(style.minHeight, touchTargetDp),
                )
            TileConfig.Mode.Fill -> Modifier.fillMaxWidth().defaultMinSize(minHeight = maxOf(style.minHeight, touchTargetDp))
        }

    val borderModifier =
        if (hasBorder) {
            val tok =
                if (isSelected) {
                    when {
                        !effectiveEnabled -> Cmp.BorderWidth.Action.Tile.Selected.Disabled
                        isPressed -> Cmp.BorderWidth.Action.Tile.Selected.Pressed
                        else -> Cmp.BorderWidth.Action.Tile.Selected.Idle
                    }
                } else {
                    when {
                        !effectiveEnabled -> Cmp.BorderWidth.Action.Tile.Unselected.Disabled
                        isPressed -> Cmp.BorderWidth.Action.Tile.Unselected.Pressed
                        else -> Cmp.BorderWidth.Action.Tile.Unselected.Idle
                    }
                }
            Modifier.border(tok.dimension().pxToDp(), stateColors.border, shape)
        } else {
            Modifier
        }

    val disabledAlphaModifier = if (!effectiveEnabled) Modifier.alpha(Sem.Opacity.Disabled.opacity()) else Modifier
    val forcedFocusRingModifier =
        if (isFocused) {
            Modifier.border(
                width =
                    Sem.BorderWidth.FocusRing
                        .dimension()
                        .pxToDp(),
                color =
                    Sem.Color.Stroke.Signal.Focus
                        .color(),
                shape = shape,
            )
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .then(sizeModifier)
                .then(disabledAlphaModifier)
                .clip(shape)
                .background(branch.surfaceFill)
                .then(borderModifier)
                .then(forcedFocusRingModifier)
                .focusableWithRing(
                    interactionSource = interactionSource,
                    shape = shape,
                    ringColor =
                        Sem.Color.Stroke.Signal.Focus
                            .color(),
                    ringWidth =
                        Sem.BorderWidth.FocusRing
                            .dimension()
                            .pxToDp(),
                    focusRequester = interactionConfig.focusRequester,
                ).interactiveClickable(
                    clickOptions = clickOptions,
                    interactionSource = interactionSource,
                    enabled = effectiveEnabled,
                    indication = indication,
                ),
        contentAlignment = Alignment.Center,
    ) {
        if (isPressed && effectiveEnabled) {
            Box(modifier = Modifier.matchParentSize().background(branch.stateLayerPressed))
        }

        Box(
            modifier = Modifier.padding(style.padding),
        ) {
            content()
        }
    }
}

private fun resolveStateColors(
    branch: TileBranchColors,
    enabled: Boolean,
    isPressed: Boolean,
): TileStateColors =
    when {
        !enabled -> branch.disabled
        isPressed -> branch.pressed
        else -> branch.idle
    }
