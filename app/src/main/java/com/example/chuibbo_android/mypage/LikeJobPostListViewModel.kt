package com.example.chuibbo_android.mypage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.home.JobPost

class LikeJobPostListViewModel(val dataSource: LikeJobPostDataSource) : ViewModel() {

    val likeJobPostsLiveData = dataSource.getLikeJobPostList()

    /* If the name and description are present, create new LikeJobPost and add it to the datasource */
    fun insertLikeJobPost(jobPost: JobPost) {

        lateinit var logoUrl: String
        if (jobPost.logoUrl == null) {
            logoUrl = ""
        } else {
            logoUrl = jobPost.logoUrl
        }

        val newLikeJobPost = JobPost(
            jobPost.id,
            logoUrl,
            jobPost.companyName,
            jobPost.subject,
            jobPost.descriptionUrl,
            jobPost.startDate,
            jobPost.endDate,
            jobPost.areas,
            jobPost.jobs,
            jobPost.careerTypes,
            true
        )

        dataSource.addLikeJobPost(newLikeJobPost)
    }

    fun deleteLikeJobPost(id: Int) {
        val jobPost: JobPost? = dataSource.getLikeJobPostForId(id)
        if (jobPost != null)
            dataSource.removeLikeJobPost(jobPost)
    }

    fun getSize(): Int {
        return dataSource.getLikeJobPostSize()
    }
}

class LikeJobPostListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikeJobPostListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LikeJobPostListViewModel(
                dataSource = LikeJobPostDataSource.getDataSource(context.resources, context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
