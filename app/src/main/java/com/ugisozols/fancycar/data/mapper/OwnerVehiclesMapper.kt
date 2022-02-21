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
        latitude = 0.0,
        longitude = 0.0
    )
}