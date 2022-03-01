package com.ugisozols.fancycar.domain.use_cases

import androidx.compose.ui.graphics.Color

class DecodeColorFromString {
    operator fun invoke(color : String) : Color{
        return Color(android.graphics.Color.parseColor(color))
    }
}