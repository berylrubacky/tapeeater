package com.bsrubacky.tapeeater.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bsrubacky.tapeeater.database.entities.Media

@Dao
interface MediaDao {
    @Query("SELECT COUNT() FROM Media")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(media: Media): Long

    @Update
    fun update(media: Media)

    @Delete
    fun delete(media: Media)

    @Query("SELECT * FROM Media WHERE Id = :mediaId")
    fun select(mediaId: Long): Media

    @Query("SELECT * FROM Media")
    fun selectAll():List<Media>

    @Query("SELECT * FROM Media WHERE Name LIKE :searchString AND Type LIKE :filter ORDER BY Name ASC")
    fun searchAlphaAscending(searchString: String, filter: String): PagingSource<Int, Media>

    @Query("SELECT * FROM Media WHERE Name LIKE :searchString AND Type LIKE :filter ORDER BY Name DESC")
    fun searchAlphaDescending(searchString: String, filter: String): PagingSource<Int, Media>

    @Query("SELECT * FROM Media WHERE Name LIKE :searchString AND Type LIKE :filter ORDER BY Last_Touched ASC")
    fun searchDateAscending(searchString: String, filter: String): PagingSource<Int, Media>

    @Query("SELECT * FROM Media WHERE Name LIKE :searchString AND Type LIKE :filter ORDER BY Last_Touched DESC")
    fun searchDateDescending(searchString: String, filter: String): PagingSource<Int, Media>

}
