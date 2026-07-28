package com.ui.audi

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ui.audi.widgets.checkbox.AudiCheckbox
import com.ui.audi.widgets.checkbox.AudiCheckboxDefaults
import com.ui.audi.widgets.chips.AudiChipDefaults
import com.ui.audi.widgets.contentgroup.AudiContentGroup
import com.ui.audi.widgets.contentgroup.ContentGroupDefaults
import com.ui.audi.widgets.imagecontainer.AudiImageContainer
import com.ui.audi.widgets.imagecontainer.AudiImageContainerDefaults
import com.ui.audi.widgets.listitems.AudiListItem
import com.ui.audi.widgets.pindisplay.AudiPinDisplay
import com.ui.audi.widgets.pindisplay.PinDisplayDefaults
import com.ui.audi.widgets.radiobuttons.AudiRadioButton
import com.ui.audi.widgets.radiobuttons.RadioButtonDefaults
import com.ui.audi.widgets.scrollbar.AudiScrollbar
import com.ui.audi.widgets.scrollbar.AudiScrollbarDefaults
import com.ui.audi.widgets.searchfields.AudiSearchField
import com.ui.audi.widgets.searchfields.AudiSearchFieldDefaults
import com.ui.audi.widgets.toggleswitch.AudiToggleSwitch
import com.ui.audi.widgets.toggleswitch.ToggleSwitchDefaults
import com.ui.audi.widgets.toolbar.AudiToolbar
import com.ui.audi.widgets.toolbar.AudiToolbarDefaults
import com.ui.core.engine.compose.DesignTokenTheme
import com.ui.core.engine.runtime.DesignTokenManager
import com.ui.core.layout.LayoutConfig
import com.ui.core.layout.LayoutOrchestrator
import com.ui.core.layout.LocalLayoutConfig
import com.ui.core.layout.effectiveLayoutDirection
import com.ui.core.styles.LocalImageContainerStyle
import com.ui.core.styles.LocalToolbarStyle
import com.ui.core.uxrestrictions.LocalUxRestrictions
import com.ui.core.uxrestrictions.UxRestrictionsOrchestrator
import com.ui.core.widgets.LocalWidgets
import com.ui.core.widgets.ToolbarWidgetContent
import com.ui.core.widgets.adaptiveicons.AdaptiveIconWidgetContent
import com.ui.core.widgets.adaptiveicons.LocalAdaptiveIconStyle
import com.ui.core.widgets.buttongroups.ButtonGroupWidgetContent
import com.ui.core.widgets.buttongroups.LocalButtonGroupStyle
import com.ui.core.widgets.buttons.ButtonWidgetContent
import com.ui.core.widgets.buttons.LocalButtonStyle
import com.ui.core.widgets.checkbox.CheckboxWidgetContent
import com.ui.core.widgets.checkbox.LocalCheckboxStyle
import com.ui.core.widgets.chips.ChipWidgetContent
import com.ui.core.widgets.chips.LocalChipStyle
import com.ui.core.widgets.contentgroup.ContentGroupWidgetContent
import com.ui.core.widgets.contentgroup.LocalContentGroupStyle
import com.ui.core.widgets.dividers.DividerWidgetContent
import com.ui.core.widgets.dividers.LocalDividerStyle
import com.ui.core.widgets.iconbuttons.IconButtonWidgetContent
import com.ui.core.widgets.iconbuttons.LocalIconButtonStyle
import com.ui.core.widgets.icons.IconWidgetContent
import com.ui.core.widgets.icons.LocalIconStyle
import com.ui.core.widgets.imagecontainer.ImageContainerWidgetContent
import com.ui.core.widgets.listitems.ListItemWidgetContent
import com.ui.core.widgets.listitems.LocalListItemStyle
import com.ui.core.widgets.listitems.subcomponents.LocalListItemSubComponentStyle
import com.ui.core.widgets.multitogglebuttons.LocalMultiToggleButtonStyle
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonWidgetContent
import com.ui.core.widgets.navigationbars.LocalNavigationBarStyle
import com.ui.core.widgets.navigationbars.NavigationBarWidgetContent
import com.ui.core.widgets.pindisplay.LocalPinDisplayStyle
import com.ui.core.widgets.pindisplay.PinDisplayWidgetContent
import com.ui.core.widgets.progressindicators.LocalProgressIndicatorStyle
import com.ui.core.widgets.progressindicators.ProgressIndicatorWidgetContent
import com.ui.core.widgets.progresstrackers.LocalProgressTrackerStyle
import com.ui.core.widgets.progresstrackers.ProgressTrackerWidgetContent
import com.ui.core.widgets.radiobuttons.LocalRadioButtonStyle
import com.ui.core.widgets.radiobuttons.RadioButtonWidgetContent
import com.ui.core.widgets.scrollbar.LocalScrollbarStyle
import com.ui.core.widgets.scrollbar.ScrollbarWidgetContent
import com.ui.core.widgets.searchfields.LocalSearchFieldStyle
import com.ui.core.widgets.searchfields.SearchFieldWidgetContent
import com.ui.core.widgets.segmentedcontrols.LocalSegmentedControlStyle
import com.ui.core.widgets.segmentedcontrols.SegmentedControlWidgetContent
import com.ui.core.widgets.selects.LocalSelectStyle
import com.ui.core.widgets.selects.SelectWidgetContent
import com.ui.core.widgets.semanticshapes.LocalSemanticShapeStyle
import com.ui.core.widgets.semanticshapes.SemanticShapeWidgetContent
import com.ui.core.widgets.sliders.LocalSliderStyle
import com.ui.core.widgets.sliders.SliderWidgetContent
import com.ui.core.widgets.steppers.LocalStepperStyle
import com.ui.core.widgets.steppers.StepperWidgetContent
import com.ui.core.widgets.tags.LocalTagStyle
import com.ui.core.widgets.tags.TagWidgetContent
import com.ui.core.widgets.text.LocalTextStyleSpec
import com.ui.core.widgets.text.TextWidgetContent
import com.ui.core.widgets.textinputs.LocalTextInputStyle
import com.ui.core.widgets.textinputs.TextInputWidgetContent
import com.ui.core.widgets.textlink.LocalTextLinkStyle
import com.ui.core.widgets.textlink.TextLinkWidgetContent
import com.ui.core.widgets.tiles.LocalTileStyle
import com.ui.core.widgets.tiles.TileWidgetContent
import com.ui.core.widgets.toggleswitch.LocalToggleSwitchStyle
import com.ui.core.widgets.toggleswitch.ToggleSwitchWidgetContent
import androidx.compose.material3.Text as M3Text
import com.ui.audi.widgets.adaptiveicons.AdaptiveIcon as AudiAdaptiveIcon
import com.ui.audi.widgets.adaptiveicons.AdaptiveIconDefaults as AudiAdaptiveIconDefaults
import com.ui.audi.widgets.buttongroups.ButtonGroup as AudiButtonGroup
import com.ui.audi.widgets.buttongroups.ButtonGroupDefaults as AudiButtonGroupDefaults
import com.ui.audi.widgets.buttons.Button as AudiButton
import com.ui.audi.widgets.buttons.ButtonDefaults as AudiButtonDefaults
import com.ui.audi.widgets.chips.Chip as AudiChip
import com.ui.audi.widgets.dividers.Divider as AudiDivider
import com.ui.audi.widgets.dividers.DividerDefaults as AudiDividerDefaults
import com.ui.audi.widgets.iconbuttons.IconButton as AudiIconButton
import com.ui.audi.widgets.iconbuttons.IconButtonDefaults as AudiIconButtonDefaults
import com.ui.audi.widgets.icons.Icon as AudiIcon
import com.ui.audi.widgets.icons.IconDefaults as AudiIconDefaults
import com.ui.audi.widgets.listitems.ListItemDefaults as AudiListItemDefaults
import com.ui.audi.widgets.listitems.ListItemSubComponentDefaults as AudiListItemSubComponentDefaults
import com.ui.audi.widgets.multitogglebuttons.MultiToggleButton as AudiMultiToggleButton
import com.ui.audi.widgets.multitogglebuttons.MultiToggleButtonDefaults as AudiMultiToggleButtonDefaults
import com.ui.audi.widgets.navigationbars.NavigationBar as AudiNavigationBar
import com.ui.audi.widgets.navigationbars.NavigationBarDefaults as AudiNavigationBarDefaults
import com.ui.audi.widgets.progressindicators.ProgressIndicator as AudiProgressIndicator
import com.ui.audi.widgets.progressindicators.ProgressIndicatorDefaults as AudiProgressIndicatorDefaults
import com.ui.audi.widgets.progresstrackers.ProgressTracker as AudiProgressTracker
import com.ui.audi.widgets.progresstrackers.ProgressTrackerDefaults as AudiProgressTrackerDefaults
import com.ui.audi.widgets.segmentedcontrols.SegmentedControl as AudiSegmentedControl
import com.ui.audi.widgets.segmentedcontrols.SegmentedControlDefaults as AudiSegmentedControlDefaults
import com.ui.audi.widgets.selects.Select as AudiSelect
import com.ui.audi.widgets.selects.SelectDefaults as AudiSelectDefaults
import com.ui.audi.widgets.semanticshapes.SemanticShape as AudiSemanticShape
import com.ui.audi.widgets.semanticshapes.SemanticShapeDefaults as AudiSemanticShapeDefaults
import com.ui.audi.widgets.sliders.Slider as AudiSlider
import com.ui.audi.widgets.sliders.SliderDefaults as AudiSliderDefaults
import com.ui.audi.widgets.steppers.Stepper as AudiStepper
import com.ui.audi.widgets.steppers.StepperDefaults as AudiStepperDefaults
import com.ui.audi.widgets.tags.Tag as AudiTag
import com.ui.audi.widgets.tags.TagDefaults as AudiTagDefaults
import com.ui.audi.widgets.text.Text as AudiText
import com.ui.audi.widgets.text.TextDefaults as AudiTextDefaults
import com.ui.audi.widgets.textinputs.TextInput as AudiTextInput
import com.ui.audi.widgets.textinputs.TextInputDefaults as AudiTextInputDefaults
import com.ui.audi.widgets.textlink.TextLink as AudiTextLink
import com.ui.audi.widgets.textlink.TextLinkDefaults as AudiTextLinkDefaults
import com.ui.audi.widgets.tiles.Tile as AudiTile
import com.ui.audi.widgets.tiles.TileDefaults as AudiTileDefaults

