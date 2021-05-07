package com.wdl.libnetwork

interface Callback<T> {
    fun onSuccess(response: ApiResponse<T>)
    fun onError(response: ApiResponse<T>)
    fun onCacheSuccess(response: ApiResponse<T>)
}

abstract class JsonCallback<T> : Callback<T> {
    override fun onSuccess(response: ApiResponse<T>) {

    }

    override fun onError(response: ApiResponse<T>) {

    }

    override fun onCacheSuccess(response: ApiResponse<T>) {

    }
}