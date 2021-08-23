package com.example.chuibbo_android.mypage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LikeJobPostListViewModel(val dataSource: LikeJobPostDataSource) : ViewModel() {

    val likeJobPostsLiveData = dataSource.getLikeJobPostList()

    /* If the name and description are present, create new LikeJobPost and add it to the datasource */
    fun insertLikeJobPost(id: Int?, companyName: String?, companyDsc: String?, companyDeadline: Int?, companyLogo: String?, companyLink: String?) {
        if (id == null || companyName == null || companyDsc == null || companyLogo == null || companyDeadline == null || companyLink == null) {
            return
        }

        val newLikeJobPost = LikeJobPost(
            id,
            companyName,
            companyDsc,
            companyDeadline,
            companyLogo,
            companyLink
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
                dataSource = LikeJobPostDataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
