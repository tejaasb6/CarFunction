@file:Suppress("TooManyFunctions")

package com.ui.audi.widgets.sliders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ui.audi.R
import com.ui.core.engine.api.Sem
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.widgets.iconbuttons.IconButtonConfig
import com.ui.core.widgets.icons.IconConfig
import com.ui.core.widgets.semanticshapes.SemanticShape
import com.ui.core.widgets.semanticshapes.SemanticShapeConfig
import com.ui.core.widgets.sliders.LocalSliderStyle
import com.ui.core.widgets.sliders.SliderBranchColors
import com.ui.core.widgets.sliders.SliderConfig
import com.ui.core.widgets.sliders.SliderContent
import com.ui.core.widgets.sliders.SliderState
import com.ui.core.widgets.sliders.SliderStateColors
import com.ui.core.widgets.sliders.SliderStyle
import com.ui.core.widgets.text.TR
import com.ui.core.widgets.text.TextState
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.roundToInt
import com.ui.core.widgets.iconbuttons.IconButton as CoreIconButton
import com.ui.core.widgets.icons.Icon as CoreIcon
import com.ui.core.widgets.text.Text as DesignText

/**
 * Modifier that anchors at the physical top-left corner of the parent,
 * allowing unbounded overflow. Direction-agnostic (works in both LTR and RTL).
 */
private fun Modifier.absoluteTopLeftAnchor(): Modifier =
    this
        .fillMaxSize()
        .wrapContentSize(unbounded = true, align = BiasAbsoluteAlignment(-1f, -1f))

/**
 * Snaps a continuous value to the nearest discrete step.
 * When [steps] is null or ≤ 0 the value passes through unchanged.
 */
private fun snapToStep(
    raw: Float,
    steps: Int?,
): Float {
    if (steps == null || steps <= 0) return raw
    val clamped = raw.coerceIn(0f, 1f)
    val stepSize = 1f / steps
    return (kotlin.math.round(clamped / stepSize) * stepSize).coerceIn(0f, 1f)
}

@Suppress("CyclomaticComplexMethod", "LongParameterList", "LongMethod")
@Composable
internal fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    config: SliderConfig,
    modifier: Modifier,
    state: SliderState,
    content: SliderContent,
    valueEnd: Float = 1f,
    onValueEndChange: ((Float) -> Unit)? = null,
) {
    if (config.alignment == SliderConfig.Alignment.Vertical) {
        VSlider(value, onValueChange, config, modifier, state, content, valueEnd, onValueEndChange)
    } else {
        HSlider(value, onValueChange, config, modifier, state, content, valueEnd, onValueEndChange)
    }
}

/**
 * Manages value-popup visibility with a timer-based auto-hide.
 *
 * While [dragging] is `true` the popup is visible. After the user releases,
 * the popup stays visible for [delayMs] then fades out.
 *
 * Returns `true` when the popup should be shown.
 */
@Composable
private fun rememberPopupVisible(
    dragging: Boolean,
    delayMs: Long,
): Boolean {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(dragging) {
        if (dragging) {
            visible = true
        } else {
            delay(delayMs)
            visible = false
        }
    }
    return visible
}

