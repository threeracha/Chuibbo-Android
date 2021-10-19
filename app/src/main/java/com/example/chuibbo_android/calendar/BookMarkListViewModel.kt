package com.example.chuibbo_android.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.home.JobPost
import kotlin.random.Random

class BookMarkListViewModel(val dataSource: BookMarkDataSource) : ViewModel() {
    val bookMarkLiveData = dataSource.getBookMarkList()

    fun insertBookMark(userId: Long?, jobPost: JobPost?) {
        if (userId == null) {
            return
        }

        val newBookMark = BookMark(
            Random.nextLong(),
            userId,
            jobPost
        )

        dataSource.addBookMark(newBookMark)
    }
}

class BookMarkListViewModelFactory(private val context: CalendarFragment) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookMarkListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookMarkListViewModel(
                dataSource = BookMarkDataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
