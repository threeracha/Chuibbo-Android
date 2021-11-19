package com.example.chuibbo_android.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JobPostMoreListViewModel(val dataSource: JobPostMoreDataSource) : ViewModel() {

    val jobPostMoreLiveData = dataSource.getJobPostMoreList()

    /* If the name and description are present, create new JobPost and add it to the datasource */
    fun insertJobPostMoreList(jobPostList: List<JobPost>) {
        dataSource.addJobPostMoreList(jobPostList)
    }

    fun saveBookmark(id: Int) {
        val jobPost: JobPost? = dataSource.getJobPostMoreForId(id)
        if (jobPost != null) {
            val index: Int? = dataSource.getJobPostMoreIndex(jobPost!!)
            if (index != null) {
                jobPost.bookmark = true
                dataSource.updateJobPostMore(jobPost, index)
            }
        }
    }

    fun deleteBookmark(id: Int) {
        val jobPost: JobPost? = dataSource.getJobPostMoreForId(id)
        if (jobPost != null) {
            val index: Int? = dataSource.getJobPostMoreIndex(jobPost!!)
            if (index != null) {
                jobPost.bookmark = false
                dataSource.updateJobPostMore(jobPost, index)
            }
        }
    }

    fun getJobPostMoreForId(id: Int): JobPost {
        val jobPost: JobPost? = dataSource.getJobPostMoreForId(id)
        return jobPost!!
    }
}

class JobPostMoreListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobPostMoreListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JobPostMoreListViewModel(
                dataSource = JobPostMoreDataSource.getDataSource(context.resources, context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
