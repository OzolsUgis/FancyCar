package com.ugisozols.fancycar.domain.use_cases

import com.ugisozols.fancycar.domain.model.CarOwner
import com.ugisozols.fancycar.domain.repository.CarOwnerRepository
import com.ugisozols.fancycar.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateOwnersVehicles(
    private val repository: CarOwnerRepository
) {

    suspend operator fun invoke(ownerId : Int) : Flow<Resource<CarOwner>>{
        return repository.getVehicleLocation(ownerId)
    }
}
