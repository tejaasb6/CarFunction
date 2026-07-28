package com.ui.core.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.ui.core.widgets.adaptiveicons.AdaptiveIconWidgetContent
import com.ui.core.widgets.buttongroups.ButtonGroupWidgetContent
import com.ui.core.widgets.buttons.ButtonWidgetContent
import com.ui.core.widgets.checkbox.CheckboxWidgetContent
import com.ui.core.widgets.chips.ChipWidgetContent
import com.ui.core.widgets.contentgroup.ContentGroupWidgetContent
import com.ui.core.widgets.dividers.DividerWidgetContent
import com.ui.core.widgets.iconbuttons.IconButtonWidgetContent
import com.ui.core.widgets.icons.IconWidgetContent
import com.ui.core.widgets.imagecontainer.ImageContainerWidgetContent
import com.ui.core.widgets.listitems.ListItemWidgetContent
import com.ui.core.widgets.multitogglebuttons.MultiToggleButtonWidgetContent
import com.ui.core.widgets.navigationbars.NavigationBarWidgetContent
import com.ui.core.widgets.pindisplay.PinDisplayWidgetContent
import com.ui.core.widgets.progressindicators.ProgressIndicatorWidgetContent
import com.ui.core.widgets.progresstrackers.ProgressTrackerWidgetContent
import com.ui.core.widgets.radiobuttons.RadioButtonWidgetContent
import com.ui.core.widgets.scrollbar.ScrollbarWidgetContent
import com.ui.core.widgets.searchfields.SearchFieldWidgetContent
import com.ui.core.widgets.segmentedcontrols.SegmentedControlWidgetContent
import com.ui.core.widgets.selects.SelectWidgetContent
import com.ui.core.widgets.semanticshapes.SemanticShapeWidgetContent
import com.ui.core.widgets.sliders.SliderWidgetContent
import com.ui.core.widgets.steppers.StepperWidgetContent
import com.ui.core.widgets.tags.TagWidgetContent
import com.ui.core.widgets.text.TextWidgetContent
import com.ui.core.widgets.textinputs.TextInputWidgetContent
import com.ui.core.widgets.textlink.TextLinkWidgetContent
import com.ui.core.widgets.tiles.TileWidgetContent
import com.ui.core.widgets.toggleswitch.ToggleSwitchWidgetContent

// ── Widget content type aliases ────────────────────────────────────────────────

/** Composable function type for a toolbar widget. */
typealias ToolbarWidgetContent = @Composable (
    title: String,
    modifier: Modifier,
    actions: @Composable () -> Unit,
) -> Unit

// ── Composition locals ─────────────────────────────────────────────────────────

/**
 * Holds the brand-provided widget implementations for the current composition.
 *
 * Access pattern:
 *   `LocalWidgets.Button.current(config, modifier, state, interactionConfig, leading, label, trailing)`
 */
object LocalWidgets {
    val Button =
        staticCompositionLocalOf<ButtonWidgetContent> {
            error("No Button widget — wrap content in AudiTheme / LamborghiniTheme")
        }
    val Toolbar =
        staticCompositionLocalOf<ToolbarWidgetContent> {
            error("No Toolbar widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val Text =
        staticCompositionLocalOf<TextWidgetContent> {
            error("No Text widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val TextLink =
        staticCompositionLocalOf<TextLinkWidgetContent> {
            error("No TextLink widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val Icon =
        staticCompositionLocalOf<IconWidgetContent> {
            error("No Icon widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val Divider =
        staticCompositionLocalOf<DividerWidgetContent> {
            error("No Divider widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val Tag =
        staticCompositionLocalOf<TagWidgetContent> {
            error("No Tag widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val AdaptiveIcon =
        staticCompositionLocalOf<AdaptiveIconWidgetContent> {
            error("No AdaptiveIcon widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val ButtonGroup =
        staticCompositionLocalOf<ButtonGroupWidgetContent> {
            error("No ButtonGroup widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val IconButton =
        staticCompositionLocalOf<IconButtonWidgetContent> {
            error("No IconButton widget — wrap content in AudiTheme / LamborghiniTheme")
        }
    val Scrollbar =
        staticCompositionLocalOf<ScrollbarWidgetContent> {
            error("No Scrollbar widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val Chip =
        staticCompositionLocalOf<ChipWidgetContent> {
            error("No Chip widget — wrap content in AudiTheme / LamborghiniTheme")
        }
    val Slider =
        staticCompositionLocalOf<SliderWidgetContent> {
            error("No Slider widget — wrap content in AudiTheme / LamborghiniTheme")
        }
    val Tile =
        staticCompositionLocalOf<TileWidgetContent> {
            error("No Tile widget — wrap content in a brand theme")
        }

    val ImageContainer =
        staticCompositionLocalOf<ImageContainerWidgetContent> {
            error("No ImageContainer widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val TextInput =
        staticCompositionLocalOf<TextInputWidgetContent> {
            error("No TextInput widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val Checkbox =
        staticCompositionLocalOf<CheckboxWidgetContent> {
            error("No Checkbox widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val RadioButton =
        staticCompositionLocalOf<RadioButtonWidgetContent> {
            error("No RadioButton widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val ContentGroup =
        staticCompositionLocalOf<ContentGroupWidgetContent> {
            error("No ContentGroup widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val PinDisplay =
        staticCompositionLocalOf<PinDisplayWidgetContent> {
            error("No PinDisplay widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val ToggleSwitch =
        staticCompositionLocalOf<ToggleSwitchWidgetContent> {
            error("No ToggleSwitch widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val SearchField =
        staticCompositionLocalOf<SearchFieldWidgetContent> {
            error("No SearchField widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val SegmentedControl =
        staticCompositionLocalOf<SegmentedControlWidgetContent> {
            error("No SegmentedControl widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val MultiToggleButton =
        staticCompositionLocalOf<MultiToggleButtonWidgetContent> {
            error("No MultiToggleButton widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val ListItem =
        staticCompositionLocalOf<ListItemWidgetContent> {
            error("No ListItem widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val Stepper =
        staticCompositionLocalOf<StepperWidgetContent> {
            error("No Stepper widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val Select =
        staticCompositionLocalOf<SelectWidgetContent> {
            error("No Select widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val SemanticShape =
        staticCompositionLocalOf<SemanticShapeWidgetContent> {
            error("No SemanticShape widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val NavigationBar =
        staticCompositionLocalOf<NavigationBarWidgetContent> {
            error("No NavigationBar widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val ProgressIndicator =
        staticCompositionLocalOf<ProgressIndicatorWidgetContent> {
            error("No ProgressIndicator widget — wrap content in AudiTheme / LamborghiniTheme")
        }

    val ProgressTracker =
        staticCompositionLocalOf<ProgressTrackerWidgetContent> {
            error("No ProgressTracker widget — wrap content in AudiTheme / LamborghiniTheme")
        }
}
