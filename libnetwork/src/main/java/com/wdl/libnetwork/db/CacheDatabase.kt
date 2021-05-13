package com.wdl.libnetwork.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wdl.libcommon.AppGlobals
import com.wdl.libnetwork.db.bean.Cache

@Database(entities = [Cache::class], version = 1, exportSchema = true)
abstract class CacheDatabase : RoomDatabase() {
    companion object {
        private val db: CacheDatabase
            get() =
                Room.databaseBuilder(
                    AppGlobals.getApplication()!!,
                    CacheDatabase::class.java,
                    "ppjoke"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                    })
                    .addMigrations(object : Migration(1, 2) {
                        override fun migrate(database: SupportSQLiteDatabase) {
                        }
                    })
                    .build()


    }

}