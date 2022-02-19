package com.ugisozols.fancycar.presentation.owner_list_screen.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.ugisozols.fancycar.presentation.ui.theme.BackgroundColor
import com.ugisozols.fancycar.presentation.ui.theme.HeaderObject
import com.ugisozols.fancycar.util.UiText

@Composable
fun ListScreenHeader(
    modifier: Modifier = Modifier,
    text : String
) {
    Canvas(
        modifier =
            modifier.fillMaxWidth()

    ) {
        val size = this.size
        val path = Path().apply {
            lineTo(0f, size.maxDimension / 4 + 100f)
            quadraticBezierTo(
                x1 = size.maxDimension / 8,
                y1 = size.maxDimension / 4 + 200f,
                x2 = size.maxDimension / 5 - 50f,
                y2 = size.maxDimension / 4 - 20f
            )
            quadraticBezierTo(
                x1 = size.maxDimension / 3,
                y1 = 0f,
                x2 = size.maxDimension,
                y2 = size.maxDimension / 2
            )
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
        }
        drawPath(
            path = path,
            color = HeaderObject
        )
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                text,
                80f,
                300f,
                Paint().apply {
                    color = BackgroundColor.toArgb()
                    textSize = 80f
                }
            )
        }
    }
}