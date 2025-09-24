package com.bsrubacky.tapeeater.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    "Media"
)
data class Media(
    @PrimaryKey(true) @ColumnInfo("Id") var id: Long,
    @ColumnInfo("Name") var name: String,
    @ColumnInfo("Type") var type: Int,
    @ColumnInfo("Plays") val plays: Int = 0,
    @ColumnInfo("Last_Touched") val lastTouched: Long = Date().time,
    @ColumnInfo("Last_Played") var lastPlayed: Long? = null
)
