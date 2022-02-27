package com.ugisozols.fancycar.data.remote.driverApi.dto

data class UserData(
    val owner: Owner,
    val userid: Int,
    val vehicles: List<Vehicle>
)