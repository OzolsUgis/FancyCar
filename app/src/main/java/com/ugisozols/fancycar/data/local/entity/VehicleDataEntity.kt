package com.ugisozols.fancycar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VehicleDataEntity(
    val color: String,
    val foto: String,
    val make: String,
    val model: String,
    val vin: String,
    val year: String,
    val latitude : Double?,
    val longitude : Double?,
    val ownerId : Int?,
    @PrimaryKey(autoGenerate = false)
    val vehicleid: Int
)
