package com.ugisozols.fancycar.data.repository

import android.util.Log
import com.google.gson.Gson
import com.ugisozols.fancycar.R
import com.ugisozols.fancycar.data.local.CarOwnerDao
import com.ugisozols.fancycar.data.mapper.toCarOwner
import com.ugisozols.fancycar.data.mapper.toOwnerDataEntity
import com.ugisozols.fancycar.data.remote.DriverApi
import com.ugisozols.fancycar.data.remote.dto.UserData
import com.ugisozols.fancycar.domain.model.CarOwner
import com.ugisozols.fancycar.domain.repository.CarOwnerRepository
import com.ugisozols.fancycar.util.Resource
import com.ugisozols.fancycar.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException

class CarOwnerRepositoryImpl(
    private val dao: CarOwnerDao,
    private val api: DriverApi
) : CarOwnerRepository {

    override fun getCarOwners(): Flow<Resource<List<CarOwner>>> = flow {
        emit(Resource.Loading())

        val ownersData = dao.getOwners().map { it.toCarOwner() }
        emit(Resource.Loading(data = ownersData))

        val apiResponse = api.getDriverList()

        try {
            toUserData(apiResponse)
        }catch (e: Exception){
            emit(
                Resource.Error(
                    UiText.SimpleString(apiResponse),
                    ownersData
                )
            )
        }
        try {
            dao.deleteAllOwners()
            toUserData(apiResponse).forEach {
                dao.insertOwner(it.toOwnerDataEntity())
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
        val newUserDataList = dao.getOwners().map { it.toCarOwner()}

        emit(Resource.Success(newUserDataList))
    }
}