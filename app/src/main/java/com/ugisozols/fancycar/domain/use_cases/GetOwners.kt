package com.ugisozols.fancycar.domain.use_cases

import com.ugisozols.fancycar.domain.model.CarOwner
import com.ugisozols.fancycar.domain.repository.CarOwnerRepository
import com.ugisozols.fancycar.util.Resource
import kotlinx.coroutines.flow.Flow

class GetOwners(
    private val repository : CarOwnerRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<CarOwner>>> {
        return repository.getCarOwners()
    }
}