private val audiButtonWidget: ButtonWidgetContent = { config, modifier, state, interactionConfig, leading, label, trailing, toggle ->
    AudiButton(
        config = config,
        modifier = modifier,
        state = state,
        interactionConfig = interactionConfig,
        leading = leading,
        label = label,
        trailing = trailing,
        toggle = toggle,
    )
}

private val audiToolbarWidget: ToolbarWidgetContent = { title, modifier, actions ->
    AudiToolbar(title = title, modifier = modifier, actions = actions)
}

private val audiTextWidget: TextWidgetContent =
    { config, modifier, state, interactionConfig, overflow, textAlign, style ->
        AudiText(
            config = config,
            modifier = modifier,
            state = state,
            interactionConfig = interactionConfig,
            overflow = overflow,
            textAlign = textAlign,
            style = style,
        )
    }

private val audiTextLinkWidget: TextLinkWidgetContent =
    { config, modifier, state, interactionConfig, leading, label, trailing ->
        AudiTextLink(
            config = config,
            modifier = modifier,
            state = state,
            interactionConfig = interactionConfig,
            leading = leading,
            label = label,
            trailing = trailing,
        )
    }

private val audiDividerWidget: DividerWidgetContent = { config, modifier ->
    AudiDivider(
        config = config,
        modifier = modifier,
    )
}

