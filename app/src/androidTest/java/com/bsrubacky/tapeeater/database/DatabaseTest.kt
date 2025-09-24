package com.bsrubacky.tapeeater.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before

abstract class DatabaseTest {
    lateinit var tapeEaterDatabase: TapeEaterDatabase

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        tapeEaterDatabase = Room.inMemoryDatabaseBuilder(context, TapeEaterDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @After
    fun teardownDatabase() {
        tapeEaterDatabase.close()
    }
}
