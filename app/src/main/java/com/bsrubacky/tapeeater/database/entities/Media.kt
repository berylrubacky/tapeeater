package com.bsrubacky.tapeeater.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    "Media"
)
data class Media(
    @PrimaryKey(true) val id: Int,
    @ColumnInfo("Name") val name:String,
    @ColumnInfo("Type") val type: Int,
    @ColumnInfo("Plays") val plays: Int = 0,
    @ColumnInfo("Last_Edited") val lastEdited: Long = Date().time,
    @ColumnInfo("Last_Played") var lastPlayed: Long? = null
)