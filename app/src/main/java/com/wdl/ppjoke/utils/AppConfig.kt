package com.wdl.ppjoke.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wdl.ppjoke.model.BottomBar
import com.wdl.ppjoke.model.Destination
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * assets中获取路由配置信息的工具类
 */
object AppConfig {
    private var sConfig: HashMap<String, Destination>? = null

    private var sBottomBar: BottomBar? = null

    fun getDestinationConfig(): HashMap<String, Destination>? {
        if (sConfig == null) {
            sConfig = Gson().fromJson(
                parseFile("destination.json"),
                object : TypeToken<HashMap<String, Destination>>() {}.type
            )
        }

        return sConfig
    }

    fun getBottomBarConfig(): BottomBar? {
        if (sBottomBar == null) {
            sBottomBar = Gson().fromJson(parseFile("main_tabs_config.json"), BottomBar::class.java)
        }
        return sBottomBar
    }


    private fun parseFile(fileName: String): String {
        val inputStream = AppGlobals.getApplication()?.assets?.open(fileName)
        val sb = StringBuilder()
        return BufferedReader(InputStreamReader(inputStream)).useLines { lines ->
            lines.forEach { sb.append(it) }
            sb.toString()
        }
    }
}