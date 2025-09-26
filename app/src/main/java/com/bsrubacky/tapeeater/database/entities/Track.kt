package com.bsrubacky.tapeeater.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    "Tracks",
    foreignKeys = [
        ForeignKey(
            entity = Media::class,
            parentColumns = ["Id"],
            childColumns = ["Media"]
        )
    ]
)
data class Track(
    @PrimaryKey(true) val id:Int,
    @ColumnInfo("Media") val mediaId:Int,
    @ColumnInfo("Name") val name: String,
    @ColumnInfo("Artist") val artist: String,
    @ColumnInfo("Album") val album: String,
    @ColumnInfo("Album_Artist") val albumArtist: String,
    @ColumnInfo("Length") val length: Long,
    @ColumnInfo("Position") val position:Int
)
