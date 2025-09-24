package com.bsrubacky.tapeeater.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bsrubacky.tapeeater.database.dao.MediaDao
import com.bsrubacky.tapeeater.database.entities.Media

@Database(
    entities = [Media::class],
    version = 1,
    exportSchema = true
)
abstract class TapeEaterDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: TapeEaterDatabase? = null

        fun getDatabase(context: Context): TapeEaterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TapeEaterDatabase::class.java,
                    "tapeeater_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }

    abstract fun mediaDao(): MediaDao
}
