package com.ugisozols.fancycar.data.remote.directionApi

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.ugisozols.fancycar.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.http.GET
import retrofit2.http.Query

fun getDirectionRequest(fromLatLng : LatLng, toLatLng : LatLng) : Request{


    val from = "origin=${fromLatLng.latitude},${fromLatLng.longitude}"
    val to = "destination=${toLatLng.latitude},${toLatLng.longitude}"
    val sensor = "sensor=false"
    val params = "$from&$to&$sensor&key=${BuildConfig.MAPS_API_KEY}"

    val url = "https://maps.googleapis.com/maps/api/directions/json?$params"

    Log.d("My-App", url)

    return Request.Builder().url(url).build()
}