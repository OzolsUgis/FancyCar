package com.ugisozols.fancycar.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ugisozols.fancycar.data.local.entity.VehicleDataEntity
import com.ugisozols.fancycar.data.remote.dto.UserData

fun toUserData(jsonString: String) : List<UserData>{
    return Gson().fromJson(
        jsonString,
        object : TypeToken<List<UserData>>() {}.type
    ) ?: emptyList()
}