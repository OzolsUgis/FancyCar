package com.ugisozols.fancycar.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ugisozols.fancycar.data.local.entity.OwnerDataEntity
import com.ugisozols.fancycar.data.local.entity.VehicleDataEntity

data class OwnersWithVehicles(
    @Embedded val owner : OwnerDataEntity,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "ownerId"
    )
    val vehicles : List<VehicleDataEntity>
)