private val audiTagWidget: TagWidgetContent = { config, modifier, state, icon, label ->
    AudiTag(
        config = config,
        modifier = modifier,
        state = state,
        icon = icon,
        label = label,
    )
}

private val audiAdaptiveIconWidget: AdaptiveIconWidgetContent = { config, modifier, icon ->
    AudiAdaptiveIcon(config = config, modifier = modifier, icon = icon)
}

private val audiButtonGroupWidget: ButtonGroupWidgetContent = { config, modifier, items ->
    AudiButtonGroup(config = config, modifier = modifier, items = items)
}

private val audiIconButtonWidget: IconButtonWidgetContent = { config, modifier, state, interactionConfig, icon, label, toggle ->
    AudiIconButton(config = config, modifier = modifier, state = state, interactionConfig = interactionConfig, icon = icon, label = label, toggle = toggle)
}

private val audiScrollbarWidget: ScrollbarWidgetContent =
    { listState, modifier, interactionConfig ->
        AudiScrollbar(
            listState = listState,
            modifier = modifier,
            interactionConfig = interactionConfig,
        )
    }

private val audiIconWidget: IconWidgetContent = { config, modifier, state, source, icon ->
    AudiIcon(
        config = config,
        modifier = modifier,
        state = state,
        source = source,
        icon = icon,
    )
}

