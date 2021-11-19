package com.example.chuibbo_android.home

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chuibbo_android.api.JobPostApi
import com.example.chuibbo_android.api.response.SpringResponse2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* Handles operations on jobPostMoreLiveData and holds details about it. */
class JobPostMoreDataSource(resources: Resources, context: Context) {
    private val jobPostMoreLiveData = init(context)

    private fun init(context: Context): MutableLiveData<List<JobPost>> {
        val data = MutableLiveData<List<JobPost>>()

        JobPostApi.instance(context).getJobPostsMore(1).enqueue(object :
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

    /* Adds jobPostMore to liveData and posts value. */
    fun addJobPostMore(jobPostMore: JobPost) {
        val currentList = jobPostMoreLiveData.value
        if (currentList == null) {
            jobPostMoreLiveData.postValue(listOf(jobPostMore))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, jobPostMore)
            jobPostMoreLiveData.postValue(updatedList)
        }
    }

    /* Removes jobPostMore from liveData and posts value. */
    fun removeJobPostMore(jobPostMore: JobPost) {
        val currentList = jobPostMoreLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(jobPostMore)
            jobPostMoreLiveData.postValue(updatedList)
        }
    }

    fun updateJobPostMore(jobPost: JobPost, index: Int) {
        val currentList = jobPostMoreLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList[index] = jobPost
            jobPostMoreLiveData.postValue(updatedList)
        }
    }

    /* Returns jobPostMore given an ID. */
    fun getJobPostMoreForId(id: Int): JobPost? {
        jobPostMoreLiveData.value?.let { jobPostMore ->
            return jobPostMore.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getJobPostMoreIndex(jobPost: JobPost): Int? {
        jobPostMoreLiveData.value?.let { jobPosts ->
            return jobPosts.indexOf(jobPost)
        }
        return null
    }

    fun getJobPostMoreList(): LiveData<List<JobPost>> {
        return jobPostMoreLiveData
    }

    companion object {
        private var INSTANCE: JobPostMoreDataSource? = null

        fun getDataSource(resources: Resources, context: Context): JobPostMoreDataSource {
            return synchronized(JobPostMoreDataSource::class) {
                val newInstance = INSTANCE ?: JobPostMoreDataSource(resources, context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
