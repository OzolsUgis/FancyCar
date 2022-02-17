package com.ugisozols.fancycar.data.local.entity

import androidx.room.Entity

@Entity
data class UserDataEntity(
    val name : String,
    val surname : String,
    val userImage : String,
    val vehicles : List<Vehicles>,
    val id : Int? = null

)
