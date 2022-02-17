package com.ugisozols.fancycar.data.remote.responses

data class Data(
    val owner: Owner,
    val userid: Int,
    val vehicles: List<Vehicle>
)