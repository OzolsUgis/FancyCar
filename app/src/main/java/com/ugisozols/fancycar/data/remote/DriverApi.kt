package com.ugisozols.fancycar.data.remote

import android.util.JsonReader
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.ugisozols.fancycar.data.remote.dto.UserData
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET

interface DriverApi {
    @GET("/api/?op=list")
    suspend fun getDriverList() : String

    companion object {
        const val BASE_URL = "http://mobi.connectedcar360.net"
    }
}