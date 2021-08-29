package com.example.chuibbo_android.image

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {
    @Query("SELECT * FROM Image_table")
    fun getAllImage(): LiveData<List<Image>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(image: Image)

    @Query("Delete FROM Image_table")
    suspend fun deleteAll()
}
