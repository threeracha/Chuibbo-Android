package com.example.chuibbo_android.image

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.io.InputStream

@Entity(tableName = "Image_table")
data class Image(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int,

    @ColumnInfo(name = "Image")
    val image: String
)