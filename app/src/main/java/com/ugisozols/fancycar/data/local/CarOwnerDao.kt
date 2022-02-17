package com.ugisozols.fancycar.data.local

import androidx.room.*
import com.ugisozols.fancycar.data.local.entity.OwnerDataEntity
import com.ugisozols.fancycar.data.local.entity.Vehicles
import kotlinx.coroutines.flow.Flow

@Dao
interface CarOwnerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwner(data : OwnerDataEntity)

    @Query("DELETE FROM ownerdataentity")
    suspend fun deleteAllOwners()

    @Query("SELECT * FROM ownerdataentity")
    fun getOwners() : Flow<List<OwnerDataEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle : Vehicles)

}