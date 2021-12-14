package com.example.chuibbo_android.home

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* Handles operations on jobPostsLiveData and holds details about it. */
class JobPostDataSource(resources: Resources, context: Context) {
    private val jobPostsLiveData = init(context)

    private fun init(context: Context): MutableLiveData<List<JobPost>> {
        val data = MutableLiveData<List<JobPost>>()

        JobPostApi.instance(context).getJobPosts().enqueue(object :
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

    /* Adds jobPost to liveData and posts value. */
    fun addJobPost(jobPost: JobPost) {
        val currentList = jobPostsLiveData.value
        if (currentList == null) {
            jobPostsLiveData.postValue(listOf(jobPost))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, jobPost)
            jobPostsLiveData.postValue(updatedList)
        }
    }

    /* Removes jobPost from liveData and posts value. */
    fun removeJobPost(jobPost: JobPost) {
        val currentList = jobPostsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(jobPost)
            jobPostsLiveData.postValue(updatedList)
        }
    }

    fun updateJobPost(jobPost: JobPost, index: Int) {
        val currentList = jobPostsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList[index] = jobPost
            jobPostsLiveData.postValue(updatedList)
        }
    }

    /* Returns jobPost given an ID. */
    fun getJobPostForId(id: Int): JobPost? {
        jobPostsLiveData.value?.let { jobPosts ->
            return jobPosts.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getJobPostIndex(jobPost: JobPost): Int? {
        jobPostsLiveData.value?.let { jobPosts ->
            return jobPosts.indexOf(jobPost)
        }
        return null
    }

    fun getJobPostList(): LiveData<List<JobPost>> {
        return jobPostsLiveData
    }

    companion object {
        private var INSTANCE: JobPostDataSource? = null

        fun getDataSource(resources: Resources, context: Context): JobPostDataSource {
            return synchronized(JobPostDataSource::class) {
                val newInstance = INSTANCE ?: JobPostDataSource(resources, context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
