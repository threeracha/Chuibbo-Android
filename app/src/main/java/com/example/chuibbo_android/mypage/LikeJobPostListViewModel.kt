package com.example.chuibbo_android.mypage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chuibbo_android.home.Area
import com.example.chuibbo_android.home.CareerType
import com.example.chuibbo_android.home.Job
import com.example.chuibbo_android.home.JobPost

class LikeJobPostListViewModel(val dataSource: LikeJobPostDataSource) : ViewModel() {

    val likeJobPostsLiveData = dataSource.getLikeJobPostList()

    /* If the name and description are present, create new LikeJobPost and add it to the datasource */
    fun insertLikeJobPost(id: Int?, logoUrl: String, companyName: String?, subject: String?, descriptionUrl: String?, startDate: String?, endDate: String?,
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

        val newLikeJobPost = JobPost(
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

        dataSource.addLikeJobPost(newLikeJobPost)
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
