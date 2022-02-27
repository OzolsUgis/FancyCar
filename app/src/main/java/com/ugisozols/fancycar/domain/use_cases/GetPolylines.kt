package com.ugisozols.fancycar.domain.use_cases

import com.google.android.gms.maps.model.LatLng
import com.ugisozols.fancycar.domain.repository.DirectionRepository
import javax.inject.Inject

class GetPolylines @Inject constructor(
    private val repository: DirectionRepository
) {
    suspend operator fun invoke(from : LatLng, to : LatLng) : List<List<LatLng>>{
        return repository.getDirection(from, to)
    }
}