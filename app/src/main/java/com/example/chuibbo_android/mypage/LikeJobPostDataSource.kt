package com.example.chuibbo_android.mypage

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.home.JobPost

/* Handles operations on likeJobPostsLiveData and holds details about it. */
class LikeJobPostDataSource(resources: Resources) {
    private val likeJobPostsLiveData = MutableLiveData<List<JobPost>>()

    fun initLikeJobPostList(likeJobPostList: List<JobPost>) {
        likeJobPostsLiveData.postValue(likeJobPostList)
    }

    /* Adds likeJobPost to liveData and posts value. */
    fun addLikeJobPost(likeJobPost: JobPost) {
        val currentList = likeJobPostsLiveData.value
        if (currentList == null) {
            likeJobPostsLiveData.postValue(listOf(likeJobPost))
        } else {
            val updatedList = currentList.toMutableList()
            // TODO: 마감순 기준으로 정렬할 수 있도록 add
            updatedList.add(0, likeJobPost)
            likeJobPostsLiveData.postValue(updatedList)
        }
    }

    /* Removes likeJobPost from liveData and posts value. */
    fun removeLikeJobPost(likeJobPost: JobPost) {
        val currentList = likeJobPostsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(likeJobPost)
            likeJobPostsLiveData.postValue(updatedList)
        }
    }

    /* Returns likeJobPost given an ID. */
    fun getLikeJobPostForId(id: Int): JobPost? {
        likeJobPostsLiveData.value?.let { likeJobPosts ->
            return likeJobPosts.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getLikeJobPostList(): LiveData<List<JobPost>> {
        return likeJobPostsLiveData
    }

    fun getLikeJobPostSize(): Int {
        if (likeJobPostsLiveData.value == null)
            return 0
        return likeJobPostsLiveData.value?.toMutableList()!!.size
    }

    companion object {
        private var INSTANCE: LikeJobPostDataSource? = null

        fun getDataSource(resources: Resources): LikeJobPostDataSource {
            return synchronized(LikeJobPostDataSource::class) {
                val newInstance = INSTANCE ?: LikeJobPostDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
