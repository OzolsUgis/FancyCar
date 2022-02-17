package com.ugisozols.fancycar.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ugisozols.fancycar.data.local.entity.Vehicles

class Converters {

    @TypeConverter
    fun fromList(vehicleList : List<Vehicles>) : String {
        return Gson().toJson(vehicleList)
    }

    @TypeConverter
    fun toList(string: String) : List<Vehicles>{
        return Gson().fromJson(string, object : TypeToken<List<Vehicles>>() {}.type)
    }
}