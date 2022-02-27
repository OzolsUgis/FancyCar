package com.ugisozols.fancycar.data.mapper

import com.ugisozols.fancycar.data.local.entity.OwnerDataEntity
import com.ugisozols.fancycar.data.remote.driverApi.dto.UserData

fun UserData.toOwnerDataEntity(): OwnerDataEntity {
    return OwnerDataEntity(
        name = owner.name,
        surname = owner.surname,
        userImage = owner.foto,
        vehicles = vehicles.map { it.toVehicleDataEntity()},
        ownerId = userid
    )
}