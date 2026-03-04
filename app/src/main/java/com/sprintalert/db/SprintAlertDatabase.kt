package com.sprintalert.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SprintRecord::class],
    version = 1,
    exportSchema = false
)
abstract class SprintAlertDatabase : RoomDatabase() {
    abstract fun sprintRecordDao(): SprintRecordDao

    companion object {
        @Volatile
        private var instance: SprintAlertDatabase? = null

        fun getInstance(context: Context): SprintAlertDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    SprintAlertDatabase::class.java,
                    "sprint_alert.db"
                ).build().also { instance = it }
            }
        }
    }
}
