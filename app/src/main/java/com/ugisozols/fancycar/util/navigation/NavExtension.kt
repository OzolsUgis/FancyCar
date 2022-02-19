package com.ugisozols.fancycar.util.navigation

import androidx.navigation.NavController
import com.ugisozols.fancycar.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}