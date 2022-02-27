package com.ugisozols.fancycar.data.repository



import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.ugisozols.fancycar.data.remote.directionApi.dto.DirectionApi
import com.ugisozols.fancycar.data.remote.directionApi.getDirectionRequest
import com.ugisozols.fancycar.domain.repository.DirectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

class DirectionRepositoryImpl(
) : DirectionRepository
{

    private fun getDirectionCall(from : LatLng, to : LatLng) : ArrayList<List<LatLng>>{
        val client = OkHttpClient()
        val result = ArrayList<List<LatLng>>()
        val request = getDirectionRequest(from, to)
        val response = client.newCall(request).execute()


        val data = response.body!!.string()


        response.close()
        val respObj = Gson().fromJson(
            data,
            DirectionApi::class.java
        )

        val path = ArrayList<LatLng>()
        for (i in 0 until respObj.routes[0].legs[0].steps.size) {
            path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
        }
        result.add(path)
        return result
    }



    override suspend fun getDirection(from: LatLng, to: LatLng): List<List<LatLng>> {

        var result = ArrayList<List<LatLng>>()
        withContext(Dispatchers.IO) {
            result = getDirectionCall(from, to)
        }
        return result


    }

    /**
     * Method to decode polyline points
     * Courtesy : https://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */

    private fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }

}