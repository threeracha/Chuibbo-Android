package com.example.chuibbo_android.image

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ImageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ImageRepository
    val allItem: LiveData<List<Image>>

    init {
        val productDao = ImageDatabase.getDatabase(application).imagedao()
        repository = ImageRepository(productDao)
        allItem = repository.allimage
    }

    fun insert(item: Image) = viewModelScope.launch {
        repository.imageInsert(item)
    }

    fun delete() = viewModelScope.launch {
        repository.delete()
    }
}
