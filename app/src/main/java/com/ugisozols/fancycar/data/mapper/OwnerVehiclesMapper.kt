package com.ugisozols.fancycar.data.mapper

import com.ugisozols.fancycar.data.local.entity.VehicleDataEntity
import com.ugisozols.fancycar.domain.model.OwnerVehicles

fun VehicleDataEntity.toOwnerVehicles() : OwnerVehicles{
    return OwnerVehicles(
        ownerId = ownerId,
        color = color,
        foto = foto,
        make = make,
        model = model,
        vehicleid = vehicleid,
        vin = vin,
        year = year,
        latitude = latitude ?: 0.0 ,
        longitude = longitude ?: 0.0,
        isShownInDetails = false
    )
}

fun OwnerVehicles.toVehicleDataEntity() : VehicleDataEntity{
    return VehicleDataEntity(
        color = color,
        foto = foto,
        make = make,
        model = model,
        vin = vin,
        year = year,
        latitude = latitude,
        longitude = longitude,
        ownerId = ownerId,
        vehicleid = vehicleid
    )
}