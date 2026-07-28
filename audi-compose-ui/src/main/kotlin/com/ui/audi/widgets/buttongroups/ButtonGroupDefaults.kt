package com.ui.audi.widgets.buttongroups

import androidx.compose.runtime.Composable
import com.ui.core.engine.api.Cmp
import com.ui.core.utils.pxToDp
import com.ui.core.widgets.buttongroups.ButtonGroupStyle

/**
 * Audi default [ButtonGroupStyle] — gap from Cmp tokens.
 *
 * Vertical gap token (Cmp.Space.Action.ButtonGroup.Vertical.MD.Gap) resolves to 0px.
 * Using horizontal gap value for vertical representation until the token is updated.
 */
@Suppress("ForbiddenComment")
internal object ButtonGroupDefaults {
    @Composable
    fun style(): ButtonGroupStyle {
        val horizontalGap =
            Cmp.Space.Action.ButtonGroup.Horizontal.MD.Gap
                .dimension()
                .pxToDp()
        val verticalGap =
            Cmp.Space.Action.ButtonGroup.Vertical.MD.Gap
                .dimension()
                .pxToDp()

        return ButtonGroupStyle(
            horizontalGap = horizontalGap,
            // verticalGap token resolves to 0px — using horizontalGap for representation
            verticalGap = if (verticalGap.value <= 0f) horizontalGap else verticalGap,
        )
    }
}
