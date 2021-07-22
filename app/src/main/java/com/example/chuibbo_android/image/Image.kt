package com.example.chuibbo_android.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Image_table")
data class Image(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int,

    @ColumnInfo(name = "Image")
    val image: String
)
