package com.ugisozols.fancycar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ugisozols.fancycar.data.local.entity.OwnerDataEntity
import com.ugisozols.fancycar.data.local.entity.VehicleDataEntity


@Database(
    entities = [
        OwnerDataEntity::class,
        VehicleDataEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CarOwnerDatabase : RoomDatabase() {
    abstract val dao : CarOwnerDao

    companion object{
        const val DATABASE_NAME = "car_owner_db"
    }
}