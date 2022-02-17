package com.ugisozols.fancycar.data.remote.dto

data class UserData(
    val owner: Owner,
    val userid: Int,
    val vehicles: List<Vehicle>
)