private val audiCheckboxWidget: CheckboxWidgetContent =
    { modifier, content, state, interactionConfig ->
        AudiCheckbox(
            modifier = modifier,
            content = content,
            state = state,
            interactionConfig = interactionConfig,
        )
    }

private val audiContentGroupWidget: ContentGroupWidgetContent =
    { modifier, hasPadding, content ->
        AudiContentGroup(
            modifier = modifier,
            hasPadding = hasPadding,
            content = content,
        )
    }

private val audiPinDisplayWidget: PinDisplayWidgetContent =
    { value, pinLength, modifier, state ->
        AudiPinDisplay(
            value = value,
            pinLength = pinLength,
            modifier = modifier,
            state = state,
        )
    }

private val audiRadioButtonWidget: RadioButtonWidgetContent =
    { modifier, content, state, interactionConfig ->
        AudiRadioButton(
            modifier = modifier,
            content = content,
            state = state,
            interactionConfig = interactionConfig,
        )
    }

private val audiSliderWidget: SliderWidgetContent =
    { value, onValueChange, config, modifier, state, content, valueEnd, onValueEndChange ->
        AudiSlider(
            value = value,
            onValueChange = onValueChange,
            config = config,
            modifier = modifier,
            state = state,
            content = content,
            valueEnd = valueEnd,
            onValueEndChange = onValueEndChange,
        )
    }

private val audiChipWidget: ChipWidgetContent = { config, modifier, state, interactionConfig, leadingIcon, label, trailingIcon ->
    AudiChip(
        config = config,
        modifier = modifier,
        state = state,
        interactionConfig = interactionConfig,
        leadingIcon = leadingIcon,
        label = label,
        trailingIcon = trailingIcon,
    )
}

private val audiTileWidget: TileWidgetContent =
    { config, modifier, state, interactionConfig, content ->
        AudiTile(
            config = config,
            modifier = modifier,
            state = state,
            interactionConfig = interactionConfig,
            content = content,
        )
    }

private val audiSelectWidget: SelectWidgetContent =
    { options, selectedOption, onOptionSelected, config, modifier, state, content, slots, onExpandedChange ->
        AudiSelect(
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
            config = config,
            modifier = modifier,
            state = state,
            content = content,
            slots = slots,
            onExpandedChange = onExpandedChange,
        )
    }

private val audiSemanticShapeWidget: SemanticShapeWidgetContent = { config, modifier ->
    AudiSemanticShape(
        config = config,
        modifier = modifier,
    )
}

private val audiImageContainerWidget: ImageContainerWidgetContent = { config, modifier, content ->
    AudiImageContainer(
        config = config,
        modifier = modifier,
        content = content,
    )
}

