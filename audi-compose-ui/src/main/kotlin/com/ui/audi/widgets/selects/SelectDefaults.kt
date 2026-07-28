package com.ui.audi.widgets.selects

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.selects.SelectStyle
import com.ui.core.widgets.selects.SelectTypeColors

/** Audi brand styling defaults for the Select widget. */
internal object SelectDefaults {
    @Composable
    fun style(): SelectStyle =
        SelectStyle(
            // Typography
            labelTextStyle =
                Cmp.Typography.Forms.FormFields.Label
                    .typography(),
            captionTextStyle =
                Cmp.Typography.Forms.FormFields.Caption
                    .typography(),
            placeholderTextStyle =
                Cmp.Typography.Forms.FormFields.Placeholder
                    .typography(),
            userInputTextStyle =
                Cmp.Typography.Forms.FormFields.UserInput
                    .typography(),
            // Field sizing
            fieldHeight =
                Cmp.Size.Forms.FormFields.Field.VisualHeight
                    .dimension()
                    .pxToDp(),
            fieldBorderRadius =
                RoundedCornerShape(
                    Cmp.BorderRadius.Forms.FormFields.Default
                        .dimension()
                        .pxToDp(),
                ),
            fieldBorderWidthIdle =
                Cmp.BorderWidth.Forms.FormFields.Default.Idle
                    .dimension()
                    .pxToDp(),
            fieldBorderWidthActive =
                Cmp.BorderWidth.Forms.FormFields.Default.Active
                    .dimension()
                    .pxToDp(),
            fieldBorderWidthPressed =
                Cmp.BorderWidth.Forms.FormFields.Default.Pressed
                    .dimension()
                    .pxToDp(),
            fieldBorderWidthDisabled =
                Cmp.BorderWidth.Forms.FormFields.Default.Disabled
                    .dimension()
                    .pxToDp(),
            // Field spacing
            fieldHorizontalPadding =
                Cmp.Space.Forms.FormFields.Field.H_Padding
                    .dimension()
                    .pxToDp(),
            fieldVerticalPadding =
                Sem.Space.Fixed._200
                    .dimension()
                    .pxToDp(),
            labelBottomSpacing =
                Cmp.Space.Forms.FormFields.LabelGroup.B_Padding
                    .dimension()
                    .pxToDp(),
            hintTopSpacing =
                Cmp.Space.Forms.FormFields.CaptionGroup.T_Padding
                    .dimension()
                    .pxToDp(),
            iconSpacing =
                Cmp.Space.Forms.FormFields.Field.Gap
                    .dimension()
                    .pxToDp(),
            // Disabled & focus
            disabledOpacity =
                Sem.Opacity.Disabled
                    .opacity(),
            focusRingColor =
                Sem.Color.Stroke.Signal.Focus
                    .color(),
            focusRingWidth =
                Sem.BorderWidth.FocusRing
                    .dimension()
                    .pxToDp(),
            // Error caption spacing & triangle sizing
            captionErrorTopSpacing =
                Cmp.Space.Forms.FormFields.CaptionGroup.T_Padding
                    .dimension()
                    .pxToDp(),
            captionErrorGap =
                Cmp.Space.Feedback.Badge.Gap
                    .dimension()
                    .pxToDp(),
            // ToDo: Semanticshape will be replaced by audi semanticshape after its implementation.
            errorTriangleHeight =
                Cmp.Size.Feedback.SemanticShape.Triangle.Height
                    .dimension()
                    .pxToDp(),
            errorTriangleWidth =
                Cmp.Size.Feedback.SemanticShape.Triangle.Width
                    .dimension()
                    .pxToDp(),
            errorTriangleBorderWidth =
                Cmp.BorderWidth.Feedback.SemanticShape.Default
                    .dimension()
                    .pxToDp(),
            errorTriangleFillColor =
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Fill
                    .color(),
            errorTriangleStrokeColor =
                Cmp.Color.Feedback.SemanticShape.Critical.Surface.Stroke
                    .color(),
            // Menu styling
            menuHorizontalPadding =
                Cmp.Space.Forms.FormFields.Menu.H_Padding
                    .dimension()
                    .pxToDp(),
            menuVerticalPadding =
                Cmp.Space.Forms.FormFields.Menu.V_Padding
                    .dimension()
                    .pxToDp(),
            menuSurfaceFill =
                Cmp.Color.Forms.FormFields.Menu.Surface.Fill
                    .color(),
            menuSurfaceStroke =
                Cmp.Color.Forms.FormFields.Menu.Surface.Stroke
                    .color(),
            menuBorderRadius =
                RoundedCornerShape(
                    Cmp.BorderRadius.Forms.FormFields.Menu.Default
                        .dimension()
                        .pxToDp(),
                ),
            menuBorderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Idle
                    .dimension()
                    .pxToDp(),
            menuShadow =
                Cmp.Shadow.Forms.Select.Menu.Default
                    .boxShadow(),
            menuItemVisualHeight =
                Cmp.Size.Forms.FormFields.Menu.VisualHeight
                    .dimension()
                    .pxToDp(),
            menuItemHeight =
                Cmp.Size.Forms.FormFields.Menu.TouchTarget
                    .dimension()
                    .pxToDp(),
            menuItemBorderRadius =
                RoundedCornerShape(
                    Cmp.BorderRadius.Forms.FormFields.Menu.Item.Default
                        .dimension()
                        .pxToDp(),
                ),
            menuItemBorderWidth =
                Cmp.BorderWidth.Forms.FormFields.Default.Idle
                    .dimension()
                    .pxToDp(),
            menuItemHorizontalPadding =
                Cmp.Space.Forms.FormFields.Menu.Item.H_Padding
                    .dimension()
                    .pxToDp(),
            menuMaxVisibleItems = 4,
            menuItemSpacing =
                Cmp.Space.Forms.FormFields.Menu.Item.Gap
                    .dimension()
                    .pxToDp(),
            // Colors
            defaultColors = defaultColors(),
            errorColors = errorColors(),
        )

