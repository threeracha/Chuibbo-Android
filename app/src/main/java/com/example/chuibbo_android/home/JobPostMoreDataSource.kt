package com.example.chuibbo_android.home

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/* Handles operations on jobPostMoreLiveData and holds details about it. */
class JobPostMoreDataSource(resources: Resources) {
    private val jobPostMoreLiveData = MutableLiveData<List<JobPost>>()

    fun initJobPostMoreList(jobPostMoreList: List<JobPost>) {
        jobPostMoreLiveData.postValue(jobPostMoreList)
    }

    /* Adds jobPostMore to liveData and posts value. */
    fun addJobPostMoreList(jobPostMoreList: List<JobPost>) {
        val currentList = jobPostMoreLiveData.value
        if (currentList == null) {
            jobPostMoreLiveData.postValue(jobPostMoreList)
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.addAll(updatedList.size, jobPostMoreList)
            jobPostMoreLiveData.postValue(updatedList)
        }
    }

    /* Removes jobPostMore from liveData and posts value. */
    fun removeJobPostMore(jobPostMore: JobPost) {
        val currentList = jobPostMoreLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(jobPostMore)
            jobPostMoreLiveData.postValue(updatedList)
        }
    }

    fun updateJobPostMore(jobPost: JobPost, index: Int) {
        val currentList = jobPostMoreLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList[index] = jobPost
            jobPostMoreLiveData.postValue(updatedList)
        }
    }

    /* Returns jobPostMore given an ID. */
    fun getJobPostMoreForId(id: Int): JobPost? {
        jobPostMoreLiveData.value?.let { jobPostMore ->
            return jobPostMore.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getJobPostMoreIndex(jobPost: JobPost): Int? {
        jobPostMoreLiveData.value?.let { jobPosts ->
            return jobPosts.indexOf(jobPost)
        }
        return null
    }

    fun getJobPostMoreList(): LiveData<List<JobPost>> {
        return jobPostMoreLiveData
    }

    companion object {
        private var INSTANCE: JobPostMoreDataSource? = null

        fun getDataSource(resources: Resources): JobPostMoreDataSource {
            return synchronized(JobPostMoreDataSource::class) {
                val newInstance = INSTANCE ?: JobPostMoreDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
