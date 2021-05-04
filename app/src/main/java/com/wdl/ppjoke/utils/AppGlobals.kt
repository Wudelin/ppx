package com.wdl.ppjoke.utils

import android.annotation.SuppressLint
import android.app.Application
import java.lang.Exception

/**
 * 反射获取application
 */
object AppGlobals {
    private var sApplication: Application? = null

    @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
    fun getApplication(): Application? {
        if (sApplication == null) {
            try {
                val method =
                    Class.forName("android.app.ActivityThread")
                        .getDeclaredMethod("currentApplication")
                sApplication = method.invoke(null) as Application
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return sApplication
    }
}