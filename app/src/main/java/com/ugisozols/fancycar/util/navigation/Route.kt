package com.ugisozols.fancycar.util.navigation

object Route {
    const val WELCOME_PAGE = "welcome"
    const val OWNER_LIST_PAGE = "owner_list"
    const val MAP_SCREEN = "map_screen"

    fun mapScreenWithArg(vararg args : Int?): String{
        return buildString {
            append(MAP_SCREEN)
            append("/$args")
        }
    }
}