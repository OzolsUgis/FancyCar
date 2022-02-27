package com.ugisozols.fancycar.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.ugisozols.fancycar.domain.model.Polyline

interface DirectionRepository {

    suspend fun getDirection(from : LatLng, to : LatLng) : List<List<LatLng>>
}