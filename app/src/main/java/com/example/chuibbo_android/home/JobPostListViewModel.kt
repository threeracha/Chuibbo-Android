package com.example.chuibbo_android.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JobPostListViewModel(val dataSource: JobPostDataSource) : ViewModel() {

    val jobPostsLiveData = dataSource.getJobPostList()

    /* If the name and description are present, create new JobPost and add it to the datasource */
    fun insertJobPost(id: Int?, logoUrl: String, companyName: String?, subject: String?, descriptionUrl: String?, startDate: String?, endDate: String?) { // TODO: 수정
        if (id == null || companyName == null || subject == null || descriptionUrl == null || startDate == null || endDate == null) { // TODO: 수정
            return
        }

        lateinit var _logoUrl: String
        if (logoUrl == null) {
            _logoUrl = ""
        } else {
            _logoUrl = logoUrl
        }

        val newJobPost = JobPost(
            id,
            _logoUrl,
            companyName,
            subject,
            descriptionUrl,
            startDate,
            endDate,
            listOf(Area(1, "")), // TODO: 수정
            listOf(Job(1, "")), // TODO: 수정
            listOf(CareerType(1, "")) // TODO: 수정
        )

        dataSource.addJobPost(newJobPost)
    }
}

class JobPostListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobPostListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JobPostListViewModel(
                dataSource = JobPostDataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
