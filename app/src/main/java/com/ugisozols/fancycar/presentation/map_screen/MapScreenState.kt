package com.ugisozols.fancycar.presentation.map_screen

import com.ugisozols.fancycar.domain.model.CarOwner

data class MapScreenState(
    val Owner : CarOwner?,
    val isLoading: Boolean = false
)
