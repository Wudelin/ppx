package com.wdl.ppjoke.model
import com.google.gson.annotations.SerializedName


data class BottomBar(
    @SerializedName("activeColor")
    val activeColor: String = "",
    @SerializedName("inActiveColor")
    val inActiveColor: String = "",
    @SerializedName("selectTab")
    val selectTab: Int = 0,
    @SerializedName("tabs")
    val tabs: List<Tab> = listOf()
)

data class Tab(
    @SerializedName("enable")
    val enable: Boolean = false,
    @SerializedName("index")
    val index: Int = 0,
    @SerializedName("pageUrl")
    val pageUrl: String = "",
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("tintColor")
    val tintColor: String = "",
    @SerializedName("title")
    val title: String = ""
)