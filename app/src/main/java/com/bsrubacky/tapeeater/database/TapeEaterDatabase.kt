package com.bsrubacky.tapeeater.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class TapeEaterDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: TapeEaterDatabase? = null

        fun getDatabase(context: Context): TapeEaterDatabase {
            return INSTANCE ?: synchronized(this){
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
}