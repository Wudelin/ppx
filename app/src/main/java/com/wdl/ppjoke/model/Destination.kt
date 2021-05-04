package com.wdl.ppjoke.model

import com.google.gson.annotations.SerializedName

data class Destination(
    @SerializedName("asStarter")
    val asStarter: Boolean = false,
    @SerializedName("clazzName")
    val clazzName: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("isFrag")
    val isFrag: Boolean = false,
    @SerializedName("needLogin")
    val needLogin: Boolean = false,
    @SerializedName("pageUrl")
    val pageUrl: String = ""
)
