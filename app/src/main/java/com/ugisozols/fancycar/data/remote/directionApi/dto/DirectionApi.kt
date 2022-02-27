package com.ugisozols.fancycar.data.remote.directionApi.dto

data class DirectionApi(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)