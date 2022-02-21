package com.ugisozols.fancycar.data.mapper

import com.ugisozols.fancycar.data.local.entity.VehicleDataEntity
import com.ugisozols.fancycar.data.remote.dto.Vehicle

fun Vehicle.toVehicleDataEntity() : VehicleDataEntity {
    return VehicleDataEntity(
        color = color,
        foto = foto,
        make = make,
        model = model,
        vin = vin,
        year = year,
        latitude = 0.0,
        longitude = 0.0,
        vehicleid = vehicleid,
        ownerId = null
    )
}