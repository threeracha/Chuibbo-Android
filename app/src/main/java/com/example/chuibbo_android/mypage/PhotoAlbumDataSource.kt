package com.example.chuibbo_android.mypage

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/* Handles operations on photoAlbumsLiveData and holds details about it. */
class PhotoAlbumDataSource(resources: Resources) {
    private val initialPhotoAlbumList = listOf(
        PhotoAlbum(
            1,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            2,
            "https://t1.daumcdn.net/cfile/blog/99547E455C4535D805",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            3,
            "https://t1.daumcdn.net/cfile/blog/997C693E5B4EACA613",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            1,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            2,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            3,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            1,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            2,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            3,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            1,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            2,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        ),
        PhotoAlbum(
            3,
            "https://mblogthumb-phinf.pstatic.net/MjAxNzEyMTJfNjEg/MDAxNTEzMDcyODYyNzk2.51XmH8Ergh3D0UaIQgrEbvkRyskQxcaWx6AM-PKTfoog.QePEYG4fGLrGj4iopbHKfIPuvPi1yaqOr_FitKRQwdgg.JPEG.pinacoll/q3.jpg?type=w800",
            "2021.08.13",
            "긴머리, 정장1"
        )
    )

    private val photoAlbumsLiveData = MutableLiveData(initialPhotoAlbumList)

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
