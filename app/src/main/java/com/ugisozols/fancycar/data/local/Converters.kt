package com.ugisozols.fancycar.data.local

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ugisozols.fancycar.data.local.entity.VehicleDataEntity

class Converters {

    @TypeConverter
    fun fromVehiclesList(vehicleList : List<VehicleDataEntity>) : String {
        return Gson().toJson(
            vehicleList,
            object : TypeToken<List<VehicleDataEntity>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun toVehiclesList(jsonString: String) : List<VehicleDataEntity>{
        return Gson().fromJson(
            jsonString,
            object : TypeToken<List<VehicleDataEntity>>() {}.type
        ) ?: emptyList()
    }
}