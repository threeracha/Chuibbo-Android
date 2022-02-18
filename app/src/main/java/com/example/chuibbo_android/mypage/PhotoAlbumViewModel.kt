package com.example.chuibbo_android.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.mypage.PhotoAlbum
import com.example.chuibbo_android.mypage.PhotoAlbumDataSource

class PhotoAlbumViewModel(val dataSource: PhotoAlbumDataSource) : ViewModel() {

    val photoAlbumsLiveData = dataSource.getPhotoAlbumList()

    fun initPhotoAlbumList(photoAlbumList: List<PhotoAlbum>) {
        dataSource.initPhotoAlbumList(photoAlbumList)
    }

    /* If the name and description are present, create new PhotoAlbum and add it to the datasource */
    fun insertPhotoAlbum(id: Int?, image: String?, date: String?, faceShape: String?, hair: String?, suit: String?) {
        if (id == null || image == null || date == null || faceShape == null || hair == null || suit == null) {
            return
        }

        val newPhotoAlbum = PhotoAlbum(
            id,
            image,
            date,
            faceShape,
            hair,
            suit
        )

        dataSource.addPhotoAlbum(newPhotoAlbum)
    }

    fun getSize(): Int {
        return dataSource.getPhotoAlbumSize()
    }
}

class PhotoAlbumViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoAlbumViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoAlbumViewModel(
                dataSource = PhotoAlbumDataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
