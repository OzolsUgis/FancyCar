package com.ugisozols.fancycar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OwnerDataEntity(
    val name : String,
    val surname : String,
    val userImage : String,
    val vehicles : List<VehicleDataEntity>,
    @PrimaryKey(autoGenerate = false)
    val ownerId : Int? = null

)
