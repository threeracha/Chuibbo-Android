package com.example.chuibbo_android.home

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringResponse2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* Handles operations on jobPostsLiveData and holds details about it. */
class JobPostDataSource(resources: Resources) {
    private val jobPostsLiveData = init()

    private fun init(): MutableLiveData<List<JobPost>> {
        val data = MutableLiveData<List<JobPost>>()

        JobPostApi.instance.getJobPosts().enqueue(object :
            Callback<SpringResponse2<List<JobPost>>> {
            override fun onFailure(call: Call<SpringResponse2<List<JobPost>>>, t: Throwable) {
                Log.d("retrofit fail", t.message)
            }

            override fun onResponse(
                call: Call<SpringResponse2<List<JobPost>>>,
                response: Response<SpringResponse2<List<JobPost>>>
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

    /* Returns jobPost given an ID. */
    fun getJobPostForId(id: Int): JobPost? {
        jobPostsLiveData.value?.let { jobPosts ->
            return jobPosts.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getJobPostList(): LiveData<List<JobPost>> {
        return jobPostsLiveData
    }

    companion object {
        private var INSTANCE: JobPostDataSource? = null

        fun getDataSource(resources: Resources): JobPostDataSource {
            return synchronized(JobPostDataSource::class) {
                val newInstance = INSTANCE ?: JobPostDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
