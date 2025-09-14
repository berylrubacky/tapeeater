package com.bsrubacky.tapeeater.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    "Media"
)
data class Media(
    @PrimaryKey(true) val id: Int,
    @ColumnInfo("Name") val name:String,
    @ColumnInfo("Type") val type: Int)