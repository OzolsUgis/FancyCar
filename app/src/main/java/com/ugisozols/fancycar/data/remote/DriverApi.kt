package com.ugisozols.fancycar.data.remote

import com.ugisozols.fancycar.data.remote.dto.ApiDto
import retrofit2.http.GET

interface DriverApi {
    @GET("/api/?op=list")
    suspend fun getDriverList() : ApiDto

    companion object {
        const val BASE_URL = "http://mobi.connectedcar360.net"
    }
}