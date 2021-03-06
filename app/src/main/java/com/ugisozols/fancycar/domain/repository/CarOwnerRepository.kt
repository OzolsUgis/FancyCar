package com.ugisozols.fancycar.domain.repository

import com.ugisozols.fancycar.domain.model.CarOwner
import com.ugisozols.fancycar.domain.model.OwnerVehicles
import com.ugisozols.fancycar.util.Resource
import kotlinx.coroutines.flow.Flow

interface CarOwnerRepository {

    suspend fun getCarOwners() : Flow<Resource<List<CarOwner>>>

    suspend fun getVehicleLocation(ownerId : Int) : Flow<Resource<CarOwner>>
}