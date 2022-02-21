package com.ugisozols.fancycar.domain.model

data class CarOwnerProfile(
    val name : String,
    val surname : String,
    val profilePicture : String,
    val vehicles : List<OwnerVehicles>,

)
