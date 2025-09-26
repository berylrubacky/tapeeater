package com.bsrubacky.tapeeater.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bsrubacky.tapeeater.database.entities.Track

@Dao
interface TrackDao{
    @Query("SELECT COUNT() FROM Tracks")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(track: Track): Long

    @Update
    fun update(track: Track)

    @Delete
    fun delete(track: Track)
}
