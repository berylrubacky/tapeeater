package com.bsrubacky.tapeeater.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
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
    @PrimaryKey(true) val id:Long,
    @ColumnInfo("Media") val mediaId:Long,
    @ColumnInfo("Name") var name: String,
    @ColumnInfo("Artist") var artist: String,
    @ColumnInfo("Album") var album: String,
    @ColumnInfo("Album_Artist") var albumArtist: String,
    @ColumnInfo("Length") var length: Long,
    @ColumnInfo("Position") var position:Int,
    @Ignore var timestamp: Long = 0
)
