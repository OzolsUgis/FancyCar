package com.ugisozols.fancycar.data.remote.responses

data class Vehicle(
    val color: String,
    val foto: String,
    val make: String,
    val model: String,
    val vehicleid: Int,
    val vin: String,
    val year: String
)