package com.ugisozols.fancycar.util

import android.content.Context

sealed class UiText(){
    data class SimpleString(val text : String): UiText()
    data class StringResource(val resId : Int) : UiText()

    fun asString(context: Context) : String {
        return when(this){
            is SimpleString -> text
            is StringResource -> context.getString(resId)
        }
    }
}
