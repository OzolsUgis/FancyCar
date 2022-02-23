package com.ugisozols.fancycar.domain.use_cases

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.*
import javax.inject.Inject

class DecodeCoordinatesToAddress(
    private val context: Context
){
    operator fun invoke(latLng : LatLng) : String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val address = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1)
        return address[0].getAddressLine(0)
    }
}