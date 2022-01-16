package com.example.chuibbo_android.mypage

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/* Handles operations on photoAlbumsLiveData and holds details about it. */
class PhotoAlbumDataSource(resources: Resources) {
    private val photoAlbumsLiveData = MutableLiveData<List<PhotoAlbum>>()

    fun initPhotoAlbumList(photoAlbumList: List<PhotoAlbum>) {
        photoAlbumsLiveData.postValue(photoAlbumList)
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

        fun getDataSource(resources: Resources): PhotoAlbumDataSource {
            return synchronized(PhotoAlbumDataSource::class) {
                val newInstance = INSTANCE ?: PhotoAlbumDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
