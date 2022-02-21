package com.ugisozols.fancycar.data.repository

import android.util.Log
import com.google.gson.JsonSyntaxException
import com.ugisozols.fancycar.R
import com.ugisozols.fancycar.data.local.CarOwnerDao
import com.ugisozols.fancycar.data.mapper.toCarOwner
import com.ugisozols.fancycar.data.mapper.toOwnerDataEntity
import com.ugisozols.fancycar.data.mapper.toOwnerVehicles
import com.ugisozols.fancycar.data.remote.DriverApi
import com.ugisozols.fancycar.data.remote.dto.UserData
import com.ugisozols.fancycar.domain.model.CarOwner
import com.ugisozols.fancycar.domain.model.OwnerVehicles
import com.ugisozols.fancycar.domain.repository.CarOwnerRepository
import com.ugisozols.fancycar.util.Resource
import com.ugisozols.fancycar.util.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException



class CarOwnerRepositoryImpl(
    private val dao: CarOwnerDao,
    private val api: DriverApi
) : CarOwnerRepository {

    override suspend fun getCarOwners(): Flow<Resource<List<CarOwner>>> = flow {
        emit(Resource.Loading())

        val ownersData = dao.getOwners().map { it.toCarOwner() }
        emit(Resource.Loading(data = ownersData))

        try {
            // Delay is only for presentation
            delay(500L)
            try {
                val apiResponse = api.getDriverList()
                dao.deleteAllOwners()
                apiResponse.data.forEach {
                    if (it.owner != null || it.vehicles != null) {
                        val userData = UserData(
                            owner = it.owner,
                            userid = it.userid,
                            vehicles = it.vehicles
                        )
                        dao.insertOwner(userData.toOwnerDataEntity())
                    }
                }
            } catch (e: JsonSyntaxException) {
                emit(
                    Resource.Error(
                        UiText.StringResource(R.string.api_error),
                        ownersData
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.http_exception_message),
                    ownersData
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.io_exception_message),
                    ownersData
                )
            )
        }
        val newUserDataList = dao.getOwners().map { it.toCarOwner() }
        emit(Resource.Success(newUserDataList))
    }

    override suspend fun getVehicleLocation(ownerId: Int): Flow<Resource<CarOwner>> = flow{
        emit(
            Resource.Loading()
        )
        val vehiclesData = dao.getOwnerWithVehicles(2)
        Log.d("MY_APP", vehiclesData.toString())
        emit(Resource.Loading(vehiclesData.owner.toCarOwner()))
        try {
            delay(500L)
            val locationUpdateResult = api.getVehicleLocation(2)
            locationUpdateResult.data.forEach { newLocation ->
                dao.updateVehicle(
                    newLocation.vehicleid,
                    newLocation.lat,
                    newLocation.lon
                )
            }
        }catch (e : JsonSyntaxException){
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.api_error),
                    vehiclesData.owner.toCarOwner()
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.http_exception_message),
                    vehiclesData.owner.toCarOwner()

                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    UiText.StringResource(R.string.io_exception_message),
                    vehiclesData.owner.toCarOwner()
                )
            )
        }
        val updatedOwnerData = dao.getOwnerWithVehicles(ownerId)
        emit(Resource.Success(updatedOwnerData.owner.toCarOwner()))

    }
}

