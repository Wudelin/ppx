package com.wdl.libnetwork

class ApiResponse<T> {
    var message: String? = null
    var status: Int = -1
    var data: T? = null
    var success: Boolean = false
}