private val audiProgressTrackerWidget: ProgressTrackerWidgetContent =
    { modifier, content ->
        AudiProgressTracker(
            modifier = modifier,
            content = content,
        )
    }

private val audiStepperWidget: StepperWidgetContent =
    { modifier, content, state, interactionConfig ->
        AudiStepper(
            modifier = modifier,
            content = content,
            state = state,
            interactionConfig = interactionConfig,
        )
    }

private val audiTextInputWidget: TextInputWidgetContent =
    { value, onValueChange, config, modifier, state, content, slots, interactionConfig ->
        AudiTextInput(
            value = value,
            onValueChange = onValueChange,
            config = config,
            modifier = modifier,
            state = state,
            content = content,
            slots = slots,
            interactionConfig = interactionConfig,
        )
    }

private val audiToggleSwitchWidget: ToggleSwitchWidgetContent =
    { modifier, content, state, interactionConfig ->
        AudiToggleSwitch(
            modifier = modifier,
            content = content,
            state = state,
            interactionConfig = interactionConfig,
        )
    }

private val audiSearchFieldWidget: SearchFieldWidgetContent =
    { value, onValueChange, modifier, content, state, interactionConfig ->
        AudiSearchField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            content = content,
            state = state,
            interactionConfig = interactionConfig,
        )
    }

private val audiMultiToggleButtonWidget: MultiToggleButtonWidgetContent =
    { config, modifier, state, interactionConfig, indicatorColorsOverride, icon, label ->
        AudiMultiToggleButton(
            config = config,
            modifier = modifier,
            state = state,
            interactionConfig = interactionConfig,
            indicatorColorsOverride = indicatorColorsOverride,
            icon = icon,
            label = label,
        )
    }

private val audiSegmentedControlWidget: SegmentedControlWidgetContent =
    { config, modifier, state, segments, interactionConfig ->
        AudiSegmentedControl(
            config = config,
            modifier = modifier,
            state = state,
            segments = segments,
            interactionConfig = interactionConfig,
        )
    }

private val audiListItemWidget: ListItemWidgetContent =
    { config, modifier, state, content, slots, interactionConfig ->
        AudiListItem(
            config = config,
            modifier = modifier,
            state = state,
            content = content,
            slots = slots,
            interactionConfig = interactionConfig,
        )
    }

private val audiNavigationBarWidget: NavigationBarWidgetContent =
    { config, modifier, state, items, interactionConfig ->
        AudiNavigationBar(
            config = config,
            modifier = modifier,
            state = state,
            items = items,
            interactionConfig = interactionConfig,
        )
    }

private val audiProgressIndicatorWidget: ProgressIndicatorWidgetContent =
    { config, modifier, progress, content ->
        AudiProgressIndicator(
            config = config,
            modifier = modifier,
            progress = progress,
            content = content,
        )
    }

/**
 * Root Audi theme composable.
 *
 * Shows a neutral placeholder while [DesignTokenManager] loads on the
 * background thread. Once loaded, renders the full themed content.
 *
 * @param isDarkOverride Null = follow system. True/false = force a mode.
 */
