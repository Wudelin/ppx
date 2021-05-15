package com.wdl.libnetwork.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "test")
data class Cache(
    @PrimaryKey
    var key: Int = 0
)