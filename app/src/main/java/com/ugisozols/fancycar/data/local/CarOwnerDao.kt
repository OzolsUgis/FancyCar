package com.ugisozols.fancycar.data.local

import androidx.room.*
import com.ugisozols.fancycar.data.local.entity.OwnerDataEntity
import com.ugisozols.fancycar.data.local.entity.VehicleDataEntity
import com.ugisozols.fancycar.data.local.entity.relations.OwnersWithVehicles

@Dao
interface CarOwnerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwner(data: OwnerDataEntity)

    @Query("DELETE FROM ownerdataentity")
    suspend fun deleteAllOwners()

    @Query("SELECT * FROM ownerdataentity")
    suspend fun getOwners(): List<OwnerDataEntity>


    @Query("""
                UPDATE vehicledataentity 
                SET latitude = :latitude, longitude = :longitude
                WHERE vehicleid =:vehicleId
        """)
    suspend fun updateVehicle(vehicleId: Int, latitude: Double, longitude: Double)

    @Transaction
    @Query("SELECT * FROM ownerdataentity WHERE ownerId = :ownerId ")
    suspend fun getOwnerWithVehicles(ownerId: Int): OwnersWithVehicles

}