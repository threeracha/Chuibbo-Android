package com.example.chuibbo_android.mypage

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringServerResponse
import com.example.chuibbo_android.home.JobPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* Handles operations on likeJobPostsLiveData and holds details about it. */
class LikeJobPostDataSource(resources: Resources, context: Context) {
    private val likeJobPostsLiveData = init(context)

    private fun init(context: Context): MutableLiveData<List<JobPost>> {
        val data = MutableLiveData<List<JobPost>>()

        JobPostApi.instance(context).getBookmarks().enqueue(object :
            Callback<SpringServerResponse<List<JobPost>>> {
            override fun onFailure(call: Call<SpringServerResponse<List<JobPost>>>, t: Throwable) {
                Log.d("retrofit fail", t.message)
            }

            override fun onResponse(
                call: Call<SpringServerResponse<List<JobPost>>>,
                response: Response<SpringServerResponse<List<JobPost>>>
            ) {
                if (response.isSuccessful) {
                    when (response.body()?.status) {
                        "OK" -> {
                            data.value = response.body()!!.data!!
                        }
                        "ERROR" -> {
                            // TODO
                        }
                    }
                }
            }
        })

        return data
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

        fun getDataSource(resources: Resources, context: Context): LikeJobPostDataSource {
            return synchronized(LikeJobPostDataSource::class) {
                val newInstance = INSTANCE ?: LikeJobPostDataSource(resources, context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
