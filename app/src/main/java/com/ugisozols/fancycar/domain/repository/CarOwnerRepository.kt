package com.ugisozols.fancycar.domain.repository

import com.ugisozols.fancycar.domain.model.CarOwner
import com.ugisozols.fancycar.util.Resource
import kotlinx.coroutines.flow.Flow

interface CarOwnerRepository {

    fun getCarOwners() : Flow<Resource<List<CarOwner>>>
}