    @Composable
    private fun defaultColors(): SelectTypeColors =
        SelectTypeColors(
            // Field surface
            fieldSurfaceFill =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Fill
                    .color(),
            // Field stroke (border) per state
            fieldStrokeIdle =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Idle
                    .color(),
            fieldStrokeActive =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Active
                    .color(),
            fieldStrokePressed =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Pressed
                    .color(),
            fieldStrokeDisabled =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Disabled
                    .color(),
            fieldStrokeReadOnly =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.ReadOnly
                    .color(),
            // Field state layer
            fieldStateLayerPressed =
                Cmp.Color.Forms.FormFields.Field.Default.StateLayer.Pressed
                    .color(),
            // Label colors
            labelIdle =
                Cmp.Color.Forms.FormFields.Label.Default.Idle
                    .color(),
            labelPressed =
                Cmp.Color.Forms.FormFields.Label.Default.Pressed
                    .color(),
            labelDisabled =
                Cmp.Color.Forms.FormFields.Label.Default.Disabled
                    .color(),
            // Appendix colors
            appendixIdle =
                Cmp.Color.Forms.FormFields.Apendix.Default
                    .color(),
            appendixPressed =
                Cmp.Color.Forms.FormFields.Apendix.Default
                    .color(),
            // Hint colors
            hintIdle =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
            hintPressed =
                Cmp.Color.Forms.FormFields.Caption.Default
                    .color(),
            // Placeholder colors
            placeholderIdle =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Idle
                    .color(),
            placeholderActive =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Active
                    .color(),
            placeholderPressed =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Pressed
                    .color(),
            placeholderDisabled =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Disabled
                    .color(),
            // User input (selected value) colors
            userInputIdle =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Idle
                    .color(),
            userInputActive =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Active
                    .color(),
            userInputPressed =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Pressed
                    .color(),
            userInputDisabled =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Disabled
                    .color(),
            userInputReadOnly =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.ReadOnly
                    .color(),
            // Icon colors
            iconIdle =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Idle
                    .color(),
            iconActive =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Active
                    .color(),
            iconPressed =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Pressed
                    .color(),
            iconDisabled =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Disabled
                    .color(),
            iconReadOnly =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.ReadOnly
                    .color(),
            // Menu item - Selected
            menuItemSelectedSurfaceFill =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Surface.Fill
                    .color(),
            menuItemSelectedStrokeIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Surface.Stroke.Idle
                    .color(),
            menuItemSelectedStrokePressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Surface.Stroke.Pressed
                    .color(),
            menuItemSelectedStrokeDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Surface.Stroke.Disabled
                    .color(),
            menuItemSelectedTextIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.UserInput.Idle
                    .color(),
            menuItemSelectedTextPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.UserInput.Pressed
                    .color(),
            menuItemSelectedTextDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.UserInput.Disabled
                    .color(),
            menuItemSelectedIconIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.Icon.Idle
                    .color(),
            menuItemSelectedIconPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.Icon.Pressed
                    .color(),
            menuItemSelectedIconDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.Icon.Disabled
                    .color(),
            menuItemSelectedStateLayerPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.StateLayer.Pressed
                    .color(),
            // Menu item - Unselected
            menuItemUnselectedSurfaceFill =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Surface.Fill
                    .color(),
            menuItemUnselectedStrokeIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Surface.Stroke.Idle
                    .color(),
            menuItemUnselectedStrokePressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Surface.Stroke.Pressed
                    .color(),
            menuItemUnselectedStrokeDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Surface.Stroke.Disabled
                    .color(),
            menuItemUnselectedTextIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.UserInput.Idle
                    .color(),
            menuItemUnselectedTextPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.UserInput.Pressed
                    .color(),
            menuItemUnselectedTextDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.UserInput.Disabled
                    .color(),
            menuItemUnselectedIconIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.Icon.Idle
                    .color(),
            menuItemUnselectedIconPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.Icon.Pressed
                    .color(),
            menuItemUnselectedIconDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.Icon.Disabled
                    .color(),
            menuItemUnselectedStateLayerPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.StateLayer.Pressed
                    .color(),
        )

