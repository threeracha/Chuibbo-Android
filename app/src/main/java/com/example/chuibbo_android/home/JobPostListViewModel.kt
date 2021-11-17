package com.example.chuibbo_android.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JobPostListViewModel(val dataSource: JobPostDataSource) : ViewModel() {

    val jobPostsLiveData = dataSource.getJobPostList()

    /* If the name and description are present, create new JobPost and add it to the datasource */
    fun insertJobPost(id: Int?, logoUrl: String, companyName: String?, subject: String?, descriptionUrl: String?, startDate: String?, endDate: String?,
                        area: List<Area>, job: List<Job>, careerType: List<CareerType>, bookmark: Boolean) {
        if (id == null || companyName == null || subject == null || descriptionUrl == null || startDate == null || endDate == null ||
            area == null || job == null || careerType == null) {
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
            area,
            job,
            careerType,
            bookmark
        )

        dataSource.addJobPost(newJobPost)
    }

    fun saveBookmark(id: Int) {
        val jobPost: JobPost? = dataSource.getJobPostForId(id)
        val index: Int? = dataSource.getJobPostIndex(jobPost!!)
        if (jobPost != null && index != null) {
            jobPost.bookmark = true
            dataSource.updateJobPost(jobPost, index)
        }
    }

    fun deleteBookmark(id: Int) {
        val jobPost: JobPost? = dataSource.getJobPostForId(id)
        val index: Int? = dataSource.getJobPostIndex(jobPost!!)
        if (jobPost != null && index != null) {
            jobPost.bookmark = false
            dataSource.updateJobPost(jobPost, index)
        }
    }
}

class JobPostListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobPostListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JobPostListViewModel(
                dataSource = JobPostDataSource.getDataSource(context.resources, context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
