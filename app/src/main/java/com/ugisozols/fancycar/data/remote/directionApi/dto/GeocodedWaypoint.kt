package com.ugisozols.fancycar.data.remote.directionApi.dto

data class GeocodedWaypoint(
    val geocoder_status: String,
    val place_id: String,
    val types: List<String>
)