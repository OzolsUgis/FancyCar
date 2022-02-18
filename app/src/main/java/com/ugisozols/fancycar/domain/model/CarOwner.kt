package com.ugisozols.fancycar.domain.model

data class CarOwner(
    val name : String,
    val surname : String,
    val userPicture : String,
    val vehicles : List<OwnerVehicles>,
    val id : Int? = null
)
