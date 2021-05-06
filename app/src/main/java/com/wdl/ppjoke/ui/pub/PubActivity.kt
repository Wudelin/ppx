package com.wdl.ppjoke.ui.pub

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.wdl.libnavannotation.ActivityDestination
import com.wdl.ppjoke.R
import com.wdl.ppjoke.utils.AppConfig
import com.wdl.ppjoke.utils.NavGraphBuilder

@ActivityDestination(pageUrl = "main/tabs/publish")
class PubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pub)
    }
}