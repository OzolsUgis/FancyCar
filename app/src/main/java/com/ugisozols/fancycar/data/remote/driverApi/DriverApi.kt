package com.ugisozols.fancycar.data.remote.driverApi

import com.ugisozols.fancycar.data.remote.driverApi.dto.ApiDto
import com.ugisozols.fancycar.data.remote.driverApi.dto.VehicleLocationUpdateDto
import retrofit2.http.GET
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