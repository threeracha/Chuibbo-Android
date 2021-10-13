package com.example.chuibbo_android.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.LocalDateTime

class JobPostListViewModel(val dataSource: JobPostDataSource) : ViewModel() {

    val jobPostsLiveData = dataSource.getJobPostList()

    /* If the name and description are present, create new JobPost and add it to the datasource */
    fun insertJobPost(id: Int?, companyName: String?, companyDsc: String?, companyDeadline: Int?, companyLogo: String?, companyLink: String?, startDate: LocalDateTime, endDate: LocalDateTime) {
        if (id == null || companyName == null || companyDsc == null || companyLogo == null || companyDeadline == null || companyLink == null) {
            return
        }

        val newJobPost = JobPost(
            id,
            companyName,
            companyDsc,
            companyDeadline,
            companyLogo,
            companyLink,
            startDate,
            endDate
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