@Composable
fun AudiTheme(
    isDarkOverride: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val isLoaded by DesignTokenManager::isLoaded

    if (!isLoaded) {
        // Quick synchronous check: is the theme provider installed?
        val context = androidx.compose.ui.platform.LocalContext.current
        val providerAvailable =
            remember {
                context.packageManager.resolveContentProvider(
                    com.ui.core.ThemeProviderContract.AUTHORITY,
                    0,
                ) != null
            }

        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            if (!providerAvailable) {
                // Provider not installed — show install instructions immediately
                Column(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    M3Text(
                        text = "No Theme Loaded",
                        style =
                            TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                            ),
                    )
                    Spacer(Modifier.height(12.dp))
                    M3Text(
                        text = "Install the Theme Provider and an NXT Theme APK to display content.",
                        style =
                            TextStyle(
                                fontSize = 14.sp,
                                color = Color.LightGray,
                                textAlign = TextAlign.Center,
                            ),
                    )
                    Spacer(Modifier.height(24.dp))
                    M3Text(
                        text =
                            "1. Install theme-provider APK\n" +
                                "2. Install theme-nxt-1 (or nxt-2 / nxt-3 / nxt-4) APK\n" +
                                "3. Relaunch the app",
                        style =
                            TextStyle(
                                fontSize = 13.sp,
                                color = Color.LightGray,
                                lineHeight = 20.sp,
                            ),
                    )
                }
            }
            // else: provider is installed, theme is loading — plain black screen (no flash)
        }
        return
    }

    // Reading `revision` subscribes AudiTheme to every theme-load and dark/light toggle.
    @Suppress("UNUSED_VARIABLE")
    val revision = DesignTokenManager.revision

    val isDark = isDarkOverride ?: isSystemInDarkTheme()

    val layoutConfig by LayoutOrchestrator.config.collectAsStateWithLifecycle()
    val resolvedLayout = layoutConfig ?: LayoutConfig()

    val uxRestrictions by UxRestrictionsOrchestrator.restrictions.collectAsStateWithLifecycle()

    DesignTokenTheme(isDarkTheme = isDark) {
        CompositionLocalProvider(
            LocalLayoutDirection provides resolvedLayout.effectiveLayoutDirection,
            LocalUxRestrictions provides uxRestrictions,
        ) {
            val buttonStyle = AudiButtonDefaults.style()
            val toolbarStyle = AudiToolbarDefaults.style()
            val textStyle = AudiTextDefaults.style()
            val textLinkStyle = AudiTextLinkDefaults.style()
            val iconStyle = AudiIconDefaults.style()
            val dividerStyle = AudiDividerDefaults.style()
            val tagStyle = AudiTagDefaults.style()
            val adaptiveIconStyle = AudiAdaptiveIconDefaults.style()
            val buttonGroupStyle = AudiButtonGroupDefaults.style()
            val iconButtonStyle = AudiIconButtonDefaults.style()
            val scrollbarStyle = AudiScrollbarDefaults.style()
            val checkboxStyle = AudiCheckboxDefaults.style()
            val sliderStyle = AudiSliderDefaults.style()
            val imageContainerStyle = AudiImageContainerDefaults.style()
            val chipStyle = AudiChipDefaults.style()
            val tileStyle = AudiTileDefaults.style()
            val textInputStyle = AudiTextInputDefaults.style()
            val radioButtonStyle = RadioButtonDefaults.style()
            val contentGroupStyle = ContentGroupDefaults.style()
            val pinDisplayStyle = PinDisplayDefaults.style()
            val toggleSwitchStyle = ToggleSwitchDefaults.style()
            val searchFieldStyle = AudiSearchFieldDefaults.style()
            val multiToggleButtonStyle = AudiMultiToggleButtonDefaults.style()
            val segmentedControlStyle = AudiSegmentedControlDefaults.style()
            val listItemStyle = AudiListItemDefaults.style()
            val listItemSubComponentStyle = AudiListItemSubComponentDefaults.style()
            val stepperStyle = AudiStepperDefaults.style()
            val progressTrackerStyle = AudiProgressTrackerDefaults.style()
            val selectStyle = AudiSelectDefaults.style()
            val navigationBarStyle = AudiNavigationBarDefaults.style()
            val semanticShapeStyle = AudiSemanticShapeDefaults.style()
            val progressIndicatorStyle = AudiProgressIndicatorDefaults.style()

            CompositionLocalProvider(
                LocalLayoutConfig provides resolvedLayout,
                LocalButtonStyle provides buttonStyle,
                LocalContentColor provides textStyle.enabledColor,
                LocalToolbarStyle provides toolbarStyle,
                LocalTextStyleSpec provides textStyle,
                LocalTextLinkStyle provides textLinkStyle,
                LocalTileStyle provides tileStyle,
                LocalScrollbarStyle provides scrollbarStyle,
                LocalCheckboxStyle provides checkboxStyle,
                LocalRadioButtonStyle provides radioButtonStyle,
                LocalContentGroupStyle provides contentGroupStyle,
                LocalPinDisplayStyle provides pinDisplayStyle,
                LocalToggleSwitchStyle provides toggleSwitchStyle,
                LocalSearchFieldStyle provides searchFieldStyle,
                LocalWidgets.Button provides audiButtonWidget,
                LocalWidgets.Toolbar provides audiToolbarWidget,
                LocalWidgets.Text provides audiTextWidget,
                LocalWidgets.TextLink provides audiTextLinkWidget,
                LocalIconStyle provides iconStyle,
                LocalWidgets.Icon provides audiIconWidget,
                LocalDividerStyle provides dividerStyle,
                LocalWidgets.Divider provides audiDividerWidget,
                LocalTagStyle provides tagStyle,
                LocalWidgets.Tag provides audiTagWidget,
                LocalAdaptiveIconStyle provides adaptiveIconStyle,
                LocalWidgets.AdaptiveIcon provides audiAdaptiveIconWidget,
                LocalButtonGroupStyle provides buttonGroupStyle,
                LocalWidgets.ButtonGroup provides audiButtonGroupWidget,
                LocalIconButtonStyle provides iconButtonStyle,
                LocalWidgets.IconButton provides audiIconButtonWidget,
                LocalScrollbarStyle provides scrollbarStyle,
                LocalWidgets.Scrollbar provides audiScrollbarWidget,
                LocalWidgets.Checkbox provides audiCheckboxWidget,
                LocalWidgets.RadioButton provides audiRadioButtonWidget,
                LocalWidgets.ContentGroup provides audiContentGroupWidget,
                LocalWidgets.PinDisplay provides audiPinDisplayWidget,
                LocalWidgets.ToggleSwitch provides audiToggleSwitchWidget,
                LocalWidgets.SearchField provides audiSearchFieldWidget,
                LocalMultiToggleButtonStyle provides multiToggleButtonStyle,
                LocalWidgets.MultiToggleButton provides audiMultiToggleButtonWidget,
                LocalImageContainerStyle provides imageContainerStyle,
                LocalWidgets.ImageContainer provides audiImageContainerWidget,
                LocalSliderStyle provides sliderStyle,
                LocalWidgets.Slider provides audiSliderWidget,
                LocalChipStyle provides chipStyle,
                LocalWidgets.Chip provides audiChipWidget,
                LocalWidgets.Tile provides audiTileWidget,
                LocalTextInputStyle provides textInputStyle,
                LocalWidgets.TextInput provides audiTextInputWidget,
                LocalSegmentedControlStyle provides segmentedControlStyle,
                LocalWidgets.SegmentedControl provides audiSegmentedControlWidget,
                LocalListItemStyle provides listItemStyle,
                LocalListItemSubComponentStyle provides listItemSubComponentStyle,
                LocalWidgets.ListItem provides audiListItemWidget,
                LocalStepperStyle provides stepperStyle,
                LocalWidgets.Stepper provides audiStepperWidget,
                LocalWidgets.Stepper provides audiStepperWidget,
                LocalSelectStyle provides selectStyle,
                LocalWidgets.Select provides audiSelectWidget,
                LocalSemanticShapeStyle provides semanticShapeStyle,
                LocalWidgets.SemanticShape provides audiSemanticShapeWidget,
                LocalProgressTrackerStyle provides progressTrackerStyle,
                LocalWidgets.ProgressTracker provides audiProgressTrackerWidget,
                LocalNavigationBarStyle provides navigationBarStyle,
                LocalWidgets.NavigationBar provides audiNavigationBarWidget,
                LocalProgressIndicatorStyle provides progressIndicatorStyle,
                LocalWidgets.ProgressIndicator provides audiProgressIndicatorWidget,
            ) {
                content()
            }
        }
    }
}
