package com.ugisozols.fancycar.util

sealed class Resource<T>(val data: T? = null, val uiText: UiText? = null) {
    class Loading<T>(data : T? = null) : Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(uiText : UiText, data: T? = null): Resource<T>(data, uiText)
}