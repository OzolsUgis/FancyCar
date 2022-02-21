package com.ugisozols.fancycar.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ugisozols.fancycar.data.remote.dto.ApiDto
import com.ugisozols.fancycar.data.remote.dto.VehicleLocationUpdateDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DriverApi {
    @GET("/api/?op=list")
    suspend fun getDriverList() : ApiDto

    @GET("/api/?op=getlocations&")
    suspend fun getVehicleLocation(
        @Query("userid")
        userid : Int
    ) : VehicleLocationUpdateDto

    companion object {
        const val BASE_URL = "http://mobi.connectedcar360.net"

    }
}