package com.example.chuibbo_android.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchJobPostListViewModel(val dataSource: SearchJobPostDataSource) : ViewModel() {

    val searchJobPostLiveData = dataSource.getSearchJobPostList()

    fun initSearchJobPostList(jobPostList: List<JobPost>) {
        dataSource.initSearchJobPostList(jobPostList)
    }


    fun saveBookmark(id: Int) {
        val jobPost: JobPost? = dataSource.getSearchJobPostForId(id)
        if (jobPost != null) {
            val index: Int? = dataSource.getSearchJobPostIndex(jobPost!!)
            if (index != null) {
                jobPost.bookmark = true
                dataSource.updateSearchJobPost(jobPost, index)
            }
        }
    }

    fun deleteBookmark(id: Int) {
        val jobPost: JobPost? = dataSource.getSearchJobPostForId(id)
        if (jobPost != null) {
            val index: Int? = dataSource.getSearchJobPostIndex(jobPost!!)
            if (index != null) {
                jobPost.bookmark = false
                dataSource.updateSearchJobPost(jobPost, index)
            }
        }
    }

    fun getSearchJobPostForId(id: Int): JobPost {
        val jobPost: JobPost? = dataSource.getSearchJobPostForId(id)
        return jobPost!!
    }
}

class SearchJobPostListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchJobPostListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchJobPostListViewModel(
                dataSource = SearchJobPostDataSource.getDataSource(context.resources, context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
