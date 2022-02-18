package com.ugisozols.fancycar.presentation.owner_list_screen

import com.ugisozols.fancycar.domain.model.CarOwner

data class OwnerListState(
    val ownersList : List<CarOwner> = emptyList(),
    val isLoading: Boolean = false
)
