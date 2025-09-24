package com.bsrubacky.tapeeater.database.entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Track(
    @PrimaryKey(true) val id:Int,
    @ColumnInfo("Name") val name: String,
    @ColumnInfo("Artist") val artist: String,
    @ColumnInfo("Album") val album: String,
    @ColumnInfo("Album_Artist") val albumArtist: String,
    @ColumnInfo("Length") val length: Long
)
