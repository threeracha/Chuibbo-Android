package com.example.chuibbo_android.home

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/* Handles operations on jobPostsLiveData and holds details about it. */
class JobPostDataSource(resources: Resources) {
    private val jobPostsLiveData = MutableLiveData<List<JobPost>>()

    fun initJobPostList(jobPostList: List<JobPost>) {
        jobPostsLiveData.postValue(jobPostList)
    }

    /* Adds jobPost to liveData and posts value. */
    fun addJobPost(jobPost: JobPost) {
        val currentList = jobPostsLiveData.value
        if (currentList == null) {
            jobPostsLiveData.postValue(listOf(jobPost))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, jobPost)
            jobPostsLiveData.postValue(updatedList)
        }
    }

    /* Removes jobPost from liveData and posts value. */
    fun removeJobPost(jobPost: JobPost) {
        val currentList = jobPostsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(jobPost)
            jobPostsLiveData.postValue(updatedList)
        }
    }

    fun updateJobPost(jobPost: JobPost, index: Int) {
        val currentList = jobPostsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList[index] = jobPost
            jobPostsLiveData.postValue(updatedList)
        }
    }

    /* Returns jobPost given an ID. */
    fun getJobPostForId(id: Int): JobPost? {
        jobPostsLiveData.value?.let { jobPosts ->
            return jobPosts.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getJobPostIndex(jobPost: JobPost): Int? {
        jobPostsLiveData.value?.let { jobPosts ->
            return jobPosts.indexOf(jobPost)
        }
        return null
    }

    fun getJobPostList(): LiveData<List<JobPost>> {
        return jobPostsLiveData
    }

    companion object {
        private var INSTANCE: JobPostDataSource? = null

        fun getDataSource(resources: Resources): JobPostDataSource {
            return synchronized(JobPostDataSource::class) {
                val newInstance = INSTANCE ?: JobPostDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
