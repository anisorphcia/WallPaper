package com.asakao.wallpaper.logic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asakao.wallpaper.logic.dao.VerticalDao
import com.asakao.wallpaper.logic.model.Vertical

@Database(version = 1, entities = [Vertical::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun verticalDao(): VerticalDao

    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let { return it }
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                "app_database")
                .build()
                .apply { instance = this }
        }
    }
}