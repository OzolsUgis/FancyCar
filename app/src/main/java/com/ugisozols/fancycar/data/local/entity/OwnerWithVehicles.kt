package com.ugisozols.fancycar.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class OwnersWithVehicles(
    @Embedded val owner : OwnerDataEntity,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "vehicleid"
    )
    val vehicles: List<Vehicles>
)
