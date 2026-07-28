package com.ui.audi.widgets.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.ui.audi.theme.AudiFont
import com.ui.core.engine.api.Sem
import com.ui.core.utils.pxToSp
import com.ui.core.widgets.text.TextStyleSpec

/** Audi default [TextStyleSpec] — resolves every value from the token engine. */
internal object TextDefaults {
    @Composable
    fun style(): TextStyleSpec =
        TextStyleSpec(
            normal =
                TextStyle(
                    fontFamily = AudiFont,
                    fontWeight = FontWeight.Normal,
                    fontSize =
                        Sem.FontSize._500
                            .font()
                            .pxToSp(),
                ),
            paragraph =
                TextStyle(
                    fontFamily = AudiFont,
                    fontWeight = FontWeight.Normal,
                    fontSize =
                        Sem.FontSize._500
                            .font()
                            .pxToSp(),
                    lineHeight =
                        Sem.FontSize._500
                            .font()
                            .pxToSp(),
                ),
            body =
                TextStyle(
                    fontFamily = AudiFont,
                    fontWeight = FontWeight.Normal,
                    fontSize =
                        Sem.FontSize._500
                            .font()
                            .pxToSp(),
                ),
            label =
                TextStyle(
                    fontFamily = AudiFont,
                    fontWeight = FontWeight.Bold,
                    fontSize =
                        Sem.FontSize._500
                            .font()
                            .pxToSp(),
                ),
            enabledColor =
                Sem.Color.Content.Primary
                    .color(),
            disabledColor =
                Sem.Color.Content.Tertiary
                    .color(),
        )
}
