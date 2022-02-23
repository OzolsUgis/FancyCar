package com.ugisozols.fancycar.domain.model

data class OwnerVehicles(
    val ownerId : Int?,
    val color: String,
    val foto: String,
    val make: String,
    val model: String,
    val vehicleid: Int,
    val vin: String,
    val year: String,
    val latitude : Double,
    val longitude : Double,
    val isShownInDetails : Boolean
)
