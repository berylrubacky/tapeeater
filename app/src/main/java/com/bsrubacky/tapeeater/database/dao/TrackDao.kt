package com.bsrubacky.tapeeater.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
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

    @Query("SELECT COUNT() FROM Tracks WHERE Media = :mediaId")
    fun countTracksFromMedia(mediaId: Long): Int

    @Query("SELECT COUNT() FROM Tracks WHERE Media = :mediaId")
    fun countTracksFromMediaLive(mediaId: Long): LiveData<Int>

    @Query("SELECT SUM(Length) FROM Tracks WHERE Media = :mediaId")
    fun sumLength(mediaId: Long): LiveData<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(track: Track): Long

    @Update
    fun update(track: Track)

    @Delete
    fun delete(track: Track)

    @Query("SELECT * FROM Tracks WHERE id = :trackId")
    fun select(trackId: Long): Track

    @Query("SELECT * FROM Tracks WHERE Media = :mediaId ORDER BY Position")
    fun selectAllTracksWithMedia(mediaId: Long): PagingSource<Int, Track>
}
