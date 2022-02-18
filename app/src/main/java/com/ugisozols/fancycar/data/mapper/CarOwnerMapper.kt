package com.ugisozols.fancycar.data.mapper

import com.ugisozols.fancycar.data.local.entity.OwnerDataEntity
import com.ugisozols.fancycar.domain.model.CarOwner

fun OwnerDataEntity.toCarOwner() : CarOwner {
    return CarOwner(
        name = name,
        surname = surname,
        userPicture = userImage,
        vehicles = vehicles.map {it.toOwnerVehicles()},
        id = ownerId
    )
}