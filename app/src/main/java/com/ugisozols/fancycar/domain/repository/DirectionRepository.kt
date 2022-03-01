package com.ugisozols.fancycar.domain.repository

import com.google.android.gms.maps.model.LatLng

interface DirectionRepository {

    suspend fun getDirection(from : LatLng, to : LatLng) : List<List<LatLng>>
}