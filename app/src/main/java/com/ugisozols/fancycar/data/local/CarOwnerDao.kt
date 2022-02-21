package com.ugisozols.fancycar.data.local

import androidx.room.*
import com.ugisozols.fancycar.data.local.entity.OwnerDataEntity
import com.ugisozols.fancycar.data.local.entity.VehicleDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.selects.select

@Dao
interface CarOwnerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwner(data: OwnerDataEntity)

    @Query("DELETE FROM ownerdataentity")
    suspend fun deleteAllOwners()

    @Query("SELECT * FROM ownerdataentity")
    suspend fun getOwners(): List<OwnerDataEntity>

    @Query("SELECT * FROM ownerdataentity WHERE ownerId =:ownerId")
    suspend fun getOwnerById(ownerId : Int) : OwnerDataEntity


    @Query( "SELECT * FROM vehicledataentity WHERE vehicleid = :vehicleId")
    suspend fun getVehicleById(vehicleId: Int): VehicleDataEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle : VehicleDataEntity)


    @Query(
        """
            UPDATE ownerdataentity
            SET vehicles = :vehicles
            WHERE ownerId =:ownerId
        """
    )
    suspend fun updateVehicles(ownerId: Int, vehicles : List<VehicleDataEntity>)

}