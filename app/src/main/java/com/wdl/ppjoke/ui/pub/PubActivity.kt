package com.wdl.ppjoke.ui.pub

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.wdl.libcommon.clipOutline
import com.wdl.libnavannotation.ActivityDestination
import com.wdl.ppjoke.R

@ActivityDestination(pageUrl = "main/tabs/publish")
class PubActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pub)

        val tv = findViewById<TextView>(R.id.tv_test)
        tv.clipOutline(20f)
    }
}

