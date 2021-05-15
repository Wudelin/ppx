package com.wdl.libcommon

import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun View.clipOutline(radius: Float = 0f) {
    outlineProvider =
        object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                // 可见区域的rect
                val rect = Rect()
                getGlobalVisibleRect(rect)

//                val marginLeft = view?.marginLeft
//                val marginTop = view?.marginTop

                // 最终的rect
                val result = Rect(0, 0, rect.right - rect.left, rect.bottom - rect.top)

                // 椭圆
//                outline?.setOval()
                // 绘制路径
//                outline?.setPath()
                // 圆角矩型
                outline?.setRoundRect(result, radius)
            }

        }

    clipToOutline = true
}