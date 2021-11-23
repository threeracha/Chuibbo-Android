package com.example.chuibbo_android.mypage

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.api.ResumePhotoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* Handles operations on photoAlbumsLiveData and holds details about it. */
class PhotoAlbumDataSource(resources: Resources, context: Context) {
    private val photoAlbumsLiveData = init(context)

    private fun init(context: Context): MutableLiveData<List<PhotoAlbum>> {
        val data = MutableLiveData<List<PhotoAlbum>>()

        ResumePhotoApi.instance(context).getResumePhotos().enqueue(object :
            Callback<List<PhotoAlbum>> {
            override fun onFailure(call: Call<List<PhotoAlbum>>, t: Throwable) {
                Log.d("retrofit fail", t.message)
            }

            override fun onResponse(
                call: Call<List<PhotoAlbum>>,
                response: Response<List<PhotoAlbum>>
            ) {
                if (response.isSuccessful) {
                            data.value = response.body()
                }
            }
        })

        return data
    }

    /* Adds photoAlbum to liveData and posts value. */
    fun addPhotoAlbum(photoAlbum: PhotoAlbum) {
        val currentList = photoAlbumsLiveData.value
        if (currentList == null) {
            photoAlbumsLiveData.postValue(listOf(photoAlbum))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, photoAlbum)
            photoAlbumsLiveData.postValue(updatedList)
        }
    }

    /* Removes photoAlbum from liveData and posts value. */
    fun removePhotoAlbum(photoAlbum: PhotoAlbum) {
        val currentList = photoAlbumsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(photoAlbum)
            photoAlbumsLiveData.postValue(updatedList)
        }
    }

    /* Returns photoAlbum given an ID. */
    fun getPhotoAlbumForId(id: Int): PhotoAlbum? {
        photoAlbumsLiveData.value?.let { photoAlbums ->
            return photoAlbums.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getPhotoAlbumList(): LiveData<List<PhotoAlbum>> {
        return photoAlbumsLiveData
    }

    fun getPhotoAlbumSize(): Int {
        if (photoAlbumsLiveData.value == null)
            return 0
        return photoAlbumsLiveData.value?.toMutableList()!!.size
    }

    companion object {
        private var INSTANCE: PhotoAlbumDataSource? = null

        fun getDataSource(resources: Resources, context: Context): PhotoAlbumDataSource {
            return synchronized(PhotoAlbumDataSource::class) {
                val newInstance = INSTANCE ?: PhotoAlbumDataSource(resources, context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
