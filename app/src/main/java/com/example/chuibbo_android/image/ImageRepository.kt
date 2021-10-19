package com.example.chuibbo_android.image

import android.util.Log
import androidx.lifecycle.LiveData

class ImageRepository(
    private val imageDao: ImageDao
) {
    val allimage: LiveData<List<Image>> = imageDao.getAllImage()

    suspend fun imageInsert(image: Image) {
        imageDao.insertImage(image)
    }

    suspend fun delete() {
        Log.d("delete", "delete 호")
        imageDao.deleteAll()
    }
}
