package com.ugisozols.fancycar.util

sealed class UiEvent(){
    data class ShowSnackbar(val message : String) : UiEvent()
}