@Suppress("LongMethod", "CyclomaticComplexMethod")
@Composable
private fun HSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    config: SliderConfig,
    modifier: Modifier,
    state: SliderState,
    content: SliderContent,
    valueEnd: Float,
    onValueEndChange: ((Float) -> Unit)?,
) {
    val style = LocalSliderStyle.current
    val ux = LocalUxRestrictions.current
    val enabled = state.enabled && !ux.isMoving
    var dragging by remember { mutableStateOf(false) }
    var draggingEnd by remember { mutableStateOf(false) }
    val isError = shouldApplyErrorBranch(config)
    val branch = if (isError && !dragging) style.colors.error else style.colors.default
    val sc = pickStateColors(branch, enabled, dragging)
    val disMod = if (!enabled) Modifier.alpha(Sem.Opacity.Disabled.opacity()) else Modifier
    val den = LocalDensity.current
    var wPx by remember { mutableFloatStateOf(0f) }
    var trackTopYPx by remember { mutableFloatStateOf(0f) }
    var trackCenterOffPx by remember { mutableFloatStateOf(0f) }
    var trackLeftOffPx by remember { mutableFloatStateOf(0f) }
    val iconBtn = content.iconButton

    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val popupVisible = rememberPopupVisible(dragging, style.popupAutoHideDelayMs)

    Box(modifier = modifier.then(disMod)) {
        var labelRowH by remember { mutableFloatStateOf(0f) }
        Column(Modifier.fillMaxWidth()) {
            val hasLabelRow = content.label != null || content.appendix != null
            if (hasLabelRow) {
                Row(
                    Modifier.fillMaxWidth().onSizeChanged { labelRowH = it.height.toFloat() },
                    horizontalArrangement =
                        if (isRtl) {
                            Arrangement.spacedBy(style.labelGroupGap, Alignment.End)
                        } else {
                            Arrangement.spacedBy(style.labelGroupGap, Alignment.Start)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    content.label?.let { labelRes ->
                        DesignText(
                            state = TextState(text = labelRes, maxLines = 1),
                            style = style.titleTextStyle.copy(color = sc.titleColor),
                        )
                    }
                    content.appendix?.let { appendixRes ->
                        DesignText(
                            state = TextState(text = appendixRes, maxLines = 1),
                            style = style.appendixTextStyle.copy(color = style.appendixColor),
                            modifier = Modifier.weight(1f, fill = false),
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Spacer(Modifier.height(style.labelGroupBottomPadding))
                trackTopYPx = labelRowH + with(den) { style.labelGroupBottomPadding.toPx() }
            } else {
                trackTopYPx = 0f
            }

            trackCenterOffPx = with(den) {
                style.minHeight.toPx()
            } / 2f
            val trackBlock: @Composable () -> Unit = {
                when (config.mode) {
                    SliderConfig.Mode.Split ->
                        HSplit(
                            value,
                            onValueChange,
                            config,
                            style,
                            sc,
                            branch,
                            enabled,
                            dragging,
                            { dragging = true },
                            { dragging = false },
                            Modifier.fillMaxWidth(),
                            isRtl,
                        ) {
                            wPx =
                                it
                        }
                    SliderConfig.Mode.Multi ->
                        HMulti(value, onValueChange, config, valueEnd, onValueEndChange, style, sc, branch, enabled, dragging, {
                            dragging = true
                            draggingEnd =
                                false
                        }, {
                            dragging = true
                            draggingEnd = true
                        }, { dragging = false }, Modifier.fillMaxWidth(), draggingEnd, isRtl) { wPx = it }
                    else ->
                        HSingle(
                            value,
                            onValueChange,
                            config,
                            style,
                            sc,
                            branch,
                            enabled,
                            dragging,
                            { dragging = true },
                            { dragging = false },
                            Modifier.fillMaxWidth(),
                            isRtl,
                        ) {
                            wPx =
                                it
                        }
                }
            }
            val hasMinMax = content.minLabel != null || content.maxLabel != null

            if (iconBtn != null) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(style.iconButtonGap),
                ) {
                    CoreIconButton(config = IconButtonConfig(tone = IconButtonConfig.Tone.Tertiary), icon = iconBtn)
                    Box(
                        Modifier
                            .weight(1f)
                            .height(style.minHeight)
                            .onGloballyPositioned {
                                trackLeftOffPx = it.positionInParent().x
                            }.onSizeChanged {
                                wPx = it.width.toFloat()
                            },
                    ) {
                        if (wPx > 0f && !popupVisible) {
                            val hPx = with(den) { style.handleWidth.toPx() }
                            val avail = wPx - hPx
                            val handleCenterPx = value.coerceIn(0f, 1f) * avail + hPx / 2f
                            HIdleValuePopup(wPx, handleCenterPx, config, value, valueEnd, style, sc, content, isRtl)
                        }
                        Box(
                            Modifier.fillMaxWidth().align(Alignment.Center),
                        ) { trackBlock() }

                        if (hasMinMax) {
                            HRangeLabels(content, sc, style, isRtl, Modifier.fillMaxWidth().align(Alignment.BottomStart))
                        }
                    }
                    Box(Modifier.graphicsLayer { rotationZ = 180f }) {
                        CoreIconButton(config = IconButtonConfig(tone = IconButtonConfig.Tone.Tertiary), icon = iconBtn)
                    }
                }
            } else {
                trackLeftOffPx = 0f
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(style.minHeight)
                        .onSizeChanged {
                            wPx = it.width.toFloat()
                        },
                ) {
                    if (wPx > 0f && !popupVisible) {
                        val hPx = with(den) { style.handleWidth.toPx() }
                        val avail = wPx - hPx
                        val handleCenterPx = value.coerceIn(0f, 1f) * avail + hPx / 2f
                        HIdleValuePopup(wPx, handleCenterPx, config, value, valueEnd, style, sc, content, isRtl)
                    }

                    Box(
                        Modifier.fillMaxWidth().align(Alignment.Center),
                    ) { trackBlock() }

                    if (hasMinMax) {
                        HRangeLabels(content, sc, style, isRtl, Modifier.fillMaxWidth().align(Alignment.BottomStart))
                    }
                }
            }

            CaptionGroup(content, config, style, sc, style.hCaptionGroupTopPadding, style.hCaptionGroupGap, isRtl, isDragging = dragging)
        }

        if (popupVisible && wPx > 0f) {
            val hPx = with(den) { style.handleWidth.toPx() }
            val avail = wPx - hPx
            val c = value.coerceIn(0f, 1f)
            val handleCenterPx = c * avail + hPx / 2f

            var popW by remember { mutableFloatStateOf(0f) }
            var popH by remember { mutableFloatStateOf(0f) }

            val labelCenterY = (labelRowH / 2f - popH / 2f)

            val rawPopupX =
                if (config.mode == SliderConfig.Mode.Multi) {
                    (wPx / 2f - popW / 2f).roundToInt()
                } else {
                    (handleCenterPx - popW / 2f).roundToInt()
                }
            val popupX = rawPopupX.coerceIn(0, (wPx - popW).roundToInt().coerceAtLeast(0))

            Box(
                Modifier
                    .offset { IntOffset(popupX, labelCenterY.roundToInt()) }
                    .onSizeChanged {
                        popW = it.width.toFloat()
                        popH = it.height.toFloat()
                    }.align(Alignment.TopStart),
            ) {
                ValPopup(value, config, valueEnd, style, sc, pressed = true, customValueDisplay = content.valueDisplay)
            }
        }
    }
}

@Suppress("CyclomaticComplexMethod")
@Composable
private fun HSingle(
    v: Float,
    onChange: (Float) -> Unit,
    cfg: SliderConfig,
    s: SliderStyle,
    sc: SliderStateColors,
    br: SliderBranchColors,
    en: Boolean,
    drg: Boolean,
    onS: () -> Unit,
    onE: () -> Unit,
    ext: Modifier,
    isRtl: Boolean,
    onW: (Float) -> Unit,
) {
    val den = LocalDensity.current
    var wPx by remember { mutableFloatStateOf(0f) }
    var rawV by remember { mutableFloatStateOf(v) }
    val drag =
        rememberDraggableState { d ->
            val h = with(den) { s.handleWidth.toPx() }
            val a = wPx - h
            if (a > 0f) {
                val c = rawV.coerceIn(0f, 1f) * a
                val delta = if (isRtl) -d else d
                val newRaw = (c + delta).coerceIn(0f, a) / a
                rawV = newRaw
                onChange(snapToStep(newRaw, cfg.steps))
            }
        }
    Box(
        ext
            .height(s.handleHeight)
            .onSizeChanged {
                wPx = it.width.toFloat()
                onW(it.width.toFloat())
            }.then(
                if (en) {
                    Modifier.draggable(drag, Orientation.Horizontal, onDragStarted = {
                        rawV = v
                        onS()
                    }, onDragStopped = { onE() })
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(s.trackHeight)
                .clip(RoundedCornerShape(s.trackCornerRadius))
                .background(sc.trackFill),
        )
        if (wPx <= 0f) return@Box
        val h = with(den) { s.handleWidth.toPx() }
        val a = wPx - h
        val c = v.coerceIn(0f, 1f)
        val progWPx = (h / 2f + c * a).coerceAtMost(wPx)
        val progW = with(den) { progWPx.toDp() }
        if (!en && (cfg.mode == SliderConfig.Mode.Temperature || cfg.mode == SliderConfig.Mode.Charging)) {
            Box(
                Modifier
                    .width(progW)
                    .height(s.progressHeight)
                    .clip(RoundedCornerShape(s.progressCornerRadius))
                    .background(progressFillColor(cfg, s, sc, en)),
            )
        } else if (cfg.mode == SliderConfig.Mode.Temperature) {
            Box(
                Modifier
                    .width(progW)
                    .height(s.progressHeight)
                    .clip(RoundedCornerShape(s.progressCornerRadius))
                    .background(Brush.horizontalGradient(listOf(s.colors.temperatureColdColor, s.colors.temperatureHotColor))),
            )
        } else if (cfg.mode == SliderConfig.Mode.Charging) {
            val progShape = RoundedCornerShape(s.progressCornerRadius)
            Box(
                Modifier
                    .width(progW)
                    .height(s.progressHeight)
                    .chargingGlow(
                        s.colors.chargingProgressFill,
                        s.chargingGlowAlpha,
                        with(den) { s.chargingGlowBlurRadius.toPx() },
                        with(den) { s.progressCornerRadius.toPx() },
                    ).clip(progShape)
                    .background(s.colors.chargingProgressFill),
            )
        } else {
            Box(
                Modifier
                    .width(progW)
                    .height(s.progressHeight)
                    .clip(RoundedCornerShape(s.progressCornerRadius))
                    .background(progressFillColor(cfg, s, sc, en)),
            )
        }
        Hdl((c * a).roundToInt(), s, sc, br, drg, en)
    }
}

@Composable
private fun HSplit(
    v: Float,
    onChange: (Float) -> Unit,
    cfg: SliderConfig,
    s: SliderStyle,
    sc: SliderStateColors,
    br: SliderBranchColors,
    en: Boolean,
    drg: Boolean,
    onS: () -> Unit,
    onE: () -> Unit,
    ext: Modifier,
    isRtl: Boolean,
    onW: (Float) -> Unit,
) {
    val den = LocalDensity.current
    var wPx by remember { mutableFloatStateOf(0f) }
    val gPx = with(den) { s.splitTrackGap.toPx() }
    var rawV by remember { mutableFloatStateOf(v) }
    val drag =
        rememberDraggableState { d ->
            val h = with(den) { s.handleWidth.toPx() }
            val a = wPx - h
            if (a > 0f) {
                val c = rawV.coerceIn(0f, 1f) * a
                val delta = if (isRtl) -d else d
                val newRaw = (c + delta).coerceIn(0f, a) / a
                rawV = newRaw
                onChange(snapToStep(newRaw, cfg.steps))
            }
        }
    Box(
        ext
            .height(s.handleHeight)
            .onSizeChanged {
                wPx = it.width.toFloat()
                onW(it.width.toFloat())
            }.then(
                if (en) {
                    Modifier.draggable(drag, Orientation.Horizontal, onDragStarted = {
                        rawV = v
                        onS()
                    }, onDragStopped = { onE() })
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (wPx <= 0f) return@Box
        val hg = gPx / 2f
        val cp = wPx / 2f
        Box(
            Modifier
                .width(with(den) { (cp - hg).toDp() })
                .height(s.trackHeight)
                .clip(RoundedCornerShape(s.trackCornerRadius))
                .background(sc.trackFill),
        )
        Box(
            Modifier
                .offset {
                    IntOffset((cp + hg).roundToInt(), 0)
                }.width(with(den) { (cp - hg).toDp() })
                .height(s.trackHeight)
                .clip(RoundedCornerShape(s.trackCornerRadius))
                .background(sc.trackFill),
        )
        val h = with(den) { s.handleWidth.toPx() }
        val a = wPx - h
        val c = v.coerceIn(0f, 1f)
        val vPx = c * a
        val mPx = a / 2f
        val lP = minOf(mPx, vPx) + h / 2f
        val rP = maxOf(mPx, vPx) + h / 2f
        Box(
            Modifier
                .offset {
                    IntOffset(lP.roundToInt(), 0)
                }.width(with(den) { (rP - lP).toDp() })
                .height(s.progressHeight)
                .clip(RoundedCornerShape(s.progressCornerRadius))
                .background(sc.progressFill),
        )
        Hdl((c * a).roundToInt(), s, sc, br, drg, en)
    }
}

@Suppress("LongParameterList", "CyclomaticComplexMethod")
@Composable
private fun HMulti(
    v: Float,
    onChange: (Float) -> Unit,
    cfg: SliderConfig,
    valueEnd: Float,
    onValueEndChange: ((Float) -> Unit)?,
    s: SliderStyle,
    sc: SliderStateColors,
    br: SliderBranchColors,
    en: Boolean,
    drg: Boolean,
    onSP: () -> Unit,
    onSE: () -> Unit,
    onE: () -> Unit,
    ext: Modifier,
    drgEnd: Boolean,
    isRtl: Boolean,
    onW: (Float) -> Unit,
) {
    val den = LocalDensity.current
    var wPx by remember { mutableFloatStateOf(0f) }
    val eV = valueEnd.coerceIn(0f, 1f)
    val eC = onValueEndChange
    var rawV by remember { mutableFloatStateOf(v) }
    var rawEV by remember { mutableFloatStateOf(eV) }
    val pD =
        rememberDraggableState { d ->
            val h = with(den) { s.handleWidth.toPx() }
            val a = wPx - h
            if (a > 0f) {
                val c = rawV.coerceIn(0f, 1f) * a
                val delta = if (isRtl) -d else d
                val newRaw = (c + delta).coerceIn(0f, a) / a
                rawV = newRaw
                onChange(snapToStep(newRaw, cfg.steps))
            }
        }
    val eD =
        rememberDraggableState { d ->
            val h = with(den) { s.handleWidth.toPx() }
            val a = wPx - h
            if (a > 0f && eC != null) {
                val c = rawEV.coerceIn(0f, 1f) * a
                val delta = if (isRtl) -d else d
                val newRaw = (c + delta).coerceIn(0f, a) / a
                rawEV = newRaw
                eC(snapToStep(newRaw, cfg.steps))
            }
        }
    Box(
        ext
            .height(s.handleHeight)
            .onSizeChanged {
                wPx = it.width.toFloat()
                onW(it.width.toFloat())
            }.then(
                if (en) {
                    Modifier.draggable(if (drgEnd) eD else pD, Orientation.Horizontal, onDragStarted = { off ->
                        rawV = v
                        rawEV = eV
                        val h = with(den) { s.handleWidth.toPx() }
                        val a =
                            wPx - h
                        if (a >
                            0f
                        ) {
                            val sp =
                                v.coerceIn(0f, 1f) * a + h / 2f
                            val ep =
                                eV * a + h / 2f
                            if (abs(off.x - ep) < abs(off.x - sp)) onSE() else onSP()
                        } else {
                            onSP()
                        }
                    }, onDragStopped = { onE() })
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(s.trackHeight)
                .clip(RoundedCornerShape(s.trackCornerRadius))
                .background(sc.trackFill),
        )
        if (wPx <= 0f) return@Box
        val h = with(den) { s.handleWidth.toPx() }
        val a = wPx - h
        val sC = v.coerceIn(0f, 1f)
        val lV = minOf(sC, eV)
        val rV = maxOf(sC, eV)
        val lP = lV * a + h / 2f
        val rP = rV * a + h / 2f
        val maxProgRight = wPx - h / 2f
        val clampedRP = minOf(rP, maxProgRight)
        val progWidth = (clampedRP - lP).coerceAtLeast(0f)
        Box(
            Modifier
                .offset {
                    IntOffset(lP.roundToInt(), 0)
                }.width(with(den) { progWidth.toDp() })
                .height(s.progressHeight)
                .clip(RoundedCornerShape(s.progressCornerRadius))
                .background(sc.progressFill),
        )
        Hdl((sC * a).roundToInt(), s, sc, br, drg && !drgEnd, en)
        Hdl((eV * a).roundToInt(), s, sc, br, drg && drgEnd, en)
    }
}

@Suppress("LongMethod", "CyclomaticComplexMethod")
@Composable
private fun VSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    config: SliderConfig,
    modifier: Modifier,
    state: SliderState,
    content: SliderContent,
    valueEnd: Float,
    onValueEndChange: ((Float) -> Unit)?,
) {
    val style = LocalSliderStyle.current
    val ux = LocalUxRestrictions.current
    val enabled = state.enabled && !ux.isMoving
    var dragging by remember { mutableStateOf(false) }
    var draggingEnd by remember { mutableStateOf(false) }
    val isError = shouldApplyErrorBranch(config)
    val branch = if (isError && !dragging) style.colors.error else style.colors.default
    val sc = pickStateColors(branch, enabled, dragging)
    val disMod = if (!enabled) Modifier.alpha(Sem.Opacity.Disabled.opacity()) else Modifier
    val den = LocalDensity.current
    var tHPx by remember { mutableFloatStateOf(0f) }
    var outerWPx by remember { mutableFloatStateOf(0f) }
    val isSplit = config.mode == SliderConfig.Mode.Split
    val isMulti = config.mode == SliderConfig.Mode.Multi
    val iconBtn = content.iconButton
    val bw = maxOf(style.handleWidth, style.trackHeight)
    val iconBtnSize = style.splitTouchTargetHeight
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val popupVisible = rememberPopupVisible(dragging, style.popupAutoHideDelayMs)

    var valuePopupWPx by remember { mutableFloatStateOf(0f) }
    var rangeLabelWPx by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight().then(disMod),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val hasLabelRow = content.label != null || content.appendix != null
        if (hasLabelRow) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(style.verticalLabelGroupGap),
            ) {
                content.label?.let { labelRes ->
                    DesignText(
                        state = TextState(text = labelRes, maxLines = 1),
                        style = style.titleTextStyle.copy(color = sc.titleColor),
                    )
                }
                content.appendix?.let { appendixRes ->
                    val contentWidthPx =
                        computeVerticalContentWidth(
                            valuePopupWPx,
                            style,
                            bw,
                            rangeLabelWPx,
                            den,
                        )
                    val maxW = if (contentWidthPx > 0f) with(den) { contentWidthPx.toDp() } else null
                    DesignText(
                        state = TextState(text = appendixRes, maxLines = 1),
                        style = style.appendixTextStyle.copy(color = style.appendixColor),
                        modifier = if (maxW != null) Modifier.widthIn(max = maxW) else Modifier,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Spacer(Modifier.height(style.verticalLabelGroupBottomPadding))
        }

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            if (iconBtn != null) {
                Box(Modifier.size(iconBtnSize), contentAlignment = Alignment.Center) {
                    CoreIconButton(config = IconButtonConfig(tone = IconButtonConfig.Tone.Tertiary), icon = iconBtn)
                }
                Spacer(Modifier.height(style.iconButtonGap))
            }

            val eV = valueEnd.coerceIn(0f, 1f)
            val eC = onValueEndChange
            var rawV by remember { mutableFloatStateOf(value) }
            var rawEV by remember { mutableFloatStateOf(eV) }

            val drag =
                rememberDraggableState { d ->
                    val h = with(den) { style.handleHeight.toPx() }
                    val a = tHPx - h
                    if (a > 0f) {
                        val c = (1f - rawV.coerceIn(0f, 1f)) * a
                        val newRaw = 1f - ((c + d).coerceIn(0f, a) / a)
                        rawV = newRaw
                        onValueChange(snapToStep(newRaw, config.steps))
                    }
                }

            val dragEnd =
                rememberDraggableState { d ->
                    val h = with(den) { style.handleHeight.toPx() }
                    val a = tHPx - h
                    if (a > 0f && eC != null) {
                        val c = (1f - rawEV.coerceIn(0f, 1f)) * a
                        val newRaw = 1f - ((c + d).coerceIn(0f, a) / a)
                        rawEV = newRaw
                        eC(snapToStep(newRaw, config.steps))
                    }
                }

            var outerPhysicalLeft by remember { mutableFloatStateOf(0f) }
            var trackPhysicalLeft by remember { mutableFloatStateOf(0f) }
            Box(
                Modifier
                    .weight(1f)
                    .onSizeChanged { outerWPx = it.width.toFloat() }
                    .onGloballyPositioned { outerPhysicalLeft = it.positionInParent().x },
            ) {
                Box(
                    Modifier
                        .width(bw)
                        .fillMaxHeight()
                        .align(Alignment.Center)
                        .onSizeChanged { tHPx = it.height.toFloat() }
                        .onGloballyPositioned { trackPhysicalLeft = it.positionInParent().x }
                        .then(
                            if (enabled) {
                                if (isMulti) {
                                    Modifier.draggable(
                                        if (draggingEnd) dragEnd else drag,
                                        Orientation.Vertical,
                                        onDragStarted = { off ->
                                            rawV = value
                                            rawEV = eV
                                            val h = with(den) { style.handleHeight.toPx() }
                                            val a = tHPx - h
                                            if (a > 0f) {
                                                val sp = (1f - value.coerceIn(0f, 1f)) * a + h / 2f
                                                val ep = (1f - eV) * a + h / 2f
                                                if (abs(off.y - ep) < abs(off.y - sp)) {
                                                    dragging = true
                                                    draggingEnd = true
                                                } else {
                                                    dragging = true
                                                    draggingEnd = false
                                                }
                                            } else {
                                                dragging = true
                                                draggingEnd = false
                                            }
                                        },
                                        onDragStopped = { dragging = false },
                                    )
                                } else {
                                    Modifier.draggable(
                                        drag,
                                        Orientation.Vertical,
                                        onDragStarted = {
                                            rawV = value
                                            dragging = true
                                        },
                                        onDragStopped = { dragging = false },
                                    )
                                }
                            } else {
                                Modifier
                            },
                        ),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    if (isSplit) {
                        val gPx = with(den) { style.splitTrackGap.toPx() }
                        val hg = gPx / 2f
                        val cp = tHPx / 2f
                        Box(
                            Modifier
                                .width(style.trackHeight)
                                .height(with(den) { (cp - hg).toDp() })
                                .clip(RoundedCornerShape(style.trackCornerRadius))
                                .background(sc.trackFill)
                                .align(Alignment.TopCenter),
                        )
                        Box(
                            Modifier
                                .offset {
                                    IntOffset(0, (cp + hg).roundToInt())
                                }.width(style.trackHeight)
                                .height(with(den) { (cp - hg).toDp() })
                                .clip(RoundedCornerShape(style.trackCornerRadius))
                                .background(sc.trackFill)
                                .align(Alignment.TopCenter),
                        )
                    } else {
                        Box(
                            Modifier
                                .width(style.trackHeight)
                                .height(with(den) { tHPx.toDp() })
                                .clip(RoundedCornerShape(style.trackCornerRadius))
                                .background(sc.trackFill)
                                .align(Alignment.BottomCenter),
                        )
                    }

                    if (tHPx > 0f) {
                        val h = with(den) { style.handleHeight.toPx() }
                        val a = tHPx - h
                        val c = value.coerceIn(0f, 1f)
                        val pf = progressFillColor(config, style, sc, enabled)
                        val hs = RoundedCornerShape(style.handleCornerRadius)

                        if (isSplit) {
                            val mPx = a / 2f
                            val vPx = (1f - c) * a
                            val topP = minOf(mPx, vPx) + h / 2f
                            val botP = maxOf(mPx, vPx) + h / 2f
                            Box(
                                Modifier
                                    .offset { IntOffset(0, topP.roundToInt()) }
                                    .width(style.progressHeight)
                                    .height(with(den) { (botP - topP).toDp() })
                                    .clip(RoundedCornerShape(style.progressCornerRadius))
                                    .background(pf)
                                    .align(Alignment.TopCenter),
                            )
                        } else if (isMulti) {
                            val sC = (1f - c) * a
                            val eO = (1f - eV) * a
                            val topP = minOf(sC, eO) + h / 2f
                            val botP = maxOf(sC, eO) + h / 2f
                            Box(
                                Modifier
                                    .offset { IntOffset(0, topP.roundToInt()) }
                                    .width(style.progressHeight)
                                    .height(with(den) { (botP - topP).toDp() })
                                    .clip(RoundedCornerShape(style.progressCornerRadius))
                                    .background(pf)
                                    .align(Alignment.TopCenter),
                            )
                        } else {
                            val progH = (c * a + h / 2f).coerceAtMost(tHPx)
                            val progHDp = with(den) { progH.toDp() }
                            if (!enabled && (config.mode == SliderConfig.Mode.Temperature || config.mode == SliderConfig.Mode.Charging)) {
                                Box(
                                    Modifier
                                        .width(style.progressHeight)
                                        .height(progHDp)
                                        .clip(RoundedCornerShape(style.progressCornerRadius))
                                        .align(Alignment.BottomCenter)
                                        .background(pf),
                                )
                            } else if (config.mode == SliderConfig.Mode.Temperature) {
                                Box(
                                    Modifier
                                        .width(style.progressHeight)
                                        .height(progHDp)
                                        .clip(RoundedCornerShape(style.progressCornerRadius))
                                        .align(Alignment.BottomCenter)
                                        .background(Brush.verticalGradient(listOf(style.colors.temperatureHotColor, style.colors.temperatureColdColor))),
                                )
                            } else if (config.mode == SliderConfig.Mode.Charging) {
                                val progShape = RoundedCornerShape(style.progressCornerRadius)
                                Box(
                                    Modifier
                                        .width(style.progressHeight)
                                        .height(progHDp)
                                        .align(Alignment.BottomCenter)
                                        .chargingGlow(
                                            pf,
                                            style.chargingGlowAlpha,
                                            with(den) { style.chargingGlowBlurRadius.toPx() },
                                            with(den) { style.progressCornerRadius.toPx() },
                                        ).clip(progShape)
                                        .background(pf),
                                )
                            } else {
                                Box(
                                    Modifier
                                        .width(style.progressHeight)
                                        .height(progHDp)
                                        .clip(RoundedCornerShape(style.progressCornerRadius))
                                        .align(Alignment.BottomCenter)
                                        .background(pf),
                                )
                            }
                        }

                        val hO = ((1f - c) * a).roundToInt()
                        Box(Modifier.offset { IntOffset(0, hO) }.align(Alignment.TopCenter)) {
                            Box(
                                Modifier
                                    .width(
                                        style.handleWidth,
                                    ).height(style.handleHeight)
                                    .clip(hs)
                                    .background(sc.handleFill)
                                    .border(style.handleBorderWidth, sc.handleStroke, hs),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (dragging && enabled && !draggingEnd) Box(Modifier.matchParentSize().background(branch.stateLayerPressed))
                            }
                        }

                        if (isMulti) {
                            val eO = ((1f - eV) * a).roundToInt()
                            Box(Modifier.offset { IntOffset(0, eO) }.align(Alignment.TopCenter)) {
                                Box(
                                    Modifier
                                        .width(style.handleWidth)
                                        .height(style.handleHeight)
                                        .clip(hs)
                                        .background(sc.handleFill)
                                        .border(style.handleBorderWidth, sc.handleStroke, hs),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    if (dragging && enabled && draggingEnd) Box(Modifier.matchParentSize().background(branch.stateLayerPressed))
                                }
                            }
                        }
                    }
                }

                val bwPx = with(den) { bw.toPx() }

                if (tHPx > 0f) {
                    val h = with(den) { style.handleHeight.toPx() }
                    val a = tHPx - h
                    val c = value.coerceIn(0f, 1f)
                    val hO = ((1f - c) * a).roundToInt()
                    var popH by remember { mutableFloatStateOf(0f) }
                    var popW by remember { mutableFloatStateOf(0f) }

                    val handleCenterY = hO + h / 2f
                    val rawPopupY = (handleCenterY - popH / 2f)
                    val popupY =
                        if (popupVisible) {
                            rawPopupY.coerceIn(0f, (tHPx - popH).coerceAtLeast(0f)).roundToInt()
                        } else {
                            rawPopupY.roundToInt()
                        }

                    val handleLeftInTrack = with(den) { ((bw - style.handleWidth) / 2).toPx() }
                    val popGap = with(den) { if (popupVisible) style.handleValuePressedGap.toPx() else style.handleValueIdleGap.toPx() }
                    val popupX = (trackPhysicalLeft + handleLeftInTrack - popGap - popW).roundToInt()

                    val showDynamic = popupVisible
                    val showStatic = !dragging && !showDynamic
                    if (showDynamic || showStatic) {
                        Box(
                            Modifier
                                .absoluteTopLeftAnchor()
                                .onSizeChanged {
                                    popH = it.height.toFloat()
                                    popW = it.width.toFloat()
                                    valuePopupWPx = it.width.toFloat()
                                }.graphicsLayer {
                                    translationX = popupX.toFloat()
                                    translationY = popupY.toFloat()
                                },
                        ) {
                            ValPopup(value, config, valueEnd, style, sc, pressed = showDynamic, customValueDisplay = content.valueDisplay)
                        }
                    }
                }

                val hasMinMax = content.minLabel != null || content.maxLabel != null
                if (hasMinMax) {
                    val rangeOffsetPx = trackPhysicalLeft + bwPx + with(den) { style.verticalRangeLabelGap.toPx() }

                    content.maxLabel?.let {
                        Box(
                            modifier =
                                Modifier
                                    .absoluteTopLeftAnchor()
                                    .onSizeChanged { rangeLabelWPx = it.width.toFloat() }
                                    .graphicsLayer {
                                        translationX = rangeOffsetPx
                                        translationY = with(den) { style.verticalMaxLabelOffsetY.toPx() }
                                    },
                        ) {
                            RangeLabel(
                                { Txt(it, sc.rangeColor, style.rangeTextStyle) },
                                sc.rangeColor,
                                sc.rangeIconColor,
                                style,
                                content.showIcons,
                                content.maxIcon,
                                content.showLabels,
                            )
                        }
                    }

                    content.minLabel?.let {
                        var minH by remember { mutableFloatStateOf(0f) }
                        Box(
                            modifier =
                                Modifier
                                    .absoluteTopLeftAnchor()
                                    .graphicsLayer {
                                        translationX = rangeOffsetPx
                                        translationY = tHPx - minH + with(den) { style.verticalMinLabelOffsetY.toPx() }
                                    },
                        ) {
                            Box(Modifier.onSizeChanged { minH = it.height.toFloat() }) {
                                RangeLabel(
                                    { Txt(it, sc.rangeColor, style.rangeTextStyle) },
                                    sc.rangeColor,
                                    sc.rangeIconColor,
                                    style,
                                    content.showIcons,
                                    content.minIcon,
                                    content.showLabels,
                                )
                            }
                        }
                    }
                }
            }
            if (iconBtn != null) {
                Spacer(Modifier.height(style.iconButtonGap))
                Box(Modifier.size(iconBtnSize).graphicsLayer { rotationZ = 180f }, contentAlignment = Alignment.Center) {
                    CoreIconButton(config = IconButtonConfig(tone = IconButtonConfig.Tone.Tertiary), icon = iconBtn)
                }
            }
        }

        val contentWidthPx =
            computeVerticalContentWidth(
                valuePopupWPx,
                style,
                bw,
                rangeLabelWPx,
                den,
            )
        val captionMaxW = if (contentWidthPx > 0f) with(den) { contentWidthPx.toDp() } else null
        CaptionGroup(
            content,
            config,
            style,
            sc,
            style.captionGroupTopPadding,
            style.captionGroupGap,
            isRtl = false,
            centerAlign = true,
            isDragging = dragging,
            maxWidth = captionMaxW,
        )
    }
}

@Composable
private fun HIdleValuePopup(
    wPx: Float,
    handleCenterPx: Float,
    config: SliderConfig,
    value: Float,
    valueEnd: Float,
    style: SliderStyle,
    sc: SliderStateColors,
    content: SliderContent,
    isRtl: Boolean = false,
) {
    val den = LocalDensity.current
    var idlePopW by remember { mutableFloatStateOf(0f) }
    var idlePopH by remember { mutableFloatStateOf(0f) }
    val physicalCenter = if (isRtl) wPx - handleCenterPx else handleCenterPx
    val handleTopPx = with(den) { (style.minHeight - style.handleHeight).toPx() / 2f }
    val rawLeftX =
        if (config.mode == SliderConfig.Mode.Multi) {
            wPx / 2f - idlePopW / 2f
        } else {
            physicalCenter - idlePopW / 2f
        }
    val idleX = rawLeftX.coerceIn(0f, (wPx - idlePopW).coerceAtLeast(0f))
    val idleY = handleTopPx - idlePopH - with(den) { style.hIdleValueGap.toPx() }
    Box(
        Modifier
            .absoluteTopLeftAnchor()
            .onSizeChanged {
                idlePopW = it.width.toFloat()
                idlePopH = it.height.toFloat()
            }.graphicsLayer {
                translationX = idleX
                translationY = idleY
            },
    ) {
        ValPopup(value, config, valueEnd, style, sc, pressed = false, customValueDisplay = content.valueDisplay)
    }
}

@Composable
private fun HRangeLabels(
    content: SliderContent,
    sc: SliderStateColors,
    style: SliderStyle,
    isRtl: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val startLabel = if (isRtl) content.maxLabel else content.minLabel
        val startIcon = if (isRtl) content.maxIcon else content.minIcon
        val endLabel = if (isRtl) content.minLabel else content.maxLabel
        val endIcon = if (isRtl) content.minIcon else content.maxIcon
        startLabel?.let {
            RangeLabel(
                { Txt(it, sc.rangeColor, style.rangeTextStyle) },
                sc.rangeColor,
                sc.rangeIconColor,
                style,
                content.showIcons,
                startIcon,
                content.showLabels,
            )
        }
        endLabel?.let {
            RangeLabel(
                { Txt(it, sc.rangeColor, style.rangeTextStyle) },
                sc.rangeColor,
                sc.rangeIconColor,
                style,
                content.showIcons,
                endIcon,
                content.showLabels,
            )
        }
    }
}

private fun Modifier.chargingGlow(
    glowColor: Color,
    glowAlpha: Float,
    glowSpreadPx: Float,
    cornerRadiusPx: Float,
): Modifier =
    this
        .graphicsLayer { clip = false }
        .drawBehind {
            val paint =
                android.graphics.Paint().apply {
                    color = glowColor.copy(alpha = glowAlpha).toArgb()
                    isAntiAlias = true
                    maskFilter =
                        android.graphics.BlurMaskFilter(
                            glowSpreadPx,
                            android.graphics.BlurMaskFilter.Blur.NORMAL,
                        )
                }
            drawContext.canvas.nativeCanvas.drawRoundRect(
                -glowSpreadPx,
                -glowSpreadPx,
                size.width + glowSpreadPx,
                size.height + glowSpreadPx,
                cornerRadiusPx + glowSpreadPx,
                cornerRadiusPx + glowSpreadPx,
                paint,
            )
        }

@Composable
private fun Hdl(
    off: Int,
    s: SliderStyle,
    sc: SliderStateColors,
    br: SliderBranchColors,
    drg: Boolean,
    en: Boolean,
) {
    val sh = RoundedCornerShape(s.handleCornerRadius)
    Box(
        Modifier
            .offset {
                IntOffset(off, 0)
            }.width(
                s.handleWidth,
            ).height(s.handleHeight)
            .clip(sh)
            .background(sc.handleFill)
            .border(s.handleBorderWidth, sc.handleStroke, sh),
        contentAlignment = Alignment.Center,
    ) {
        if (drg && en) Box(Modifier.matchParentSize().background(br.stateLayerPressed))
    }
}

@Composable
private fun ValPopup(
    v: Float,
    cfg: SliderConfig,
    valueEnd: Float,
    s: SliderStyle,
    sc: SliderStateColors,
    pressed: Boolean,
    customValueDisplay: (@Composable (value: Float, pressed: Boolean) -> Unit)? = null,
) {
    if (customValueDisplay != null) {
        customValueDisplay(v, pressed)
        return
    }

    val txt = formatValue(v, cfg, valueEnd)
    val shape = RoundedCornerShape(s.valueCornerRadius)

    val bw = if (pressed) s.valueBorderWidthPressed else s.valueBorderWidthIdle
    val strokeColor = if (pressed) s.colors.valueSurfaceStroke else Color.Transparent
    val tStyle = if (pressed) s.valueTextStylePressed else s.valueTextStyleIdle
    val shadowMod =
        if (pressed) {
            Modifier.graphicsLayer(
                shadowElevation = s.valuePopupShadow.elevation,
                shape = shape,
                clip = false,
            )
        } else {
            Modifier
        }
    val bgMod = if (pressed) Modifier.clip(shape).background(s.colors.valueSurfaceFill) else Modifier
    val padH = if (pressed) s.valuePaddingH else 0.dp
    val padV = if (pressed) s.valuePaddingV else 0.dp

    Box(
        Modifier
            .then(shadowMod)
            .then(bgMod)
            .border(bw, strokeColor, shape)
            .padding(horizontal = padH, vertical = padV),
        contentAlignment = Alignment.Center,
    ) {
        DesignText(state = TextState(text = txt.TR, maxLines = 1), style = tStyle.copy(color = sc.valueTextColor))
    }
}

/**
 * Optional hint text and error caption below the slider.
 *
 * @param content slider content bundle containing hint and error text resources.
 * @param config slider configuration used to determine the error branch.
 * @param style design-token style for text styles and spacing.
 * @param sc resolved state colours for the current enabled/pressed state.
 * @param topPadding spacing above the caption group.
 * @param gap vertical spacing between hint and error caption.
 * @param isRtl `true` when the layout direction is right-to-left.
 * @param centerAlign `true` to centre-align the captions (used by vertical slider).
 * @param isDragging `true` while the user is actively dragging; hides the error caption.
 * @param maxWidth optional maximum width for the hint text. When non-null the hint
 *  is constrained to this width and truncated with ellipsis after 2 lines.
 */
@Composable
private fun CaptionGroup(
    content: SliderContent,
    config: SliderConfig,
    style: SliderStyle,
    sc: SliderStateColors,
    topPadding: androidx.compose.ui.unit.Dp,
    gap: androidx.compose.ui.unit.Dp,
    isRtl: Boolean = false,
    centerAlign: Boolean = false,
    isDragging: Boolean = false,
    maxWidth: androidx.compose.ui.unit.Dp? = null,
) {
    val showError = shouldApplyErrorBranch(config) && !isDragging
    if (content.hint == null && !showError) return
    Spacer(Modifier.height(topPadding))
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment =
            when {
                centerAlign -> Alignment.CenterHorizontally
                isRtl -> Alignment.End
                else -> Alignment.Start
            },
        verticalArrangement = Arrangement.spacedBy(gap),
    ) {
        content.hint?.let { hintRes ->
            val hintMod = if (maxWidth != null) Modifier.widthIn(max = maxWidth) else Modifier
            DesignText(
                state = TextState(text = hintRes, maxLines = 2),
                style = style.hintTextStyle.copy(color = sc.hintColor),
                modifier = hintMod,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (showError) ErrorCaption(style, content, isRtl)
    }
}

/**
 * Error caption row: [SemanticShape] (Critical variant) + error text.
 *
 * The Critical variant renders an upward-pointing triangle (△) whose size
 * and colours are resolved from `Cmp.*.Feedback.SemanticShape.*` tokens via
 * [LocalSemanticShapeStyle], ensuring visual consistency with all other
 * widgets that display semantic status indicators.
 *
 * @param s design-token style providing gap and text style.
 * @param content slider content bundle containing the error text resource.
 * @param isRtl `true` when the layout direction is right-to-left; mirrors
 *  the icon/text order.
 */
@Composable
private fun ErrorCaption(
    s: SliderStyle,
    content: SliderContent,
    isRtl: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(s.captionGroupGap),
    ) {
        if (isRtl) {
            content.errorText?.let { errorRes ->
                DesignText(
                    state = TextState(text = errorRes, maxLines = 1),
                    style = s.errorCaptionTextStyle.copy(color = s.errorCaptionColor),
                )
            }
            SemanticShape(
                config = SemanticShapeConfig(variant = SemanticShapeConfig.Variant.Critical),
            )
        } else {
            SemanticShape(
                config = SemanticShapeConfig(variant = SemanticShapeConfig.Variant.Critical),
            )
            content.errorText?.let { errorRes ->
                DesignText(
                    state = TextState(text = errorRes, maxLines = 1),
                    style = s.errorCaptionTextStyle.copy(color = s.errorCaptionColor),
                )
            }
        }
    }
}

@Composable
private fun Txt(
    text: String,
    color: Color,
    ts: TextStyle,
) {
    DesignText(state = TextState(text = text.TR), style = ts.copy(color = color))
}

/**
 * Range label: optional icon + composable label slot, separated by rangeGap.
 * The label slot is wrapped with [textColor] and [style.rangeTextStyle] via
 * `CompositionLocalProvider` so the caller's content inherits the correct
 * design-token colours.
 * When [showIcon] is `false` only the label is rendered (text-only label).
 * When [customIcon] is provided, it replaces the default placeholder icon.
 */
@Composable
private fun RangeLabel(
    label: @Composable () -> Unit,
    textColor: Color,
    iconColor: Color,
    style: SliderStyle,
    showIcon: Boolean,
    customIcon: (@Composable () -> Unit)? = null,
    showLabel: Boolean = true,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(style.rangeGap),
    ) {
        if (showIcon) {
            CompositionLocalProvider(LocalContentColor provides iconColor) {
                if (customIcon != null) {
                    customIcon()
                } else {
                    CoreIcon(config = IconConfig(size = IconConfig.Size.SM)) {
                        Icon(
                            painter = painterResource(R.drawable.exxx_interactive_placeholder),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
        if (showLabel) {
            CompositionLocalProvider(
                LocalContentColor provides textColor,
                LocalTextStyle provides style.rangeTextStyle.copy(color = textColor),
            ) { label() }
        }
    }
}

/** Resolves the progress-fill colour. Temperature/Charging use default fill when disabled. */
private fun progressFillColor(
    config: SliderConfig,
    style: SliderStyle,
    stateColors: SliderStateColors,
    enabled: Boolean = true,
): Color =
    when {
        !enabled &&
            (
                config.mode == SliderConfig.Mode.Temperature ||
                    config.mode == SliderConfig.Mode.Charging
            ) -> style.colors.default.disabled.progressFill
        config.mode == SliderConfig.Mode.Charging -> style.colors.chargingProgressFill
        else -> stateColors.progressFill
    }

/** Selects the appropriate [SliderStateColors] based on enabled and dragging state. */
private fun pickStateColors(
    branch: SliderBranchColors,
    enabled: Boolean,
    dragging: Boolean,
): SliderStateColors =
    when {
        !enabled -> branch.disabled
        dragging -> branch.pressed
        else -> branch.idle
    }

private fun ltrWrap(s: String): String = "\u200E$s\u200E"

private fun formatValue(
    value: Float,
    config: SliderConfig,
    valueEnd: Float,
): String =
    when (config.mode) {
        SliderConfig.Mode.Split -> {
            val p = ((value - 0.5f) * 200).toInt()
            ltrWrap("$p%")
        }
        SliderConfig.Mode.Multi -> {
            val a = (minOf(value, valueEnd) * 100).toInt()
            val b = (maxOf(value, valueEnd) * 100).toInt()
            ltrWrap("$a%") + " to " + ltrWrap("$b%")
        }
        else -> ltrWrap("${(value * 100).toInt()}%")
    }

/**
 * Computes the total visual content width of a vertical slider in pixels.
 * This spans from the left edge of the value popup to the right edge of
 * the range label: popup + gap + track + gap + rangeLabel.
 * Returns 0f when measurements are not yet available.
 */
private fun computeVerticalContentWidth(
    valuePopupWPx: Float,
    style: SliderStyle,
    trackWidth: androidx.compose.ui.unit.Dp,
    rangeLabelWPx: Float,
    density: androidx.compose.ui.unit.Density,
): Float {
    if (valuePopupWPx <= 0f && rangeLabelWPx <= 0f) return 0f
    val trackPx = with(density) { trackWidth.toPx() }
    val popGapPx = with(density) { style.handleValueIdleGap.toPx() }
    val rangeGapPx = with(density) { style.verticalRangeLabelGap.toPx() }
    return valuePopupWPx + popGapPx + trackPx + rangeGapPx + rangeLabelWPx
}

/** Returns `true` when the error colour branch applies (excluded for Temperature / Charging). */
private fun shouldApplyErrorBranch(config: SliderConfig): Boolean =
    config.isError && config.mode != SliderConfig.Mode.Temperature && config.mode != SliderConfig.Mode.Charging
