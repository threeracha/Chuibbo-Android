package com.example.chuibbo_android.home

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* Handles operations on jobPostsLiveData and holds details about it. */
class SearchJobPostDataSource(resources: Resources) {
    private val searchJobPostsLiveData = MutableLiveData<List<JobPost>>()

    fun initSearchJobPostList(searchJobPostList: List<JobPost>) {
        searchJobPostsLiveData.postValue(searchJobPostList)
    }

    fun updateSearchJobPost(jobPost: JobPost, index: Int) {
        val currentList = searchJobPostsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList[index] = jobPost
            searchJobPostsLiveData.postValue(updatedList)
        }
    }

    /* Returns jobPost given an ID. */
    fun getSearchJobPostForId(id: Int): JobPost? {
        searchJobPostsLiveData.value?.let { jobPosts ->
            return jobPosts.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getSearchJobPostIndex(jobPost: JobPost): Int? {
        searchJobPostsLiveData.value?.let { jobPosts ->
            return jobPosts.indexOf(jobPost)
        }
        return null
    }

    fun getSearchJobPostList(): LiveData<List<JobPost>> {
        return searchJobPostsLiveData
    }

    companion object {
        private var INSTANCE: SearchJobPostDataSource? = null

        fun getDataSource(resources: Resources): SearchJobPostDataSource {
            return synchronized(SearchJobPostDataSource::class) {
                val newInstance = INSTANCE ?: SearchJobPostDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}

