package com.ugisozols.fancycar.data.remote

import com.ugisozols.fancycar.data.remote.responses.Driver
import retrofit2.Response
import retrofit2.http.GET

interface DriverApi {
    @GET("/api/?op=list")
    suspend fun getDriverList() : Response<Driver>

    companion object {
        const val BASE_URL = "http://mobi.connectedcar360.net"
    }
}