package com.ugisozols.fancycar.presentation.map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MapScreen(viewModel: MapScreenViewModel = hiltViewModel()){
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

    }
    Text(
        text =
            "owner : ${state.Owner?.name + state.Owner?.surname}\n"
    )
    state.Owner?.vehicles?.forEach { vehicle ->
        Text(
            text ="Vehicle = ${vehicle.make + vehicle.model + vehicle.year}\n Latitude : ${vehicle.latitude}\n Longitude : ${vehicle.longitude}"


        )

    }
}