    @Composable
    private fun errorColors(): SelectTypeColors =
        SelectTypeColors(
            // Field surface
            fieldSurfaceFill =
                Cmp.Color.Forms.FormFields.Field.Error.Surface.Fill
                    .color(),
            // Field stroke (border) - Error tokens don't have Disabled/ReadOnly, fallback to Default
            fieldStrokeIdle =
                Cmp.Color.Forms.FormFields.Field.Error.Surface.Stroke.Idle
                    .color(),
            fieldStrokeActive =
                Cmp.Color.Forms.FormFields.Field.Error.Surface.Stroke.Active
                    .color(),
            fieldStrokePressed =
                Cmp.Color.Forms.FormFields.Field.Error.Surface.Stroke.Pressed
                    .color(),
            fieldStrokeDisabled =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.Disabled
                    .color(),
            // Fallback
            fieldStrokeReadOnly =
                Cmp.Color.Forms.FormFields.Field.Default.Surface.Stroke.ReadOnly
                    .color(),
            // Fallback
            // Field state layer
            fieldStateLayerPressed =
                Cmp.Color.Forms.FormFields.Field.Error.StateLayer.Pressed
                    .color(),
            // Label colors - Error
            labelIdle =
                Cmp.Color.Forms.FormFields.Label.Error.Idle
                    .color(),
            labelPressed =
                Cmp.Color.Forms.FormFields.Label.Error.Pressed
                    .color(),
            labelDisabled =
                Cmp.Color.Forms.FormFields.Label.Error.Disabled
                    .color(),
            // Appendix colors - Error
            appendixIdle =
                Cmp.Color.Forms.FormFields.Apendix.Error
                    .color(),
            appendixPressed =
                Cmp.Color.Forms.FormFields.Apendix.Error
                    .color(),
            // Hint colors - Error
            hintIdle =
                Cmp.Color.Forms.FormFields.Caption.Error
                    .color(),
            hintPressed =
                Cmp.Color.Forms.FormFields.Caption.Error
                    .color(),
            // Placeholder colors - Error (no Disabled token, fallback)
            placeholderIdle =
                Cmp.Color.Forms.FormFields.Field.Error.Content.Placeholder.Idle
                    .color(),
            placeholderActive =
                Cmp.Color.Forms.FormFields.Field.Error.Content.Placeholder.Active
                    .color(),
            placeholderPressed =
                Cmp.Color.Forms.FormFields.Field.Error.Content.Placeholder.Pressed
                    .color(),
            placeholderDisabled =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Placeholder.Disabled
                    .color(),
            // Fallback
            // User input (selected value) colors - Error (no Disabled/ReadOnly, fallback)
            userInputIdle =
                Cmp.Color.Forms.FormFields.Field.Error.Content.UserInput.Idle
                    .color(),
            userInputActive =
                Cmp.Color.Forms.FormFields.Field.Error.Content.UserInput.Active
                    .color(),
            userInputPressed =
                Cmp.Color.Forms.FormFields.Field.Error.Content.UserInput.Pressed
                    .color(),
            userInputDisabled =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.Disabled
                    .color(),
            // Fallback
            userInputReadOnly =
                Cmp.Color.Forms.FormFields.Field.Default.Content.UserInput.ReadOnly
                    .color(),
            // Fallback
            // Icon colors - Error (no Disabled/ReadOnly, fallback)
            iconIdle =
                Cmp.Color.Forms.FormFields.Field.Error.Content.Icon.Idle
                    .color(),
            iconActive =
                Cmp.Color.Forms.FormFields.Field.Error.Content.Icon.Active
                    .color(),
            iconPressed =
                Cmp.Color.Forms.FormFields.Field.Error.Content.Icon.Pressed
                    .color(),
            iconDisabled =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.Disabled
                    .color(),
            // Fallback
            iconReadOnly =
                Cmp.Color.Forms.FormFields.Field.Default.Content.Icon.ReadOnly
                    .color(),
            // Fallback
            // Menu item colors - same for both default and error (menu doesn't change on error)
            menuItemSelectedSurfaceFill =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Surface.Fill
                    .color(),
            menuItemSelectedStrokeIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Surface.Stroke.Idle
                    .color(),
            menuItemSelectedStrokePressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Surface.Stroke.Pressed
                    .color(),
            menuItemSelectedStrokeDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Surface.Stroke.Disabled
                    .color(),
            menuItemSelectedTextIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.UserInput.Idle
                    .color(),
            menuItemSelectedTextPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.UserInput.Pressed
                    .color(),
            menuItemSelectedTextDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.UserInput.Disabled
                    .color(),
            menuItemSelectedIconIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.Icon.Idle
                    .color(),
            menuItemSelectedIconPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.Icon.Pressed
                    .color(),
            menuItemSelectedIconDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.Content.Icon.Disabled
                    .color(),
            menuItemSelectedStateLayerPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Selected.StateLayer.Pressed
                    .color(),
            menuItemUnselectedSurfaceFill =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Surface.Fill
                    .color(),
            menuItemUnselectedStrokeIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Surface.Stroke.Idle
                    .color(),
            menuItemUnselectedStrokePressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Surface.Stroke.Pressed
                    .color(),
            menuItemUnselectedStrokeDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Surface.Stroke.Disabled
                    .color(),
            menuItemUnselectedTextIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.UserInput.Idle
                    .color(),
            menuItemUnselectedTextPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.UserInput.Pressed
                    .color(),
            menuItemUnselectedTextDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.UserInput.Disabled
                    .color(),
            menuItemUnselectedIconIdle =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.Icon.Idle
                    .color(),
            menuItemUnselectedIconPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.Icon.Pressed
                    .color(),
            menuItemUnselectedIconDisabled =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.Content.Icon.Disabled
                    .color(),
            menuItemUnselectedStateLayerPressed =
                Cmp.Color.Forms.FormFields.Menu.Item.Unselected.StateLayer.Pressed
                    .color(),
        )
}
